package services;

import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

import exceptions.ClefInexistanteException;

public class TestMessages {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, NoSuchAlgorithmException, UnknownHostException, ClefInexistanteException {
		bd.tools.MessagesTools.supprimerCollectionMessages();
		bd.tools.MessagesTools.supprimerCollectionCompteurs();
		bd.tools.MessagesTools.creerCollectionMessages();
		bd.tools.MessagesTools.creerCollectionCompteurs();
		
		System.out.println(bd.tools.MessagesTools.getTousLesMessages().toString(4));
		System.out.println(bd.tools.MessagesTools.getTousLesCompteurs().toString(4));
		
		testAjouterMessage();
		//testSupprimerMessage();
		//testListerMessages();
		
		System.out.println(bd.tools.MessagesTools.getTousLesMessages().toString(4));
		System.out.println(bd.tools.MessagesTools.getTousLesCompteurs().toString(4));
	}

	private static void testAjouterMessage() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, ClefInexistanteException, NoSuchAlgorithmException, UnknownHostException {
		System.out.println("Debut du test d'ajout de messages");
		String clef ="";
		// On commence par ajouter une personne dans notre base de donnees
		bd.tools.UtilisateursTools.ajouterUtilisateur("500", "Test_utilisateur_500", outils.MesMethodes.hasherMotDePasse("MotDePasse"), "mail1@gmail.com", null, null, null);
		bd.tools.SessionsTools.insertSession("500", false);
		clef = bd.tools.SessionsTools.getClefbyId("500");

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
			clef = bd.tools.SessionsTools.getClefbyId("500");
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

	private static void testListerMessages() throws JSONException, UnknownHostException {
//		System.out.println("Debut du test de l'affichage de la liste des messages");
//		System.out.println("On ne peut pas afficher les messages d'un utilisateur qui n'existe pas:");
//		System.out.println(services.message.ListerMessages.listerMessages("-500", 0, 30));
//		String clef="";
//		
//		//Ajout d'utilisateurs et de messages
//		try {
//			bd.tools.SessionsTools.insertSession("500", false);
//			clef = bd.tools.SessionsTools.getClefbyId("500");
//			bd.tools.UtilisateursTools.ajouterUtilisateur("500", "Test_utilisateur_500", outils.MesMethodes.hasherMotDePasse("MotDePasse"), "mail1@gmail.com", null, null, null);
//			bd.tools.MessagesTools.ajouterMessage("Je suis le message numero un", "500");
//			bd.tools.MessagesTools.ajouterMessage("Je suis le message numero deux", "500");
//			bd.tools.MessagesTools.ajouterMessage("Je suis le message numero trois", "500");
//			bd.tools.MessagesTools.ajouterMessage("Je suis le message numero quatre", "500");
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 
//		//suite des tests
//		System.out.println("Un utilisateur ne peut pas afficher de messages si sa cle n'existe pas:");
//		System.out.println(services.message.ListerMessages.listerMessages("ygdhzdz!dzyg54", 0, 500) );
//		//TODO verifier les trois tests ci-dessous
//		System.out.println("On affiche la liste des messages de l'utilisateur en partant du premier et en allant jusqu'au 4 em:");
//		System.out.println(services.message.ListerMessages.listerMessages(clef, 0,4).toString(4));
//		System.out.println("On affiche la liste des messages de l'utilisateur en partant du premier et en allant jusqu'au 2 em:");
//		System.out.println(services.message.ListerMessages.listerMessages(clef, 0,2).toString(4));
//		System.out.println("On affiche la liste des messages de l'utilisateur en partant du 2em et en allant jusqu'au 2 em:");
//		System.out.println(services.message.ListerMessages.listerMessages(clef, 1,1).toString(4));
//
//		
//		
//		// Suppression des messages et de l'utilisateur 
//		try {
//			bd.tools.MessagesTools.supprimerMessage("500","Je suis le message numero un");
//			bd.tools.MessagesTools.supprimerMessage("500","Je suis le message numero deux");
//			bd.tools.MessagesTools.supprimerMessage("500","Je suis le message numero trois");
//			bd.tools.MessagesTools.supprimerMessage("500","Je suis le message numero quatre");
//			bd.tools.SessionsTools.suppressionCle(clef);
//			bd.tools.UtilisateursTools.supprimerUtilisateurAvecPseudo("Test_utilisateur_500");
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 
//		System.out.println("Fin du test de listage de messages");
	}


}
