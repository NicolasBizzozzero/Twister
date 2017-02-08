package services.amis;

import java.sql.SQLException;

import org.json.JSONObject;

import services.CodesErreur;
import services.ErrorJSON;
import bd.AmitiesTools;
import bd.SessionsTools;
import bd.UtilisateursTools;

public class ListerAmis {
	public static JSONObject listerAmis(String id_utilisateur, int limite) {
		if (! verificationParametres(id_utilisateur)){
			return ErrorJSON.serviceRefused("L'un des parametres est null", CodesErreur.ERREUR_ARGUMENTS);
		}

		try {
			// On vérifie que l'utilisateur existe
			boolean isUser = UtilisateursTools.verificationExistenceId(id_utilisateur);
			if (! isUser) {
				return ErrorJSON.serviceRefused(String.format("L'utilisateur %s n'existe pas", id_utilisateur), CodesErreur.ERREUR_UTILISATEUR_INEXISTANT);
			}
			
			// On vérifie que l'utilisateur est connecté
			boolean estConnecte = SessionsTools.estConnecte(id_utilisateur);
			if(! estConnecte){
				return ErrorJSON.serviceRefused(String.format("L'utilisateur %s n'est pas connecté", id_utilisateur), CodesErreur.ERREUR_UTILISATEUR_DECONNECTE);
			}

	        //Lister les amis 
			JSONObject reponse = AmitiesTools.listerAmis(id_utilisateur, limite);
	
			// On renvoie une réponse
			return reponse;
			
		} catch (SQLException e) {
			return ErrorJSON.serviceRefused("Erreur de la base de donnees", CodesErreur.ERREUR_SQL);
		}
	}

   /**
    * Vérification de la validité des paramètres.
    * @return : Un boolean à true si les paramètres sont valides.
    */
	private static boolean verificationParametres(String id_utilisateur) {
		return (id_utilisateur != null);
	}
}
