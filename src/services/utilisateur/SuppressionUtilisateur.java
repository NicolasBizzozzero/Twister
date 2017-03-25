package services.utilisateur;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;

import org.json.JSONObject;

import exceptions.ClefInexistanteException;
import bd.tools.AmitiesTools;
import bd.tools.SessionsTools;
import bd.tools.UtilisateursTools;
import services.CodesErreur;
import services.ErrorJSON;

public class SuppressionUtilisateur {

	public static JSONObject suppressionUtilisateur(String clef, String motDePasse) {
		try {
			// On verifie qu'un des parametres obligatoire n'est pas null
			if (! verificationParametres(clef, motDePasse)){
				return ErrorJSON.serviceRefused("Erreur, le mot de passe doit etre renseigne", CodesErreur.ERREUR_ARGUMENTS);
			}

			// On hash le mot de passe
			motDePasse = outils.MesMethodes.hasherMotDePasse(motDePasse);

			// On recupere l'ID de l'utilisateur
			String id_utilisateur = SessionsTools.getIDByClef(clef);
			
			// On verifie que l'utilisateur n'a pas ete inactif trop longtemps
			boolean isInactif = SessionsTools.estInactifDepuisTropLongtemps(clef);
			if (isInactif) {
				SessionsTools.suppressionCle(clef);
				return ErrorJSON.serviceRefused(String.format("L'utilisateur %s est inactif depuis trop longtemps", id_utilisateur), CodesErreur.ERREUR_UTILISATEUR_INACTIF);
			}
			
			// On verifie que le mot de passe est le bon
			boolean motDePasseCorrect = UtilisateursTools.checkMotDePasseAvecId(id_utilisateur, motDePasse);
			if (! motDePasseCorrect) {
				return ErrorJSON.serviceRefused("Erreur, mot de passe incorrect", CodesErreur.ERREUR_MDP_INCORRECT);
			}

			// On supprime l'utilisateur
			UtilisateursTools.supprimerUtilisateurAvecId(id_utilisateur);
			
			// On supprime toute relation d'amitie avec cet utilisateur
			AmitiesTools.supprimerAmitiesConcernant(id_utilisateur);
			
			// On le deconnecte
			SessionsTools.suppressionCle(clef);

			// On renvoie une reponse
			return new JSONObject();

		} catch (NoSuchAlgorithmException e) {
			return ErrorJSON.serviceRefused("Erreur lors du hashage du mot de passe", CodesErreur.ERREUR_HASHAGE);
		} catch (InstantiationException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donnees MySQL (InstantiationException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
		} catch (IllegalAccessException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donnees MySQL (IllegalAccessException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
		} catch (ClassNotFoundException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donnees MySQL (ClassNotFoundException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
		} catch (SQLException e) {
			return ErrorJSON.serviceRefused("Erreur, requete SQL Incorrecte", CodesErreur.ERREUR_SQL);
		} catch (ClefInexistanteException e) {
			return ErrorJSON.serviceRefused(String.format("La clef %s n'est pas presente dans la Base de donnees", clef), CodesErreur.ERREUR_CLEF_INEXISTANTE);
		}  catch (ParseException e) {
			return ErrorJSON.serviceRefused(String.format("Erreur lors du parsing de la date du jour", clef), CodesErreur.ERREUR_PARSE_DATE);
		}
	}

	
   /**
    * Verification de la validite des parametres.
    * @return : Un boolean a true si les parametres sont valides.
    */
	private static boolean verificationParametres(String clef, String motDePasse) {
		return (clef != null && motDePasse != null);
	}
}
