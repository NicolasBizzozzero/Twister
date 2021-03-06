package services.like;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.ParseException;

import org.json.JSONObject;

import bd.tools.LikesTools;
import bd.tools.SessionsTools;
import exceptions.ClefInexistanteException;
import exceptions.tailles.ClefInvalideException;
import exceptions.tailles.IDTropGrandException;
import exceptions.tailles.IDTropPetitException;
import exceptions.tailles.TypeLikeTropGrandException;
import exceptions.tailles.TypeLikeTropPetitException;
import outils.Tailles;
import services.CodesErreur;
import services.ErrorJSON;

public class ListerLikes {


	/**
	 * Liste tous les likes contenus dans un message
	 * @param clef : La clef de session de l'utilisateur listant les likes
	 * @param id_message: L'ID du message sur lequel on liste les likes
	 * @return Un JSONObject de la forme :
	 * 			{"0": [],
	 *           "1": [],
	 *           "2": [],
	 *           "3": [],
	 *           "4": []}
	 */
	public static JSONObject listerLikes(String clef, String id_message) {
		try {
			// On verifie qu'un des parametres obligatoire n'est pas null
			if (! verificationParametres(clef, id_message)){
				return ErrorJSON.serviceRefused("L'un des parametres est null", CodesErreur.ERREUR_ARGUMENTS);
			}
			
			// On verifie que les parametres entres respectent nos criteres de taille
			verificationTailleInput(clef, id_message);
			
			// On verifie que la clef de connexion existe
			boolean cleExiste = SessionsTools.clefExiste(clef);
			if (! cleExiste){
				return ErrorJSON.serviceRefused(String.format("La session %s n'existe pas", clef), CodesErreur.ERREUR_SESSION_INEXISTANTE);
			}
			
			// On verifie que l'utilisateur n'a pas ete inactif trop longtemps
			boolean isInactif = SessionsTools.estInactifDepuisTropLongtemps(clef);
			if (isInactif) {
				SessionsTools.suppressionCle(clef);
				return ErrorJSON.serviceRefused(String.format("L'utilisateur est inactif depuis trop longtemps"), CodesErreur.ERREUR_UTILISATEUR_INACTIF);
			}
			
			// On verifie que le message sur lequel on veut lister les likes existe
			if (! bd.tools.MessagesTools.messageExistant(id_message)){
				return ErrorJSON.serviceRefused(String.format("Le message %s n'existe pas", id_message), CodesErreur.ERREUR_MESSAGE_INEXISTANT);
			}
			
			// On liste les commentaires du message
			JSONObject reponse = LikesTools.listerLikes(id_message);
			
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
		} catch (ParseException e) {
			return ErrorJSON.serviceRefused(String.format("Erreur lors du parsing de la date du jour", clef), CodesErreur.ERREUR_PARSE_DATE);
		} catch (ClefInvalideException e) {
			return ErrorJSON.serviceRefused("Erreur, clef de session invalide", CodesErreur.ERREUR_CLEF_INVALIDE);
		} catch (IDTropPetitException e) {
			return ErrorJSON.serviceRefused("Erreur, ID message trop petit", CodesErreur.ERREUR_ID_TROP_COURT);
		} catch (IDTropGrandException e) {
			return ErrorJSON.serviceRefused("Erreur, ID message trop grand", CodesErreur.ERREUR_ID_TROP_LONG);
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
	 * @throws IDTropPetitException
	 * @throws IDTropGrandException
	 */
    private static void verificationTailleInput(String clef, String id_message) throws ClefInvalideException, IDTropPetitException, IDTropGrandException {
		if (clef.length() != Tailles.TAILLE_CLEF) {
			throw new ClefInvalideException();
		}
		
		if (id_message.length() < Tailles.MIN_ID) {
			throw new IDTropPetitException();
		} else if (id_message.length() > Tailles.MAX_ID) {
			throw new IDTropGrandException();
		}
	}
	
	
   /**
    * Verification de la validite des parametres
    * @return : Un booleen a true si les paramatres sont valides.
    */
	private static boolean verificationParametres(String clef, String id_message) {
		return (clef != null && id_message != null);
	}
}
