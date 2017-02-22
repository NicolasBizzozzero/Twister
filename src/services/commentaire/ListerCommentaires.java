package services.commentaire;

import java.net.UnknownHostException;
import java.sql.SQLException;

import org.json.JSONObject;

import exceptions.ClefInexistanteException;
import services.CodesErreur;
import services.ErrorJSON;
import bd.tools.UtilisateursTools;

public class ListerCommentaires {
	public static JSONObject listerCommentaires(String clef, int index_debut, int limite) {
		if (! verificationParametres(clef)){
			return ErrorJSON.serviceRefused("La cle de session est null", CodesErreur.ERREUR_ARGUMENTS);
		}
		
		try {
			//on verifie que la clef de connexion existe
			boolean cleExiste=bd.tools.SessionsTools.clefExiste(clef);
			if (! cleExiste){
				return ErrorJSON.serviceRefused(String.format("La session %s n'existe pas", clef), CodesErreur.ERREUR_SESSION_INEXISTANTE);
			}
			
			// On recupere l'identifiant de la session
			String id_utilisateur = bd.tools.SessionsTools.getIDbyClef(clef);
			
			// On verifie que l'utilisateur existe
			boolean isUser = UtilisateursTools.verificationExistenceId(id_utilisateur);
			if (! isUser) {
				return ErrorJSON.serviceRefused(String.format("L'utilisateur %s n'existe pas", id_utilisateur), CodesErreur.ERREUR_UTILISATEUR_INEXISTANT);
			}
	
			// On recupere les commentaires
			JSONObject reponse = bd.tools.CommentairesTools.listerCommentaires(id_utilisateur, index_debut, limite);
	
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
	private static boolean verificationParametres(String cle) {
		return (cle != null);
	}
}
