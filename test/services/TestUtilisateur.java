package services;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import org.json.JSONObject;

import exceptions.BDException;
import exceptions.ClefInexistanteException;

public class TestUtilisateur {
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, NoSuchAlgorithmException, ClefInexistanteException {
		testCreationUtilisateur();
		testSuppressionUtilisateur();
		testModificationUtilisateur();
	}
	
	private static void testCreationUtilisateur() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		String pseudo = "TEST_utilisateur_1";
		String motDePasse = "motDePasseEnClair77";
		String mail = "mail1@gmail.com";
		
		System.out.println("#####\n# Debut du test de creation d'utilisateurs\n#####");
		
		System.out.println("\nOn peut creer un utilisateur en remplissant tous les champs:");
		System.out.println(services.utilisateur.CreationUtilisateur.creationUtilisateur(pseudo, motDePasse,
				                                                                        mail, "John", "Smith",
				                                                                        "1900-01-01"));
		bd.tools.UtilisateursTools.supprimerUtilisateurAvecPseudo(pseudo);
		
		System.out.println("\nOn peut creer un utilisateur en omettant des champs:");
		System.out.println(services.utilisateur.CreationUtilisateur.creationUtilisateur(pseudo, motDePasse,
				                                                                        mail, null, null, null));
		
		System.out.println("\nOn ne peut pas creer d'utilisateur avec le meme pseudo:");
		System.out.println(services.utilisateur.CreationUtilisateur.creationUtilisateur(pseudo, motDePasse,
				                                                                        "mail2@gmail.com", null, null, null));

		System.out.println("\nOn ne peut pas creer d'utilisateur avec le meme mail:");
		System.out.println(services.utilisateur.CreationUtilisateur.creationUtilisateur(pseudo + "x", motDePasse,
				                                                                        mail, null, null, null));
		bd.tools.UtilisateursTools.supprimerUtilisateurAvecPseudo(pseudo);
		
		System.out.println("\nOn ne peut pas creer d'utilisateur avec un mot de passe pas assez securise:");
		System.out.println(services.utilisateur.CreationUtilisateur.creationUtilisateur(pseudo, "mdpmdpmdpmdp",
				                                                                        "mail5@gmail.com", null, null, null));
		
		System.out.println("\nOn ne peut pas creer d'utilisateur avec un pseudo trop court:");
		System.out.println(services.utilisateur.CreationUtilisateur.creationUtilisateur("",
				                                                                        motDePasse,
				                                                                        mail, null, null, null));
		
		System.out.println("\nOn ne peut pas creer d'utilisateur avec un pseudo trop long:");
		System.out.println(services.utilisateur.CreationUtilisateur.creationUtilisateur("CePseudoEstVraimentLongCePseudoEstVraimentLongCePseudoEstVraimentLongCePseudoEstVraimentLongCePseudoEstVraimentLongCePseudoEstVraimentLongCePseudoEstVraimentLongCePseudoEstVraimentLongCePseudoEstVraimentLong",
				                                                                        motDePasse,
				                                                                        mail, null, null, null));
		
		System.out.println("\nOn ne peut pas creer d'utilisateur avec un mot de passe trop court:");
		System.out.println(services.utilisateur.CreationUtilisateur.creationUtilisateur(pseudo, "mdp",
				                                                                        mail, null, null, null));
		
		System.out.println("\nOn ne peut pas creer d'utilisateur avec un mot de passe trop long:");
		System.out.println(services.utilisateur.CreationUtilisateur.creationUtilisateur(pseudo, "CeMotDePasseEstDrolementLong,JemedemandejusquaOuPeutIlaller,SurementresLoin,EnToutCasilDoitEtreVraimentTresLongDeManiereASassurerQuilNePassePas...VousEtesEncoreEnTrainDeLire?77",
				                                                                        mail, null, null, null));
		
		System.out.println("\nOn ne peut pas creer d'utilisateur avec un email trop court:");
		System.out.println(services.utilisateur.CreationUtilisateur.creationUtilisateur(pseudo, motDePasse,
				                                                                        "",
				                                                                        null, null, null));
		
		System.out.println("\nOn ne peut pas creer d'utilisateur avec un email trop long:");
		System.out.println(services.utilisateur.CreationUtilisateur.creationUtilisateur(pseudo, motDePasse,
				                                                                        "adressemailtreslongueadressemailtreslongueadressemailtreslongueadressemailtreslongueadressemailtreslongueadressemailtreslongueadressemailtreslongueadressemailtreslongueadressemailtreslongue@gmail.com",
				                                                                        null, null, null));
		
		System.out.println("\nOn ne peut pas creer d'utilisateur avec un prenom trop court:");
		System.out.println(services.utilisateur.CreationUtilisateur.creationUtilisateur(pseudo, motDePasse,
				                                                                        mail,
				                                                                        "",
				                                                                        null, null));
		
		System.out.println("\nOn ne peut pas creer d'utilisateur avec un prenom trop long:");
		System.out.println(services.utilisateur.CreationUtilisateur.creationUtilisateur(pseudo, motDePasse,
				                                                                        mail,
				                                                                        "CeprenomestdrolementlongCeprenomestdrolementlongCeprenomestdrolementlongCeprenomestdrolementlongCeprenomestdrolementlongCeprenomestdrolementlongCeprenomestdrolementlongCeprenomestdrolementlong",
				                                                                        null, null));
		
		System.out.println("\nOn ne peut pas creer d'utilisateur avec un nom trop court:");
		System.out.println(services.utilisateur.CreationUtilisateur.creationUtilisateur(pseudo, motDePasse,
				                                                                        mail, null,
				                                                                        "",
				                                                                        null));
		
		System.out.println("\nOn ne peut pas creer d'utilisateur avec un nom trop long:");
		System.out.println(services.utilisateur.CreationUtilisateur.creationUtilisateur(pseudo, motDePasse,
				                                                                        mail,  null,
				                                                                        "CenomestdrolementlongCenomestdrolementlongCenomestdrolementlongCenomestdrolementlongCenomestdrolementlongCenomestdrolementlongCenomestdrolementlongCenomestdrolementlongCenomestdrolementlongCenomestdrolementlong",
				                                                                        null));
		
		/*
		// TODO: Valider ce test
		System.out.println("\nOn ne peut pas creer d'utilisateur avec un anniversaire trop court:");
		System.out.println(services.utilisateur.CreationUtilisateur.creationUtilisateur(pseudo, motDePasse,
				                                                                        mail, null, null,
				                                                                        ""));
		
		// TODO: Valider ce test
		System.out.println("\nOn ne peut pas creer d'utilisateur avec un anniversaire trop long:");
		System.out.println(services.utilisateur.CreationUtilisateur.creationUtilisateur(pseudo, motDePasse,
				                                                                        mail,  null, null,
				                                                                        "CetAnniversaireEsTropLongCetAnniversaireEsTropLongCetAnniversaireEsTropLongCetAnniversaireEsTropLongCetAnniversaireEsTropLongCetAnniversaireEsTropLongCetAnniversaireEsTropLongCetAnniversaireEsTropLong"));
		*/
		
		System.out.println("\nOn peut creer un utilisateur avec un mot de passe contenant des caracteres speciaux:");
		System.out.println(services.utilisateur.CreationUtilisateur.creationUtilisateur(pseudo, "AAz3s\"\"\\\\//!@#😁😈'\";DROP TABLE Utilisateurs",
				                                                                        mail, null, null, null));		
		
		// Nettoyage des utilisateurs crees
		bd.tools.UtilisateursTools.supprimerUtilisateurAvecPseudo(pseudo);
		
		System.out.println("\n#####\n# Fin du test de creation d'utilisateurs\n#####");
	}

	
	private static void testSuppressionUtilisateur() throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchAlgorithmException, SQLException, ClefInexistanteException {
		// On ajoute un utilisateur pour des tests
		String pseudo = "TEST_utilisateur_1";
		String mdpUtilisateur = "MotDePasseRaisonnableEtCorrect77";
		String mail = "mailunique@77gmail.com";
		bd.tools.UtilisateursTools.ajouterUtilisateur(pseudo, outils.MesMethodes.hasherMotDePasse(mdpUtilisateur), mail, null, null, null);
		String idUtilisateur = bd.tools.UtilisateursTools.getIdUtilisateur(pseudo);
		String clef = bd.tools.SessionsTools.insertSession(idUtilisateur, false);
		
		System.out.println("#####\n# Debut du test de suppression d'utilisateurs\n#####");

		System.out.println("\nOn ne peut pas supprimer d'utilisateur avec une clef de mauvaise taille:");
		System.out.println(services.utilisateur.SuppressionUtilisateur.suppressionUtilisateur("", mdpUtilisateur));
		
		System.out.println("\nOn ne peut pas supprimer d'utilisateur avec un mot de passe trop court:");
		System.out.println(services.utilisateur.SuppressionUtilisateur.suppressionUtilisateur(clef, ""));
		
		System.out.println("\nOn ne peut pas supprimer d'utilisateur avec un mot de passe trop long:");
		System.out.println(services.utilisateur.SuppressionUtilisateur.suppressionUtilisateur(clef, "77MotDePasseTresLongMotDePasseTresLongMotDePasseTresLongMotDePasseTresLongMotDePasseTresLongMotDePasseTresLongMotDePasseTresLongMotDePasseTresLongMotDePasseTresLongMotDePasseTresLong"));
		
		System.out.println("\nOn ne peut pas supprimer d'utilisateur sans etre connecte:");
		System.out.println(services.utilisateur.SuppressionUtilisateur.suppressionUtilisateur("clefinexistanteXXXXXXXXXXXXXXXXX", "MotDePassePeuImportant"));
		
		System.out.println("\nOn ne peut pas supprimer d'utilisateur avec le mauvais mot de passe:");
		System.out.println(services.utilisateur.SuppressionUtilisateur.suppressionUtilisateur(clef, "MauvaisMotDePasse"));
		
		System.out.println("\nOn peut supprimer un utilisateur avec la bonne clef et le bon mot de passe:");
		System.out.println(services.utilisateur.SuppressionUtilisateur.suppressionUtilisateur(clef, mdpUtilisateur));
		
		System.out.println("\n#####\n# Fin du test de suppression d'utilisateurs\n#####");
		
		// Nettoyage des utilisateurs crees
		bd.tools.UtilisateursTools.supprimerUtilisateurAvecPseudo(pseudo);
	}
	
	
	private static void testModificationUtilisateur() throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchAlgorithmException, SQLException {
		// On ajoute un utilisateur pour des tests
		String pseudoUtilisateur = "TEST_utilisateur_1";
		String mdpUtilisateur = "MotDePasseRaisonnableEtCorrect77";
		String mail = "mailunique@77gmail.com";
		bd.tools.UtilisateursTools.ajouterUtilisateur(pseudoUtilisateur, outils.MesMethodes.hasherMotDePasse(mdpUtilisateur), mail, null, null, null);
		String idUtilisateur = bd.tools.UtilisateursTools.getIdUtilisateur(pseudoUtilisateur);
		String clef = bd.tools.SessionsTools.insertSession(idUtilisateur, false);
		
		System.out.println("#####\n# Debut du test de modification d'utilisateurs\n#####");

		System.out.println("\nOn ne peut pas modifier d'utilisateur non connecte:");
		System.out.println(services.utilisateur.ModificationUtilisateur.modificationUtilisateur("ClefinexistanteXXXXXXXXXXXXXXXXX", mdpUtilisateur, "TEST_utilisateur_inexistant", null, null, null, null, null));
		
		System.out.println("\nOn peut lancer le service sans rien modifier:");
		System.out.println(services.utilisateur.ModificationUtilisateur.modificationUtilisateur(clef, mdpUtilisateur, null, null, null, null, null, null));
		
		System.out.println("\nOn ne peut pas modifier d'utilisateur avec le mauvais mot de passe:");
		System.out.println(services.utilisateur.ModificationUtilisateur.modificationUtilisateur(clef, "mauvaisMotDePasse", null, null, null, null, null, null));
		
		//TODO: Valider ce test
		System.out.println("\nOn ne peut pas modifier d'utilisateur si le nouveau pseudo est deja prit:");
		System.out.println(services.utilisateur.ModificationUtilisateur.modificationUtilisateur(clef, mdpUtilisateur, pseudoUtilisateur, null, null, null, null, null));
		
		//TODO: Valider ce test
		System.out.println("\nOn ne peut pas modifier d'utilisateur si le nouveau mail est deja prit:");
		System.out.println(services.utilisateur.ModificationUtilisateur.modificationUtilisateur(clef, mdpUtilisateur, null, null, mail, null, null, null));		
		
		System.out.println("\nOn ne peut pas modifier d'utilisateur si le nouveau mot de passe est pas assez securise:");
		System.out.println(services.utilisateur.ModificationUtilisateur.modificationUtilisateur(clef, mdpUtilisateur, null, "mdpmdpmdp", null, null, null, null));
		
		//TODO: Valider ce test
		System.out.println("\nOn ne peut pas modifier d'utilisateur si le nouvel anniversaire est invalide:");
		System.out.println(services.utilisateur.ModificationUtilisateur.modificationUtilisateur(clef, mdpUtilisateur, null, null, null, null, null, "Anniversaire invalide"));
		
		System.out.println("\nOn peut modifier le pseudo d'un utilisateur:");
		System.out.println(services.utilisateur.ModificationUtilisateur.modificationUtilisateur(clef, mdpUtilisateur, "TEST_utilisateur_mieux", null, null, null, null, null));
		
		System.out.println("\nOn peut modifier le mot de passe d'un utilisateur:");
		System.out.println(services.utilisateur.ModificationUtilisateur.modificationUtilisateur(clef, mdpUtilisateur, null, "MotDePasseVachementMieux77", null, null, null, null));
		bd.tools.UtilisateursTools.modifierMotDePasse(idUtilisateur, outils.MesMethodes.hasherMotDePasse(mdpUtilisateur));
		
		System.out.println("\nOn peut modifier le mail d'un utilisateur:");
		String mail2 = "nouveaumailunique@77gmail.com";
		System.out.println(services.utilisateur.ModificationUtilisateur.modificationUtilisateur(clef, mdpUtilisateur, null, null, mail2, null, null, null));
		
		System.out.println("\nOn peut modifier le prenom d'un utilisateur:");
		System.out.println(services.utilisateur.ModificationUtilisateur.modificationUtilisateur(clef, mdpUtilisateur, null, null, null, "PrenomCool", null, null));
		
		System.out.println("\nOn peut modifier le nom d'un utilisateur:");
		System.out.println(services.utilisateur.ModificationUtilisateur.modificationUtilisateur(clef, mdpUtilisateur, null, null, null, null, "NomCool", null));
		
		// TODO: Valider ce test
		System.out.println("\nOn peut modifier l'anniversaire d'un utilisateur:");
		System.out.println(services.utilisateur.ModificationUtilisateur.modificationUtilisateur(clef, mdpUtilisateur, null, null, null, null, null, "1900-01-01"));
		
		// TODO: Valider ce test
		System.out.println("\nOn peut tout modifier d'un coup:");
		System.out.println(services.utilisateur.ModificationUtilisateur.modificationUtilisateur(clef, mdpUtilisateur, "TEST_utilisateur_ENCORE_mieux", "MotDePasseVachementPLUSMieux77", "mailENCOREplusbeau@gmail.com", "PrenomGenial", "NomSuper", "2000-01-01"));
		
		// Nettoyage de l'utilisateur cree
		bd.tools.UtilisateursTools.supprimerUtilisateurAvecId(idUtilisateur);
		
		System.out.println("\n#####\n# Fin du test de modification d'utilisateurs\n#####");
	}
}
