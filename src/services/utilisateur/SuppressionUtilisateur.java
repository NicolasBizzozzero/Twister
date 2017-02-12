package services.utilisateur;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import org.json.JSONObject;

import bd.tools.UtilisateursTools;
import services.CodesErreur;
import services.ErrorJSON;

public class SuppressionUtilisateur {

	public static JSONObject suppressionUtilisateur(String id, String motDePasse) {
		if (! verificationParametres(motDePasse)){
			return ErrorJSON.serviceRefused("Erreur, le mot de passe doit etre renseigne", CodesErreur.ERREUR_ARGUMENTS);
		}

		try {
			// On hash le mot de passe
			motDePasse = outils.MesMethodes.hasherMotDePasse(motDePasse);

			// On verifie que l'id existe
			boolean idExiste = UtilisateursTools.verificationExistenceId(id);
			if (! idExiste) {
				return ErrorJSON.serviceRefused(String.format("Erreur, l'utilisateur d'ID %s n'existe pas.", id), CodesErreur.ERREUR_ID_INEXISTANT);
			}
			
			// On verifie que le mot de passe est le bon
			boolean motDePasseCorrect = UtilisateursTools.checkMotDePasseAvecId(id, motDePasse);
			if (! motDePasseCorrect) {
				return ErrorJSON.serviceRefused("Erreur, mot de passe incorrect", CodesErreur.ERREUR_MDP_INCORRECT);
			}

			// On supprime l'utilisateur
			UtilisateursTools.supprimerUtilisateurAvecId(id);
			
			// On supprime ses messages
			//TODO: Implementer
			
			// On supprime la relation d'amitie avec les amis qu'il suivait
			//TODO: Implementer
			
			// On supprime la relation d'amitie des amis le suivant
			//TODO: Implementer
			
			// On le deconnecte
			//TODO: Implementer

			// On renvoie une reponse
			JSONObject reponse = new JSONObject();
			return reponse;
		} catch (NoSuchAlgorithmException e) {
			return ErrorJSON.serviceRefused("Erreur lors du hashage du mot de passe", CodesErreur.ERREUR_HASHAGE);
		} catch (InstantiationException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donnees MySQL (InstantiationException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
		} catch (IllegalAccessException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donnees MySQL (IllegalAccessException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
		} catch (ClassNotFoundException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donnees MySQL (ClassNotFoundException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
		} catch (SQLException e) {
			return ErrorJSON.serviceRefused("Erreur, requete SQL Incorrecte", CodesErreur.ERREUR_SQL);
		}
	}

       /**
        * Verification de la validite des parametres.
        * @return : Un boolean a true si les parametres sont valides.
        */
		private static boolean verificationParametres(String motDePasse) {
			return (motDePasse != null);
		}
}
