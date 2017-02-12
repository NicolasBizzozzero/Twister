package services.amis;

import java.sql.SQLException;

import org.json.JSONObject;

import bd.tools.AmitiesTools;
import bd.tools.SessionsTools;
import bd.tools.UtilisateursTools;
import exceptions.ClefInexistanteException;
import services.CodesErreur;
import services.ErrorJSON;

public class SupprimerAmi {
	public static JSONObject supprimerAmi(String clef, String id_ami) {
		if (! verificationParametres(clef, id_ami)){
			return ErrorJSON.serviceRefused("L'un des parametres est null", CodesErreur.ERREUR_ARGUMENTS);
		}

		try {
			// On recupere l'ID du supprimant
			String id_supprimant = SessionsTools.getIDbyClef(clef);
			
			// On verifie que l'utilisateur supprimant est connecte
			boolean estConnecte = SessionsTools.estConnecte(id_supprimant);
			if (! estConnecte) {
				return ErrorJSON.serviceRefused(String.format("L'utilisateur %s n'est pas connecte", id_supprimant), CodesErreur.ERREUR_UTILISATEUR_DECONNECTE);
			}
			
			// On verifie que les deux ID sont differents
			if (id_supprimant.equals(id_ami)){
				return ErrorJSON.serviceRefused(String.format("Les identifiants sont identiques : %s.", id_supprimant), CodesErreur.ERREUR_ID_IDENTIQUES);
			}
			
			// On verifie que id_ami existe
			boolean isUser = UtilisateursTools.verificationExistenceId(id_ami);
			if (! isUser) {
				return ErrorJSON.serviceRefused(String.format("L'utilisateur %s n'existe pas", id_ami), CodesErreur.ERREUR_UTILISATEUR_INEXISTANT);
			}
			
			// On verifie que id_supprimant suit bien id_ami
			boolean suitDeja = AmitiesTools.suitDeja(id_supprimant, id_ami);
			if (! suitDeja) {
				return ErrorJSON.serviceRefused(String.format("%s ne suit pas %s", id_supprimant, id_ami), CodesErreur.ERREUR_NE_SUIVAIT_PAS);
			}
			
			// On retire une relation d'amitiee a la base de donnees
			AmitiesTools.supprimerAmi(id_supprimant, id_ami);

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
		}
	}

   /**
    * Verification de la validite des parametres
    * @return : Un booleen a true si les paramatres sont valides.
    */
	private static boolean verificationParametres(String id_ami1, String id_ami) {
		return (id_ami1 != null && id_ami != null);
	}

}
