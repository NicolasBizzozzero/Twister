package services;

import java.net.UnknownHostException;

import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;

import services.CodesErreur;
import services.ErrorJSON;

public class TestMongoDB {

	public static void main(String[] args) {
		System.out.println("Debut du test");
		JSONObject resultat = null;
		try {
			resultat = bd.tools.CommentairesTools.getTousLesCommentaires();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(resultat.toString(4));
		System.out.println("Fin du test");
	}

}
