package services.amis;

import org.json.JSONObject;

import exceptions.BDException;

public class TestAjouterAmi {

public static void main(String[] args) throws BDException {
	//testCreationUtilisateur();
	//testSuppressionUtilisateur();
	testAjouterAmis();
	testSupprimerAmi();
}

private static void testAjouterAmis() {
	
	System.out.println("Debut du test d'ajout d'amis");
	
	//on commence par ajouter des personnes dans notre base de donnees
	try {
		bd.tools.UtilisateursTools.ajouterUtilisateur("5", "TEST_utilisateur_5", outils.MesMethodes.hasherMotDePasse("MotDePasse"), "mail1@gmail.com", null, null, null);
		bd.tools.UtilisateursTools.ajouterUtilisateur("6", "TEST_utilisateur_6", outils.MesMethodes.hasherMotDePasse("MotDePasse2"), "mail2@gmail.com", null, null, null);
		bd.tools.UtilisateursTools.ajouterUtilisateur("7", "TEST_utilisateur_7", outils.MesMethodes.hasherMotDePasse("MotDePasse3"), "mail3@gmail.com", null, null, null);
		bd.tools.UtilisateursTools.ajouterUtilisateur("8", "TEST_utilisateur_8", outils.MesMethodes.hasherMotDePasse("MotDePasse"), "mail4@gmail.com", null, null, null);
	} catch (Exception e) {
		e.printStackTrace();
	} 

	System.out.println("On faire en sort qu'un utilisateur en suive un autre");
	System.out.println(services.amis.AjouterAmi.ajouterAmi("5","6") );
	System.out.println(services.amis.AjouterAmi.ajouterAmi("7","8") );
	System.out.println("L'utilisateur suivi peut a son tour suivre son suiveur");
	System.out.println(services.amis.AjouterAmi.ajouterAmi("6","5") );
	System.out.println("Un utilisateur peut suivre plusieurs personnes");
	System.out.println(services.amis.AjouterAmi.ajouterAmi("5","8") );
	System.out.println(services.amis.AjouterAmi.ajouterAmi("5","7") );
	System.out.println("Un utilisateur ne peut pas suivre une personne innexistante");
	System.out.println(services.amis.AjouterAmi.ajouterAmi("5","100") );
	System.out.println("Un utilisateur ne peut pas se suivre lui meme");
	System.out.println(services.amis.AjouterAmi.ajouterAmi("5","5") );
	System.out.println("Un utilisateur inexistant ne peut suivre personne");
	System.out.println(services.amis.AjouterAmi.ajouterAmi("500","7") );

	// Nettoyage des utilisateurs crees +suppression des amities creees
	try {
		bd.tools.AmitiesTools.supprimerAmi("5","6");
		bd.tools.AmitiesTools.supprimerAmi("5","8");
		bd.tools.AmitiesTools.supprimerAmi("5","7");
		bd.tools.AmitiesTools.supprimerAmi("6","5");
		bd.tools.AmitiesTools.supprimerAmi("7","8");
		bd.tools.UtilisateursTools.supprimerUtilisateurAvecPseudo("TEST_utilisateur_5");
		bd.tools.UtilisateursTools.supprimerUtilisateurAvecPseudo("TEST_utilisateur_6");
		bd.tools.UtilisateursTools.supprimerUtilisateurAvecPseudo("TEST_utilisateur_7");
		bd.tools.UtilisateursTools.supprimerUtilisateurAvecPseudo("TEST_utilisateur_8");
	} catch (Exception e) {
		e.printStackTrace();
	} 
	System.out.println("Fin du test d'ajout de relation d'amities");
}


private static void testSupprimerAmi() {
	System.out.println("Debut du test de suppression d'amis");
	System.out.println("On ne peut pas supprimer la relation d'amitie d'un utilisateur n'existant pas:");
	System.out.println(services.amis.SupprimerAmi.supprimerAmi("-500", "5"));
	System.out.println("On ne peut pas supprimer la relation d'amitie avec soit meme:");
	System.out.println(services.amis.SupprimerAmi.supprimerAmi("5", "5"));
	
	//Ajout d'utilisateurs et de relations d'amities
	try {
		bd.tools.UtilisateursTools.ajouterUtilisateur("5", "TEST_utilisateur_5", outils.MesMethodes.hasherMotDePasse("MotDePasse"), "mail1@gmail.com", null, null, null);
		bd.tools.UtilisateursTools.ajouterUtilisateur("6", "TEST_utilisateur_6", outils.MesMethodes.hasherMotDePasse("MotDePasse2"), "mail2@gmail.com", null, null, null);
		bd.tools.UtilisateursTools.ajouterUtilisateur("7", "TEST_utilisateur_7", outils.MesMethodes.hasherMotDePasse("MotDePasse3"), "mail3@gmail.com", null, null, null);
		bd.tools.AmitiesTools.ajouterAmi("5","6");
		bd.tools.AmitiesTools.ajouterAmi("6","5");
	} catch (Exception e) {
		e.printStackTrace();
	} 
	//suite des tests
	System.out.println("On ne peut pas supprimer une relation d'amitie avec un ami qui n'existe pas:");
	System.out.println(services.amis.SupprimerAmi.supprimerAmi("5", "40"));
	System.out.println("On ne peut pas supprimer une relation d'amitie avec un utilisateur que l'on ne suit pas:");
	System.out.println(services.amis.SupprimerAmi.supprimerAmi("5", "7"));
	System.out.println("On peut supprimer une relation d'amitie avec un utilisateur qui existe et que l'on suit:");
	System.out.println(services.amis.SupprimerAmi.supprimerAmi("5", "6"));
	System.out.println(services.amis.SupprimerAmi.supprimerAmi("6", "5"));
	
	// Nettoyage des utilisateurs crees 
	try {
		bd.tools.UtilisateursTools.supprimerUtilisateurAvecPseudo("TEST_utilisateur_5");
		bd.tools.UtilisateursTools.supprimerUtilisateurAvecPseudo("TEST_utilisateur_6");
		bd.tools.UtilisateursTools.supprimerUtilisateurAvecPseudo("TEST_utilisateur_7");
	} catch (Exception e) {
		e.printStackTrace();
	} 
	System.out.println("Fin du test de suppression d'amities");
}

}
