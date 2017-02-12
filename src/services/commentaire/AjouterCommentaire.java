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
import com.mongodb.Mongo;

import bd.tools.UtilisateursTools;
import exceptions.BDException;


public class AjouterCommentaire {
	public static JSONObject ajouterCommentaire(String clef, String contenu) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		if (! verificationParametres(contenu, clef)){
			return ErrorJSON.serviceRefused("L'un des parametres est null", CodesErreur.ERREUR_ARGUMENTS);
		}
		
		try {
			//on verifie que la clef de connexion existe
			boolean cleExiste=bd.tools.SessionsTools.clefExiste(clef);
			if (! cleExiste){
				return ErrorJSON.serviceRefused(String.format("La session %s n'existe pas", clef), CodesErreur.ERREUR_SESSION_INEXISTANTE);
			}
			// On recupere l'identifiant de la session
			String id = bd.tools.SessionsTools.clefIdentifiant(clef);
			
			//On verifie que l'utilisateur existe
			boolean isUser = UtilisateursTools.verificationExistenceId(id);
			if (! isUser) {
				return ErrorJSON.serviceRefused(String.format("L'utilisateur %s n'existe pas", id), CodesErreur.ERREUR_UTILISATEUR_INEXISTANT);
			}
			
			// On ajoute le commentaire à la BDD
			bd.tools.CommentairesTools.ajouterCommentaire(contenu, id);
	
			// On renvoie une reponse
			JSONObject reponse = new JSONObject();
			return reponse;
		} catch (UnknownHostException e) {
			return ErrorJSON.serviceRefused("Hote inconnu", CodesErreur.HOTE_INCONNU);
		}
	}
	
   /**
    * Verification de la validite des parametres
    * @return : Un booleen a true si les paramatres sont valides.
    */
	private static boolean verificationParametres(String contenu, String clef) {
		return (contenu != null && clef != null);
	}
}
