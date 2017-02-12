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
	public static JSONObject ajouterCommentaire(String clef, String contenu) {
		if (! verificationParametres(contenu, clef)){
			return ErrorJSON.serviceRefused("L'un des parametres est null", CodesErreur.ERREUR_ARGUMENTS);
		}
		
		try {
			// On verifie qu'il est connecte
			//TODO:
			
			// On ajoute le commentaire à la BDD
			bd.tools.CommentairesTools.ajouterCommentaire(contenu, clef);
	
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
