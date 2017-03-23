package services.utilisateur;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;

import org.json.JSONObject;

import exceptions.AnniversaireInvalideException;
import exceptions.ClefInexistanteException;
import bd.tools.SessionsTools;
import bd.tools.UtilisateursTools;
import outils.StatutMotDePasse;
import services.CodesErreur;
import services.ErrorJSON;

public class ModificationUtilisateur {
	public static JSONObject modificationUtilisateur(String clef, String motDePasse, String nouveauPseudo, String nouveauMotDePasse, String nouvelEmail, String nouveauPrenom, String nouveauNom, String nouvelAnniversaire) {
		try {
			// On verifie qu'un des parametres obligatoire n'est pas null
			if (! verificationParametres(clef, motDePasse)){
				return ErrorJSON.serviceRefused("Erreur, un des parametres est null", CodesErreur.ERREUR_ARGUMENTS);
			}
			
			// On hash le mot de passe
			motDePasse = outils.MesMethodes.hasherMotDePasse(motDePasse);

			// On recupere l'ID de l'utilisateur
			String id_utilisateur = SessionsTools.getIDByClef(clef);

			// On verifie que l'utilisateur est connecte
			boolean estConnecte = SessionsTools.estConnecte(id_utilisateur);
			if (! estConnecte) {
				return ErrorJSON.serviceRefused(String.format("L'utilisateur %s n'est pas connecte", id_utilisateur), CodesErreur.ERREUR_UTILISATEUR_DECONNECTE);
			}
			
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

			// On va modifier les parametres renseignes uns par uns
			// Changement du pseudo
			if (nouveauPseudo != null) {
				// On verifie que le nouveau pseudo n'est pas deja prit avant de le modifier
				boolean pseudoExiste = UtilisateursTools.verificationExistencePseudo(nouveauPseudo);
				if (pseudoExiste) {
					return ErrorJSON.serviceRefused(String.format("Erreur, le pseudo %s est deja prit.", nouveauPseudo), CodesErreur.ERREUR_PSEUDO_DEJA_PRIT);
				} else {
					UtilisateursTools.modifierPseudo(id_utilisateur, nouveauPseudo);
				}
			}
			
			// Changement du mot de passe
			if (nouveauMotDePasse != null) {
				// On verifie que le nouveau mot de passe est securise
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
				UtilisateursTools.modifierMotDePasse(id_utilisateur, outils.MesMethodes.hasherMotDePasse(nouveauMotDePasse));
			}
			
			// Changement de l'email
			if (nouvelEmail != null) {
				// On verifie que le nouvel email est bien unique
				boolean emailExiste = UtilisateursTools.verificationExistenceEmail(nouvelEmail);
				if (emailExiste) {
					return ErrorJSON.serviceRefused(String.format("Erreur, l'email \"%s\" est deja prit.", nouvelEmail), CodesErreur.ERREUR_EMAIL_DEJA_PRIT);
				} else {
					UtilisateursTools.modifierEmail(id_utilisateur, nouvelEmail);
				}
			}
			
			// Changement du prenom
			if (nouveauPrenom != null) {
				UtilisateursTools.modifierPrenom(id_utilisateur, nouveauPrenom);
			}
			
			// Changement du nom
			if (nouveauNom != null) {
				UtilisateursTools.modifierNom(id_utilisateur, nouveauNom);
			}
			
			// Changement de l'anniversaire
			if (nouvelAnniversaire != null) {
				// TODO: verifier que l'anniversaire est valide
				UtilisateursTools.modifierAnniversaire(id_utilisateur, nouvelAnniversaire);
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
		}  catch (ClefInexistanteException e) {
			return ErrorJSON.serviceRefused(String.format("La clef %s n'est pas presente dans la Base de donnees", clef), CodesErreur.ERREUR_CLEF_INEXISTANTE);
		}  catch (ParseException e) {
			return ErrorJSON.serviceRefused(String.format("Erreur lors du parsing de la date du jour"), CodesErreur.ERREUR_PARSE_DATE);
		} catch (AnniversaireInvalideException e) {
			return ErrorJSON.serviceRefused(String.format("Erreur, la date d'anniversaire entree est invalide"), CodesErreur.ERREUR_ANNIVERSAIRE_INVALIDE);
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
