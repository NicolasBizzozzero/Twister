package services.authentification;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import bd.tools.AmitiesTools;
import bd.tools.SessionsTools;
import bd.tools.UtilisateursTools;
import services.CodesErreur;
import services.ErrorJSON;

public class Login {
	public static JSONObject login(String pseudo, String motDePasse) {
		try {
			// On verifie qu'un des parametres obligatoire n'est pas null
			if (! verificationParametres(pseudo, motDePasse)) {
				return ErrorJSON.serviceRefused("L'un des parametres est null", CodesErreur.ERREUR_ARGUMENTS);
			}
			
			// On verifie que l'utilisateur existe
			boolean isUser = UtilisateursTools.verificationExistencePseudo(pseudo);
			if (! isUser) {
				return ErrorJSON.serviceRefused("L'utilisateur n'existe pas", CodesErreur.ERREUR_UTILISATEUR_INEXISTANT);
			}
			
			// On recupere l'identifiant de l'utilisateur
			String identifiant = UtilisateursTools.getIdUtilisateur(pseudo);
			
			// On verifie que l'utilisateur n'est pas deja connecte
			boolean isConnecte = SessionsTools.estConnecte(identifiant);
			if (isConnecte) {
				return ErrorJSON.serviceRefused("L'utilisateur est deja connecte", CodesErreur.ERREUR_UTILISATEUR_DEJA_CONNECTE);
			}
			
			// On hash le mot de passe
			motDePasse = outils.MesMethodes.hasherMotDePasse(motDePasse);
			
			// On verifie que le couple (login, mot de passe) fonctionne
			boolean passwordOk = UtilisateursTools.checkMotDePasseAvecPseudo(pseudo, motDePasse);
			if (! passwordOk) {
				return ErrorJSON.serviceRefused("Erreur, mot de passe incorrect", CodesErreur.ERREUR_MDP_INCORRECT);
			}

			// On insere la session dans la base de donnees
			boolean estAdministrateur = estAdministrateur(pseudo);
			String key = SessionsTools.insertSession(identifiant, estAdministrateur);
			
			// On recupere la liste des id des gens suivis par l'utilisateur
			JSONObject jsonIDSuivis = AmitiesTools.listerTousLesAmis(identifiant);
			
			// On transforme le JSONObject en JSONArray
			JSONArray listeIDSuivis = jsonIDSuivis.getJSONArray("Amis");

			// On genere une reponse
			JSONObject retour = new JSONObject();
			retour.put("id", identifiant);
			retour.put("pseudo", pseudo);
			retour.put("clef", key);
			retour.put("suivis", listeIDSuivis);
			return retour;

		} catch (SQLException e) {
			return ErrorJSON.serviceRefused("Erreur, requete SQL incorrecte", CodesErreur.ERREUR_SQL);
		} catch (InstantiationException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donnees MySQL (InstantiationException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
		} catch (IllegalAccessException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donnees MySQL (IllegalAccessException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
		} catch (ClassNotFoundException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donnees MySQL (ClassNotFoundException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
		} catch (NoSuchAlgorithmException e) {
			return ErrorJSON.serviceRefused("Erreur lors du hashage du mot de passe", CodesErreur.ERREUR_HASHAGE);
		} 
	}
	
	private static boolean verificationParametres(String login, String motDePasse) {
		return (login != null && motDePasse != null);
	}
	
	private static boolean estAdministrateur(String login) {
		return (login.equals("SuperAlexia")) || (login.equals("SuperNicolas"));
	}
}
