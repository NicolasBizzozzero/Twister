package services.amis;

import java.sql.SQLException;
import java.text.ParseException;

import org.json.JSONObject;

import bd.tools.AmitiesTools;
import bd.tools.SessionsTools;
import bd.tools.UtilisateursTools;
import exceptions.ClefInexistanteException;
import exceptions.tailles.ClefInvalideException;
import exceptions.tailles.IDTropGrandException;
import exceptions.tailles.IDTropPetitException;
import outils.Tailles;
import services.CodesErreur;
import services.ErrorJSON;

public class AjouterAmi {
	
	
	/**
	 * Ajoute un utilisateur dans la liste d'ami de celui identifie
	 * par la clef.
	 * @param clef : Clef de session de l'utilisateur ajoutant en ami
	 * @param id_ami : ID de l'ami ajoute
	 * @return Un JSONObject representant le statut de la reponse
	 */
	public static JSONObject ajouterAmi(String clef, String id_ami) {
		try {
			// On verifie qu'un des parametres obligatoire n'est pas null
			if (! verificationParametres(clef, id_ami)){
				return ErrorJSON.serviceRefused("L'un des parametres est null", CodesErreur.ERREUR_ARGUMENTS);
			}
			
			// On verifie que les parametres entres respectent nos criteres de taille
			verificationTailleInput(clef, id_ami);

			// On recupere l'ID de l'ajoutant
			String id_ajoutant = SessionsTools.getIDByClef(clef);

			// On verifie que l'utilisateur ajoutant est connecte
			boolean estConnecte = SessionsTools.estConnecte(id_ajoutant);
			if (! estConnecte) {
				return ErrorJSON.serviceRefused(String.format("L'utilisateur %s n'est pas connecte", id_ajoutant), CodesErreur.ERREUR_UTILISATEUR_DECONNECTE);
			}
			
			// On verifie que l'utilisateur n'a pas ete inactif trop longtemps
			boolean isInactif = SessionsTools.estInactifDepuisTropLongtemps(clef);
			if (isInactif) {
				SessionsTools.suppressionCle(clef);
				return ErrorJSON.serviceRefused(String.format("L'utilisateur %s est inactif depuis trop longtemps", id_ajoutant), CodesErreur.ERREUR_UTILISATEUR_INACTIF);
			}
			
			// On verifie que les deux ID sont differents
			if (id_ajoutant.equals(id_ami)){
				return ErrorJSON.serviceRefused(String.format("Les identifiants sont identiques : %s.", id_ajoutant), CodesErreur.ERREUR_ID_IDENTIQUES);
			}			

			// On verifie que id_ami existe
			boolean isUser = UtilisateursTools.checkExistenceId(id_ami);
			if (! isUser) {
				return ErrorJSON.serviceRefused(String.format("L'utilisateur %s n'existe pas", id_ami), CodesErreur.ERREUR_UTILISATEUR_INEXISTANT);
			}
			
			// On verifie que id_ajoutant ne suit pas deja id_ami
			boolean suitDeja = AmitiesTools.suitDeja(id_ajoutant, id_ami);
			if (suitDeja) {
				return ErrorJSON.serviceRefused(String.format("%s suit deja %s", id_ajoutant, id_ami), CodesErreur.ERREUR_DEJA_SUIVI);
			}
			
			// On ajoute une relation d'amitie a la base de donnees
			AmitiesTools.ajouterAmi(id_ajoutant, id_ami);
			
			// On met a jour le temps d'inactivite
			SessionsTools.updateTempsCle(clef);

			// On renvoie une reponse
			JSONObject reponse = new JSONObject();
			return reponse;
		} catch (SQLException e) {
			return ErrorJSON.serviceRefused("Erreur, requete SQL incorrecte", CodesErreur.ERREUR_SQL);
		} catch (InstantiationException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donnees MySQL (InstantiationException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
		} catch (IllegalAccessException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donnees MySQL (IllegalAccessException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
		} catch (ClassNotFoundException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donnees MySQL (ClassNotFoundException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
		} catch (ClefInexistanteException e) {
			return ErrorJSON.serviceRefused(String.format("La clef %s n'est pas presente dans la Base de donnees", clef), CodesErreur.ERREUR_CLEF_INEXISTANTE);
		} catch (ParseException e) {
			return ErrorJSON.serviceRefused(String.format("Erreur lors du parsing de la date du jour", clef), CodesErreur.ERREUR_PARSE_DATE);
		} catch (ClefInvalideException e) {
			return ErrorJSON.serviceRefused(String.format("Erreur, clef de session %s invalide", clef), CodesErreur.ERREUR_CLEF_INVALIDE);
		} catch (IDTropPetitException e) {
			return ErrorJSON.serviceRefused("Erreur, ID d'ami trop petit : %s", CodesErreur.ERREUR_ID_TROP_COURT);
		} catch (IDTropGrandException e) {
			return ErrorJSON.serviceRefused("Erreur, ID d'ami trop grand : %s", CodesErreur.ERREUR_ID_TROP_LONG);
		}
	}

	
	/**
	 * Verifie que les parametres entres respectent nos criteres de taille.
	 * Ces tailles sont situees dans le fichier services.Tailles.java
	 * Cette fonction lance une exception si un des parametres ne respecte
	 * pas ces criteres
	 * @param clef
	 * @param id_ami
	 * @throws ClefInvalideException
	 * @throws IDTropPetitException
	 * @throws IDTropGrandException
	 */
    private static void verificationTailleInput(String clef, String id_ami) throws ClefInvalideException, IDTropPetitException, IDTropGrandException {
	    if (clef.length() != Tailles.TAILLE_CLEF) {
			throw new ClefInvalideException();
		} else if (id_ami.length() < Tailles.MIN_ID) {
			throw new IDTropPetitException();
	    } else if (id_ami.length() > Tailles.MAX_ID) {
			throw new IDTropGrandException();
	    }
	}


/**
    * Verification de la validite des parametres
    * @return : Un booleen a true si les paramatres sont valides.
    */
	private static boolean verificationParametres(String clef, String id_ami) {
		return (clef != null && id_ami != null);
	}
}
