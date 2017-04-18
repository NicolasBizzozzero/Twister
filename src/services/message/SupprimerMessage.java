package services.message;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.ParseException;

import org.json.JSONObject;

import exceptions.ClefInexistanteException;
import exceptions.tailles.ClefInvalideException;
import exceptions.tailles.IDTropGrandException;
import exceptions.tailles.IDTropPetitException;
import exceptions.tailles.MessageTropGrandException;
import exceptions.tailles.MessageTropPetitException;
import outils.Tailles;
import bd.tools.SessionsTools;
import bd.tools.UtilisateursTools;
import services.CodesErreur;
import services.ErrorJSON;

public class SupprimerMessage {

	public static JSONObject supprimerMessage(String clef, String id_message) {			
			try {
				// On verifie qu'un des parametres obligatoire n'est pas null
				if (! verificationParametres(clef, id_message)){
					return ErrorJSON.serviceRefused("L'un des parametres est null", CodesErreur.ERREUR_ARGUMENTS);
				}
				
				// On verifie que les parametres entres respectent nos criteres de taille
				verificationTailleInput(clef, id_message);
				
				// On verifie que la clef de connexion existe
				boolean cleExiste = bd.tools.SessionsTools.clefExiste(clef);
				if (! cleExiste){
					return ErrorJSON.serviceRefused(String.format("La session %s n'existe pas", clef), CodesErreur.ERREUR_SESSION_INEXISTANTE);
				}
				
				// On recupere l'identifiant de la session
				String id = bd.tools.SessionsTools.getIDByClef(clef);
				
				// On verifie que l'utilisateur n'a pas ete inactif trop longtemps
				boolean isInactif = SessionsTools.estInactifDepuisTropLongtemps(clef);
				if (isInactif) {
					SessionsTools.suppressionCle(clef);
					return ErrorJSON.serviceRefused(String.format("L'utilisateur %s est inactif depuis trop longtemps", id), CodesErreur.ERREUR_UTILISATEUR_INACTIF);
				}
				
				// On verifie que l'utilisateur existe
				boolean isUser = UtilisateursTools.checkExistenceId(id);
				if (! isUser) {
					return ErrorJSON.serviceRefused(String.format("L'utilisateur %s n'existe pas", id), CodesErreur.ERREUR_UTILISATEUR_INEXISTANT);
				}
				
				// On verifie que le message existe
				if (! bd.tools.MessagesTools.messageExistant(id_message)){
					return ErrorJSON.serviceRefused(String.format("Le message %s n'existe pas", id_message), CodesErreur.ERREUR_MESSAGE_INEXISTANT);
				}
				
				// On verifie que l'utilisateur supprimant est bien l'auteur du message
				if (bd.tools.MessagesTools.getIDAuteur(id_message) != id) {
					return ErrorJSON.serviceRefused(String.format("L'utilisateur %s n'est pas l'auteur du message %s.", id, id_message), CodesErreur.ERREUR_PAS_AUTEUR);
				}
				
				// On supprime le message de la BDD
				bd.tools.MessagesTools.supprimerMessage(clef, id_message);
				
				// On met a jour le temps d'inactivite
				SessionsTools.updateTempsCle(clef);
		
				// On renvoie une reponse
				return new JSONObject();
				
			} catch (UnknownHostException e) {
				return ErrorJSON.serviceRefused("Hote inconnu", CodesErreur.ERREUR_HOTE_INCONNU);
			} catch (InstantiationException e) {
				return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donnees MySQL (InstantiationException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
			} catch (IllegalAccessException e) {
				return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donnees MySQL (IllegalAccessException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
			} catch (ClassNotFoundException e) {
				return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donnees MySQL (ClassNotFoundException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
			} catch (SQLException e) {
				return ErrorJSON.serviceRefused("Erreur, requete SQL Incorrecte", CodesErreur.ERREUR_SQL);
			} catch (ClefInexistanteException e) {
				return ErrorJSON.serviceRefused(String.format("La clef %s n'appartient pas a la base de donnees", clef), CodesErreur.ERREUR_CLEF_INEXISTANTE);
			} catch (ParseException e) {
				return ErrorJSON.serviceRefused(String.format("Erreur lors du parsing de la date du jour", clef), CodesErreur.ERREUR_PARSE_DATE);
			} catch (IDTropPetitException e) {
				return ErrorJSON.serviceRefused("Erreur, ID message trop petit", CodesErreur.ERREUR_ID_TROP_COURT);
			} catch (IDTropGrandException e) {
				return ErrorJSON.serviceRefused("Erreur, ID message trop grand", CodesErreur.ERREUR_ID_TROP_LONG);
			} catch (ClefInvalideException e) {
				return ErrorJSON.serviceRefused(String.format("Erreur, clef de session %s invalide", clef), CodesErreur.ERREUR_CLEF_INVALIDE);
			}
		}
		
		
		/**
		 * Verifie que les parametres entres respectent nos criteres de taille.
		 * Ces tailles sont situees dans le fichier services.Tailles.java
		 * Cette fonction lance une exception si un des parametres ne respecte
		 * pas ces criteres
		 * @param clef
		 * @param id_message
		 * @throws IDTropPetitException
		 * @throws IDTropGrandException
		 * @throws ClefInvalideException
		 */
	   private static void verificationTailleInput(String clef, String id_message) throws IDTropPetitException, IDTropGrandException, ClefInvalideException {
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
			return (id_message != null && clef != null);
		}
	}
