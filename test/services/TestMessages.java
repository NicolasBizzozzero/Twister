package services;

import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import org.json.JSONException;

import exceptions.ClefInexistanteException;

public class TestMessages {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, NoSuchAlgorithmException, UnknownHostException, ClefInexistanteException {
//		bd.tools.MessagesTools.supprimerCollectionMessages();
//		bd.tools.MessagesTools.supprimerCollectionCompteurs();
//		bd.tools.MessagesTools.creerCollectionMessages();
//		bd.tools.MessagesTools.creerCollectionCompteurs();
//		
//		System.out.println(bd.tools.MessagesTools.getTousLesMessages().toString(4));
//		System.out.println(bd.tools.MessagesTools.getTousLesCompteurs().toString(4));
//		
//		testAjouterMessage();
//		testSupprimerMessage();
//		System.out.println(bd.tools.MessagesTools.getTousLesMessages().toString(4));
		testListerMessages();
//		
//		System.out.println(bd.tools.MessagesTools.getTousLesMessages().toString(4));
//		System.out.println(bd.tools.MessagesTools.getTousLesCompteurs().toString(4));
//		System.out.println(bd.tools.MessagesTools.getTousLesMessages().toString(4));
	}

	private static void testAjouterMessage() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, ClefInexistanteException, NoSuchAlgorithmException, UnknownHostException {
		System.out.println("Debut du test d'ajout de messages");

		// On commence par ajouter une personne dans notre base de donnees
		bd.tools.UtilisateursTools.ajouterUtilisateur("500", "Test_utilisateur_500", outils.MesMethodes.hasherMotDePasse("MotDePasse"), "mail1@gmail.com", null, null, null);
		bd.tools.SessionsTools.insertSession("500", false);
		String clef = bd.tools.SessionsTools.getClefById("500");

		System.out.println("S'il manque un des parametres (message, identifiant, pseudo) de l'utilisateur, il ne peut pas ajouter de messages:");
		System.out.println(services.message.AjouterMessage.ajouterMessage(clef, null));
		System.out.println("Un utilisateur ne peut pas ajouter de messages si sa cle n'existe pas:");
		System.out.println(services.message.AjouterMessage.ajouterMessage("4416!!fuihfeufnfez","Je suis un joli message") );
		System.out.println("Ajout de messages:");
		System.out.println(services.message.AjouterMessage.ajouterMessage(clef, "Je suis le message numero un"));
		System.out.println(services.message.AjouterMessage.ajouterMessage(clef, "Je suis le message numero deux"));
		System.out.println(services.message.AjouterMessage.ajouterMessage(clef, "Je suis le message numero trois"));
		System.out.println(services.message.AjouterMessage.ajouterMessage(clef, "Je suis le message numero quatre"));
		
		System.out.println(bd.tools.MessagesTools.getTousLesMessages().toString(4));
		System.out.println(bd.tools.LikesTools.compteTypeLikes("1").toString(4));


		//  Suppression des messages et de l'utilisateur 
		bd.tools.MessagesTools.supprimerMessage(clef, "1");
		bd.tools.MessagesTools.supprimerMessage(clef, "2");
		bd.tools.MessagesTools.supprimerMessage(clef, "3");
		bd.tools.MessagesTools.supprimerMessage(clef, "4");
		bd.tools.SessionsTools.suppressionCle(clef);
		bd.tools.UtilisateursTools.supprimerUtilisateurAvecPseudo("Test_utilisateur_500");

		System.out.println("Fin du test d'ajout de messages\n\n");
	}


	private static void testSupprimerMessage() throws JSONException, UnknownHostException {
		System.out.println("Debut du test de suppression des messages");
		System.out.println("On ne peut pas supprimer un messages d'un utilisateur n'existant pas:");
		System.out.println(services.message.SupprimerMessage.supprimerMessage("-500", "je suis un message"));
		String clef="";
		
		//Ajout d'utilisateurs et creation de messages
		try {
			bd.tools.UtilisateursTools.ajouterUtilisateur("500", "Test_utilisateur_500", outils.MesMethodes.hasherMotDePasse("MotDePasse"), "mail1@gmail.com", null, null, null);
			bd.tools.SessionsTools.insertSession("500", false);
			clef = bd.tools.SessionsTools.getClefById("500");
			services.message.AjouterMessage.ajouterMessage(clef, "Je suis le message numero un");
			services.message.AjouterMessage.ajouterMessage(clef, "Je suis le message numero deux");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		//suite des tests
		System.out.println("Un utilisateur ne peut pas supprimer de message si sa cle n'existe pas:");
		System.out.println(services.message.SupprimerMessage.supprimerMessage("JeSuisUneClefInexistante","Je suis un joli message") );
		System.out.println("On ne peut pas supprimer un message qui n'existe pas:");
		//TODO: Verifier ce test
		System.out.println(services.message.SupprimerMessage.supprimerMessage(clef, "-500"));
		System.out.println("Une suppression de message ne fonctionne pas si l'un des parametre est null:");
		System.out.println(services.message.SupprimerMessage.supprimerMessage(null, "1"));
		System.out.println(services.message.SupprimerMessage.supprimerMessage(clef, null));
		System.out.println("Suppression de messages:");
		//TODO: Verifier ces tests
		System.out.println(services.message.SupprimerMessage.supprimerMessage(clef, "1"));
		System.out.println(services.message.SupprimerMessage.supprimerMessage(clef, "2"));
		
		// Nettoyage des utilisateurs crees 
		try {
			bd.tools.SessionsTools.suppressionCle(clef);
			bd.tools.UtilisateursTools.supprimerUtilisateurAvecPseudo("Test_utilisateur_500");
		} catch (Exception e) {
			e.printStackTrace();
		} 

		System.out.println("Fin du test de suppression de messages\n\n");
	}

	private static void testListerMessages() throws JSONException, UnknownHostException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, ClefInexistanteException, NoSuchAlgorithmException {
		System.out.println("Debut du test de l'affichage de la liste des messages");

		// On commence par ajouter une personne dans notre base de donnees
		String pseudo = "TEST_utilisateur_1";
		String motDePasse = "motDePasseEnClair77";
		String email = "mail1@gmail.com";
//		bd.tools.UtilisateursTools.ajouterUtilisateur(pseudo, outils.MesMethodes.hasherMotDePasse(motDePasse), email, null, null, null);
		System.out.println("On login");
		services.authentification.Login.login(pseudo, motDePasse);
		String id_utilisateur = bd.tools.UtilisateursTools.getIDByPseudo(pseudo);
		String clef = bd.tools.SessionsTools.getClefById(id_utilisateur);
//		System.out.println("On ajoute le message");
//		System.out.println(services.message.AjouterMessage.ajouterMessage(clef, "Ceci est un message"));
//		System.out.println(services.message.AjouterMessage.ajouterMessage(clef, "Ceci est un message 2"));
//		System.out.println(services.message.AjouterMessage.ajouterMessage(clef, "Ceci est un message 3"));
//		System.out.println(services.message.AjouterMessage.ajouterMessage(clef, "Ceci est un message 4"));
//		System.out.println(services.message.AjouterMessage.ajouterMessage(clef, "Ceci est un message 5"));
		Integer id_message = (Integer) bd.tools.MessagesTools.getNombreDeMessages();
		
		System.out.println("On liste les messages");
		System.out.println(services.message.ListerMessages.listerMessages(clef, "", id_utilisateur, "-1", "-1", "10").toString(4));
		System.out.println(services.message.ListerMessages.listerMessages(clef, "", id_utilisateur, "-1", "-1", "2").toString(4));
		System.out.println(services.message.ListerMessages.listerMessages(clef, "", id_utilisateur, "66", "63", "2").toString(4));
		System.out.println(services.message.ListerMessages.listerMessages(clef, "", id_utilisateur, "66", "63", "10").toString(4));
		
		// Suppression des messages et de l'utilisateur 
		bd.tools.SessionsTools.suppressionCle(clef);
//		bd.tools.UtilisateursTools.supprimerUtilisateurAvecPseudo("Test_utilisateur_1");

		System.out.println("Fin du test de listage de messages");
	}
}
