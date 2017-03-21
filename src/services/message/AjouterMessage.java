package services.message;

import java.net.UnknownHostException;
import java.sql.SQLException;

import org.json.JSONObject;

import exceptions.ClefInexistanteException;
import services.CodesErreur;
import services.ErrorJSON;
import bd.tools.UtilisateursTools;
import bd.tools.MessagesTools;


public class AjouterMessage {
	
	
	/**
	 * Ajoute le message d'un utilisateur dans la BDD MongoDB
	 * @param clef : La clef de session de l'utilisateur ajoutant le message
	 * @param contenu : Le contenu du message a ajouter
	 * @return Un JSONObject vide si tout va bien, ou avec un champ d'erreur sinon
	 */
	public static JSONObject ajouterMessage(String clef, String contenu) {
		if (! verificationParametres(contenu, clef)){
			return ErrorJSON.serviceRefused("L'un des parametres est null", CodesErreur.ERREUR_ARGUMENTS);
		}
		
		try {
			// On verifie que la clef de connexion existe
			boolean cleExiste = bd.tools.SessionsTools.clefExiste(clef);
			if (! cleExiste){
				return ErrorJSON.serviceRefused(String.format("La session %s n'existe pas", clef), CodesErreur.ERREUR_SESSION_INEXISTANTE);
			}

			// On recupere l'identifiant de la session
			String id_auteur = bd.tools.SessionsTools.getIDbyClef(clef);
			
			//On verifie que l'utilisateur existe
			boolean isUser = UtilisateursTools.verificationExistenceId(id_auteur);
			if (! isUser) {
				return ErrorJSON.serviceRefused(String.format("L'utilisateur %s n'existe pas", id_auteur), CodesErreur.ERREUR_UTILISATEUR_INEXISTANT);
			}
			
			// On ajoute le message a la BDD
			MessagesTools.ajouterMessage(id_auteur, contenu);
	
			// On renvoie une reponse
			JSONObject reponse = new JSONObject();
			return reponse;
		} catch (UnknownHostException e) {
			return ErrorJSON.serviceRefused("Hote inconnu", CodesErreur.HOTE_INCONNU);
		} catch (SQLException e) {
			return ErrorJSON.serviceRefused("Erreur, requete SQL incorrecte", CodesErreur.ERREUR_SQL);
		} catch (InstantiationException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donnees MySQL (InstantiationException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
		} catch (IllegalAccessException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donnees MySQL (IllegalAccessException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
		} catch (ClassNotFoundException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donnees MySQL (ClassNotFoundException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
		} catch (ClefInexistanteException e) {
			return ErrorJSON.serviceRefused(String.format("La clef %s n'appartient pas a la base de donnees", clef), CodesErreur.ERREUR_CLEF_INEXISTANTE);
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
