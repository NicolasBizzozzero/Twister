package bd.tools;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

import bd.tools.MessagesTools;

public class CommentairesTools {
	private final static String NOM_CHAMP_ID_MESSAGE = "id_message";
	private final static String NOM_CHAMP_NOMBRE_DE_COMMENTAIRES = "nb_commentaires";
	private final static String NOM_BDD_MONGODB = "gr2-2017-bourmaud-bizzozzero";
	private final static String NOM_COLLECTION_MESSAGES = "Messages";
	private final static String NOM_COLLECTION_COMPTEURS = "Compteurs";
	private final static String NOM_CHAMP_NOM_COLLECTION = "nom_collection";
	private final static String NOM_CHAMP_MESSAGES = "messages";
	private final static String NOM_CHAMP_COMMENTAIRES = "commentaires";
	private final static String NOM_CHAMP_NOMBRE_DE_MESSAGES = "nb_messages";
	private final static String NOM_CHAMP_ID_AUTEUR = "id_auteur";
	private final static String NOM_CHAMP_ID_COMMENTAIRE = "id_commentaire";
	private final static String NOM_CHAMP_DATE = "date";
	private final static String NOM_CHAMP_CONTENU = "contenu";
	private final static String ID_DOCUMENT_COMPTEURS = "jesuisunidunique";

	
	public static JSONObject ajouterCommentaire(String id_auteur, String id_message, String contenu) throws UnknownHostException {
		// On se connecte a la BDD puis on recupere la collection
		DBCollection messages = MessagesTools.getCollectionMessages();
		
		// On recupere l'ID du commentaire a poster
		Object id_commentaire = getIDNouveauCommentaire(id_message);
		
		// Creation du commentaire
		Date date = new Date();
		BasicDBObject commentaire = new BasicDBObject();
		commentaire.put(NOM_CHAMP_ID_AUTEUR, id_auteur);
		commentaire.put(NOM_CHAMP_CONTENU, contenu);
		commentaire.put(NOM_CHAMP_DATE, date);
		commentaire.put(NOM_CHAMP_ID_COMMENTAIRE, id_commentaire);
		
		// On ajoute le commentaire
		BasicDBObject searchQuery = new BasicDBObject(NOM_CHAMP_ID_MESSAGE, id_message);
		BasicDBObject updateQuery = new BasicDBObject("$push", new BasicDBObject(NOM_CHAMP_COMMENTAIRES, commentaire));
		messages.update(searchQuery, updateQuery);
		
		// On retourne une reponse
		JSONObject reponse = new JSONObject();
		reponse.put("id", id_commentaire);
		reponse.put("auteur", id_auteur);
		reponse.put("texte", contenu);
		reponse.put("date", date);
		
		return reponse;
	}

	
	/**
	 * Retourne le prochain ID disponible pour un nouveau message, puis
	 * incremente le compteur de nombre de messages dans la collection
	 * "Messages"
	 * @return Un ID pour un nouveau message
	 * @throws UnknownHostException 
	 */
	public static Object getIDNouveauCommentaire(String id_message) throws UnknownHostException {
		DBCollection collectionMessages = MessagesTools.getCollectionMessages();
		
		DBObject doc = collectionMessages.findAndModify(
	             new BasicDBObject(NOM_CHAMP_ID_MESSAGE, id_message), null, null, false,
	             new BasicDBObject("$inc", new BasicDBObject(NOM_CHAMP_NOMBRE_DE_COMMENTAIRES, 1)),
	             true, true);

	    return (Integer) doc.get(NOM_CHAMP_NOMBRE_DE_COMMENTAIRES);
	}
}
