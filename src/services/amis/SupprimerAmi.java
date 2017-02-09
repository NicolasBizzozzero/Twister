package services.amis;

import java.sql.SQLException;

import org.json.JSONObject;

import bd.tools.AmitiesTools;
import bd.tools.UtilisateursTools;
import services.CodesErreur;
import services.ErrorJSON;

public class SupprimerAmi {
	public static JSONObject supprimerAmi(String id_ami1, String id_ami2) {
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
			
			// On vérifie que ami1 suit ami2
			boolean suitDeja = AmitiesTools.suitDeja(id_ami1, id_ami2);
			if (! suitDeja) {
				return ErrorJSON.serviceRefused(String.format("%s ne suit pas %s", id_ami1, id_ami2), CodesErreur.ERREUR_NE_SUIVAIT_PAS);
			}
			
			// on ajoute une relation d'amitié à la base de données
			AmitiesTools.supprimerAmi(id_ami1, id_ami2);

			// On renvoie une réponse
			JSONObject reponse = new JSONObject();
			return reponse;
		} catch (SQLException e) {
			return ErrorJSON.serviceRefused("Erreur de la base de donnees", CodesErreur.ERREUR_SQL);
		} catch (InstantiationException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donn�es MySQL (InstantiationException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
		} catch (IllegalAccessException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donn�es MySQL (IllegalAccessException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
		} catch (ClassNotFoundException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donn�es MySQL (ClassNotFoundException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
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
