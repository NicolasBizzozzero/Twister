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

public class Recherche {
//	private final static String map = "function() {" +
//						              "    var text = \"%s\";" +
//							          "    var words = text.match(/\\w+/g);" +
//							          "    var tf = {};" +
//							          "    for (var i = 0; i < words.length; i++) {" +
//							          "        if (tf[words[i]] == null) {" +
//							          "            tf[words[i]] = 1;" +
//							          "        } else {" +
//							          "            tf[words[i]] += 1;" +
//							          "        }" +
//							          "    }" +
//							          "    for (w in tf) {" +
//							          "        emit(w, 1);" +
//							          "    }" +
//							          "}";
//	
//	private final static String reduce = "function(k, v) {" +
//							             "    v = v.split(\",\")" +
//							             "    var df = v.lenth;" +
//							             "    var ret = [];" +
//							             "    for (value in v) {" +
//							             "        for (k in v[value]) {" +
//							             "            ret[k] = v[value][k];" +
//							             "        }" +
//							             "    }" +
//							             "    return ret.toString();" +
//							             "}";
//	
//	private static final String finalize = "function(k, v) {" +
//								           "    var df = v.length;" +
//								           "    for (d in v) {" +
//								           "        v[d] = v[d] * Math.log(N/parseFloat(df));" +
//								           "    }" +
//								           "    return v;" +
//								           "}";
//
//    public static JSONArray MapReduce(String query) throws UnknownHostException {
//
//        String map_function = String.format(map, query);
//        String reduce_function = reduce;
//        String finalize_function = finalize;
//
//        DBCollection collection = bd.tools.MessagesTools.getCollectionMessages();
//
//        MapReduceCommand cmd = new MapReduceCommand(collection, map, reduce, "out", MapReduceCommand.OutputType.REPLACE, null);
//        cmd.setFinalize(finalize);
//        BasicDBObject obj = new BasicDBObject();
//        obj.put("N", collection.count());
//        cmd.setScope(obj);
//
//        MapReduceOutput out = collection.mapReduce(cmd);
//        JSONArray result = new JSONArray();
//        for (DBObject o : out.results()) {
//            try {
//                JSONObject message = new JSONObject(JSON.serialize(o));
//                result.put(message);
//            } catch (JSONException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//        return result;
//    }
}