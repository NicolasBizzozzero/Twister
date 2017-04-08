package services.utilisateur;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;

import org.json.JSONObject;

import exceptions.AnniversaireInvalideException;
import exceptions.ClefInexistanteException;
import exceptions.tailles.AnniversaireTropGrandException;
import exceptions.tailles.AnniversaireTropPetitException;
import exceptions.tailles.ClefInvalideException;
import exceptions.tailles.EmailTropGrandException;
import exceptions.tailles.EmailTropPetitException;
import exceptions.tailles.MotDePasseTropGrandException;
import exceptions.tailles.MotDePasseTropPetitException;
import exceptions.tailles.NomTropGrandException;
import exceptions.tailles.NomTropPetitException;
import exceptions.tailles.NouveauMotDePasseTropGrandException;
import exceptions.tailles.NouveauMotDePasseTropPetitException;
import exceptions.tailles.PrenomTropGrandException;
import exceptions.tailles.PrenomTropPetitException;
import exceptions.tailles.PseudoTropGrandException;
import exceptions.tailles.PseudoTropPetitException;
import bd.tools.SessionsTools;
import bd.tools.UtilisateursTools;
import services.CodesErreur;
import services.ErrorJSON;
import services.Tailles;

public class ModificationUtilisateur {
	public static JSONObject modificationUtilisateur(String clef, String motDePasse, String nouveauPseudo, String nouveauMotDePasse, String nouvelEmail, String nouveauPrenom, String nouveauNom, String nouvelAnniversaire) {
		try {
			// On verifie qu'un des parametres obligatoire n'est pas null
			if (! verificationParametres(clef, motDePasse)){
				return ErrorJSON.serviceRefused("Erreur, un des parametres est null", CodesErreur.ERREUR_ARGUMENTS);
			}
			
			// On verifie que les parametres entres respectent nos criteres de taille
			verificationTailleInput(clef, motDePasse, nouveauPseudo, nouveauMotDePasse, nouvelEmail, nouveauPrenom, nouveauNom, nouvelAnniversaire);

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
			
			// On hash le mot de passe
			motDePasse = outils.MesMethodes.hasherMotDePasse(motDePasse);
			
			// On verifie que le mot de passe est le bon
			boolean motDePasseCorrect = UtilisateursTools.checkMotDePasseAvecId(id_utilisateur, motDePasse);
			if (! motDePasseCorrect) {
				return ErrorJSON.serviceRefused("Erreur, mot de passe incorrect", CodesErreur.ERREUR_MDP_INCORRECT);
			}

			/* Avant de modifier les variables de l'utilisateur, on regarde d'abord si 
			 * toutes celles devant etre modifies peuvent l'etre.
			 * Cela nous permet de ne pas modifier seulement une partie des variables
			 * en cas d'erreur. */
			// Pseudo
			if (nouveauPseudo != null) {
				// On verifie que le nouveau pseudo n'est pas deja prit avant de le modifier
				boolean pseudoExiste = UtilisateursTools.checkExistencePseudo(nouveauPseudo);
				if (pseudoExiste) {
					return ErrorJSON.serviceRefused(String.format("Erreur, le pseudo %s est deja prit.", nouveauPseudo), CodesErreur.ERREUR_PSEUDO_DEJA_PRIT);
				}
			}
			
			// Email
			if (nouvelEmail != null) {
				// On verifie que le nouvel email est bien unique
				boolean emailExiste = UtilisateursTools.checkExistenceEmail(nouvelEmail);
				if (emailExiste) {
					return ErrorJSON.serviceRefused(String.format("Erreur, l'email \"%s\" est deja prit.", nouvelEmail), CodesErreur.ERREUR_EMAIL_DEJA_PRIT);
				}
			}
			
			// Anniversaire
			if (nouvelAnniversaire != null) {
				if (nouvelAnniversaire.length() != 0) {
					if (! services.utilisateur.CreationUtilisateur.estValide(nouvelAnniversaire)) {
						return ErrorJSON.serviceRefused(String.format("Erreur, l'anniversaire %s est invalide.", nouvelAnniversaire), CodesErreur.ERREUR_ANNIVERSAIRE_INVALIDE);
					}
				}
			}
			
			/* Maintenant que nous savons que si nous changeons un parametre, tout
			 * fonctionnera, on modifie les parametres renseignes uns par uns */
			// Changement du pseudo
			if (nouveauPseudo != null) {
				UtilisateursTools.modifierPseudo(id_utilisateur, nouveauPseudo);
			}
			
			// Changement du mot de passe
			if (nouveauMotDePasse != null) {
				UtilisateursTools.modifierMotDePasse(id_utilisateur, outils.MesMethodes.hasherMotDePasse(nouveauMotDePasse));
			}
			
			// Changement de l'email
			if (nouvelEmail != null) {
				UtilisateursTools.modifierEmail(id_utilisateur, nouvelEmail);
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
				if (nouvelAnniversaire.length() == 0) {
					UtilisateursTools.supprimerAnniversaire(id_utilisateur);		
				} else {
					UtilisateursTools.modifierAnniversaire(id_utilisateur, nouvelAnniversaire);
				}
			}

			// On renvoie une reponse
			JSONObject reponse = new JSONObject();
			return reponse;
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
		} catch (ParseException e) {
			return ErrorJSON.serviceRefused(String.format("Erreur lors du parsing de la date du jour"), CodesErreur.ERREUR_PARSE_DATE);
		} catch (ClefInvalideException e) {
			return ErrorJSON.serviceRefused(String.format("Erreur, clef de session %s invalide", clef), CodesErreur.ERREUR_CLEF_INVALIDE);
		} catch (MotDePasseTropPetitException e) {
			return ErrorJSON.serviceRefused("Erreur, mot de passe trop petit", CodesErreur.ERREUR_MDP_TROP_COURT);
		} catch (MotDePasseTropGrandException e) {
			return ErrorJSON.serviceRefused("Erreur, mot de passe trop grand", CodesErreur.ERREUR_MDP_TROP_LONG);
		} catch (PseudoTropPetitException e) {
			return ErrorJSON.serviceRefused("Erreur, pseudo trop petit", CodesErreur.ERREUR_PSEUDO_TROP_COURT);
		} catch (PseudoTropGrandException e) {
			return ErrorJSON.serviceRefused("Erreur, pseudo trop grand", CodesErreur.ERREUR_PSEUDO_TROP_GRAND);
		} catch (NouveauMotDePasseTropPetitException e) {
			return ErrorJSON.serviceRefused("Erreur, nouveau mot de passe trop petit", CodesErreur.ERREUR_NOUVEAU_MDP_TROP_COURT);
		} catch (NouveauMotDePasseTropGrandException e) {
			return ErrorJSON.serviceRefused("Erreur, nouveau mot de passe trop grand", CodesErreur.ERREUR_NOUVEAU_MDP_TROP_LONG);
		} catch (EmailTropPetitException e) {
			return ErrorJSON.serviceRefused("Erreur, email trop petit", CodesErreur.ERREUR_EMAIL_TROP_COURT);
		} catch (EmailTropGrandException e) {
			return ErrorJSON.serviceRefused("Erreur, email trop grand", CodesErreur.ERREUR_EMAIL_TROP_LONG);
		} catch (PrenomTropPetitException e) {
			return ErrorJSON.serviceRefused("Erreur, prenom trop petit", CodesErreur.ERREUR_PRENOM_TROP_COURT);
		} catch (PrenomTropGrandException e) {
			return ErrorJSON.serviceRefused("Erreur, prenom trop grand", CodesErreur.ERREUR_PRENOM_TROP_LONG);
		} catch (NomTropPetitException e) {
			return ErrorJSON.serviceRefused("Erreur, nom trop petit", CodesErreur.ERREUR_NOM_TROP_COURT);
		} catch (NomTropGrandException e) {
			return ErrorJSON.serviceRefused("Erreur, nom trop grand", CodesErreur.ERREUR_NOM_TROP_LONG);
		} catch (AnniversaireTropPetitException e) {
			return ErrorJSON.serviceRefused("Erreur, anniversaire trop petit", CodesErreur.ERREUR_ANNIVERSAIRE_TROP_COURT);
		} catch (AnniversaireTropGrandException e) {
			return ErrorJSON.serviceRefused("Erreur, anniversaire trop grand", CodesErreur.ERREUR_ANNIVERSAIRE_TROP_LONG);
		}
	}

	
	/**
	 * Verifie que les parametres entres respectent nos criteres de taille.
	 * Ces tailles sont situees dans le fichier services.Tailles.java
	 * Cette fonction lance une exception si un des parametres ne respecte
	 * pas ces criteres
	 * @param clef
	 * @param motDePasse
	 * @param nouveauPseudo
	 * @param nouveauMotDePasse
	 * @param nouvelEmail
	 * @param nouveauPrenom
	 * @param nouveauNom
	 * @param nouvelAnniversaire
	 * @throws ClefInvalideException 
	 * @throws MotDePasseTropPetitException 
	 * @throws MotDePasseTropGrandException 
	 * @throws PseudoTropPetitException 
	 * @throws PseudoTropGrandException
	 * @throws NouveauMotDePasseTropPetitException 
	 * @throws NouveauMotDePasseTropGrandException 
	 * @throws EmailTropPetitException 
	 * @throws EmailTropGrandException 
	 * @throws PrenomTropPetitException 
	 * @throws PrenomTropGrandException 
	 * @throws NomTropPetitException 
	 * @throws NomTropGrandException 
	 * @throws AnniversaireTropPetitException 
	 * @throws AnniversaireTropGrandException 
	 */
	private static void verificationTailleInput(String clef, String motDePasse, String nouveauPseudo,
			String nouveauMotDePasse, String nouvelEmail, String nouveauPrenom, String nouveauNom,
			String nouvelAnniversaire) throws ClefInvalideException, MotDePasseTropPetitException, MotDePasseTropGrandException, PseudoTropPetitException, PseudoTropGrandException, NouveauMotDePasseTropPetitException, NouveauMotDePasseTropGrandException, EmailTropPetitException, EmailTropGrandException, PrenomTropPetitException, PrenomTropGrandException, NomTropPetitException, NomTropGrandException, AnniversaireTropPetitException, AnniversaireTropGrandException {
		// Clef et mot de passe
		if (clef.length() != Tailles.TAILLE_CLEF) {
			throw new ClefInvalideException();
		} else if (motDePasse.length() < Tailles.MIN_MOT_DE_PASSE) {
			throw new MotDePasseTropPetitException();
		} else if (motDePasse.length() > Tailles.MAX_MOT_DE_PASSE) {
			throw new MotDePasseTropGrandException();
		}
		
		// Nouveau pseudo
		if (nouveauPseudo != null) {
			if (nouveauPseudo.length() < Tailles.MIN_PSEUDO) {
				throw new PseudoTropPetitException();
			} else if (nouveauPseudo.length() > Tailles.MAX_PSEUDO) {
				throw new PseudoTropGrandException();
			}
		}
		
		// Nouveau mot de passe
		if (nouveauMotDePasse != null) {
			if (nouveauMotDePasse.length() < Tailles.MIN_MOT_DE_PASSE) {
				throw new NouveauMotDePasseTropPetitException();
			} else if (nouveauMotDePasse.length() > Tailles.MAX_MOT_DE_PASSE) {
				throw new NouveauMotDePasseTropGrandException();
			}
		}
		
		// Nouvel email
		if (nouvelEmail != null) {
			if (nouvelEmail.length() < Tailles.MIN_EMAIL) {
				throw new EmailTropPetitException();
			} else if (nouvelEmail.length() > Tailles.MAX_EMAIL) {
				throw new EmailTropGrandException();
			}
		}		
		
		// Nouveau prenom
		if (nouveauPrenom != null) {
			if (nouveauPrenom.length() < Tailles.MIN_PRENOM) {
				throw new PrenomTropPetitException();
			} else if (nouveauPrenom.length() > Tailles.MAX_PRENOM) {
				throw new PrenomTropGrandException();
			}
		}
		
		// Nouveau nom
		if (nouveauNom != null) {
			if (nouveauNom.length() < Tailles.MIN_NOM) {
				throw new NomTropPetitException();
			} else if (nouveauNom.length() > Tailles.MAX_NOM) {
				throw new NomTropGrandException();
			}
		}
		
		// Nouvel anniversaire
		if (nouvelAnniversaire != null) {
			if (nouvelAnniversaire.length() < Tailles.MIN_ANNIVERSAIRE) {
				throw new AnniversaireTropPetitException();
			} else if (nouvelAnniversaire.length() > Tailles.MAX_ANNIVERSAIRE) {
				throw new AnniversaireTropGrandException();
			}
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
