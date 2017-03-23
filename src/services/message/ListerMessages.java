package services.message;

import java.net.UnknownHostException;
import java.sql.SQLException;

import org.json.JSONObject;

import exceptions.ClefInexistanteException;
import services.CodesErreur;
import services.ErrorJSON;
import bd.tools.UtilisateursTools;

public class ListerMessages {
	
	
	/**
	 * Permet d'obtenir les messages ecrits par un utilisateur. 
	 * @param clef : La clef de la session
	 * @param recherche : Les mots clefs de la recherche (vide si pas de mot clef)
	 * @param id_utilisateur : L'ID de l'utilisateur dont on veut les messages (-1 si on est sur la page principale et que l'on souhaite avoir les messages de tout le monde)
	 * @param id_max : l'ID de chaque message retourne doit etre inferieur a 'id_max'
	 * @param id_min : l'ID de chaque message retourne doit etre superieur a 'id_min'
	 * @param limite : Nombre de messages a retourner (-1 si pas de limite)
	 * @return Un JSONObject contenant les messages demandes
	 */
	public static JSONObject listerMessages(String clef, String recherche, String id_utilisateur, String id_max, String id_min, String limite) {
		if (! verificationParametres(clef, recherche, id_utilisateur, id_max, id_min, limite)){
			return ErrorJSON.serviceRefused("Un des parametres est null", CodesErreur.ERREUR_ARGUMENTS);
		}
		
		try {
			// On verifie que la clef de connexion existe
			boolean cleExiste = bd.tools.SessionsTools.clefExiste(clef);
			if (! cleExiste){
				return ErrorJSON.serviceRefused(String.format("La session %s n'existe pas", clef), CodesErreur.ERREUR_SESSION_INEXISTANTE);
			}
			
			// On recupere l'identifiant de la session
			String id_session = bd.tools.SessionsTools.getIDByClef(clef);
			
			// On verifie que l'utilisateur existe
			boolean isUser = UtilisateursTools.verificationExistenceId(id_session);
			if (! isUser) {
				return ErrorJSON.serviceRefused(String.format("L'utilisateur %s n'existe pas", id_utilisateur), CodesErreur.ERREUR_UTILISATEUR_INEXISTANT);
			}
	
			// On recupere les messages
			JSONObject reponse;
			if (id_utilisateur.equals("-1")) {
				reponse = bd.tools.MessagesTools.listerMessagesToutLeMonde(recherche, id_max, id_min, limite);
			} else {
				reponse = bd.tools.MessagesTools.listerMessagesUtilisateur(recherche, id_utilisateur, id_max, id_min, limite);
			}
			
	
			// On renvoie une reponse
			return reponse;
		}  catch (InstantiationException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donnees MySQL (InstantiationException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
		} catch (IllegalAccessException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donnees MySQL (IllegalAccessException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
		} catch (ClassNotFoundException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donnees MySQL (ClassNotFoundException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
		} catch (SQLException e) {
			return ErrorJSON.serviceRefused("Erreur, requete SQL Incorrecte", CodesErreur.ERREUR_SQL);
		} catch (UnknownHostException e) {
			return ErrorJSON.serviceRefused("Hote inconnu", CodesErreur.HOTE_INCONNU);
		} catch (ClefInexistanteException e) {
			return ErrorJSON.serviceRefused(String.format("La clef %s n'appartient pas a la base de donnees", clef), CodesErreur.ERREUR_CLEF_INEXISTANTE);
		}
	}
	
	
   /**
    * Verification de la validite des parametres
    * @return : Un booleen a true si les paramatres sont valides.
    */
	private static boolean verificationParametres(String clef, String[] recherche, String id_utilisateur, String id_min, String id_max, String limite) {
		return (clef != null) && (recherche != null) && (id_utilisateur != null) && (id_min != null) && (id_max != null) && (limite != null);
	}
}
