package services.authentification;

import java.sql.SQLException;

import org.json.JSONObject;

import bd.tools.SessionsTools;
import services.CodesErreur;
import services.ErrorJSON;


public class Logout {
	public static JSONObject logout(String cle) {
		if (! verificationParametres(cle)) {
			return ErrorJSON.serviceRefused("L'un des parametres est null", CodesErreur.ERREUR_ARGUMENTS);
		}

		try {
			// On verifie que l'utilisateur est bien connecte
			boolean estConnecte = SessionsTools.clefExiste(cle);
			if (! estConnecte){
				return ErrorJSON.serviceRefused(String.format("L'utilisateur possedant la clef %s n'est pas connecte", cle), CodesErreur.ERREUR_UTILISATEUR_DECONNECTE);
			}
			
			// On supprime la cle de connexion
			boolean estSupprime = SessionsTools.suppressionCle(cle);
			if (! estSupprime){
				return ErrorJSON.serviceRefused("Erreur lors de la deconnexion, impossible de supprimer la clef dans la Base de donnees", CodesErreur.ERREUR_DECONNEXION);
			}
			
			// On genere une reponse
			JSONObject retour = new JSONObject();
			return retour;
		} catch (SQLException e) {
			return ErrorJSON.serviceRefused("Erreur de SQL", CodesErreur.ERREUR_SQL);			
		}catch (InstantiationException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donnees MySQL (InstantiationException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
		} catch (IllegalAccessException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donnees MySQL (IllegalAccessException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
		} catch (ClassNotFoundException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donnees MySQL (ClassNotFoundException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
		} 
	}

	
	private static boolean verificationParametres(String cle) {
		return (cle != null);
	}
}
	
	
	