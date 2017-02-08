package services.amis;

import java.sql.SQLException;

import org.json.JSONObject;

import services.CodesErreur;
import services.ErrorJSON;
import bd.AmitiesTools;
import bd.UtilisateursTools;

public class AjouterAmi {
	public static JSONObject ajouterAmi(String id_ami1, String id_ami2) {
		if (! verificationParametres(id_ami1, id_ami2)){
			return ErrorJSON.serviceRefused("L'un des parametres est null", CodesErreur.ERREUR_ARGUMENTS);
		}

		try {
			// On vérifie que les deux ID sont différents
			if (id_ami1.equals(id_ami2)){
				return ErrorJSON.serviceRefused(String.format("Les identifiants sont identiques : %s.", id_ami1), CodesErreur.ERREUR_ID_IDENTIQUES);
			}
			
			// On vérifie que les deux utilisateurs existent
			boolean isUser = UtilisateursTools.verificationExistenceId(id_ami1);
			if (! isUser) {
				return ErrorJSON.serviceRefused(String.format("L'utilisateur %s n'existe pas", id_ami1), CodesErreur.ERREUR_UTILISATEUR_INEXISTANT);
			}
			isUser = UtilisateursTools.verificationExistenceId(id_ami2);
			if (! isUser) {
				return ErrorJSON.serviceRefused(String.format("L'utilisateur %s n'existe pas", id_ami2), CodesErreur.ERREUR_UTILISATEUR_INEXISTANT);
			}
			
			// On vérifie que ami1 ne suit pas déjà ami2
			boolean suitDeja = AmitiesTools.suitDeja(id_ami1, id_ami2);
			if (suitDeja) {
				return ErrorJSON.serviceRefused(String.format("%s suit déjà %s", id_ami1, id_ami2), CodesErreur.ERREUR_DEJA_SUIVI);
			}
			
			// on ajoute une relation d'amitié à la base de données
			AmitiesTools.ajouterAmi(id_ami1, id_ami2);

			// On renvoie une réponse
			JSONObject reponse = new JSONObject();
			return reponse;
		} catch (SQLException e) {
			return ErrorJSON.serviceRefused("Erreur de la base de donnees", CodesErreur.ERREUR_SQL);
		}
	}

   /**
    * Vérification de la validité des paramètres.
    * @return : Un boolean à true si les paramètres sont valides.
    */
	private static boolean verificationParametres(String id_ami1, String id_ami2) {
		return (id_ami1 != null && id_ami2 != null);
	}
}
