package services.amis;

import java.sql.SQLException;
import java.text.ParseException;

import org.json.JSONObject;

import bd.tools.AmitiesTools;
import bd.tools.SessionsTools;
import bd.tools.UtilisateursTools;
import exceptions.ClefInexistanteException;
import exceptions.IndexInvalideException;
import services.CodesErreur;
import services.ErrorJSON;

public class ListerAmis {
	
	
	/**
	 * Liste nombre_demandes amis d'un utilisateur, tries par date d'ajout et
	 * dont l'index commence a index_debut.
	 * @param clef : La clef de session de l'utilisateur faisant la requete
	 * @param id_utilisateur : L'ID de l'utilisateur dont on veut lister les amis
	 * @param index_debut : L'index par lequel on veut commencer a lister les amis
	 * @param nombre_demandes : Le nombre d'amis souhaites
	 * @return Un JSONObject contenant les amis demandes. Il est de la forme:
	 * {"Amis": [{"id_ami2": 123}, "id_ami2": 546}, ...]}
	 * En cas d'erreur, un JSONObject d'erreur sera genere a la place.
	 */
	public static JSONObject listerAmis(String clef, String id_utilisateur, String index_debut, String nombre_demandes) {
		try {
			// On verifie qu'un des parametres obligatoire n'est pas null
			if (! verificationParametres(id_utilisateur)){
				return ErrorJSON.serviceRefused("L'un des parametres est null", CodesErreur.ERREUR_ARGUMENTS);
			}
			
			// On verifie que l'utilisateur est connecte
			boolean cleExiste = bd.tools.SessionsTools.clefExiste(clef);
			if (! cleExiste){
				return ErrorJSON.serviceRefused(String.format("La session %s n'existe pas", clef), CodesErreur.ERREUR_SESSION_INEXISTANTE);
			}
			
			// On verifie que l'utilisateur n'a pas ete inactif trop longtemps
			boolean isInactif = SessionsTools.estInactifDepuisTropLongtemps(clef);
			if (isInactif) {
				SessionsTools.suppressionCle(clef);
				return ErrorJSON.serviceRefused(String.format("L'utilisateur %s est inactif depuis trop longtemps", id_utilisateur), CodesErreur.ERREUR_UTILISATEUR_INACTIF);
			}
			
			// On verifie que l'ID de l'utilisateur existe
			boolean isUser = UtilisateursTools.verificationExistenceId(id_utilisateur);
			if (! isUser) {
				return ErrorJSON.serviceRefused(String.format("L'utilisateur %s n'existe pas", id_utilisateur), CodesErreur.ERREUR_UTILISATEUR_INEXISTANT);
			}

	        // On liste les amis
			JSONObject reponse = AmitiesTools.listerAmis(id_utilisateur, index_debut, nombre_demandes);
			
			// On met a jour le temps d'inactivite
			SessionsTools.updateTempsCle(clef);
	
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
		} catch (ClefInexistanteException e) {
			return ErrorJSON.serviceRefused(String.format("La clef %s n'est pas presente dans la Base de donnees", clef), CodesErreur.ERREUR_CLEF_INEXISTANTE);
		}  catch (ParseException e) {
			return ErrorJSON.serviceRefused(String.format("Erreur lors du parsing de la date du jour", clef), CodesErreur.ERREUR_PARSE_DATE);
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
