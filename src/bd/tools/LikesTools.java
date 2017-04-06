package bd.tools;

import java.net.UnknownHostException;
import java.util.ArrayList;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

public class LikesTools {
	public static final int NOMBRE_LIKE_DIFFERENTS = 5;
	
	
	/**
	 * Retourne un JSONObject contenant associant un type de like avec
	 * le nombre de likes pour un message.
	 * @param id_message : l'ID du message sur lequel on va compter les likes
	 * @return Un JSONObject de la forme :
	 * 			{"0": NOMBRE_LIKES_JE_DETESTE,
	 *           "1": NOMBRE_LIKES_J_AIME_PAS,
	 *           "2": NOMBRE_LIKES_BOF,
	 *           "3": NOMBRE_LIKES_J_AIME,
	 *           "4": NOMBRE_LIKES_J_ADORE}
	 * @throws UnknownHostException
	 */
	public static JSONObject compteTypeLikes(String id_message) throws UnknownHostException {
		// Connection a la BDD et recuperation de la collection
		DBCollection collectionMessages = MessagesTools.getCollectionMessages();
		
		// Recuperation du message
		BasicDBObject query = new BasicDBObject();
		query.put(Nom.CHAMP_ID_MESSAGE, Integer.parseInt(id_message));
		DBObject message = collectionMessages.find(query).next();
		
		// Recuperation des likes
		DBObject likes = (DBObject) message.get(Nom.CHAMP_LIKES);
		JSONObject reponse = new JSONObject();
		
		// Modification de la map des likes en map des nombre de likes
		for (Integer indexLike=0; indexLike < NOMBRE_LIKE_DIFFERENTS; indexLike++) {
			@SuppressWarnings("unchecked")
			ArrayList<DBObject> listeLikes = (ArrayList<DBObject>) likes.get(indexLike.toString());
			int nombreDeLikes = listeLikes.size();
			reponse.put(indexLike.toString(), nombreDeLikes);
		}
		
		return reponse;
	}


	/**
	 * Verifie si un identifiant de type de like donne existe.
	 * @param type_like : Le type du like a verifier
	 * @return Un boolean correspondant a l'existence de ce type de like
	 */
	public static boolean likeExiste(String type_like) {
		for (Integer index_like=0; index_like < NOMBRE_LIKE_DIFFERENTS; index_like++) {
			if (type_like.equals(index_like.toString())) {
				return true;
			}
		}
		
		return false;
	}


	/**
	 * Ajoute un like a un message.
	 * Cela revient a mettre l'ID de l'utilisateur qui like dans la
	 * liste associee a la clef correspondant au type de like.
	 * @param id_likeur : L'ID de la personne qui like
	 * @param id_message : L'ID du message like
	 * @param type_like : Le type du like depose
	 * @return un JSONObject vide
	 * @throws UnknownHostException
	 */
	public static void ajouterLike(String id_likeur, String id_message, String type_like) throws UnknownHostException {
		// On se connecte a la BDD puis on recupere la collection
		DBCollection messages = MessagesTools.getCollectionMessages();
		
		// On ajoute le like
		BasicDBObject searchQuery = new BasicDBObject(Nom.CHAMP_ID_MESSAGE, Integer.parseInt(id_message));
		BasicDBObject updateQuery = new BasicDBObject("$push", new BasicDBObject(String.format("%s.%s", Nom.CHAMP_LIKES, type_like), id_likeur));
		messages.update(searchQuery, updateQuery);
	}
	
	
	/**
	 * Supprime un like d'un message.
	 * Cela revient a retirer l'ID de l'utilisateur qui avait like dans la
	 * liste associee a la clef correspondant au type de like.
	 * @param id_delikeur : L'ID de la personne qui delike
	 * @param id_message : L'ID du message delike
	 * @param type_like : Le type du like retire
	 * @return un JSONObject vide
	 * @throws UnknownHostException
	 */
	public static void supprimerLike(String id_delikeur, String id_message, String type_like) throws UnknownHostException {
		// On se connecte a la BDD puis on recupere la collection
		DBCollection messages = MessagesTools.getCollectionMessages();
		
		// On supprime le like
		BasicDBObject searchQuery = new BasicDBObject(Nom.CHAMP_ID_MESSAGE, Integer.parseInt(id_message));
		BasicDBObject updateQuery = new BasicDBObject("$pull", new BasicDBObject(String.format("%s.%s", Nom.CHAMP_LIKES, type_like), id_delikeur));
		messages.update(searchQuery, updateQuery);
	}


	/**
	 * Liste les likes presents dans un message.
	 * @param id_message : L'ID du message dont on veut lister
	 * les likes.
	 * @return Un JSONObject contenant les likes du message.
	 * @throws UnknownHostException
	 */
	public static JSONObject listerLikes(String id_message) throws UnknownHostException {
		// On se connecte a la BDD puis on recupere les messages
		DBCollection messages = bd.tools.MessagesTools.getCollectionMessages();
		
		// Creation du message
		BasicDBObject message = new BasicDBObject();
		message.put(Nom.CHAMP_ID_MESSAGE, Integer.parseInt(id_message));

		// On verifie si le message existe
		DBCursor curseur = messages.find(message);
		if (! curseur.hasNext()) {
			// Le message n'existe pas, donc le commentaire non plus
			return new JSONObject();
		}
		
		// On recupere le JSONObject des likes
		JSONObject reponse_message = new JSONObject(JSON.serialize(curseur.next()));
		JSONObject likes = reponse_message.getJSONObject(Nom.CHAMP_LIKES);
		
		// On retourne ce JSONObject des likes
		return likes;
	}
	
	
	
	public static boolean aDejaLike(String id_utilisateur, String id_message, String type_like) {
		//TODO: Faire cette fonction
		return false;
	}
}
