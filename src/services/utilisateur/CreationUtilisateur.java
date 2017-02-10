package services.utilisateur;

import java.sql.SQLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.json.JSONObject;

import bd.tools.UtilisateursTools;
import services.CodesErreur;
import services.ErrorJSON;


public class CreationUtilisateur {
	private enum StatutMotDePasse {
		SECURISE,
		NON_SECURISE,
		TROP_COURT,
		TROP_LONG
	}
	
	public static JSONObject creationUtilisateur(String pseudo, String motDePasse, String email, String prenom, String nom, String anniversaire) {
		if (! verificationParametres(pseudo, motDePasse, email)){
			return ErrorJSON.serviceRefused("Erreur, le pseudo, mot de passe et l'email doivent �tre renseign�s", CodesErreur.ERREUR_ARGUMENTS);
		}

		try {
			// On vérifie que l'utilisateur n'existe pas déjà
			boolean isUser = UtilisateursTools.verificationExistencePseudo(pseudo);
			if (isUser) {
				return ErrorJSON.serviceRefused("Erreur, l'utilisateur existe deja", CodesErreur.ERREUR_UTILISATEUR_EXISTANT);
			}

			// On verifie que le mot de passe est sécurisé
			StatutMotDePasse statutMotDePasse = verifierSecuriteMotDePasse(motDePasse);
			switch (statutMotDePasse) {
				case TROP_COURT:
					return ErrorJSON.serviceRefused("Mot de passe pas trop court", CodesErreur.ERREUR_MDP_TROP_COURT);
				case TROP_LONG:
					return ErrorJSON.serviceRefused("Mot de passe pas trop long", CodesErreur.ERREUR_MDP_TROP_LONG);
				case NON_SECURISE:
					return ErrorJSON.serviceRefused("Mot de passe pas non s�curis�", CodesErreur.ERREUR_MDP_NON_SECURISE);
				case SECURISE:
					break;
				default:
					break;					
			}

			// On hash le mot de passe
			//motDePasse = hasherMotDePasse(motDePasse);

			// On ajoute l'utilisateur à la BDD
			UtilisateursTools.ajouterUtilisateur(pseudo, motDePasse, email, prenom, nom, anniversaire);

			// On renvoie une réponse
			JSONObject reponse = new JSONObject();
			return reponse;
		} catch (SQLException e) {
			return ErrorJSON.serviceRefused("Erreur, requ�te SQL Incorrecte", CodesErreur.ERREUR_SQL);
		//} catch (NoSuchAlgorithmException e) {
			//return ErrorJSON.serviceRefused("Erreur lors du hashage du mot de passe", CodesErreur.ERREUR_HASHAGE);
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
	private static boolean verificationParametres(String pseudo, String motDePasse, String email) {
		return (pseudo != null && email != null && motDePasse != null);
	}


       /**
        * Utilise l'algorithme SHA-521 pour encrypter un mot de passe.
        * @param motDePasse: Le mot de passe en clair à encrypter.
        * @return : Une string encryptée correspondant au mot de passe passé en paramaètre.
     * @throws NoSuchAlgorithmException 
        */
	private static String hasherMotDePasse(String motDePasse) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
        messageDigest.update(motDePasse.getBytes());
        motDePasse = new String(messageDigest.digest());
		return motDePasse;
	}

   /**
	* Vérifie que le mot de passe entré par l'utilisateur est assez fort selon nos critères de sécurité.
        * Le mot de passe entré doit respecter les règles suivantes :
        *       - Doit contenir au moins 8 caractères.
        *       - Ne doit pas faire plus de 64 caractères.
	* @param motDePasse : Le mot de passe dont la force reste à vérifier.
	* @return : Un enum correspondant au statut du mot de passe
	*/
	private static StatutMotDePasse verifierSecuriteMotDePasse(String motDePasse) {
		// Mot de passe trop court
		if (motDePasse.length() < 8)
		        return StatutMotDePasse.TROP_COURT;

		// Mot de passe trop long
		if (motDePasse.length() > 64)
		        return StatutMotDePasse.TROP_LONG;

		return StatutMotDePasse.SECURISE;
	}
}