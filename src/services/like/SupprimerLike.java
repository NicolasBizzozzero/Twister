package services.like;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.ParseException;

import org.json.JSONObject;

import bd.tools.LikesTools;
import bd.tools.SessionsTools;
import exceptions.ClefInexistanteException;
import services.CodesErreur;
import services.ErrorJSON;

public class SupprimerLike {

	
	/**
	 * Supprime un like dans un message donne
	 * @param clef : La clef de session de l'utilisateur supprimant le like
	 * @param id_message: L'ID du message sur lequel on supprime le like
	 * @param type_like : Le type du like qu'on supprime
	 */
	public static JSONObject supprimerLike(String clef, String id_message, String type_like) {
		try {
			// On verifie qu'un des parametres obligatoire n'est pas null
			if (! verificationParametres(type_like, id_message, clef)){
				return ErrorJSON.serviceRefused("L'un des parametres est null", CodesErreur.ERREUR_ARGUMENTS);
			}
			
			// On verifie que la clef de connexion existe
			boolean cleExiste = SessionsTools.clefExiste(clef);
			if (! cleExiste){
				return ErrorJSON.serviceRefused(String.format("La session %s n'existe pas", clef), CodesErreur.ERREUR_SESSION_INEXISTANTE);
			}

			// On recupere l'identifiant de la session
			String id_delikeur = SessionsTools.getIDByClef(clef);
			
			// On verifie que l'utilisateur n'a pas ete inactif trop longtemps
			boolean isInactif = SessionsTools.estInactifDepuisTropLongtemps(clef);
			if (isInactif) {
				SessionsTools.suppressionCle(clef);
				return ErrorJSON.serviceRefused(String.format("L'utilisateur %s est inactif depuis trop longtemps", id_delikeur), CodesErreur.ERREUR_UTILISATEUR_INACTIF);
			}
			
			// On verifie que le message sur lequel supprimer un like existe
			if (! bd.tools.MessagesTools.messageExistant(id_message)){
				return ErrorJSON.serviceRefused(String.format("Le message %s n'existe pas", id_message), CodesErreur.ERREUR_MESSAGE_INEXISTANT);
			}
			
			// On verifie que le type de like a supprimer existe
			if (! LikesTools.likeExiste(type_like)) {
				return ErrorJSON.serviceRefused(String.format("Le type de like \"%s\" n'existe pas.", type_like), CodesErreur.ERREUR_TYPE_LIKE_INCONNU);
			}
			
			// On supprime le like du message
			LikesTools.supprimerLike(id_delikeur, id_message, type_like);
			
			// On met a jour le temps d'inactivite
			SessionsTools.updateTempsCle(clef);
	
			// On renvoie une reponse
			return new JSONObject();
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
		} catch (ParseException e) {
			return ErrorJSON.serviceRefused(String.format("Erreur lors du parsing de la date du jour", clef), CodesErreur.ERREUR_PARSE_DATE);
		}
	}
	
	
   /**
    * Verification de la validite des parametres
    * @return : Un booleen a true si les paramatres sont valides.
    */
	private static boolean verificationParametres(String type_like, String id_message, String clef) {
		return (type_like != null && clef != null && id_message != null);
	}
}
