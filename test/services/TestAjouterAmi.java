package services;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import org.json.JSONObject;

import bd.tools.SessionsTools;
import exceptions.BDException;

public class TestAjouterAmi {
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchAlgorithmException, SQLException {
		//testAjouterAmis();
		//testSupprimerAmi();
		testListerAmis();
	}
	
	private static void testAjouterAmis() throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchAlgorithmException, SQLException {
		System.out.println("Debut du test d'ajout d'amis");
	
		// On commence par ajouter des personnes dans notre base de donnees
		String pseudo1 = "TEST_utilisateur_500";
		String pseudo2 = "TEST_utilisateur_600";
		String pseudo3 = "TEST_utilisateur_700";
		String pseudo4 = "TEST_utilisateur_800";
		String mdp1 = outils.MesMethodes.hasherMotDePasse("jesuisUnMotDePasseValidelalala");
		String mdp2 = outils.MesMethodes.hasherMotDePasse("MotDePasse2");
		String mdp3 = outils.MesMethodes.hasherMotDePasse("MotDePasse3");
		String mdp4 = outils.MesMethodes.hasherMotDePasse("MotDePasse");
		String sNull = null;
		services.utilisateur.CreationUtilisateur.creationUtilisateur(pseudo1, mdp1, "mail1@gmail.com", sNull, sNull, sNull);
		services.utilisateur.CreationUtilisateur.creationUtilisateur(pseudo2, mdp2, "mail1@gmail.com", sNull, sNull, sNull);
		services.utilisateur.CreationUtilisateur.creationUtilisateur(pseudo3, mdp3, "mail1@gmail.com", sNull, sNull, sNull);
		services.utilisateur.CreationUtilisateur.creationUtilisateur(pseudo4, mdp4, "mail1@gmail.com", sNull, sNull, sNull);
		String id1 = bd.tools.UtilisateursTools.getIdUtilisateur(pseudo1);
		String id2 = bd.tools.UtilisateursTools.getIdUtilisateur(pseudo2);
		String id3 = bd.tools.UtilisateursTools.getIdUtilisateur(pseudo3);
		String id4 = bd.tools.UtilisateursTools.getIdUtilisateur(pseudo4);
		
		// Ensuite on les connecte
		String clef1 = services.authentification.Login.login(pseudo1, mdp1).getString("clef");
		String clef2 = services.authentification.Login.login(pseudo2, mdp2).getString("clef");
		String clef3 = services.authentification.Login.login(pseudo3, mdp3).getString("clef");
		
		System.out.println("On fait en sorte qu'un utilisateur en suive un autre");
		System.out.println(services.amis.AjouterAmi.ajouterAmi(clef1, id2));
		System.out.println(services.amis.AjouterAmi.ajouterAmi(clef3, id4));
		System.out.println("L'utilisateur suivi peut a son tour suivre son suiveur");
		System.out.println(services.amis.AjouterAmi.ajouterAmi(clef2, id1));
		System.out.println("Un utilisateur peut suivre plusieurs personnes");
		System.out.println(services.amis.AjouterAmi.ajouterAmi(clef1, id4));
		System.out.println(services.amis.AjouterAmi.ajouterAmi(clef1, id3));
		System.out.println("Un utilisateur ne peut pas suivre une personne inexistante");
		System.out.println(services.amis.AjouterAmi.ajouterAmi(clef1, "-500"));
		System.out.println("Un utilisateur ne peut pas se suivre lui meme");
		System.out.println(services.amis.AjouterAmi.ajouterAmi(clef1, id1));
		System.out.println("Un utilisateur inexistant ne peut suivre personne");
		System.out.println(services.amis.AjouterAmi.ajouterAmi("1g7r81ez31fefe1!f2qsdf1", id3));
		
		// Nettoyage des utilisateurs et des amitiees crees
		bd.tools.AmitiesTools.supprimerAmi(id1, id2);
		bd.tools.AmitiesTools.supprimerAmi(id3, id4);
		bd.tools.AmitiesTools.supprimerAmi(id2, id1);
		bd.tools.AmitiesTools.supprimerAmi(id1, id3);
		bd.tools.AmitiesTools.supprimerAmi(id1, id4);
		bd.tools.UtilisateursTools.supprimerUtilisateurAvecPseudo(pseudo1);
		bd.tools.UtilisateursTools.supprimerUtilisateurAvecPseudo(pseudo2);
		bd.tools.UtilisateursTools.supprimerUtilisateurAvecPseudo(pseudo3);
		bd.tools.UtilisateursTools.supprimerUtilisateurAvecPseudo(pseudo4);
		System.out.println("Fin du test d'ajout de relation d'amities");
	}
	
	
	private static void testSupprimerAmi() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, NoSuchAlgorithmException {
		System.out.println("Debut du test de suppression d'amis");
		
		// Ajout d'utilisateurs et de relations d'amities
		// On commence par ajouter des personnes dans notre base de donnees
		String pseudo1 = "TEST_utilisateur_500";
		String pseudo2 = "TEST_utilisateur_600";
		String pseudo3 = "TEST_utilisateur_700";
		String mdp1 = outils.MesMethodes.hasherMotDePasse("jesuisUnMotDePasseValidelalala");
		String mdp2 = outils.MesMethodes.hasherMotDePasse("MotDePasse2");
		String mdp3 = outils.MesMethodes.hasherMotDePasse("MotDePasse3");
		String sNull = null;
		services.utilisateur.CreationUtilisateur.creationUtilisateur(pseudo1, mdp1, "mail1@gmail.com", sNull, sNull, sNull);
		services.utilisateur.CreationUtilisateur.creationUtilisateur(pseudo2, mdp2, "mail1@gmail.com", sNull, sNull, sNull);
		services.utilisateur.CreationUtilisateur.creationUtilisateur(pseudo3, mdp3, "mail1@gmail.com", sNull, sNull, sNull);
		String id1 = bd.tools.UtilisateursTools.getIdUtilisateur(pseudo1);
		String id2 = bd.tools.UtilisateursTools.getIdUtilisateur(pseudo2);
		String id3 = bd.tools.UtilisateursTools.getIdUtilisateur(pseudo3);
		
		// Ensuite on les connecte
		String clef1 = services.authentification.Login.login(pseudo1, mdp1).getString("clef");
		String clef2 = services.authentification.Login.login(pseudo2, mdp2).getString("clef");
		
		// Puis on ajoute quelques amis
		services.amis.AjouterAmi.ajouterAmi(clef1, id2);
		services.amis.AjouterAmi.ajouterAmi(clef2, id1);

		System.out.println("Une clef inexistante ne peut pas supprimer d'amis:");
		System.out.println(services.amis.SupprimerAmi.supprimerAmi("!4dzdDDe54f5fe545", id1));
		System.out.println("On ne peut pas supprimer la relation d'amitie d'un utilisateur n'existant pas:");
		System.out.println(services.amis.SupprimerAmi.supprimerAmi(clef1, "-500"));
		System.out.println("On ne peut pas supprimer la relation d'amitie avec soit meme:");
		System.out.println(services.amis.SupprimerAmi.supprimerAmi(clef1, id1));
		System.out.println("On ne peut pas supprimer une relation d'amitie avec un utilisateur que l'on ne suit pas:");
		System.out.println(services.amis.SupprimerAmi.supprimerAmi(clef1, id3));
		System.out.println("On peut supprimer une relation d'amitie avec un utilisateur qui existe et que l'on suit:");
		System.out.println(services.amis.SupprimerAmi.supprimerAmi(clef1, id2));
		System.out.println(services.amis.SupprimerAmi.supprimerAmi(clef2, id1));
		
		// Nettoyage des utilisateurs crees 
		bd.tools.UtilisateursTools.supprimerUtilisateurAvecPseudo(pseudo1);
		bd.tools.UtilisateursTools.supprimerUtilisateurAvecPseudo(pseudo2);
		bd.tools.UtilisateursTools.supprimerUtilisateurAvecPseudo(pseudo3);
		System.out.println("Fin du test de suppression d'amis");
	}
	
	
	private static void testListerAmis() {
		System.out.println("Debut du test de l'affichage de la liste d'amis");
		System.out.println("On ne peut pas afficher les amis d'un utilisateur qui n'existe pas:");
		System.out.println(services.amis.ListerAmis.listerAmis("-500",0,30));
		String cle1 = "",cle2="";
		//Ajout d'utilisateurs et de relations d'amities
		try {
			bd.tools.UtilisateursTools.ajouterUtilisateur("100", "TEST_utilisateur_100", outils.MesMethodes.hasherMotDePasse("MotDePasse"), "mail1@gmail.com", null, null, null);
			bd.tools.UtilisateursTools.ajouterUtilisateur("600", "TEST_utilisateur_600", outils.MesMethodes.hasherMotDePasse("MotDePasse2"), "mail2@gmail.com", null, null, null);
			bd.tools.UtilisateursTools.ajouterUtilisateur("700", "TEST_utilisateur_700", outils.MesMethodes.hasherMotDePasse("MotDePasse3"), "mail3@gmail.com", null, null, null);
			bd.tools.UtilisateursTools.ajouterUtilisateur("800", "TEST_utilisateur_800", outils.MesMethodes.hasherMotDePasse("MotDePasse3"), "mail3@gmail.com", null, null, null);
			bd.tools.SessionsTools.insertSession("100",false);
			bd.tools.SessionsTools.insertSession("600",false);
			cle1= bd.tools.SessionsTools.getClefbyId("100");
			cle2= bd.tools.SessionsTools.getClefbyId("600");		
			bd.tools.AmitiesTools.ajouterAmi("100","600");
			bd.tools.AmitiesTools.ajouterAmi("600","100");
			bd.tools.AmitiesTools.ajouterAmi("100","700");
			bd.tools.AmitiesTools.ajouterAmi("100","800");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		//suite des tests
		System.out.println("On affiche la liste des amis de l'utilisateur en disant combien on souhaite en afficher ici 30:");
		System.out.println(services.amis.ListerAmis.listerAmis("100", 1,30));
		System.out.println("On affiche la liste des amis de l'utilisateur en disant combien on souhaite en afficher ici 1:");
		System.out.println(services.amis.ListerAmis.listerAmis("100", 1,1));
		
		
		// Nettoyage des relations d'amities et des utilisateurs crees 
		try {
			bd.tools.AmitiesTools.supprimerAmi("100","600");
			bd.tools.AmitiesTools.supprimerAmi("100","700");
			bd.tools.AmitiesTools.supprimerAmi("100","800");
			bd.tools.AmitiesTools.supprimerAmi("600","100");
			bd.tools.AmitiesTools.supprimerAmi("700","800");
			bd.tools.SessionsTools.suppressionCle(cle1);
			bd.tools.SessionsTools.suppressionCle(cle2);
			bd.tools.UtilisateursTools.supprimerUtilisateurAvecPseudo("TEST_utilisateur_100");
			bd.tools.UtilisateursTools.supprimerUtilisateurAvecPseudo("TEST_utilisateur_600");
			bd.tools.UtilisateursTools.supprimerUtilisateurAvecPseudo("TEST_utilisateur_700");
			bd.tools.UtilisateursTools.supprimerUtilisateurAvecPseudo("TEST_utilisateur_800");
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		System.out.println("Fin du test de l'affichage de la liste d'amis");
	}
}
