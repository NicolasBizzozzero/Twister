package services;

import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import org.json.JSONException;

import exceptions.ClefInexistanteException;

public class TestCommentaires {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, NoSuchAlgorithmException, UnknownHostException, ClefInexistanteException {
		bd.tools.CommentairesTools.ajouterCommentaire("Salut les amis", "9");
		bd.tools.CommentairesTools.viderMongoDB();
		//testAjouterCommentaire();
		//testSupprimerCommentaire();
		//testListerCommentaires();

	}

	private static void testAjouterCommentaire() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, ClefInexistanteException, NoSuchAlgorithmException, UnknownHostException {
		System.out.println("Debut du test d'ajout de commentaires");
		String clef ="";
		//on commence par ajouter une personne dans notre base de donnees
		bd.tools.UtilisateursTools.ajouterUtilisateur("500", "Test_utilisateur_500", outils.MesMethodes.hasherMotDePasse("MotDePasse"), "mail1@gmail.com", null, null, null);
		bd.tools.SessionsTools.insertSession("500", false);
		clef = bd.tools.SessionsTools.getClefbyId("500");

		System.out.println("S'il manque un des parametres ( message, identifiant et pseudo de l'utilisateur, il ne peut pas ajouter de messages:");
		System.out.println(services.commentaire.AjouterCommentaire.ajouterCommentaire(clef,null));
		System.out.println("Un utilisateur ne peut pas ajouter de commentaires si sa cle n'existe pas:");
		System.out.println(services.commentaire.AjouterCommentaire.ajouterCommentaire("4416!!fuihfeufnfez","Je suis un joli commentaire") );
		System.out.println("Ajout de commentaires:");
		System.out.println(services.commentaire.AjouterCommentaire.ajouterCommentaire(clef,"Je suis le message numero un"));
		System.out.println(services.commentaire.AjouterCommentaire.ajouterCommentaire(clef,"Je suis le message numero deux"));
		System.out.println(services.commentaire.AjouterCommentaire.ajouterCommentaire(clef,"Je suis le message numero trois"));
		System.out.println(services.commentaire.AjouterCommentaire.ajouterCommentaire(clef,"Je suis le message numero quatre"));

		//  Suppression des commentaires et de l'utilisateur 
		bd.tools.CommentairesTools.supprimerCommentaire("500","Je suis le message numero un");
		bd.tools.CommentairesTools.supprimerCommentaire("500","Je suis le message numero deux");
		bd.tools.CommentairesTools.supprimerCommentaire("500","Je suis le message numero trois");
		bd.tools.CommentairesTools.supprimerCommentaire("500","Je suis le message numero quatre");
		bd.tools.SessionsTools.suppressionCle(clef);
		bd.tools.UtilisateursTools.supprimerUtilisateurAvecPseudo("Test_utilisateur_500");

		System.out.println("Fin du test d'ajout de Commentaires\n\n");
	}


	private static void testSupprimerCommentaire() throws JSONException, UnknownHostException {
		System.out.println("Debut du test de suppression des commentaires");
		bd.tools.CommentairesTools.viderMongoDB();
		System.out.println("On ne peut pas supprimer un commentaire d'un utilisateur n'existant pas:");
		System.out.println(services.commentaire.SupprimerCommentaire.supprimerCommentaire("-500", "je suis un commentaire"));
		String clef="";
		
		//Ajout d'utilisateurs et creation de messages
		try {
			bd.tools.UtilisateursTools.ajouterUtilisateur("500", "Test_utilisateur_500", outils.MesMethodes.hasherMotDePasse("MotDePasse"), "mail1@gmail.com", null, null, null);
			bd.tools.SessionsTools.insertSession("500", false);
			clef = bd.tools.SessionsTools.getClefbyId("500");
			bd.tools.CommentairesTools.ajouterCommentaire("Je suis le message numero un", "500");
			bd.tools.CommentairesTools.ajouterCommentaire("Je suis le message numero deux", "500");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		//suite des tests
		System.out.println("Un utilisateur ne peut pas supprimer de commentaires si sa cle n'existe pas:");
		System.out.println(services.commentaire.SupprimerCommentaire.supprimerCommentaire("2578","Je suis un joli commentaire") );
		System.out.println("On ne peut pas supprimer un commentaire qui n'existe pas:");
		//TODO: Verifier ce test
		System.out.println(services.commentaire.SupprimerCommentaire.supprimerCommentaire(clef, "je suis un commentaire"));
		System.out.println("Une suppression de commentaire ne fonctionne pas si l'un des parametre est null:");
		System.out.println(services.commentaire.SupprimerCommentaire.supprimerCommentaire(null, "je suis un commentaire"));
		System.out.println(services.commentaire.SupprimerCommentaire.supprimerCommentaire(clef, null));
		System.out.println("Suppression de commentaires:");
		//TODO: Verifier ces tests
		System.out.println(services.commentaire.SupprimerCommentaire.supprimerCommentaire(clef, "Je suis le message numero un"));
		System.out.println(services.commentaire.SupprimerCommentaire.supprimerCommentaire(clef, "Je suis le message numero deux"));
		
		// Nettoyage des utilisateurs crees 
		try {
			bd.tools.SessionsTools.suppressionCle(clef);
			bd.tools.UtilisateursTools.supprimerUtilisateurAvecPseudo("Test_utilisateur_500");
		} catch (Exception e) {
			e.printStackTrace();
		} 

		System.out.println("Fin du test de suppression de commentaires\n\n");
	}
	private static void testListerCommentaires() throws JSONException, UnknownHostException {
		System.out.println("Debut du test de l'affichage de la liste des commentaires");
		System.out.println("On ne peut pas afficher les commentaire d'un utilisateur qui n'existe pas:");
		System.out.println(services.commentaire.ListerCommentaires.listerCommentaires("-500",0,30));
		String clef="";
		
		//Ajout d'utilisateurs et de commentaires
		try {
			bd.tools.SessionsTools.insertSession("500", false);
			clef = bd.tools.SessionsTools.getClefbyId("500");
			bd.tools.UtilisateursTools.ajouterUtilisateur("500", "Test_utilisateur_500", outils.MesMethodes.hasherMotDePasse("MotDePasse"), "mail1@gmail.com", null, null, null);
			bd.tools.CommentairesTools.ajouterCommentaire("Je suis le message numero un", "500");
			bd.tools.CommentairesTools.ajouterCommentaire("Je suis le message numero deux", "500");
			bd.tools.CommentairesTools.ajouterCommentaire("Je suis le message numero trois", "500");
			bd.tools.CommentairesTools.ajouterCommentaire("Je suis le message numero quatre", "500");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		//suite des tests
		System.out.println("Un utilisateur ne peut pas afficher de commentaires si sa cle n'existe pas:");
		System.out.println(services.commentaire.ListerCommentaires.listerCommentaires("ygdhzdz!dzyg54", 0, 500) );
		//TODO verifier les trois tests ci-dessous
		System.out.println("On affiche la liste des commentaires de l'utilisateur en partant du premier et en allant jusqu'au 4 em:");
		System.out.println(services.commentaire.ListerCommentaires.listerCommentaires(clef, 0,4).toString(4));
		System.out.println("On affiche la liste des commentaires de l'utilisateur en partant du premier et en allant jusqu'au 2 em:");
		System.out.println(services.commentaire.ListerCommentaires.listerCommentaires(clef, 0,2).toString(4));
		System.out.println("On affiche la liste des commentaires de l'utilisateur en partant du 2em et en allant jusqu'au 2 em:");
		System.out.println(services.commentaire.ListerCommentaires.listerCommentaires(clef, 1,1).toString(4));
		
		//System.out.println(bd.tools.CommentairesTools.getTousLesCommentaires().toString(4));
		
		//bd.tools.CommentairesTools.viderMongoDB();
		
		
		
		// Suppression des commentaires et de l'utilisateur 
		try {
			bd.tools.CommentairesTools.supprimerCommentaire("500","Je suis le message numero un");
			bd.tools.CommentairesTools.supprimerCommentaire("500","Je suis le message numero deux");
			bd.tools.CommentairesTools.supprimerCommentaire("500","Je suis le message numero trois");
			bd.tools.CommentairesTools.supprimerCommentaire("500","Je suis le message numero quatre");
			bd.tools.SessionsTools.suppressionCle(clef);
			bd.tools.UtilisateursTools.supprimerUtilisateurAvecPseudo("Test_utilisateur_500");
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		System.out.println("Fin du test de listage de commentaires");

	}


	}

