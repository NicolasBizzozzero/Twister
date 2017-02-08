package services.commentaire;

import java.net.UnknownHostException;
import java.util.Date;

import org.json.JSONObject;

import services.CodesErreur;
import services.ErrorJSON;
import bd.UtilisateursTools;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;

import exceptions.BDException;

public class ListerCommentaires {
	public static JSONObject listerCommentaires(String id_utilisateur, int limite) {
		if (! verificationParametres(id_utilisateur)){
			return ErrorJSON.serviceRefused("L'un des parametres est null", CodesErreur.ERREUR_ARGUMENTS);
		}
		
		try {
			// On vérifie que l'utilisateur existe
			boolean isUser = UtilisateursTools.verificationExistenceId(id_utilisateur);
			if (! isUser) {
				return ErrorJSON.serviceRefused(String.format("L'utilisateur %s n'existe pas", id_utilisateur), CodesErreur.ERREUR_UTILISATEUR_INEXISTANT);
			}
	
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
		} catch (BDException e) {
			return ErrorJSON.serviceRefused("Erreur de la base de données MySQL", CodesErreur.ERREUR_SQL);
		}
	}
	
	   /**
	    * Vérification de la validité des paramètres.
	    * @return : Un boolean à true si les paramètres sont valides.
	    */
		private static boolean verificationParametres(String id_utilisateur) {
			return (id_utilisateur != null);
		}
}
