package bd.tools;

import java.net.UnknownHostException;

import org.json.JSONObject;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import bd.tools.MessagesTools;

import outils.Noms;

public abstract class RechercheTools {
    private DBCollection D;


//    /**
//     * Calcule le Relevant Score Value de la requête 'q' sur le document 'd'.
//     */
//    private int RSV(DBObject d, String[] q) {
//
//    }
//
//
//    /**
//     * Calcule le poid du mot 'w' dans le document 'd'.
//     */
//    private float TF_IDF(String w, DBObject d) {
//        return TF(w, d) * IDF(w);
//    }
//
//
//    /**
//     * Calcule le Term Frequency du mot 'w' dans le document 'd'.
//     */
//    private int TF(String w, DBObject d) {
//
//    }
//
//
//    /**
//     * Calcule le Inverse Document Frequency du mot 'w'.
//     */
//    private float IDF(String w) {
//
//    }
    
	/**
	 * Se connecte a MongoDB puis recupere la collection "Index_inverses" de notre base de donnees MongoDB
	 * @return La collection "Index_inverses"
	 * @throws UnknownHostException
	 */
	public static DBCollection getCollectionIndexInverses() throws UnknownHostException {
		return MessagesTools.seConnecterAMongoDB().getCollection(Noms.COLLECTION_INDEX_INVERSES);
	}
	
	
	/**
	 * Creer la collection "Index_inverses" dans MongoDB
	 * @throws UnknownHostException 
	 */
	public static void creerCollectionIndexInverses() throws UnknownHostException {
		MessagesTools.seConnecterAMongoDB().getCollection(Noms.COLLECTION_INDEX_INVERSES);
	}
	
	
	/**
	 * Supprime la Collection "Index_inverses" de la BDD MongoDB si elle existe
	 * @throws UnknownHostException
	 */
	public static void supprimerCollectionIndexInverses() throws UnknownHostException {
		DB db = MessagesTools.seConnecterAMongoDB();
		if (db.collectionExists(Noms.COLLECTION_INDEX_INVERSES)) {
			db.getCollection(Noms.COLLECTION_INDEX_INVERSES).drop();
		}
	}
	
	
	/**
	 * Permet d'obtenir tous les index contenus dans la collection "Index_inverses"
	 * Utilisee seulement a des fins de debugage
	 * @return Un JSONObject contenant tous les index inverses
	 * @throws UnknownHostException
	 */
	public static JSONObject getTousLesIndexInverses() throws UnknownHostException {
		// On se connecte a la BDD puis on recupere les index inverses
		DBCollection messages = getCollectionIndexInverses();

		// On itere sur les resultats
		DBCursor curseur = messages.find();
		JSONObject reponse = new JSONObject();
		while (curseur.hasNext()) {
			reponse.accumulate("Index_inverses", curseur.next());
		}
	
		return reponse;
	}
}