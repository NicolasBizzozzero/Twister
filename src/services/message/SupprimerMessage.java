package services.message;

import java.net.UnknownHostException;
import java.sql.SQLException;

import org.json.JSONObject;

import exceptions.ClefInexistanteException;
import bd.tools.UtilisateursTools;
import services.CodesErreur;
import services.ErrorJSON;

public class SupprimerMessage {

	public static JSONObject supprimerMessage(String clef, String id_message) {
			if (! verificationParametres(clef, id_message)){
				return ErrorJSON.serviceRefused("L'un des parametres est null", CodesErreur.ERREUR_ARGUMENTS);
			}
			
			try {
				// On verifie que la clef de connexion existe
				boolean cleExiste = bd.tools.SessionsTools.clefExiste(clef);
				if (! cleExiste){
					return ErrorJSON.serviceRefused(String.format("La session %s n'existe pas", clef), CodesErreur.ERREUR_SESSION_INEXISTANTE);
				}
				
				// On recupere l'identifiant de la session
				String id = bd.tools.SessionsTools.getIDbyClef(clef);
				
				// On verifie que l'utilisateur existe
				boolean isUser = UtilisateursTools.verificationExistenceId(id);
				if (! isUser) {
					return ErrorJSON.serviceRefused(String.format("L'utilisateur %s n'existe pas", id), CodesErreur.ERREUR_UTILISATEUR_INEXISTANT);
				}
				
				// On verifie que le message existe
				if(! bd.tools.MessagesTools.messageExistant(id_message)){
					return ErrorJSON.serviceRefused(String.format("Le message %s n'existe pas", id_message), CodesErreur.ERREUR_MESSAGE_INEXISTANT);
				}
				
				// On supprime le message de la BDD
				bd.tools.MessagesTools.supprimerMessage(clef, id_message);
		
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
			} catch (ClefInexistanteException e) {
				return ErrorJSON.serviceRefused(String.format("La clef %s n'appartient pas a la base de donnees", clef), CodesErreur.ERREUR_CLEF_INEXISTANTE);
			} 
		}
		
	
	   /**
	    * Verification de la validite des parametres
	    * @return : Un booleen a true si les paramatres sont valides.
	    */
		private static boolean verificationParametres(String clef, String id_message) {
			return (id_message != null && clef != null);
		}
	}
