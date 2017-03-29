package services.utilisateur;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;

import javax.mail.MessagingException;

import org.json.JSONObject;

import exceptions.ClefInexistanteException;
import exceptions.tailles.ClefInvalideException;
import exceptions.tailles.MotDePasseTropGrandException;
import exceptions.tailles.MotDePasseTropPetitException;
import mails.MailsTools;
import bd.tools.AmitiesTools;
import bd.tools.SessionsTools;
import bd.tools.UtilisateursTools;
import services.CodesErreur;
import services.ErrorJSON;
import services.Tailles;

public class SuppressionUtilisateur {

	
	/**
	 * Supprime un utilisateur de la table 'Utilisateurs' de la BDD MySQL.
	 * Supprime aussi ses relations d'amities dans la table 'Amities'
	 * Nous avons fait le choix de ne pas supprimer les messages, les
	 * commentaires et les likes lors de la suppression de 
	 * l'utilisateur.
	 * @param clef : La clef de session
	 * @param motDePasse : Le mot de passe de l'utilisateur voulant
	 * supprimer son mot de passe
	 * @return Un JSONObject avec un code d'erreur en cas d'erreur, ou vide
	 * en cas de succes :
	 */
	public static JSONObject suppressionUtilisateur(String clef, String motDePasse) {
		try {
			// On verifie qu'un des parametres obligatoire n'est pas null
			if (! verificationParametres(clef, motDePasse)){
				return ErrorJSON.serviceRefused("Erreur, le mot de passe doit etre renseigne", CodesErreur.ERREUR_ARGUMENTS);
			}
			
			// On verifie que les parametres entres respectent nos criteres de taille
			verificationTailleInput(clef, motDePasse);

			// On recupere l'ID de l'utilisateur
			String id_utilisateur = SessionsTools.getIDByClef(clef);
			
			// On verifie que l'utilisateur n'a pas ete inactif trop longtemps
			boolean isInactif = SessionsTools.estInactifDepuisTropLongtemps(clef);
			if (isInactif) {
				SessionsTools.suppressionCle(clef);
				return ErrorJSON.serviceRefused(String.format("L'utilisateur %s est inactif depuis trop longtemps", id_utilisateur), CodesErreur.ERREUR_UTILISATEUR_INACTIF);
			}

			// On hash le mot de passe
			motDePasse = outils.MesMethodes.hasherMotDePasse(motDePasse);
			
			// On verifie que le mot de passe est le bon
			boolean motDePasseCorrect = UtilisateursTools.checkMotDePasseAvecId(id_utilisateur, motDePasse);
			if (! motDePasseCorrect) {
				return ErrorJSON.serviceRefused("Erreur, mot de passe incorrect", CodesErreur.ERREUR_MDP_INCORRECT);
			}
			
			// On recupere son email et son pseudo
			// String email = bd.tools.UtilisateursTools.getEmailByID(id_utilisateur);
			// String pseudo = bd.tools.UtilisateursTools.getPseudoUtilisateur(id_utilisateur);

			// On supprime l'utilisateur
			UtilisateursTools.supprimerUtilisateurAvecId(id_utilisateur);
			
			// On supprime toute relation d'amitie avec cet utilisateur
			AmitiesTools.supprimerAmitiesConcernant(id_utilisateur);
			
			// On le deconnecte
			SessionsTools.suppressionCle(clef);
			
			// On envoie un mail de au revoir a l'utilisateur
			// MailsTools.envoyerMailAuRevoir(email, pseudo); 

			// On renvoie une reponse
			return new JSONObject();

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
		} catch (ClefInexistanteException e) {
			return ErrorJSON.serviceRefused(String.format("La clef %s n'est pas presente dans la Base de donnees", clef), CodesErreur.ERREUR_CLEF_INEXISTANTE);
		}  catch (ParseException e) {
			return ErrorJSON.serviceRefused(String.format("Erreur lors du parsing de la date du jour", clef), CodesErreur.ERREUR_PARSE_DATE);
		} catch (ClefInvalideException e) {
			return ErrorJSON.serviceRefused(String.format("Erreur, la clef de session doit faire %d caracteres.", Tailles.TAILLE_CLEF), CodesErreur.ERREUR_CLEF_INVALIDE);
		} catch (MotDePasseTropPetitException e) {
			return ErrorJSON.serviceRefused("Erreur, mot de passe trop petit", CodesErreur.ERREUR_MDP_TROP_COURT);
		} catch (MotDePasseTropGrandException e) {
			return ErrorJSON.serviceRefused("Erreur, mot de passe trop grand", CodesErreur.ERREUR_MDP_TROP_LONG);
		}/* catch (MessagingException e) {
			return ErrorJSON.serviceRefused("Erreur lors de l'envoi du mail", CodesErreur.ERREUR_ENVOI_MAIl);
	    } */
	}

	
	/**
	 * Verifie que les parametres entres respectent nos criteres de taille.
	 * Ces tailles sont situees dans le fichier services.Tailles.java
	 * Cette fonction lance une exception si un des parametres ne respecte
	 * pas ces criteres
	 * @param clef
	 * @param motDePasse
	 * @throws ClefInvalideException
	 * @throws MotDePasseTropPetitException
	 * @throws MotDePasseTropGrandException
	 */
    private static void verificationTailleInput(String clef, String motDePasse) throws ClefInvalideException, MotDePasseTropPetitException, MotDePasseTropGrandException {
		if (clef.length() != Tailles.TAILLE_CLEF) {
			throw new ClefInvalideException();
		} else if (motDePasse.length() < Tailles.MIN_MOT_DE_PASSE) {
			throw new MotDePasseTropPetitException();
		} else if (motDePasse.length() > Tailles.MAX_MOT_DE_PASSE) {
			throw new MotDePasseTropGrandException();
		}
	}


   /**
    * Verification de la validite des parametres.
    * @return : Un boolean a true si les parametres sont valides.
    */
	private static boolean verificationParametres(String clef, String motDePasse) {
		return (clef != null && motDePasse != null);
	}
}
