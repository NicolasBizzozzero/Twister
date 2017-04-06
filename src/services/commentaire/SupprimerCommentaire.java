package services.commentaire;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.ParseException;

import org.json.JSONObject;

import bd.tools.CommentairesTools;
import bd.tools.SessionsTools;
import bd.tools.UtilisateursTools;
import exceptions.ClefInexistanteException;
import exceptions.tailles.ClefInvalideException;
import exceptions.tailles.IDCommentaireTropGrandException;
import exceptions.tailles.IDCommentaireTropPetitException;
import exceptions.tailles.IDMessageTropGrandException;
import exceptions.tailles.IDMessageTropPetitException;
import services.CodesErreur;
import services.ErrorJSON;
import services.Tailles;

public class SupprimerCommentaire {
	
	
	/**
	 * Supprime un commentaire d'un message donne
	 * @param clef : La clef de session de l'utilisateur supprimant le message
	 * @param id_message: L'ID du message sur lequel on supprime le commentaire
	 * @param id_commentaire : Le contenu du message a supprimer
	 * @return Un JSONObject avec des infos si tout va bien, ou avec un champ d'erreur sinon
	 */
	public static JSONObject supprimerCommentaire(String clef, String id_message, String id_commentaire) {
		try {
			// On verifie qu'un des parametres obligatoire n'est pas null
			if (! verificationParametres(clef, id_message, id_commentaire)){
				return ErrorJSON.serviceRefused("L'un des parametres est null", CodesErreur.ERREUR_ARGUMENTS);
			}
			
			// On verifie que les parametres entres respectent nos criteres de taille
			verificationTailleInput(clef, id_message, id_commentaire);			
			
			// On verifie que la clef de connexion existe
			boolean cleExiste = bd.tools.SessionsTools.clefExiste(clef);
			if (! cleExiste){
				return ErrorJSON.serviceRefused(String.format("La session %s n'existe pas", clef), CodesErreur.ERREUR_SESSION_INEXISTANTE);
			}

			// On recupere l'identifiant de la session
			String id_auteur = bd.tools.SessionsTools.getIDByClef(clef);
			
			// On verifie que l'utilisateur n'a pas ete inactif trop longtemps
			boolean isInactif = SessionsTools.estInactifDepuisTropLongtemps(clef);
			if (isInactif) {
				SessionsTools.suppressionCle(clef);
				return ErrorJSON.serviceRefused(String.format("L'utilisateur %s est inactif depuis trop longtemps", id_auteur), CodesErreur.ERREUR_UTILISATEUR_INACTIF);
			}
			
			//On verifie que l'utilisateur existe
			boolean isUser = UtilisateursTools.checkExistenceId(id_auteur);
			if (! isUser) {
				return ErrorJSON.serviceRefused(String.format("L'utilisateur %s n'existe pas", id_auteur), CodesErreur.ERREUR_UTILISATEUR_INEXISTANT);
			}
			
			// On verifie que le message sur lequel supprimer le commentaire existe
			if (! bd.tools.MessagesTools.messageExistant(id_message)){
				return ErrorJSON.serviceRefused(String.format("Le message %s n'existe pas", id_message), CodesErreur.ERREUR_MESSAGE_INEXISTANT);
			}
			
			// On supprime le commentaire du message
			 JSONObject reponse = CommentairesTools.supprimerCommentaire(id_message, id_commentaire);
				
			// On met a jour le temps d'inactivite
			SessionsTools.updateTempsCle(clef);
	
			// On renvoie une reponse
			return reponse;
		} catch (UnknownHostException e) {
			return ErrorJSON.serviceRefused("Hote inconnu", CodesErreur.ERREUR_HOTE_INCONNU);
		} catch (SQLException e) {
			return ErrorJSON.serviceRefused("Erreur, requete SQL incorrecte", CodesErreur.ERREUR_SQL);
		} catch (InstantiationException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donnees MySQL (InstantiationException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
		} catch (IllegalAccessException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donnees MySQL (IllegalAccessException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
		} catch (ClassNotFoundException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donnees MySQL (ClassNotFoundException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
		} catch (ClefInexistanteException e) {
			return ErrorJSON.serviceRefused(String.format("La clef %s n'appartient pas a la base de donnees", clef), CodesErreur.ERREUR_CLEF_INEXISTANTE);
		}  catch (ParseException e) {
			return ErrorJSON.serviceRefused(String.format("Erreur lors du parsing de la date du jour", clef), CodesErreur.ERREUR_PARSE_DATE);
		} catch (ClefInvalideException e) {
			return ErrorJSON.serviceRefused(String.format("Erreur, clef de session %s invalide", clef), CodesErreur.ERREUR_CLEF_INVALIDE);
		} catch (IDMessageTropPetitException e) {
			return ErrorJSON.serviceRefused("Erreur, ID message trop petit", CodesErreur.ERREUR_ID_MESSAGE_TROP_COURT);
		} catch (IDMessageTropGrandException e) {
			return ErrorJSON.serviceRefused("Erreur, ID message trop grand", CodesErreur.ERREUR_ID_MESSAGE_TROP_LONG);
		} catch (IDCommentaireTropPetitException e) {
			return ErrorJSON.serviceRefused("Erreur, ID commentaire trop petit", CodesErreur.ERREUR_ID_COMMENTAIRE_TROP_COURT);
		} catch (IDCommentaireTropGrandException e) {
			return ErrorJSON.serviceRefused("Erreur, ID commentaire trop grand", CodesErreur.ERREUR_ID_COMMENTAIRE_TROP_LONG);
		}
	}
	
	
	/**
	 * Verifie que les parametres entres respectent nos criteres de taille.
	 * Ces tailles sont situees dans le fichier services.Tailles.java
	 * Cette fonction lance une exception si un des parametres ne respecte
	 * pas ces criteres
	 * @param clef
	 * @param id_message
	 * @param type_like
	 * @throws ClefInvalideException
	 * @throws IDMessageTropPetitException 
	 * @throws IDMessageTropGrandException 
	 * @throws IDCommentaireTropPetitException 
	 * @throws IDCommentaireTropGrandException 
	 */
    private static void verificationTailleInput(String clef, String id_message, String id_commentaire) throws ClefInvalideException, IDMessageTropPetitException, IDMessageTropGrandException, IDCommentaireTropPetitException, IDCommentaireTropGrandException {
		if (clef.length() != Tailles.TAILLE_CLEF) {
			throw new ClefInvalideException();
		}
		
		if (id_message.length() < Tailles.MIN_ID) {
			throw new IDMessageTropPetitException();
		} else if (id_message.length() > Tailles.MAX_ID) {
			throw new IDMessageTropGrandException();
		}
		
		if (id_commentaire.length() < Tailles.MIN_ID) {
			throw new IDCommentaireTropPetitException();
		} else if (id_commentaire.length() > Tailles.MAX_ID) {
			throw new IDCommentaireTropGrandException();
		}
	}
	
	
   /**
    * Verification de la validite des parametres
    * @return : Un booleen a true si les paramatres sont valides.
    */
	private static boolean verificationParametres(String contenu, String id_message, String clef) {
		return (contenu != null && clef != null && id_message != null);
	}
}
