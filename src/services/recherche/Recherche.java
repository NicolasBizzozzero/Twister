package services.recherche;

import java.net.UnknownHostException;
import java.util.Map;

import org.bson.BSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.util.JSON;

import outils.Noms;

public class Recherche {
	// N est une variable globale contenant le nombre de documents au total
	
	private final static String map = "function() {" +
	                                  "    var id = this.id_message;" +
						              "    var text = this.contenu;" +			// /!\
							          "    var words = text.match(/\\w+/g);" +
							          "    var tf = {};" +
							          "    for (var i = 0; i < words.length; i++) {" +
							          "        if (tf[words[i]] == null) {" +
							          "            tf[words[i]] = 1;" +
							          "        } else {" +
							          "            tf[words[i]] += 1;" +
							          "        }" +
							          "    }" +
							          "    for (w in tf) {" +
							          "        var ret = {};" +
							          "        ret[id] = tf[w];" +
							          "        emit(w, ret);" +
							          "    }" +
							          "}";
	
	private final static String reduce = "function(key, values) {" +
							             "    var df = values.length;" +
							             "    var res = {};" +
							             "    for (var index in values) {" +
							             "        for (var k in values[index]) {" +
							             "            res[k] = values[index][k];" +
							             "        }" +
							             "    }" +
							             "    return res;" +
							             "}";
	
	private static final String finalize = "function(k, v) {" +
								           "    var df = Object.keys(v).length;" +
								           "    for (var d in v) {" +
								           "        v[d] = v[d] * Math.log(N / df);" +
								           "    }" +
								           "    return v;" +
								           "}";

    public static JSONArray MapReduce(String query) throws UnknownHostException {
    	// On recupere les fonctions
        String mapFunction = String.format(map, query);
        String reduceFunction = reduce;
        String finalizeFunction = finalize;
        
        // On recupere les collections
        DBCollection collectionMessages = bd.tools.MessagesTools.getCollectionMessages();
        DBCollection collectionOut = bd.tools.RechercheTools.getCollectionIndexInverses();
        
        MapReduceCommand cmd = new MapReduceCommand(collectionMessages, mapFunction, reduceFunction, Noms.COLLECTION_INDEX_INVERSES, MapReduceCommand.OutputType.REPLACE, null);
        cmd.setFinalize(finalizeFunction);
        
        // On définit N (le nombre de documents) comme variable globale
        BasicDBObject n = new BasicDBObject();
        n.put("N", collectionMessages.count());
        cmd.setScope(n);
        
        // On applique le MapReduce
        MapReduceOutput out = collectionMessages.mapReduce(cmd);
        
        System.out.println(out.toString());
        System.out.println("OUTPUT FINI");
        
        for (DBObject o : out.results()) {
        	System.out.println(o.toString());
        }
        System.out.println("LISTAGE FINI");
        

        JSONArray result = new JSONArray();
        for (DBObject o : out.results()) {
            try {
                JSONObject message = new JSONObject(JSON.serialize(o));
                result.put(message);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        return result;
    }

}