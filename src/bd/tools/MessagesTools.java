package bd.tools;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

import outils.Noms;

public class MessagesTools {
	
	
	/**
	 * Ajoute le message d'un utilisateur dans la BDD MongoDB
	 * @param clef : La clef de session de l'utilisateur ajoutant le message
	 * @param contenu : Le contenu du message a ajouter
	 * @throws UnknownHostException
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static JSONObject ajouterMessage(String id_auteur, String contenu) throws UnknownHostException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		// On se connecte a la BDD puis on recupere la collection
		DBCollection messages = getCollectionMessages();
		
		// On recupere l'ID du message a poster
		Integer id_message = getIDNouveauMessage();
		
		// Creation du message
		String date = new Date().toString();
		BasicDBObject message = new BasicDBObject();
		message.put(Noms.CHAMP_CONTENU, contenu);
		message.put(Noms.CHAMP_DATE, date);
		message.put(Noms.CHAMP_ID_MESSAGE, id_message);
		message.put(Noms.CHAMP_NOMBRE_DE_COMMENTAIRES, 0);
		message.put(Noms.CHAMP_COMMENTAIRES, new ArrayList<BasicDBObject>());
		// Ajout de l'auteur
		BasicDBObject auteur = new BasicDBObject();
		auteur.put(Noms.CHAMP_ID_AUTEUR, id_auteur);
		auteur.put(Noms.CHAMP_PSEUDO_AUTEUR, bd.tools.UtilisateursTools.getPseudoUtilisateur(id_auteur));
		message.put(Noms.CHAMP_AUTEUR, auteur);
		// Ajout des likes
		BasicDBObject likes = new BasicDBObject();
		for (Integer indexLike=0; indexLike < LikesTools.NOMBRE_LIKE_DIFFERENTS; indexLike++) {
			likes.put(indexLike.toString(), new ArrayList<BasicDBObject>());
		}
		message.put(Noms.CHAMP_LIKES, likes);

		// On ajoute le message dans la collection
		messages.insert(message);
		
		
		// On renvoie une reponse		
		JSONObject reponse = new JSONObject();		
		reponse.put(Noms.CHAMP_CONTENU, contenu);		
		reponse.put(Noms.CHAMP_DATE, date);		
		reponse.put(Noms.CHAMP_ID_MESSAGE, id_message);		
		reponse.put(Noms.CHAMP_NOMBRE_DE_COMMENTAIRES, 0);		
		reponse.put(Noms.CHAMP_COMMENTAIRES, new JSONArray());		
		JSONObject auteur_rep = new JSONObject();		
		auteur_rep.put(Noms.CHAMP_ID_AUTEUR, id_auteur);		
		auteur_rep.put(Noms.CHAMP_PSEUDO_AUTEUR, bd.tools.UtilisateursTools.getPseudoUtilisateur(id_auteur));		
		reponse.put(Noms.CHAMP_AUTEUR, auteur_rep);		
		JSONObject likes_rep = new JSONObject();		
		for (Integer indexLike=0; indexLike < LikesTools.NOMBRE_LIKE_DIFFERENTS; indexLike++) {		
			likes_rep.put(indexLike.toString(), new JSONArray());		
		}		
		reponse.put(Noms.CHAMP_LIKES, likes_rep);		
				
		return reponse;
	}
	
	
	/**
	 * Retourne le prochain ID disponible pour un nouveau message, puis
	 * incremente le compteur de nombre de messages dans la collection
	 * "Messages"
	 * @return Un ID pour un nouveau message
	 * @throws UnknownHostException 
	 */
	public static Integer getIDNouveauMessage() throws UnknownHostException {
		DBCollection collectionCompteurs = getCollectionCompteurs();
		
		DBObject doc = collectionCompteurs.findAndModify(
	             new BasicDBObject("_id", Noms.ID_DOCUMENT_COMPTEURS), null, null, false,
	             new BasicDBObject("$inc", new BasicDBObject(Noms.CHAMP_NOMBRE_DE_MESSAGES, 1)),
	             true, true);
	     return (Integer) doc.get(Noms.CHAMP_NOMBRE_DE_MESSAGES);
	}
	
	
	/**
	 * Supprime le message de la BDD
	 * @param clef : La clef de session de l'utilisateur
	 * @param id_message : L'ID du message a supprimer
	 * @throws UnknownHostException
	 */
	public static void supprimerMessage(String clef, String id_message) throws UnknownHostException {
		//TODO: Ajouter plus de verifications (utilisateur bien l'auteur du message, message existant)
		// On se connecte a la BDD puis on recupere les messages
		DBCollection collectionMessages = getCollectionMessages();
		
		// Creation du message
		BasicDBObject query = new BasicDBObject();
		query.put(Noms.CHAMP_ID_MESSAGE, Integer.parseInt(id_message));

		// On retire le message
		collectionMessages.remove(query);
	}
	
	
	/**
	 * Supprime tous les messages d'un utilisateur precis.
	 * Utilisee lors de la suppression du compte de l'utilisateur.
	 * @param id_auteur : l'ID de l'utilisateur dont on doit supprimer les messages
	 * @throws UnknownHostException
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static void supprimerMessagesUtilisateur(String id_auteur) throws UnknownHostException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		// On se connecte a la BDD puis on recupere les messages
		DBCollection messages = getCollectionMessages();
		
		// Creation de la requete
		BasicDBObject requete = new BasicDBObject();
		BasicDBObject auteur = new BasicDBObject();
		auteur.put(Noms.CHAMP_ID_AUTEUR, id_auteur);
		auteur.put(Noms.CHAMP_PSEUDO_AUTEUR, bd.tools.UtilisateursTools.getPseudoUtilisateur(id_auteur));
		requete.put(Noms.CHAMP_AUTEUR, auteur);

		// On retire les messages correspondants a la requete
		messages.remove(requete);
	}
	
	
	/**
	 * Vide entierement la collection 'Messages' de son contenu.
	 * Utilisee a des fins de debugage
	 * @throws UnknownHostException
	 */
	public static void viderMongoDB() throws UnknownHostException {
		// On se connecte a la BDD puis on recupere les messages
		DBCollection messages = getCollectionMessages();

		// On retire tous les messages
		messages.remove(new BasicDBObject());
	}


	/**
	 * Verifie si un message existe dans la BDD
	 * @param id_message : l'ID du message dont l'existence doit etre verifiee
	 * @return Un booleen correspondant a l'existence du message.
	 * @throws UnknownHostException
	 */
	public static boolean messageExistant(String id_message) throws UnknownHostException {
		// On se connecte a la BDD puis on recupere les messages
		DBCollection messages = getCollectionMessages();
		
		// Creation du message
		BasicDBObject message = new BasicDBObject();
		message.put(Noms.CHAMP_ID_MESSAGE, Integer.parseInt(id_message));

		// On verifie si le message existe
		DBCursor curseur = messages.find(message);
		return curseur.hasNext();
	}
	
	
	public static JSONObject listerMessagesUtilisateur(String id_utilisateur, String recherche, String id_max, String id_min, String limite) throws UnknownHostException {
  		// On se connecte a la BDD puis on recupere les messages
  		DBCollection messages = getCollectionMessages();
		  		
  		// Creation de la requete
  		BasicDBObject requete = new BasicDBObject();
 		ArrayList<BasicDBObject> listeAnd = new ArrayList<BasicDBObject>();
		if (! id_min.equals("-1")) {
			listeAnd.add(new BasicDBObject(Noms.CHAMP_ID_MESSAGE, new BasicDBObject("$gt", Integer.parseInt(id_min))));
		}
		if (! id_max.equals("-1")) {
			listeAnd.add(new BasicDBObject(Noms.CHAMP_ID_MESSAGE, new BasicDBObject("$lt", Integer.parseInt(id_max))));
		}
		if (listeAnd.size() != 0) {
			requete.put("$and", listeAnd);
		}
		requete.put(String.format("%s.%s", Noms.CHAMP_AUTEUR, Noms.CHAMP_ID_AUTEUR), id_utilisateur);
		System.out.println(requete.toString());
		
  		// On itere sur les resultats
  		JSONObject reponse = new JSONObject();
 		reponse.put(Noms.CHAMP_MESSAGES, new JSONArray());
 		DBCursor curseur = messages.find(requete).sort(new BasicDBObject(Noms.CHAMP_ID_MESSAGE, -1))
                                                 .limit(Integer.parseInt(limite));
  		while (curseur.hasNext()) {
  			reponse.accumulate(Noms.CHAMP_MESSAGES, curseur.next());
  		}
  		return reponse;
  	}
	
	
	public static JSONObject listerMessagesToutLeMonde(String id_utilisateur, String recherche, String id_max, String id_min, String limite) throws UnknownHostException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
  		// On se connecte a la BDD puis on recupere les messages
  		DBCollection messages = getCollectionMessages();
  		
  		// On recupere les id des amis de l'utilisateur
		ArrayList<String> id_amis = bd.tools.AmitiesTools.getAmisEnArrayList(id_utilisateur);
		id_amis.add(id_utilisateur);
  		
  		// Creation de la requete
  		BasicDBObject requete = new BasicDBObject();
 		ArrayList<BasicDBObject> listeAnd = new ArrayList<BasicDBObject>();
		if (! id_min.equals("-1")) {
			listeAnd.add(new BasicDBObject(Noms.CHAMP_ID_MESSAGE, new BasicDBObject("$gt", Integer.parseInt(id_min))));
		}
		if (! id_max.equals("-1")) {
			listeAnd.add(new BasicDBObject(Noms.CHAMP_ID_MESSAGE, new BasicDBObject("$lt", Integer.parseInt(id_max))));
		}
		if (listeAnd.size() != 0) {
			requete.put("$and", listeAnd);
 		}
		requete.put(String.format("%s.%s", Noms.CHAMP_AUTEUR, Noms.CHAMP_ID_AUTEUR), new BasicDBObject("$in", id_amis));
		System.out.println(requete.toString());
  
  		// On itere sur les resultats
  		JSONObject reponse = new JSONObject();
		reponse.put(Noms.CHAMP_MESSAGES, new JSONArray());
		DBCursor curseur = messages.find(requete).sort(new BasicDBObject(Noms.CHAMP_ID_MESSAGE, -1))
                                                 .limit(Integer.parseInt(limite));
  		while (curseur.hasNext()) {
  			reponse.accumulate(Noms.CHAMP_MESSAGES, curseur.next());
  		}
 				
 		return reponse;
 	}
	
	
	/**
	 * Permet d'obtenir tous les messages contenus dans la collection "Messages"
	 * Utilisee seulement a des fins de debugage
	 * @return Un JSONObject contenant tous les messages
	 * @throws UnknownHostException
	 */
	public static JSONObject getTousLesMessages() throws UnknownHostException {
		// On se connecte a la BDD puis on recupere les messages
		DBCollection messages = getCollectionMessages();

		// On itere sur les resultats
		DBCursor curseur = messages.find();
		JSONObject reponse = new JSONObject();
		while (curseur.hasNext()) {
			reponse.accumulate("Messages", curseur.next());
		}
	
		return reponse;
	}
	
	
	/**
	 * Retourne le nombre de messages postes depuis le debut
	 * @return Le nombre de messages contenus dans la BDD MongoDB
	 * @throws UnknownHostException
	 */
	public static int getNombreDeMessages() throws UnknownHostException {
		DBCollection collectionCompteurs = getCollectionCompteurs();
		
		DBObject doc = collectionCompteurs.findOne(new BasicDBObject("_id", Noms.ID_DOCUMENT_COMPTEURS));
		return (Integer) doc.get(Noms.CHAMP_NOMBRE_DE_MESSAGES);
	}
	

	/**
	 * Permet d'obtenir tous les messages contenus dans la collection "Compteurs"
	 * Utilisee seulement à des fins de debugage
	 * @return Un JSONObject contenant tous les messages
	 * @throws UnknownHostException
	 */
	public static JSONObject getTousLesCompteurs() throws UnknownHostException {
		// On se connecte a la BDD puis on recupere les messages
		DBCollection messages = getCollectionCompteurs();

		// On itere sur les resultats
		DBCursor curseur = messages.find();
		JSONObject reponse = new JSONObject();
		while (curseur.hasNext()) {
			reponse.accumulate("Compteurs", curseur.next());
		}
	
		return reponse;
	}
	
	
	/**
	 * Se connecte a MongoDB
	 * @return La base de donnees MongoDB
	 * @throws UnknownHostException
	 */
	public static DB seConnecterAMongoDB() throws UnknownHostException {
		Mongo mongo = null;
		mongo = new Mongo(Noms.SERVEUR, Noms.PORT_SERVEUR);
		return mongo.getDB(Noms.BDD_MONGODB);
	}

	
	/**
	 * Se connecte a MongoDB puis recupere la collection "Messages" de notre base de donnees MongoDB
	 * @return La collection "Messages"
	 * @throws UnknownHostException
	 */
	public static DBCollection getCollectionMessages() throws UnknownHostException {
		return seConnecterAMongoDB().getCollection(Noms.COLLECTION_MESSAGES);
	}
	
	
	/**
	 * Se connecte a MongoDB puis recupere la collection "Compteurs" de notre base de donnees MongoDB
	 * @return La collection "Compteurs"
	 * @throws UnknownHostException
	 */
	public static DBCollection getCollectionCompteurs() throws UnknownHostException {
		return seConnecterAMongoDB().getCollection(Noms.COLLECTION_COMPTEURS);
	}
	
	
	/**
	 * Creer la collection "Messages" dans MongoDB
	 * @throws UnknownHostException 
	 */
	public static void creerCollectionMessages() throws UnknownHostException {
		seConnecterAMongoDB().getCollection(Noms.COLLECTION_MESSAGES);
	}
	
	
	/**
	 * Creer la collection "Compteurs" dans MongoDB
	 * @throws UnknownHostException 
	 */
	public static void creerCollectionCompteurs() throws UnknownHostException {
		DB db = seConnecterAMongoDB();
		BasicDBObject doc = new BasicDBObject();
		doc.put("_id", Noms.ID_DOCUMENT_COMPTEURS);
		doc.put(Noms.CHAMP_NOMBRE_DE_MESSAGES, 0);
		db.getCollection(Noms.COLLECTION_COMPTEURS).insert(doc);
	}
	
	
	/**
	 * Supprime la Collection "Messages" de la BDD MongoDB si elle existe
	 * @throws UnknownHostException
	 */
	public static void supprimerCollectionMessages() throws UnknownHostException {
		DB db = seConnecterAMongoDB();
		if (db.collectionExists(Noms.COLLECTION_MESSAGES)) {
			db.getCollection(Noms.COLLECTION_MESSAGES).drop();
		}
	}
	
	
	/**
	 * Supprime la Collection "Compteurs" de la BDD MongoDB si elle existe
	 * @throws UnknownHostException
	 */
	public static void supprimerCollectionCompteurs() throws UnknownHostException {
		DB db = seConnecterAMongoDB();
		if (db.collectionExists(Noms.COLLECTION_COMPTEURS)) {
			db.getCollection(Noms.COLLECTION_COMPTEURS).drop();
		}
	}
	
	
	/**
	 * Print dans la sortie standard le nom de chaque collection dans notre BDD MongoDB
	 * @throws UnknownHostException
	 */
	public static void printNomsCollections() throws UnknownHostException {
		DB db = seConnecterAMongoDB();
		Set<String > collections = db.getCollectionNames();
		for (String nom : collections) {
			System.out.println(nom);
		}
	}
	
	
	public static int getNombreDeMessagesPostes(String idUtilisateur) throws UnknownHostException {
  		// On se connecte a la BDD puis on recupere les messages
  		DBCollection messages = getCollectionMessages();
		  		
  		// Creation de la requete
  		BasicDBObject requete = new BasicDBObject();
		requete.put(String.format("%s.%s", Noms.CHAMP_AUTEUR, Noms.CHAMP_ID_AUTEUR), idUtilisateur);
		
  		// On itere sur les resultats
  		JSONObject reponse = new JSONObject();
 		reponse.put(Noms.CHAMP_MESSAGES, new JSONArray());
 		DBCursor curseur = messages.find(requete);
 		
 		return curseur.count();
	}


	public static String getIDAuteur(String id_message) throws UnknownHostException {
		// On se connecte a la BDD puis on recupere les messages
  		DBCollection messages = getCollectionMessages();
		  		
  		// Creation de la requete
  		BasicDBObject requete = new BasicDBObject();
		requete.put(Noms.CHAMP_ID_MESSAGE, Integer.parseInt(id_message));
		
  		// On recupere le resultat de la requete);
 		DBCursor curseur = messages.find(requete);
 		DBObject resultat = curseur.next();
 		
 		// On retourne l'ID de l'auteur
  		DBObject auteur =  (DBObject) resultat.get(Noms.CHAMP_AUTEUR);
  		return (String) auteur.get(Noms.CHAMP_ID_AUTEUR);
	}
}
