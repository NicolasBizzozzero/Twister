package services.recherche;

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

//    public static JSONArray MapReduce(String query) {
//
//        String map =
//                "function() {" +
//                        "  var text = \"" + query + "\";" +
//                        "  var id = this.id;" +
//                        "  var words = text.match(/\\w+/g);" +
//                        "  var tf = {};" +
//                        "  for (var i = 0; i < words.length; i++) {" +
//                        "    if (tf[words[i]] == null) {" +
//                        "      tf[words[i]] = 1;" +
//                        "    } else {" +
//                        "      tf[words[i]] += 1;" +
//                        "    }" +
//                        "  }" +
//                        "  for (w in tf) {" +
//                        "    var ret = {};" +
//                        "    ret[id] = tf[w];" +
//                        "    emit(w, ret);" +
//                        "  }" +
//                        "}";
//
//        String reduce =
//                "function(k, v) {" +
//                        "  v = v.split(\",\")" +
//                        "  var df = v.lenth;" +
//                        "  var ret = [];" +
//                        "  for (value in v) {" +
//                        "    for (k in v[value]) {" +
//                        "      ret[k] = v[value][k];" +
//                        "    }" +
//                        "  }" +
//                        "  return ret.toString();" +
//                        "}";
//
//        String finalize =
//                "function(k, v) {" +
//                        "  var df = v.length;" +
//                        "  for (d in v) {" +
//                        "    v[d] = v[d] * Math.log(N/parseFloat(df));" +
//                        "  }" +
//                        "  return v;" +
//                        "}";
//
//		/*
//		String map =
//				"function() {" +
//				"  var text = \"" + query + "\";" +
//				"  var id = this.id;" +
//				"  var words = text.match(/\\w+/g);" +
//				"  var tf = {};" +
//				"  for (var i = 0; i < words.length; i++) {" +
//				"    if (tf[words[i]] == null) {" +
//				"      tf[words[i]] = 1;" +
//				"    } else {" +
//				"      tf[words[i]] += 1;" +
//				"    }" +
//				"  }" +
//				"  for (w in tf) {" +
//				"    emit(w, 1);" +
//				"  }" +
//				"}";
//		*/
//        String _reduce =
//                "function(k, v) {" +
//                        "  db.result.save(v);" +
//                        "  return null;" +
//                        "}";
//
//        DBCollection collection = BD.Database.getMongoCollection("messages");
//
//        MapReduceCommand cmd = new MapReduceCommand(collection, map, reduce, "out", MapReduceCommand.OutputType.REPLACE, null);
//        cmd.setFinalize(finalize);
//        BasicDBObject obj = new BasicDBObject();
//        obj.put("N", collection.count());
//        cmd.setScope(obj);
//
//        MapReduceOutput out = collection.mapReduce(cmd);
//		/*
//		DBCollection new_coll = BD.Database.getMongoCollection("out");
//        DBCursor cursor = new_coll.find();
//        
//        JSONArray result = new JSONArray();
//        while (cursor.hasNext()) {
//        	DBObject object = cursor.next();
//        	try {
//				JSONObject message = new JSONObject(JSON.serialize(object));
//				result.put(object);
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//        }
//        */
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