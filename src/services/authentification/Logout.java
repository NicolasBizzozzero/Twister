package services.authentification;

import java.sql.SQLException;

import org.json.JSONObject;

import bd.tools.SessionsTools;
import exceptions.tailles.ClefInvalideException;
import services.CodesErreur;
import services.ErrorJSON;
import services.Tailles;


public class Logout {
	public static JSONObject logout(String clef) {
		try {
			// On verifie qu'un des parametres obligatoire n'est pas null
			if (! verificationParametres(clef)) {
				return ErrorJSON.serviceRefused("L'un des parametres est null", CodesErreur.ERREUR_ARGUMENTS);
			}
			
			// On verifie que les parametres entres respectent nos criteres de taille
			verificationTailleInput(clef);
			
			// On verifie que l'utilisateur est bien connecte
			boolean estConnecte = SessionsTools.clefExiste(clef);
			if (! estConnecte){
				return ErrorJSON.serviceRefused(String.format("L'utilisateur possedant la clef %s n'est pas connecte", clef), CodesErreur.ERREUR_UTILISATEUR_DECONNECTE);
			}
			
			// On supprime la clef de connexion
			boolean estSupprime = SessionsTools.suppressionCle(clef);
			if (! estSupprime){
				return ErrorJSON.serviceRefused("Erreur lors de la deconnexion, impossible de supprimer la clef dans la Base de donnees", CodesErreur.ERREUR_DECONNEXION);
			}
			
			// On genere une reponse
			JSONObject retour = new JSONObject();
			return retour;
		} catch (SQLException e) {
			return ErrorJSON.serviceRefused("Erreur de SQL", CodesErreur.ERREUR_SQL);			
		} catch (InstantiationException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donnees MySQL (InstantiationException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
		} catch (IllegalAccessException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donnees MySQL (IllegalAccessException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
		} catch (ClassNotFoundException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donnees MySQL (ClassNotFoundException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
		} catch (ClefInvalideException e) {
			return ErrorJSON.serviceRefused(String.format("Erreur, clef de session %s invalide", clef), CodesErreur.ERREUR_CLEF_INVALIDE);
		}
	}

	
	/**
	 * Verifie que les parametres entres respectent nos criteres de taille.
	 * Ces tailles sont situees dans le fichier services.Tailles.java
	 * Cette fonction lance une exception si un des parametres ne respecte
	 * pas ces criteres
	 * @param clef
	 * @throws ClefInvalideException 
	 */
	private static void verificationTailleInput(String clef) throws ClefInvalideException {
		if (clef.length() != Tailles.TAILLE_CLEF) {
			throw new ClefInvalideException();
		}
	}


	private static boolean verificationParametres(String cle) {
		return (cle != null);
	}
}
	
	
	