package services.mail;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.mail.MessagingException;

import org.json.JSONObject;

import exceptions.tailles.EmailTropGrandException;
import exceptions.tailles.EmailTropPetitException;
import mails.TypeDeMail;
import services.CodesErreur;
import services.ErrorJSON;
import services.Tailles;

public class RecuperationMotDePasse {

	
	/**
	 * Genere un nouveau mot de passe pour un utilisateur, le modifie dans
	 * la table 'Utilisateurs', puis envoie ce mot de passe a l'adresse
	 * mail renseignee en parametre.
	 * @param email : L'addresse email a laquelle envoyer le mot de passe
	 * @return Un JSONObject avec un code d'erreur en cas
	 * d'erreur, ou alors de la forme suivante en cas de succes :
	 * 	{"mail": ADRESSE_EMAIL}
	 */
	public static JSONObject recuperationMotDePasse(String email) {
		try {	
			// On verifie qu'un des parametres obligatoire n'est pas null
			if (! verificationParametres(email)){
				return ErrorJSON.serviceRefused("Erreur, l'email doit etre renseigne", CodesErreur.ERREUR_ARGUMENTS);
			}
			
			// On verifie que les parametres entres respectent nos criteres de taille
			verificationTailleInput(email);
			
			// On verifie que l'adresse mail existe
			if (! bd.tools.UtilisateursTools.checkExistenceEmail(email)) {
				return ErrorJSON.serviceRefused("Erreur, l'email %s n'existe pas", CodesErreur.ERREUR_EMAIL_INEXISTANT);
			}
			
			// On genere un mot de passe aleatoirement
			String motDePasse = outils.MesMethodes.getStringAleatoire(32);
			
			// On recupere l'ID de l'utilisateur dont on doit modifier le mail
			String id = bd.tools.UtilisateursTools.getIDByEmail(email);
			
			// On altere son mot de passe
			bd.tools.UtilisateursTools.modifierMotDePasse(id, outils.MesMethodes.hasherMotDePasse(motDePasse));
			
			// On lui envoie le nouveau mot de passe par mail
			mails.MailsTools.envoyerMailRecuperationMotDePasse(email, motDePasse);
			
			// On retourne une reponse
			JSONObject reponse = new JSONObject();
			reponse.put("mail", email);
			return reponse;
		} catch (EmailTropPetitException e) {
			return ErrorJSON.serviceRefused("Erreur, email trop petit", CodesErreur.ERREUR_EMAIL_TROP_COURT);
		} catch (EmailTropGrandException e) {
			return ErrorJSON.serviceRefused("Erreur, email trop grand", CodesErreur.ERREUR_EMAIL_TROP_LONG);
		} catch (InstantiationException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donnees MySQL (InstantiationException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
		} catch (IllegalAccessException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donnees MySQL (IllegalAccessException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
		} catch (ClassNotFoundException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donnees MySQL (ClassNotFoundException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
		} catch (SQLException e) {
			return ErrorJSON.serviceRefused("Erreur, requete SQL Incorrecte", CodesErreur.ERREUR_SQL);
		}  catch (NoSuchAlgorithmException e) {
			return ErrorJSON.serviceRefused("Erreur lors du hashage du mot de passe", CodesErreur.ERREUR_HASHAGE);
		} catch (MessagingException e) {
			return ErrorJSON.serviceRefused(String.format("Erreur lors de l'envoi du mail : ", e.getMessage()), CodesErreur.ERREUR_ENVOI_MAIl);
		}
	}

	
   /**
    * Verification de la validite des parametres
    * @return : Un booleen a true si les paramatres sont valides.
    */
	private static boolean verificationParametres(String email) {
		return (email != null);
	}
	
	
	/**
	 * Verifie que les parametres entres respectent nos criteres de taille.
	 * Ces tailles sont situees dans le fichier services.Tailles.java
	 * Cette fonction lance une exception si un des parametres ne respecte
	 * pas ces criteres
	 * @param email
	 * @throws EmailTropPetitException 
	 * @throws EmailTropGrandException
	 */
	private static void verificationTailleInput(String email) throws EmailTropPetitException, EmailTropGrandException {
		 if (email.length() < Tailles.MIN_EMAIL) {
				throw new EmailTropPetitException();
		 } else if (email.length() > Tailles.MAX_EMAIL) {
				throw new EmailTropGrandException();
		 }
	}
}
