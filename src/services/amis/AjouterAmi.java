package services.amis;

import java.sql.SQLException;

import org.json.JSONObject;

import bd.tools.AmitiesTools;
import bd.tools.UtilisateursTools;
import services.CodesErreur;
import services.ErrorJSON;

public class AjouterAmi {
	public static JSONObject ajouterAmi(String id_ami1, String id_ami2) {
		if (! verificationParametres(id_ami1, id_ami2)){
			return ErrorJSON.serviceRefused("L'un des parametres est null", CodesErreur.ERREUR_ARGUMENTS);
		}

		try {
			// On verifie que les deux ID sont differents
			if (id_ami1.equals(id_ami2)){
				return ErrorJSON.serviceRefused(String.format("Les identifiants sont identiques : %s.", id_ami1), CodesErreur.ERREUR_ID_IDENTIQUES);
			}
			
			// On verifieque les deux utilisateurs existent
			boolean isUser = UtilisateursTools.verificationExistenceId(id_ami1);
			if (! isUser) {
				return ErrorJSON.serviceRefused(String.format("L'utilisateur %s n'existe pas", id_ami1), CodesErreur.ERREUR_UTILISATEUR_INEXISTANT);
			}
			isUser = UtilisateursTools.verificationExistenceId(id_ami2);
			if (! isUser) {
				return ErrorJSON.serviceRefused(String.format("L'utilisateur %s n'existe pas", id_ami2), CodesErreur.ERREUR_UTILISATEUR_INEXISTANT);
			}
			
			// On verifie que ami1 ne suit pas deja ami2
			boolean suitDeja = AmitiesTools.suitDeja(id_ami1, id_ami2);
			if (suitDeja) {
				return ErrorJSON.serviceRefused(String.format("%s suit deja  %s", id_ami1, id_ami2), CodesErreur.ERREUR_DEJA_SUIVI);
			}
			
			// On ajoute une relation d'amitie a la base de donnees
			AmitiesTools.ajouterAmi(id_ami1, id_ami2);

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
		}
	}

   /**
    * Verification de la validite des parametres
    * @return : Un booleen a true si les paramatres sont valides.
    */
	private static boolean verificationParametres(String id_ami1, String id_ami2) {
		return (id_ami1 != null && id_ami2 != null);
	}
}
