package services.utilisateur;

import java.sql.SQLException;

import javax.mail.MessagingException;

import java.security.NoSuchAlgorithmException;

import org.json.JSONObject;

import bd.tools.UtilisateursTools;
import services.CodesErreur;
import services.ErrorJSON;
import services.Tailles;
import exceptions.tailles.*;
import mails.MailsTools;


public class CreationUtilisateur {
	
	
	/**
	 * Ajoute un utilisateur dans la table "Utilisateurs" de la BDD MySQL
	 * @param pseudo : Le pseudo de l'utilisateur, doit etre unique
	 * @param motDePasse : Le mot de passe de l'utilisateur
	 * @param email : L'email de l'utilisateur, doit etre unique
	 * @param prenom : Le prenom de l'utilisateur, facultatif
	 * @param nom : Le nom de l'utilisateur, facultatif
	 * @param anniversaire : L'anniversaire de l'utilisateur, facultatif
	 * @return Un JSONObject vide en cas de succes, ou contenant un code d'erreur
	 * en cas d'erreur
	 */
	public static JSONObject creationUtilisateur(String pseudo, String motDePasse, String email, String prenom, String nom, String anniversaire) {
		try {
			// On verifie qu'un des parametres obligatoire n'est pas null
			if (! verificationParametres(pseudo, motDePasse, email)){
				return ErrorJSON.serviceRefused("Erreur, le pseudo, mot de passe et l'email doivent etre renseignes", CodesErreur.ERREUR_ARGUMENTS);
			}

			// On verifie que les parametres entres respectent nos criteres de taille
			verificationTailleInput(pseudo, motDePasse, email, prenom, nom, anniversaire);

			// On verifie que le mot de passe est securise
			if (! estSecurise(motDePasse)) {
				return ErrorJSON.serviceRefused(String.format("Erreur, le mot de passe n'est pas assez securise.", motDePasse), CodesErreur.ERREUR_MDP_NON_SECURISE);
			}
			
			// On verifie que le pseudo n'existe pas deja
			boolean isUser = UtilisateursTools.checkExistencePseudo(pseudo);
			if (isUser) {
				return ErrorJSON.serviceRefused("Erreur, l'utilisateur existe deja", CodesErreur.ERREUR_UTILISATEUR_EXISTANT);
			}
			
			// On verifie que l'email n'existe pas deja
			boolean emailExiste = UtilisateursTools.checkExistenceEmail(email);
			if (emailExiste) {
				return ErrorJSON.serviceRefused(String.format("Erreur, l'email %s est deja prit.", email), CodesErreur.ERREUR_EMAIL_DEJA_PRIT);
			}
			
			// On verifie que la date d'anniversaire est valide
			if (anniversaire != null) {
				if (! estValide(anniversaire)) {
					return ErrorJSON.serviceRefused(String.format("Erreur, l'anniversaire %s est invalide.", anniversaire), CodesErreur.ERREUR_ANNIVERSAIRE_INVALIDE);
				}
			}

			// On hash le mot de passe
			motDePasse = outils.MesMethodes.hasherMotDePasse(motDePasse);

			// On ajoute l'utilisateur a la BDD
			UtilisateursTools.ajouterUtilisateur(pseudo, motDePasse, email, prenom, nom, anniversaire);
			
			// On envoie un mail de bienvenue a l'utilisateur
			//MailsTools.envoyerMailBienvenue(email, pseudo); 

			// On renvoie une reponse
			return new JSONObject();
		}  catch (NoSuchAlgorithmException e) {
			return ErrorJSON.serviceRefused("Erreur lors du hashage du mot de passe", CodesErreur.ERREUR_HASHAGE);
		}  catch (InstantiationException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donnees MySQL (InstantiationException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
		} catch (IllegalAccessException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donnees MySQL (IllegalAccessException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
		} catch (ClassNotFoundException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donnees MySQL (ClassNotFoundException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
		} catch (SQLException e) {
			return ErrorJSON.serviceRefused("Erreur, requete SQL Incorrecte", CodesErreur.ERREUR_SQL);
		} catch (PseudoTropPetitException e) {
			return ErrorJSON.serviceRefused("Erreur, pseudo trop petit", CodesErreur.ERREUR_PSEUDO_TROP_COURT);
		} catch (PseudoTropGrandException e) {
			return ErrorJSON.serviceRefused("Erreur, pseudo trop grand", CodesErreur.ERREUR_PSEUDO_TROP_GRAND);
		} catch (MotDePasseTropPetitException e) {
			return ErrorJSON.serviceRefused("Erreur, mot de passe trop petit", CodesErreur.ERREUR_MDP_TROP_COURT);
		} catch (MotDePasseTropGrandException e) {
			return ErrorJSON.serviceRefused("Erreur, mot de passe trop grand", CodesErreur.ERREUR_MDP_TROP_LONG);
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
		}/* catch (MessagingException e) {
			return ErrorJSON.serviceRefused("Erreur lors de l'envoi du mail", CodesErreur.ERREUR_ENVOI_MAIl);
		} */
	}

	
   /**
    * Verification de la validite des parametres
    * @return : Un booleen a true si les paramatres sont valides.
    */
	private static boolean verificationParametres(String pseudo, String motDePasse, String email) {
		return (pseudo != null && email != null && motDePasse != null);
	}
	
	
	/**
	 * Verifie que les parametres entres respectent nos criteres de taille.
	 * Ces tailles sont situees dans le fichier services.Tailles.java
	 * Cette fonction lance une exception si un des parametres ne respecte
	 * pas ces criteres
	 * @param pseudo
	 * @param motDePasse
	 * @param email
	 * @param prenom
	 * @param nom
	 * @param anniversaire
	 * @throws PseudoTropPetitException 
	 * @throws PseudoTropGrandException 
	 * @throws MotDePasseTropPetitException 
	 * @throws MotDePasseTropGrandException 
	 * @throws EmailTropPetitException 
	 * @throws EmailTropGrandException 
	 * @throws PrenomTropPetitException 
	 * @throws PrenomTropGrandException 
	 * @throws NomTropPetitException 
	 * @throws NomTropGrandException 
	 * @throws AnniversaireTropPetitException 
	 * @throws AnniversaireTropGrandException 
	 */
	private static void verificationTailleInput(String pseudo, String motDePasse, String email, String prenom, String nom, String anniversaire) throws PseudoTropPetitException, PseudoTropGrandException, MotDePasseTropPetitException, MotDePasseTropGrandException, EmailTropPetitException, EmailTropGrandException, PrenomTropPetitException, PrenomTropGrandException, NomTropPetitException, NomTropGrandException, AnniversaireTropPetitException, AnniversaireTropGrandException {
		// Pseudo, mot de passe et email
		if (pseudo.length() < Tailles.MIN_PSEUDO) {
			throw new PseudoTropPetitException();
		} else if (pseudo.length() > Tailles.MAX_PSEUDO) {
			throw new PseudoTropGrandException();
		} else if (motDePasse.length() < Tailles.MIN_MOT_DE_PASSE) {
			throw new MotDePasseTropPetitException();
		} else if (motDePasse.length() > Tailles.MAX_MOT_DE_PASSE) {
			throw new MotDePasseTropGrandException();
		} else if (email.length() < Tailles.MIN_EMAIL) {
			throw new EmailTropPetitException();
		} else if (email.length() > Tailles.MAX_EMAIL) {
			throw new EmailTropGrandException();
		}
		
		// Prenom
		if (prenom != null) {
			if (prenom.length() < Tailles.MIN_PRENOM) {
				throw new PrenomTropPetitException();
			} else if (prenom.length() > Tailles.MAX_PRENOM) {
				throw new PrenomTropGrandException();
			}
		}
		
		// Nom
		if (nom != null) {
			if (nom.length() < Tailles.MIN_NOM) {
				throw new NomTropPetitException();
			} else if (nom.length() > Tailles.MAX_NOM) {
				throw new NomTropGrandException();
			}
		}
		
		// Anniversaire
		if (anniversaire != null) {
			if (anniversaire.length() < Tailles.MIN_ANNIVERSAIRE) {
				throw new AnniversaireTropPetitException();
			} else if (anniversaire.length() > Tailles.MAX_ANNIVERSAIRE) {
				throw new AnniversaireTropGrandException();
			}
		}
	}
	
	
	/**
	 * Verifier selon nos criteres de securite si un mot de passe
	 * est assez securise. Pour qu'il le soit, il doit contenir au
	 * moins une majuscule, une minuscule, et un chiffre.
	 * @param motDePasse : Le mot de passe a tester
	 * @return Un boolean a true si le mot de passe est assez
	 * securise, false sinon.
	 */
	public static boolean estSecurise(String motDePasse) {
		char lettre;
	    boolean drapeauMajuscule = false;
	    boolean drapeauMinuscule = false;
	    boolean drapeauChiffre = false;
	    
	    // On itere une fois sur tout le mot de passe
	    for (int i=0; i < motDePasse.length(); i++) {
	        lettre = motDePasse.charAt(i);
	        if (Character.isDigit(lettre)) {
	            drapeauChiffre = true;
	        } else if (Character.isUpperCase(lettre)) {
	            drapeauMajuscule = true;
	        } else if (Character.isLowerCase(lettre)) {
	            drapeauMinuscule = true;
	        }
	        
	        if (drapeauChiffre && drapeauMajuscule && drapeauMinuscule)
	            return true;
	    }
	    
	    return false;
	}
	
	
	/**
	 * Verifie qu'une date d'anniversaire est valide et sera comprise
	 * par MySQL.
	 * @param anniversaire : La date d'anniversaire dont la validite doit
	 * etre verifiee
	 * @return Un boolean a true si la date d'anniversaire est
	 * valide, false sinon.
	 */
	public static boolean estValide(String anniversaire) {
		//TODO: Remplir cette fonction
		return true;
	}
}