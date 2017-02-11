package bd.tools;

import java.net.UnknownHostException;
import java.util.Date;

import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;

import services.CodesErreur;
import services.ErrorJSON;

public class CommentairesTools {
	public static void ajouterCommentaire(String contenu, String id_auteur, String pseudo) throws UnknownHostException {
		// Connexion a la base de donnees
		Mongo mongo = null;
		mongo = new Mongo("li328.lip6.fr", 27130);
		DB db = mongo.getDB("gr2-2017-bourmaud-bizzozzero");
		DBCollection collection = db.getCollection("Commentaires");
		
		// Creation du commentaire
		BasicDBObject commentaire = new BasicDBObject();
		commentaire.put("id_auteur", id_auteur);
		commentaire.put("contenu", contenu);
		commentaire.put("pseudo", pseudo);
		commentaire.put("date", new Date());
		
		// On ajoute le commentaire
		collection.insert(commentaire);
	}
	
	public static void supprimerCommentaire(String id_auteur,String contenu) throws UnknownHostException {
		// Connexion a la base de donnees
		Mongo mongo = null;
		mongo = new Mongo("li328.lip6.fr", 27130);
		DB db = mongo.getDB("gr2-2017-bourmaud-bizzozzero");
		DBCollection collection = db.getCollection("Commentaires");
		
		// Creation du commentaire
		BasicDBObject commentaire = new BasicDBObject();
		commentaire.put("id_auteur", id_auteur);
		commentaire.put("contenu", contenu);

		
		// On ajoute le commentaire
		collection.remove(commentaire);
	}
	public static boolean commentaireExistant( String id_auteur,String contenu) throws UnknownHostException {
		// Connexion a la base de donnees
		Mongo mongo = null;
		mongo = new Mongo("li328.lip6.fr", 27130);
		DB db = mongo.getDB("gr2-2017-bourmaud-bizzozzero");
		DBCollection collection = db.getCollection("Commentaires");
		
		// Creation du commentaire
		BasicDBObject commentaire = new BasicDBObject();
		commentaire.put("id_auteur", id_auteur);
		commentaire.put("contenu", contenu);

		// On ajoute le commentaire
		DBCursor curseur = collection.find(commentaire);
		return curseur.hasNext();
	
	}
	
	
	public static JSONObject listerCommentaires(String id_utilisateur, int index_debut, int limite) {
		// Connexion a la base de donnees
		Mongo mongo = null;
		try {
			mongo = new Mongo("li328.lip6.fr", 27130);
		} catch (UnknownHostException e) {
			return ErrorJSON.serviceRefused("Hote inconnu", CodesErreur.HOTE_INCONNU);
		}
		DB db = mongo.getDB("gr2-2017-bourmaud-bizzozzero");
		DBCollection collection = db.getCollection("Commentaires");
		
		// Creation du commentaire
		BasicDBObject requete = new BasicDBObject();
		requete.put("id_auteur", id_utilisateur);

		// On itere sur les resultats
		DBCursor curseur = collection.find(requete).skip(index_debut).limit(limite);
		JSONObject reponse = new JSONObject();
		while (curseur.hasNext()) {
			reponse.accumulate("comments", curseur.next());
		}
		
		return reponse;
	}
}
