package services.amis;

import java.sql.SQLException;
import java.text.ParseException;

import org.json.JSONObject;

import bd.tools.AmitiesTools;
import bd.tools.SessionsTools;
import bd.tools.UtilisateursTools;
import exceptions.ClefInexistanteException;
import exceptions.IndexInvalideException;
import exceptions.tailles.ClefInvalideException;
import exceptions.tailles.IDTropGrandException;
import exceptions.tailles.IDTropPetitException;
import exceptions.tailles.IndexDebutTropGrandException;
import exceptions.tailles.IndexDebutTropPetitException;
import exceptions.tailles.NombreDemandesTropGrandException;
import exceptions.tailles.NombreDemandesTropPetitException;
import services.CodesErreur;
import services.ErrorJSON;
import services.Tailles;

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
			
			// On verifie que les parametres entres respectent nos criteres de taille
			verificationTailleInput(clef, id_utilisateur, index_debut, nombre_demandes);
			
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
			boolean isUser = UtilisateursTools.checkExistenceId(id_utilisateur);
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
		} catch (ClefInvalideException e) {
			return ErrorJSON.serviceRefused(String.format("Erreur, clef de session %s invalide", clef), CodesErreur.ERREUR_CLEF_INVALIDE);
		} catch (IDTropPetitException e) {
			return ErrorJSON.serviceRefused(String.format("Erreur, ID d'ami trop petit : %s", id_utilisateur), CodesErreur.ERREUR_ID_TROP_COURT);
		} catch (IDTropGrandException e) {
			return ErrorJSON.serviceRefused(String.format("Erreur, ID d'ami trop grand : %s", id_utilisateur), CodesErreur.ERREUR_ID_TROP_LONG);
		} catch (IndexDebutTropPetitException e) {
			return ErrorJSON.serviceRefused(String.format("Erreur, index debut trop petit : %s", index_debut), CodesErreur.ERREUR_INDEX_DEBUT_TROP_COURT);
		} catch (IndexDebutTropGrandException e) {
			return ErrorJSON.serviceRefused(String.format("Erreur, index debut trop grand : %s", index_debut), CodesErreur.ERREUR_INDEX_DEBUT_TROP_LONG);
		} catch (NombreDemandesTropPetitException e) {
			return ErrorJSON.serviceRefused(String.format("Erreur, nombre demandes trop petit : %s", index_debut), CodesErreur.ERREUR_NOMBRE_DEMANDES_TROP_COURT);
		} catch (NombreDemandesTropGrandException e) {
			return ErrorJSON.serviceRefused(String.format("Erreur, nombre demandes trop grand : %s", index_debut), CodesErreur.ERREUR_NOMBRE_DEMANDES_TROP_LONG);
		}
	}

	
    private static void verificationTailleInput(String clef, String id_utilisateur, String index_debut,
			String nombre_demandes) throws ClefInvalideException, IDTropPetitException, IDTropGrandException, IndexDebutTropPetitException, IndexDebutTropGrandException, NombreDemandesTropPetitException, NombreDemandesTropGrandException {
    	if (clef.length() != Tailles.TAILLE_CLEF) {
			throw new ClefInvalideException();
		} else if (id_utilisateur.length() < Tailles.MIN_ID) {
			throw new IDTropPetitException();
	    } else if (id_utilisateur.length() > Tailles.MAX_ID) {
			throw new IDTropGrandException();
	    } else if (index_debut.length() < Tailles.MIN_INT) {
	    	throw new IndexDebutTropPetitException();
	    } else if (index_debut.length() > Tailles.MAX_INT) {
	    	throw new IndexDebutTropGrandException();
	    } else if (nombre_demandes.length() < Tailles.MIN_INT) {
	    	throw new NombreDemandesTropPetitException();
	    } else if (nombre_demandes.length() > Tailles.MAX_INT) {
	    	throw new NombreDemandesTropGrandException();
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
