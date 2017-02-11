package services.commentaire;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Date;

import org.json.JSONObject;

import services.CodesErreur;
import services.ErrorJSON;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;

import bd.tools.UtilisateursTools;
import exceptions.BDException;

public class ListerCommentaires {
	public static JSONObject listerCommentaires(String id_utilisateur, int index_debut, int limite) {
		if (! verificationParametres(id_utilisateur)){
			return ErrorJSON.serviceRefused("L'un des parametres est null", CodesErreur.ERREUR_ARGUMENTS);
		}
		
		try {
			// On verifie que l'utilisateur existe
			boolean isUser = UtilisateursTools.verificationExistenceId(id_utilisateur);
			if (! isUser) {
				return ErrorJSON.serviceRefused(String.format("L'utilisateur %s n'existe pas", id_utilisateur), CodesErreur.ERREUR_UTILISATEUR_INEXISTANT);
			}
	
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
	
			// On renvoie une reponse
			return reponse;
		}  catch (InstantiationException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donnees MySQL (InstantiationException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
		} catch (IllegalAccessException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donnees MySQL (IllegalAccessException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
		} catch (ClassNotFoundException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donnees MySQL (ClassNotFoundException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
		} catch (SQLException e) {
			return ErrorJSON.serviceRefused("Erreur, requete SQL Incorrecte", CodesErreur.ERREUR_SQL);
		}
	}
	
   /**
    * Verification de la validite des parametres
    * @return : Un booleen a true si les paramatres sont valides.
    */
	private static boolean verificationParametres(String id_utilisateur) {
		return (id_utilisateur != null);
	}
}
