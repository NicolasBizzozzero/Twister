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
	public static JSONObject ajouterCommentaire(String contenu, String id_auteur, String pseudo) {
		if (! verificationParametres(contenu, id_auteur, pseudo)){
			return ErrorJSON.serviceRefused("L'un des parametres est null", CodesErreur.ERREUR_ARGUMENTS);
		}
		
		try {
			// On verifie que l'utilisateur existe
			boolean isUser = UtilisateursTools.verificationExistenceId(id_auteur);
			if (! isUser) {
				return ErrorJSON.serviceRefused(String.format("L'utilisateur %s n'existe pas", id_auteur), CodesErreur.ERREUR_UTILISATEUR_INEXISTANT);
			}
			
			// On ajoute le commentaire à la BDD
			bd.tools.CommentairesTools.ajouterCommentaire(contenu, id_auteur, pseudo);
	
			// On renvoie une reponse
			JSONObject reponse = new JSONObject();
			return reponse;
		} catch (UnknownHostException e) {
			return ErrorJSON.serviceRefused("Hote inconnu", CodesErreur.HOTE_INCONNU);
		} catch (InstantiationException e) {
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
	private static boolean verificationParametres(String contenu, String id_auteur, String pseudo) {
		return (contenu != null && id_auteur != null && pseudo != null);
	}
}
