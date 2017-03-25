package services;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import org.json.JSONObject;

import exceptions.BDException;

public class TestUtilisateur {
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, NoSuchAlgorithmException {
		testCreationUtilisateur();
		testSuppressionUtilisateur();
		testModificationUtilisateur();
	}
	
	private static void testCreationUtilisateur() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		System.out.println("Debut du test de creation d'utilisateurs");
		
		System.out.println("On peut creer un utilisateur en remplissant tous les champs:");
		System.out.println(services.utilisateur.CreationUtilisateur.creationUtilisateur("TEST_utilisateur_1", "motDePasseEnClair",
				                                                                        "mail1@gmail.com", "John", "Smith",
				                                                                        "1900-01-01"));
		
		System.out.println("On peut creer un utilisateur en omettant des champs:");
		System.out.println(services.utilisateur.CreationUtilisateur.creationUtilisateur("TEST_utilisateur_2", "motDePasseEnClair",
				                                                                        "mail2@gmail.com", null, null, null));
		
		System.out.println("On ne peut pas creer d'utilisateur avec le meme pseudo:");
		System.out.println(services.utilisateur.CreationUtilisateur.creationUtilisateur("TEST_utilisateur_2", "motDePasseEnClair",
				                                                                        "mail3@gmail.com", null, null, null));

		System.out.println("On ne peut pas creer d'utilisateur avec le meme mail:");
		System.out.println(services.utilisateur.CreationUtilisateur.creationUtilisateur("TEST_utilisateur_3", "motDePasseEnClair",
				                                                                        "mail1@gmail.com", null, null, null));
		
		System.out.println("On ne peut pas creer d'utilisateur avec un mot de passe trop court:");
		System.out.println(services.utilisateur.CreationUtilisateur.creationUtilisateur("TEST_utilisateur_3", "mdp",
				                                                                        "mail4@gmail.com", null, null, null));
		
		System.out.println("On ne peut pas creer d'utilisateur avec un mot de passe trop long:");
		System.out.println(services.utilisateur.CreationUtilisateur.creationUtilisateur("TEST_utilisateur_4", "CeMotDePasseEstDrolementLong,JemedemandejusquaOuPeutIlaller,SurementresLoin,EnToutCasilDoitEtreVraimentTresLongDeManiereASassurerQuilNePassePas...VousEtesEncoreEnTrainDeLire?",
				                                                                        "mail5@gmail.com", null, null, null));
		
		System.out.println("On peut creer un utilisateur avec un mot de passe contenant des caracteres speciaux:");
		System.out.println(services.utilisateur.CreationUtilisateur.creationUtilisateur("TEST_utilisateur_5", "AAzs\"\"\\\\//!@#😁😈'\";DROP TABLE Utilisateurs",
				                                                                        "mail6@gmail.com", null, null, null));		
		
		// Nettoyage des utilisateurs crees
		bd.tools.UtilisateursTools.supprimerUtilisateurAvecPseudo("TEST_utilisateur_1");
		bd.tools.UtilisateursTools.supprimerUtilisateurAvecPseudo("TEST_utilisateur_2");
		bd.tools.UtilisateursTools.supprimerUtilisateurAvecPseudo("TEST_utilisateur_5");
		
		System.out.println("Fin du test de creation d'utilisateurs");
	}

	
	private static void testSuppressionUtilisateur() throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchAlgorithmException, SQLException {
		// On ajoute un utilisateur pour des tests
		String pseudoUtilisateur = "TEST_utilisateur_1";
		String mdpUtilisateur = "MotDePasseRaisonnableEtCorrect";
		bd.tools.UtilisateursTools.ajouterUtilisateur(pseudoUtilisateur, outils.MesMethodes.hasherMotDePasse(mdpUtilisateur), "mailunique@77gmail.com", null, null, null);
		String idUtilisateur = bd.tools.UtilisateursTools.getIdUtilisateur(pseudoUtilisateur);
		
		System.out.println("Debut du test de suppression d'utilisateurs");
		
		System.out.println("On ne peut pas supprimer d'utilisateur n'existant pas:");
		System.out.println(services.utilisateur.SuppressionUtilisateur.suppressionUtilisateur("-500", "MotDePassePeuImportant"));
		
		System.out.println("On ne peut pas supprimer d'utilisateur avec le mauvais mot de passe:");
		System.out.println(services.utilisateur.SuppressionUtilisateur.suppressionUtilisateur(idUtilisateur, "MauvaisMotDePasse"));
		
		System.out.println("On peut supprimer un utilisateur avec le bon id et le bon mot de passe:");
		System.out.println(services.utilisateur.SuppressionUtilisateur.suppressionUtilisateur(idUtilisateur, mdpUtilisateur));
		
		System.out.println("Fin du test de suppression d'utilisateurs");
	}
	
	
	private static void testModificationUtilisateur() {
		System.out.println("Debut du test de modification d'utilisateurs");
		try {
			bd.tools.UtilisateursTools.ajouterUtilisateur("10000", "TEST_utilisateur_nul", outils.MesMethodes.hasherMotDePasse("motDePasseEnClair"), "mail@gmail.com", null, null, null);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		System.out.println("On ne peut pas modifier d'utilisateur n'existant pas:");
		System.out.println(services.utilisateur.ModificationUtilisateur.modificationUtilisateur("-500", "motDePasseEnClair", "TEST_utilisateur_mieux", null, null, null, null, null));
		System.out.println("On ne peut pas modifier d'utilisateur avec le mauvais mot de passe:");
		System.out.println(services.utilisateur.ModificationUtilisateur.modificationUtilisateur("10000", "mauvaisMotDePasse", "TEST_utilisateur_mieux", null, null, null, null, null));
		System.out.println("On ne peut pas modifier d'utilisateur si le nouveau pseudo est deja prit:");
		//TODO: Valider ce test
		System.out.println(services.utilisateur.ModificationUtilisateur.modificationUtilisateur("10000", "motDePasseEnClair", "SuperNicolas", null, null, null, null, null));
		System.out.println("On ne peut pas modifier d'utilisateur si le nouveau mot de passe est pas assez securise:");
		System.out.println(services.utilisateur.ModificationUtilisateur.modificationUtilisateur("10000", "motDePasseEnClair", null, "mdp", null, null, null, null));
		System.out.println("On ne peut pas modifier d'utilisateur si le nouvel anniversaire est invalide:");
		//TODO: Valider ce test
		System.out.println(services.utilisateur.ModificationUtilisateur.modificationUtilisateur("10000", "motDePasseEnClair", null, null, null, null, null, "1900-00-00"));
		System.out.println("On peut modifier le pseudo d'un utilisateur:");
		System.out.println(services.utilisateur.ModificationUtilisateur.modificationUtilisateur("10000", "motDePasseEnClair", "TEST_utilisateur_mieux", null, null, null, null, null));
		System.out.println("On peut modifier le mot de passe d'un utilisateur:");
		System.out.println(services.utilisateur.ModificationUtilisateur.modificationUtilisateur("10000", "motDePasseEnClair", null, "MotDePasseVachementMieux", null, null, null, null));
		System.out.println("On peut modifier le mail d'un utilisateur:");
		System.out.println(services.utilisateur.ModificationUtilisateur.modificationUtilisateur("10000", "MotDePasseVachementMieux", null, null, "mailplusbeau@gmail.com", null, null, null));
		System.out.println("On peut modifier le prenom d'un utilisateur:");
		System.out.println(services.utilisateur.ModificationUtilisateur.modificationUtilisateur("10000", "MotDePasseVachementMieux", null, null, null, "PrenomCool", null, null));
		System.out.println("On peut modifier le nom d'un utilisateur:");
		System.out.println(services.utilisateur.ModificationUtilisateur.modificationUtilisateur("10000", "MotDePasseVachementMieux", null, null, null, null, "NomCool", null));
		System.out.println("On peut modifier l'anniversaire d'un utilisateur:");
		System.out.println(services.utilisateur.ModificationUtilisateur.modificationUtilisateur("10000", "MotDePasseVachementMieux", null, null, null, null, null, "1900-01-01"));
		System.out.println("On peut tout modifier d'un coup:");
		System.out.println(services.utilisateur.ModificationUtilisateur.modificationUtilisateur("10000", "MotDePasseVachementMieux", "TEST_utilisateur_ENCORE_mieux", "MotDePasseVachementPLUSMieux", "mailENCOREplusbeau@gmail.com", "PrenomGenial", "NomSuper", "2000-01-01"));
		
		// Nettoyage de l'utilisateur cree
		try {
			bd.tools.UtilisateursTools.supprimerUtilisateurAvecId("10000");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Fin du test de modification d'utilisateurs");
	}
}
