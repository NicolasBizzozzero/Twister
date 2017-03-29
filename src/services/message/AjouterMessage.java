package services.message;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.ParseException;

import org.json.JSONObject;

import exceptions.ClefInexistanteException;
import exceptions.tailles.ClefInvalideException;
import exceptions.tailles.MessageTropGrandException;
import exceptions.tailles.MessageTropPetitException;
import services.CodesErreur;
import services.ErrorJSON;
import services.Tailles;
import bd.tools.SessionsTools;
import bd.tools.UtilisateursTools;
import bd.tools.MessagesTools;


public class AjouterMessage {
	
	
	/**
	 * Ajoute le message d'un utilisateur dans la BDD MongoDB
	 * @param clef : La clef de session de l'utilisateur ajoutant le message
	 * @param contenu : Le contenu du message a ajouter
	 * @return Un JSONObject vide si tout va bien, ou avec un champ d'erreur sinon
	 */
	public static JSONObject ajouterMessage(String clef, String contenu) {
		try {
			// On verifie qu'un des parametres obligatoire n'est pas null
			if (! verificationParametres(contenu, clef)){
				return ErrorJSON.serviceRefused("L'un des parametres est null", CodesErreur.ERREUR_ARGUMENTS);
			}
			
			// On verifie que les parametres entres respectent nos criteres de taille
			verificationTailleInput(clef, contenu);
			
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
			
			// On ajoute le message a la BDD
			MessagesTools.ajouterMessage(id_auteur, contenu);
			
			// On met a jour le temps d'inactivite
			SessionsTools.updateTempsCle(clef);
	
			// On renvoie une reponse
			JSONObject reponse = new JSONObject();
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
		} catch (MessageTropPetitException e) {
			return ErrorJSON.serviceRefused("Erreur, Le message est trop petit", CodesErreur.ERREUR_MESSAGE_TROP_COURT);
		} catch (MessageTropGrandException e) {
			return ErrorJSON.serviceRefused("Erreur, Le message est trop grand", CodesErreur.ERREUR_MESSAGE_TROP_LONG);
		}
	}
	
	
	/**
	 * Verifie que les parametres entres respectent nos criteres de taille.
	 * Ces tailles sont situees dans le fichier services.Tailles.java
	 * Cette fonction lance une exception si un des parametres ne respecte
	 * pas ces criteres
	 * @param clef
	 * @param contenu
	 * @throws ClefInvalideException
	 * @throws MessageTropPetitException
	 * @throws MessageTropGrandException
	 */
   private static void verificationTailleInput(String clef, String contenu) throws ClefInvalideException, MessageTropPetitException, MessageTropGrandException {
		if (clef.length() != Tailles.TAILLE_CLEF) {
			throw new ClefInvalideException();
		}
		
		if (contenu.length() < Tailles.MIN_MESSAGE) {
			throw new MessageTropPetitException();
		} else if (contenu.length() > Tailles.MAX_MESSAGE) {
			throw new MessageTropGrandException();
		}
	}


/**
    * Verification de la validite des parametres
    * @return : Un booleen a true si les paramatres sont valides.
    */
	private static boolean verificationParametres(String contenu, String clef) {
		return (contenu != null && clef != null);
	}
}
