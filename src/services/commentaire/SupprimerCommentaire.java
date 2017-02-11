package services.commentaire;

import java.net.UnknownHostException;
import java.sql.SQLException;

import org.json.JSONObject;

import bd.tools.UtilisateursTools;
import services.CodesErreur;
import services.ErrorJSON;

public class SupprimerCommentaire {

	public static JSONObject supprimerCommentaire(String id_auteur, String contenu) {
			if (! verificationParametres( id_auteur, contenu)){
				return ErrorJSON.serviceRefused("L'un des parametres est null", CodesErreur.ERREUR_ARGUMENTS);
			}
			
			try {
				// On verifie que l'utilisateur existe
				boolean isUser = UtilisateursTools.verificationExistenceId(id_auteur);
				if (! isUser) {
					return ErrorJSON.serviceRefused(String.format("L'utilisateur %s n'existe pas", id_auteur), CodesErreur.ERREUR_UTILISATEUR_INEXISTANT);
				}
				// on verifie que le commentaire existe
				if(! commentaireExistant(id_auteur,contenu)){
					return ErrorJSON.serviceRefused(String.format("Le commentaire %s n'existe pas", contenu), CodesErreur.ERREUR_COMMENTAIRE_INEXISTANT);
				}
				
				// On supprime le commentaire à la BDD
				bd.tools.CommentairesTools.supprimerCommentaire( id_auteur,contenu);
		
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
		private static boolean verificationParametres( String id_auteur, String contenu) {
			return (contenu != null && id_auteur != null);
		}
		private static boolean commentaireExistant(String id_auteur, String contenu)throws UnknownHostException{
			return bd.tools.CommentairesTools.commentaireExistant(id_auteur,contenu);
		}
	}
