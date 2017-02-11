package services.utilisateur;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import org.json.JSONObject;

import bd.tools.UtilisateursTools;
import outils.StatutMotDePasse;
import services.CodesErreur;
import services.ErrorJSON;

public class ModificationUtilisateur {
	public static JSONObject modificationUtilisateur(String id, String motDePasse, String nouveauPseudo, String nouveauMotDePasse, String nouvelEmail, String nouveauPrenom, String nouveauNom, String nouvelAnniversaire) {
		if (! verificationParametres(motDePasse)){
			return ErrorJSON.serviceRefused("Erreur, le mot de passe doit etre renseigne", CodesErreur.ERREUR_ARGUMENTS);
		}

		try {
			// On hash le mot de passe
			motDePasse = outils.MesMethodes.hasherMotDePasse(motDePasse);

			// On verifie que l'id existe
			boolean idExiste = UtilisateursTools.verificationExistenceId(id);
			if (! idExiste) {
				return ErrorJSON.serviceRefused(String.format("Erreur, l'utilisateur d'ID %s n'existe pas.", id), CodesErreur.ERREUR_ID_INEXISTANT);
			}
			
			// On verifie que le mot de passe est le bon
			boolean motDePasseCorrect = UtilisateursTools.checkMotDePasseAvecId(id, motDePasse);
			if (! motDePasseCorrect) {
				return ErrorJSON.serviceRefused("Erreur, mot de passe incorrect", CodesErreur.ERREUR_MDP_INCORRECT);
			}

			// On va modifier les parametres renseignes uns par uns
			if (nouveauPseudo != null) {
				// On verifie que le nouveau pseudo n'est pas deja prit avant de le modifier
				boolean pseudoExiste = UtilisateursTools.verificationExistencePseudo(nouveauPseudo);
				if (pseudoExiste) {
					return ErrorJSON.serviceRefused(String.format("Erreur, le pseudo %s est déjà prit.", nouveauPseudo), CodesErreur.ERREUR_PSEUDO_DEJA_PRIT);
				} else {
					UtilisateursTools.modifierPseudo(id, nouveauPseudo);
				}
			}
			
			if (nouveauMotDePasse != null) {
				// On verifie que le nouveaumot de passe est securise
				StatutMotDePasse statutMotDePasse = outils.MesMethodes.verifierSecuriteMotDePasse(nouveauMotDePasse);
				switch (statutMotDePasse) {
					case TROP_COURT:
						return ErrorJSON.serviceRefused("Nouveau mot de passe trop court", CodesErreur.ERREUR_MDP_TROP_COURT);
					case TROP_LONG:
						return ErrorJSON.serviceRefused("Nouveau mot de passe trop long", CodesErreur.ERREUR_MDP_TROP_LONG);
					case NON_SECURISE:
						return ErrorJSON.serviceRefused("Nouveau mot de passe non securise", CodesErreur.ERREUR_MDP_NON_SECURISE);
					case SECURISE:
						break;
					default:
						break;					
				}
				UtilisateursTools.modifierMotDePasse(id, outils.MesMethodes.hasherMotDePasse(nouveauMotDePasse));
			}
			
			if (nouvelEmail != null) {
				UtilisateursTools.modifierEmail(id, nouvelEmail);
			}
			
			if (nouveauPrenom != null) {
				UtilisateursTools.modifierPrenom(id, nouveauPrenom);
			}
			
			if (nouveauNom != null) {
				UtilisateursTools.modifierNom(id, nouveauNom);
			}
			
			if (nouvelAnniversaire != null) {
				// TODO: verifier que l'anniversaire est valide
				UtilisateursTools.modifierAnniversaire(id, nouvelAnniversaire);
			}

			// On renvoie une reponse
			JSONObject reponse = new JSONObject();
			return reponse;
		} catch (NoSuchAlgorithmException e) {
			return ErrorJSON.serviceRefused("Erreur lors du hashage du mot de passe", CodesErreur.ERREUR_HASHAGE);
		}   catch (InstantiationException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donnees MySQL (InstantiationException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
		} catch (IllegalAccessException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donnees MySQL (IllegalAccessException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
		} catch (ClassNotFoundException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donnees MySQL (ClassNotFoundException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
		} catch (SQLException e) {
			return ErrorJSON.serviceRefused("Erreur, requete SQL Incorrecte", CodesErreur.ERREUR_SQL);
		}
	}

       /**
        * Verification de la validite des parametres.
        * @return : Un boolean a true si les parametres sont valides.
        */
		private static boolean verificationParametres(String motDePasse) {
			return (motDePasse != null);
		}
}
