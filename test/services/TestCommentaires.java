package services;

import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import org.json.JSONException;

import exceptions.ClefInexistanteException;

public class TestCommentaires {
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchAlgorithmException, SQLException, UnknownHostException, ClefInexistanteException {
		bd.tools.MessagesTools.supprimerCollectionMessages();
		bd.tools.MessagesTools.supprimerCollectionCompteurs();
		bd.tools.MessagesTools.creerCollectionMessages();
		bd.tools.MessagesTools.creerCollectionCompteurs();
		
		testAjouterCommentaire();
//		testSupprimerCommentaire();
//		testListerCommentaires();
//		
		System.out.println(bd.tools.MessagesTools.getTousLesMessages().toString(4));
		System.out.println(bd.tools.MessagesTools.getTousLesCompteurs().toString(4));
	}
	
	private static void testAjouterCommentaire() throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchAlgorithmException, SQLException, ClefInexistanteException, UnknownHostException {
		System.out.println("Debut du test d'ajout de commentaires");

		// On commence par ajouter une personne dans notre base de donnees
		String clef ="";
		bd.tools.UtilisateursTools.ajouterUtilisateur("500", "Test_utilisateur_500", outils.MesMethodes.hasherMotDePasse("MotDePasse"), "mail1@gmail.com", null, null, null);
		bd.tools.SessionsTools.insertSession("500", false);
		clef = bd.tools.SessionsTools.getClefById("500");
		// Puis par ajouter des messages
		System.out.println(services.message.AjouterMessage.ajouterMessage(clef, "Je suis le message numero un"));

		System.out.println("S'il manque un des parametres de l'utilisateur, il ne peut pas ajouter de commentaires :");
		System.out.println(services.commentaire.AjouterCommentaire.ajouterCommentaire(null, "1", "Bien vu mec !"));
		System.out.println(services.commentaire.AjouterCommentaire.ajouterCommentaire(clef, null, "Bien vu mec !"));
		System.out.println(services.commentaire.AjouterCommentaire.ajouterCommentaire(clef, "1", null));
		System.out.println("Un utilisateur ne peut pas ajouter de commentaire si sa cle n'existe pas:");
		System.out.println(services.commentaire.AjouterCommentaire.ajouterCommentaire("Jesuisuneclefinexistante", "1", "Bien vu mec !"));
		System.out.println("Ajout de commentaires:");
		System.out.println(services.commentaire.AjouterCommentaire.ajouterCommentaire(clef, "1", "Coucou c'est moi le commentaire 1."));
		System.out.println(services.commentaire.AjouterCommentaire.ajouterCommentaire(clef, "1", "Et moi je suis le commentaire 2 !"));
		
		System.out.println(bd.tools.MessagesTools.getTousLesMessages().toString(4));
		
		System.out.println("Ajout like");
		System.out.println(services.like.AjouterLike.ajouterLike(clef, "1", "4"));
		System.out.println(bd.tools.MessagesTools.getTousLesMessages().toString(4));
		
		System.out.println("Suppression Like");
		System.out.println(services.like.SupprimerLike.supprimerLike(clef, "1", "4"));		
		System.out.println(bd.tools.MessagesTools.getTousLesMessages().toString(4));

		//  Suppression des messages et de l'utilisateur 
		bd.tools.MessagesTools.supprimerMessage(clef, "1");
		bd.tools.SessionsTools.suppressionCle(clef);
		bd.tools.UtilisateursTools.supprimerUtilisateurAvecPseudo("Test_utilisateur_500");
		
		System.out.println("Fin du test d'ajout de commentaires");
	}
	
	
	private static void testSupprimerCommentaire() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, NoSuchAlgorithmException, ClefInexistanteException, JSONException, UnknownHostException {
		System.out.println("Debut du test de suppression de commentaires");

		// On commence par ajouter une personne dans notre base de donnees
		String clef ="";
		bd.tools.UtilisateursTools.ajouterUtilisateur("500", "Test_utilisateur_500", outils.MesMethodes.hasherMotDePasse("MotDePasse"), "mail1@gmail.com", null, null, null);
		bd.tools.SessionsTools.insertSession("500", false);
		clef = bd.tools.SessionsTools.getClefById("500");
		// Puis par ajouter des messages
		System.out.println(services.message.AjouterMessage.ajouterMessage(clef, "Je suis le message numero un"));
		// Puis des commentaires
		System.out.println(services.commentaire.AjouterCommentaire.ajouterCommentaire(clef, "1", "Coucou c'est moi le commentaire 1."));
		System.out.println(services.commentaire.AjouterCommentaire.ajouterCommentaire(clef, "1", "Et moi je suis le commentaire 2 !"));
		
		System.out.println(bd.tools.MessagesTools.getTousLesMessages().toString(4));

		// On commence le test
		System.out.println("On supprime un commentaire poste avant un autre");
		System.out.println(services.commentaire.SupprimerCommentaire.supprimerCommentaire(clef, "1", "1"));
		System.out.println(bd.tools.MessagesTools.getTousLesMessages().toString(4));

		//  Suppression des messages et de l'utilisateur 
		bd.tools.MessagesTools.supprimerMessage(clef, "1");
		bd.tools.SessionsTools.suppressionCle(clef);
		bd.tools.UtilisateursTools.supprimerUtilisateurAvecPseudo("Test_utilisateur_500");
		
		System.out.println("Fin du test de suppression de commentaires");
	}
	
	
	private static void testListerCommentaires() throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchAlgorithmException, SQLException, JSONException, UnknownHostException, ClefInexistanteException {
		System.out.println("Debut du test de l'affichage de la liste de commentaires");
		
		// On commence par ajouter une personne dans notre base de donnees
		String clef ="";
		bd.tools.UtilisateursTools.ajouterUtilisateur("500", "Test_utilisateur_500", outils.MesMethodes.hasherMotDePasse("MotDePasse"), "mail1@gmail.com", null, null, null);
		bd.tools.SessionsTools.insertSession("500", false);
		clef = bd.tools.SessionsTools.getClefById("500");
		// Puis par ajouter des messages
		System.out.println(services.message.AjouterMessage.ajouterMessage(clef, "Je suis le message numero un"));
		// Puis des commentaires
		System.out.println(services.commentaire.AjouterCommentaire.ajouterCommentaire(clef, "1", "Coucou c'est moi le commentaire 1."));
		System.out.println(services.commentaire.AjouterCommentaire.ajouterCommentaire(clef, "1", "Et moi je suis le commentaire 2 !"));
		
		System.out.println(bd.tools.MessagesTools.getTousLesMessages().toString(4));
		
		// On commence le test
		System.out.println("On liste les commentaires d'un message");
		System.out.println(services.commentaire.ListerCommentaires.listerCommentaires(clef, "1").toString(4));
		
		//System.out.println(bd.tools.MessagesTools.getTousLesMessages().toString(4));

		//  Suppression des messages et de l'utilisateur 
		bd.tools.MessagesTools.supprimerMessage(clef, "1");
		bd.tools.SessionsTools.suppressionCle(clef);
		bd.tools.UtilisateursTools.supprimerUtilisateurAvecPseudo("Test_utilisateur_500");
		
		System.out.println("Fin du test de l'affichage de la liste de commentaires");
	}
}
