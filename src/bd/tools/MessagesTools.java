package bd.tools;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class MessagesTools {
	private final static String NOM_SERVEUR = "li328.lip6.fr";
	private final static int PORT_SERVEUR = 27130;
	private final static String NOM_BDD_MONGODB = "gr2-2017-bourmaud-bizzozzero";
	private final static String NOM_COLLECTION_MESSAGES = "Messages";
	private final static String NOM_COLLECTION_COMPTEURS = "Compteurs";
	private final static String NOM_CHAMP_NOM_COLLECTION = "nom_collection";
	private final static String NOM_CHAMP_MESSAGES = "messages";
	private final static String NOM_CHAMP_COMMENTAIRES = "commentaires";
	private final static String NOM_CHAMP_NOMBRE_DE_COMMENTAIRES = "nb_commentaires";
	private final static String NOM_CHAMP_NOMBRE_DE_MESSAGES = "nb_messages";
	private final static String NOM_CHAMP_ID_AUTEUR = "id_auteur";
	private final static String NOM_CHAMP_ID_MESSAGE = "id_message";
	private final static String NOM_CHAMP_ID_COMMENTAIRE = "id_commentaire";
	private final static String NOM_CHAMP_DATE = "date";
	private final static String NOM_CHAMP_CONTENU = "contenu";
	private final static String ID_DOCUMENT_COMPTEURS = "jesuisunidunique";
	
	/**
	 * Ajoute le message d'un utilisateur dans la BDD MongoDB
	 * @param clef : La clef de session de l'utilisateur ajoutant le message
	 * @param contenu : Le contenu du message a ajouter
	 * @throws UnknownHostException
	 */
	public static void ajouterMessage(String id_auteur, String contenu) throws UnknownHostException {
		// On se connecte a la BDD puis on recupere la collection
		DBCollection messages = getCollectionMessages();
		
		// On recupere l'ID du message a poster
		Object id_message = getIDNouveauMessage();
		
		// Creation du message
		BasicDBObject message = new BasicDBObject();
		message.put(NOM_CHAMP_ID_AUTEUR, id_auteur);
		message.put(NOM_CHAMP_CONTENU, contenu);
		message.put(NOM_CHAMP_DATE, new Date());
		message.put(NOM_CHAMP_ID_MESSAGE, id_message);
		message.put(NOM_CHAMP_NOMBRE_DE_COMMENTAIRES, 0);
		message.put(NOM_CHAMP_COMMENTAIRES, new ArrayList<BasicDBObject>());
		
		// On ajoute le message
		messages.insert(message);
	}
	
	
	/**
	 * Retourne le prochain ID disponible pour un nouveau message, puis
	 * incremente le compteur de nombre de messages dans la collection
	 * "Messages"
	 * @return Un ID pour un nouveau message
	 * @throws UnknownHostException 
	 */
	public static int getIDNouveauMessage() throws UnknownHostException {
		DBCollection collectionCompteurs = getCollectionCompteurs();
		
		DBObject doc = collectionCompteurs.findAndModify(
	             new BasicDBObject("_id", ID_DOCUMENT_COMPTEURS), null, null, false,
	             new BasicDBObject("$inc", new BasicDBObject(NOM_CHAMP_NOMBRE_DE_MESSAGES, 1)),
	             true, true);
	     return (Integer) doc.get(NOM_CHAMP_NOMBRE_DE_MESSAGES);
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
		DBCollection messages = getCollectionMessages();
		
		// Creation du message
		BasicDBObject message = new BasicDBObject();
		message.put(NOM_CHAMP_ID_MESSAGE, Integer.parseInt(id_message));

		// On retire le message
		messages.remove(message);
	}
	
	
	/**
	 * Supprime tous les messages d'un utilisateur precis.
	 * Utilisee lors de la suppression du compte de l'utilisateur.
	 * @param id_auteur : l'ID de l'utilisateur dont on doit supprimer les messages
	 * @throws UnknownHostException
	 */
	public static void supprimerMessagesUtilisateur(String id_auteur) throws UnknownHostException {
		//TODO: tester
		// On se connecte a la BDD puis on recupere les messages
		DBCollection messages = getCollectionMessages();
		
		// Creation de la requete
		BasicDBObject requete = new BasicDBObject();
		requete.put(NOM_CHAMP_ID_AUTEUR, id_auteur);

		// On retire les messages correspondants a la requete
		messages.remove(requete);
	}
	
	
	/**
	 * Vide entierement la collection 'Messages' de son contenu.
	 * Utilisee a des fins de debugage
	 * @throws UnknownHostException
	 */
	private static void viderMongoDB() throws UnknownHostException {
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
		//TODO: verifier si elle fonctionne
		// On se connecte a la BDD puis on recupere les messages
		DBCollection messages = getCollectionCompteurs();
		
		// Creation du message
		BasicDBObject message = new BasicDBObject();
		message.put(NOM_CHAMP_ID_MESSAGE, Integer.parseInt(id_message));

		// On verifie si le message existe
		DBCursor curseur = messages.find(message);
		return curseur.hasNext();
	}
	
	
	public static JSONObject listerMessages(String clef, String[] recherche, String id_utilisateur, int id_min, int id_max, int limite) throws UnknownHostException {
		//TODO: FAIRE CETTE FONCTION
		/*// On se connecte a la BDD puis on recupere les messages
		DBCollection messages = getCollectionMessages();
		
		// Creation de la requete
		BasicDBObject requete = new BasicDBObject();
		requete.put(NOM_CHAMP_ID_AUTEUR, id_utilisateur);

		// On itere sur les resultats
		DBCursor curseur = messages.find(requete).skip(index_debut).limit(limite);
		JSONObject reponse = new JSONObject();
		while (curseur.hasNext()) {
			reponse.accumulate(NOM_CHAMP_MESSAGES, curseur.next());
		}
				
		return reponse;
		*/
		return null;
	}
	
	
	/**
	 * Permet d'obtenir tous les messages contenus dans la collection "Messages"
	 * Utilisee seulement à des fins de debugage
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
		mongo = new Mongo(NOM_SERVEUR, PORT_SERVEUR);
		return mongo.getDB(NOM_BDD_MONGODB);
	}

	
	/**
	 * Se connecte a MongoDB puis recupere la collection "Messages" de notre base de donnees MongoDB
	 * @return La collection "Messages"
	 * @throws UnknownHostException
	 */
	public static DBCollection getCollectionMessages() throws UnknownHostException {
		return seConnecterAMongoDB().getCollection(NOM_COLLECTION_MESSAGES);
	}
	
	
	/**
	 * Se connecte a MongoDB puis recupere la collection "Compteurs" de notre base de donnees MongoDB
	 * @return La collection "Compteurs"
	 * @throws UnknownHostException
	 */
	public static DBCollection getCollectionCompteurs() throws UnknownHostException {
		return seConnecterAMongoDB().getCollection(NOM_COLLECTION_COMPTEURS);
	}
	
	
	/**
	 * Creer la collection "Messages" dans MongoDB
	 * @throws UnknownHostException 
	 */
	public static void creerCollectionMessages() throws UnknownHostException {
		DB db = seConnecterAMongoDB();
		db.getCollection(NOM_COLLECTION_MESSAGES).insert(new BasicDBObject());
	}
	
	
	/**
	 * Creer la collection "Compteurs" dans MongoDB
	 * @throws UnknownHostException 
	 */
	public static void creerCollectionCompteurs() throws UnknownHostException {
		DB db = seConnecterAMongoDB();
		BasicDBObject doc = new BasicDBObject();
		doc.put("_id", ID_DOCUMENT_COMPTEURS);
		doc.put(NOM_CHAMP_NOMBRE_DE_MESSAGES, 0);
		db.getCollection(NOM_COLLECTION_COMPTEURS).insert(doc);
	}
	
	
	/**
	 * Supprime la Collection "Messages" de la BDD MongoDB si elle existe
	 * @throws UnknownHostException
	 */
	public static void supprimerCollectionMessages() throws UnknownHostException {
		DB db = seConnecterAMongoDB();
		if (db.collectionExists(NOM_COLLECTION_MESSAGES)) {
			db.getCollection(NOM_COLLECTION_MESSAGES).drop();
		}
	}
	
	
	/**
	 * Supprime la Collection "Compteurs" de la BDD MongoDB si elle existe
	 * @throws UnknownHostException
	 */
	public static void supprimerCollectionCompteurs() throws UnknownHostException {
		DB db = seConnecterAMongoDB();
		if (db.collectionExists(NOM_COLLECTION_COMPTEURS)) {
			db.getCollection(NOM_COLLECTION_COMPTEURS).drop();
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
}
