package services.commentaire;

import java.net.UnknownHostException;
import java.util.Date;

import org.json.JSONObject;

import services.CodesErreur;
import services.ErrorJSON;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;

public class ListerCommentaires {
	public static JSONObject listerCommentaires(int id_utilisateur, int limite) {
		// Connexion à la base de données
		Mongo mongo = null;
		try {
			mongo = new Mongo("li328.lip6.fr", 27130);
		} catch (UnknownHostException e) {
			return ErrorJSON.serviceRefused("Hote inconnu", CodesErreur.HOTE_INCONNU);
		}
		DB db = mongo.getDB("gr2-2017-bourmaud-bizzozzero");
		DBCollection collection = db.getCollection("Commentaires");
		
		// Création du commentaire
		BasicDBObject requete = new BasicDBObject();
		requete.put("id_auteur", id_utilisateur);

		// On itère sur les résultats
		DBCursor curseur = collection.find(requete).limit(limite);
		JSONObject reponse = new JSONObject();
		while (curseur.hasNext()) {
			reponse.accumulate("comments", curseur.next());
		}

		// On renvoie une réponse
		return reponse;
	}
}
