package services.amis;

import java.sql.SQLException;

import org.json.JSONObject;

import bd.tools.AmitiesTools;
import bd.tools.SessionsTools;
import bd.tools.UtilisateursTools;
import exceptions.IndexInvalideException;
import services.CodesErreur;
import services.ErrorJSON;

public class ListerAmis {
	public static JSONObject listerAmis(String id_utilisateur, int index_debut, int nombre_demandes) {
		if (! verificationParametres(id_utilisateur)){
			return ErrorJSON.serviceRefused("L'un des parametres est null", CodesErreur.ERREUR_ARGUMENTS);
		}

		try {
			// On verifie que l'utilisateur existe
			boolean isUser = UtilisateursTools.verificationExistenceId(id_utilisateur);
			if (! isUser) {
				return ErrorJSON.serviceRefused(String.format("L'utilisateur %s n'existe pas", id_utilisateur), CodesErreur.ERREUR_UTILISATEUR_INEXISTANT);
			}
			
			// On verifie que l'utilisateur est connecte
			boolean estConnecte = SessionsTools.estConnecte(id_utilisateur);
			if(! estConnecte){
				return ErrorJSON.serviceRefused(String.format("L'utilisateur %s n'est pas connecte", id_utilisateur), CodesErreur.ERREUR_UTILISATEUR_DECONNECTE);
			}

	        // On liste les amis 
			JSONObject reponse = AmitiesTools.listerAmis(id_utilisateur, index_debut, nombre_demandes);
	
			// On renvoie une reponse
			return reponse;
			
		} catch (SQLException e) {
			return ErrorJSON.serviceRefused("Erreur, requete SQL incorrecte", CodesErreur.ERREUR_SQL);
		} catch (InstantiationException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donnees MySQL (InstantiationException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
		} catch (IllegalAccessException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donnees MySQL (IllegalAccessException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
		} catch (ClassNotFoundException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donnees MySQL (ClassNotFoundException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
		} catch (IndexInvalideException e) {
			return ErrorJSON.serviceRefused("Erreur, indexation invalide lors du listing d'amis", CodesErreur.ERREUR_INDEX_INVALIDE);
		}
	}

   /**
    * Verification de la validite des parametres
    * @return : Un booleen a true si les paramatres sont valides.
    */
	private static boolean verificationParametres(String id_utilisateur) {
		return (id_utilisateur != null);
	}
}
