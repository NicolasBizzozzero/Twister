package services.utilisateur;

import java.sql.SQLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.json.JSONObject;

import bd.UtilisateursTools;
import services.CodesErreur;
import services.ErrorJSON;


public class CreationUtilisateur {
	public static JSONObject creationUtilisateur(String login, String email, String motDePasse, String nom, String prenom) {
		if (! verificationParametres(login, email, motDePasse, nom, prenom)){
			return ErrorJSON.serviceRefused("L'un des parametres est null", CodesErreur.ERREUR_ARGUMENTS);
		}

		try {
			// On vérifie que l'utilisateur n'existe pas déjà
			boolean isUser = UtilisateursTools.verificationExistenceLogin(login);
			if (isUser) {
				return ErrorJSON.serviceRefused("L'utilisateur existe deja", CodesErreur.ERREUR_UTILISATEUR_EXISTANT);
			}

			// On verifie que le mot de passe est sécurisé
			boolean motDePasseSecurise = motDePasseEstSecurise(motDePasse);
			if (! motDePasseSecurise) {
				return ErrorJSON.serviceRefused("Mot de passe pas assez fort", CodesErreur.ERREUR_MDP_FAIBLE);
			}

			// On hash le mot de passe
			motDePasse = hasherMotDePasse(motDePasse);

			// On ajoute l'utilisateur à la BDD
			UtilisateursTools.ajouterUtilisateur(login, email, motDePasse, nom, prenom);

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
	private static boolean verificationParametres(String login, String email, String motDePasse, String nom, String prenom) {
		return (login != null && email != null && motDePasse != null && nom != null && prenom != null);
	}


       /**
        * Utilise l'algorithme SHA-521 pour encrypter un mot de passe.
        * @param motDePasse: Le mot de passe en clair à encrypter.
        * @return : Une string encryptée correspondant au mot de passe passé en paramaètre.
        */
	private static String hasherMotDePasse(String motDePasse) {
          /*try {
                        MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
                        messageDigest.update(motDePasse.getBytes());
                        motDePasse = new String(messageDigest.digest());
                } catch (NoSuchAlgorithmException e) {}
		*/
		return motDePasse;
	}

   /**
	* Vérifie que le mot de passe entré par l'utilisateur est assez fort selon nos critères de sécurité.
        * Le mot de passe entré doit respecter les règles suivantes :
        *       - Doit contenir au moins 8 caractères.
        *       - Ne doit pas faire plus de 64 caractères.
	* @param motDePasse : Le mot de passe dont la force reste à vérifier.
	* @return : Un boolean étant à true si le mot de passe est assez fort, ou à false sinon.
	*/
	private static boolean motDePasseEstSecurise(String motDePasse) {
		// Mot de passe trop court
		if (motDePasse.length() < 8)
		        return false;

		// Mot de passe trop long
		if (motDePasse.length() > 64)
		        return false;

		return true;
	}
}