package services.like;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.ParseException;

import org.json.JSONObject;

import services.CodesErreur;
import services.ErrorJSON;
import services.Tailles;
import bd.tools.LikesTools;
import bd.tools.SessionsTools;
import exceptions.ClefInexistanteException;
import exceptions.tailles.ClefInvalideException;
import exceptions.tailles.IDTropGrandException;
import exceptions.tailles.IDTropPetitException;
import exceptions.tailles.MessageTropGrandException;
import exceptions.tailles.MessageTropPetitException;
import exceptions.tailles.TypeLikeTropGrandException;
import exceptions.tailles.TypeLikeTropPetitException;

public class AjouterLike {

	
	/**
	 * Ajoute un like dans un message donne
	 * @param clef : La clef de session de l'utilisateur ajoutant le like
	 * @param id_message: L'ID du message sur lequel on ajoute le like
	 * @param type_like : Le type du like qu'on ajoute
	 */
	public static JSONObject ajouterLike(String clef, String id_message, String type_like) {
		try {
			// On verifie qu'un des parametres obligatoire n'est pas null
			if (! verificationParametres(type_like, id_message, clef)){
				return ErrorJSON.serviceRefused("L'un des parametres est null", CodesErreur.ERREUR_ARGUMENTS);
			}
			
			// On verifie que les parametres entres respectent nos criteres de taille
			verificationTailleInput(clef, id_message, type_like);
			
			// On verifie que la clef de connexion existe
			boolean cleExiste = SessionsTools.clefExiste(clef);
			if (! cleExiste){
				return ErrorJSON.serviceRefused(String.format("La session %s n'existe pas", clef), CodesErreur.ERREUR_SESSION_INEXISTANTE);
			}

			// On recupere l'identifiant de la session
			String id_likeur = SessionsTools.getIDByClef(clef);
			
			// On verifie que l'utilisateur n'a pas ete inactif trop longtemps
			boolean isInactif = SessionsTools.estInactifDepuisTropLongtemps(clef);
			if (isInactif) {
				SessionsTools.suppressionCle(clef);
				return ErrorJSON.serviceRefused(String.format("L'utilisateur %s est inactif depuis trop longtemps", id_likeur), CodesErreur.ERREUR_UTILISATEUR_INACTIF);
			}
			
			// On verifie que le message sur lequel ajouter un like existe
			if (! bd.tools.MessagesTools.messageExistant(id_message)){
				return ErrorJSON.serviceRefused(String.format("Le message %s n'existe pas", id_message), CodesErreur.ERREUR_MESSAGE_INEXISTANT);
			}
			
			// On verifie que le type de like a ajouter existe
			if (! LikesTools.likeExiste(type_like)) {
				return ErrorJSON.serviceRefused(String.format("Le type de like \"%s\" n'existe pas.", type_like), CodesErreur.ERREUR_TYPE_LIKE_INCONNU);
			}
			
			// On ajoute le like au message
			LikesTools.ajouterLike(id_likeur, id_message, type_like);
			
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
		} catch (ClefInvalideException e) {
			return ErrorJSON.serviceRefused(String.format("Erreur, clef de session %s invalide", clef), CodesErreur.ERREUR_CLEF_INVALIDE);
		} catch (IDTropPetitException e) {
			return ErrorJSON.serviceRefused("Erreur, ID message trop petit", CodesErreur.ERREUR_ID_TROP_COURT);
		} catch (IDTropGrandException e) {
			return ErrorJSON.serviceRefused("Erreur, ID message trop grand", CodesErreur.ERREUR_ID_TROP_LONG);
		} catch (TypeLikeTropPetitException e) {
			return ErrorJSON.serviceRefused("Erreur, Type Like trop petit", CodesErreur.ERREUR_TYPE_LIKE_TROP_COURT);
		} catch (TypeLikeTropGrandException e) {
			return ErrorJSON.serviceRefused("Erreur, Type Like trop grand", CodesErreur.ERREUR_TYPE_LIKE_TROP_LONG);
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
	 * @throws TypeLikeTropPetitException
	 * @throws TypeLikeTropGrandException
	 */
    private static void verificationTailleInput(String clef, String id_message, String type_like) throws ClefInvalideException, IDTropPetitException, IDTropGrandException, TypeLikeTropPetitException, TypeLikeTropGrandException {
		if (clef.length() != Tailles.TAILLE_CLEF) {
			throw new ClefInvalideException();
		}
		
		if (id_message.length() < Tailles.MIN_ID) {
			throw new IDTropPetitException();
		} else if (id_message.length() > Tailles.MAX_ID) {
			throw new IDTropGrandException();
		}
		
		if (type_like.length() < Tailles.MIN_TYPE_LIKE) {
			throw new TypeLikeTropPetitException();
		} else if (type_like.length() > Tailles.MAX_TYPE_LIKE) {
			throw new TypeLikeTropGrandException();
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
