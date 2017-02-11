package services.utilisateur;

import java.sql.SQLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.json.JSONObject;

import bd.tools.UtilisateursTools;
import outils.StatutMotDePasse;
import services.CodesErreur;
import services.ErrorJSON;


public class CreationUtilisateur {	
	public static JSONObject creationUtilisateur(String pseudo, String motDePasse, String email, String prenom, String nom, String anniversaire) {
		if (! verificationParametres(pseudo, motDePasse, email)){
			return ErrorJSON.serviceRefused("Erreur, le pseudo, mot de passe et l'email doivent etre renseignes", CodesErreur.ERREUR_ARGUMENTS);
		}

		try {
			// On verifie que l'utilisateur n'existe pas deja
			boolean isUser = UtilisateursTools.verificationExistencePseudo(pseudo);
			if (isUser) {
				return ErrorJSON.serviceRefused("Erreur, l'utilisateur existe deja", CodesErreur.ERREUR_UTILISATEUR_EXISTANT);
			}

			// On verifie que le mot de passe est securise
			StatutMotDePasse statutMotDePasse = outils.MesMethodes.verifierSecuriteMotDePasse(motDePasse);
			switch (statutMotDePasse) {
				case TROP_COURT:
					return ErrorJSON.serviceRefused("Mot de passe trop court", CodesErreur.ERREUR_MDP_TROP_COURT);
				case TROP_LONG:
					return ErrorJSON.serviceRefused("Mot de passe trop long", CodesErreur.ERREUR_MDP_TROP_LONG);
				case NON_SECURISE:
					return ErrorJSON.serviceRefused("Mot de passe non securise", CodesErreur.ERREUR_MDP_NON_SECURISE);
				case SECURISE:
					break;
				default:
					break;					
			}

			// On hash le mot de passe
			motDePasse = outils.MesMethodes.hasherMotDePasse(motDePasse);

			// On ajoute l'utilisateur a la BDD
			UtilisateursTools.ajouterUtilisateur(pseudo, motDePasse, email, prenom, nom, anniversaire);

			// On renvoie une reponse
			JSONObject reponse = new JSONObject();
			return reponse;
		}  catch (NoSuchAlgorithmException e) {
			return ErrorJSON.serviceRefused("Erreur lors du hashage du mot de passe", CodesErreur.ERREUR_HASHAGE);
		}  catch (InstantiationException e) {
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
    * Verification de la validite des parametres
    * @return : Un booleen a true si les paramatres sont valides.
    */
	private static boolean verificationParametres(String pseudo, String motDePasse, String email) {
		return (pseudo != null && email != null && motDePasse != null);
	}
}