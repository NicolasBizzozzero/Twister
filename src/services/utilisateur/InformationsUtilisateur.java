package services.utilisateur;

import java.sql.SQLException;

import org.json.JSONObject;

import exceptions.tailles.ClefInvalideException;
import exceptions.tailles.PseudoTropGrandException;
import exceptions.tailles.PseudoTropPetitException;
import outils.Tailles;
import services.CodesErreur;
import services.ErrorJSON;

public class InformationsUtilisateur {

	public static JSONObject informationsUtilisateur(String clef, String pseudo) {
		try {
			// On verifie qu'un des parametres obligatoire n'est pas null
			if (! verificationParametres(clef, pseudo)){
				return ErrorJSON.serviceRefused("Erreur, parametres non-renseignes", CodesErreur.ERREUR_ARGUMENTS);
			}

			// On verifie que les parametres entres respectent nos criteres de taille
			verificationTailleInput(clef, pseudo);

			// On renvoie une reponse
			return bd.tools.UtilisateursTools.getInformationsUtilisateur(pseudo);

		} catch (InstantiationException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donnees MySQL (InstantiationException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
		} catch (IllegalAccessException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donnees MySQL (IllegalAccessException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
		} catch (ClassNotFoundException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donnees MySQL (ClassNotFoundException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
		} catch (SQLException e) {
			return ErrorJSON.serviceRefused("Erreur, requete SQL Incorrecte", CodesErreur.ERREUR_SQL);
		} catch (PseudoTropPetitException e) {
			return ErrorJSON.serviceRefused("Erreur, pseudo trop petit", CodesErreur.ERREUR_PSEUDO_TROP_COURT);
		} catch (PseudoTropGrandException e) {
			return ErrorJSON.serviceRefused("Erreur, pseudo trop grand", CodesErreur.ERREUR_PSEUDO_TROP_GRAND);
		} catch (ClefInvalideException e) {
			return ErrorJSON.serviceRefused(String.format("Erreur, clef de session %s invalide", clef), CodesErreur.ERREUR_CLEF_INVALIDE);
		} 
	}

	
   /**
    * Verification de la validite des parametres
    * @return : Un booleen a true si les paramatres sont valides.
    */
	private static boolean verificationParametres(String clef, String pseudo) {
		return (pseudo != null && clef != null);
	}
	
	
	/**
	 * Verifie que les parametres entres respectent nos criteres de taille.
	 * Ces tailles sont situees dans le fichier services.Tailles.java
	 * Cette fonction lance une exception si un des parametres ne respecte
	 * pas ces criteres
	 * @throws PseudoTropPetitException 
	 * @throws ClefInvalideException 
	 * @throws PseudoTropGrandException 
	 */
	private static void verificationTailleInput(String clef, String pseudo) throws PseudoTropPetitException, ClefInvalideException, PseudoTropGrandException {
		if (clef.length() != Tailles.TAILLE_CLEF) {
			throw new ClefInvalideException();
		}

		if (pseudo.length() < Tailles.MIN_PSEUDO) {
			throw new PseudoTropPetitException();
		} else if (pseudo.length() > Tailles.MAX_PSEUDO) {
			throw new PseudoTropGrandException();
		}
	}
}
