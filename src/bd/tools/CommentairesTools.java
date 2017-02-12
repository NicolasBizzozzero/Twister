package bd.tools;

import java.net.UnknownHostException;
import java.util.Date;

import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;

public class CommentairesTools {
	public static void ajouterCommentaire(String contenu, String id_auteur) throws UnknownHostException {
		// On se connecte à la BDD puis on recupere les commentaires
		DBCollection commentaires = getCollectionCommentaires();
		
		// Creation du commentaire
		BasicDBObject commentaire = new BasicDBObject();
		commentaire.put("id_auteur", id_auteur);
		commentaire.put("contenu", contenu);
		commentaire.put("date", new Date());
		
		// On ajoute le commentaire
		commentaires.insert(commentaire);
	}
	
	public static void supprimerCommentaire(String id_auteur, String contenu) throws UnknownHostException {
		// On se connecte à la BDD puis on recupere les commentaires
		DBCollection commentaires = getCollectionCommentaires();
		
		// Creation du commentaire
		BasicDBObject commentaire = new BasicDBObject();
		commentaire.put("id_auteur", id_auteur);
		commentaire.put("contenu", contenu);

		// On retire le commentaire
		commentaires.remove(commentaire);
	}


	public static boolean commentaireExistant( String id_auteur, String contenu) throws UnknownHostException {
		// On se connecte à la BDD puis on recupere les commentaires
		DBCollection commentaires = getCollectionCommentaires();
		
		// Creation du commentaire
		BasicDBObject commentaire = new BasicDBObject();
		commentaire.put("id_auteur", id_auteur);
		commentaire.put("contenu", contenu);

		// On ajoute le commentaire
		DBCursor curseur = commentaires.find(commentaire);
		return curseur.hasNext();
	
	}
	
	
	public static JSONObject listerCommentaires(String id_utilisateur, int index_debut, int limite) throws UnknownHostException {
		// On se connecte à la BDD puis on recupere les commentaires
		DBCollection commentaires = getCollectionCommentaires();
		
		// Creation du commentaire
		BasicDBObject requete = new BasicDBObject();
		requete.put("id_auteur", id_utilisateur);

		// On itere sur les resultats
		DBCursor curseur = commentaires.find(requete).skip(index_debut).limit(limite);
		JSONObject reponse = new JSONObject();
		while (curseur.hasNext()) {
			reponse.accumulate("comments", curseur.next());
		}
		
		return reponse;
	}
	
	public static JSONObject getTousLesCommentaires() throws UnknownHostException {
		// On se connecte à la BDD puis on recupere les commentaires
		DBCollection commentaires = getCollectionCommentaires();

		// On itere sur les resultats
		DBCursor curseur = commentaires.find();
		JSONObject reponse = new JSONObject();
		while (curseur.hasNext()) {
			//reponse.accumulate("comments", curseur.next());
			Object o = curseur.next();
			System.out.println(o);
		}
		
		return reponse;
	}
	
	
	/**
	 * Se connecte a MongoDB
	 * @return La base de donnees MongoDB
	 * @throws UnknownHostException
	 */
	private static DB seConnecterAMongoDB() throws UnknownHostException {
		Mongo mongo = null;
		mongo = new Mongo("li328.lip6.fr", 27130);
		return mongo.getDB("gr2-2017-bourmaud-bizzozzero");
	}

	
	/**
	 *Se connecte a MongoDB puis recupere la table "Commentaires" de notre base de donnees MongoDB
	 * @return La table "Commentaires"
	 * @throws UnknownHostException
	 */
	private static DBCollection getCollectionCommentaires() throws UnknownHostException {
		return seConnecterAMongoDB().getCollection("Commentaires");
	}
}
