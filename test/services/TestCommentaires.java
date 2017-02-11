package services;


public class TestCommentaires {

	public static void main(String[] args) {
		testAjouterCommentaire();
		testSupprimerCommentaire();
		testListerCommentaires();

	}
	private static void testAjouterCommentaire() {
		
		System.out.println("Debut du test d'ajout de commentaires");
		
		//on commence par ajouter une personne dans notre base de donnees
		try {
			bd.tools.UtilisateursTools.ajouterUtilisateur("5", "TEST_utilisateur_5", outils.MesMethodes.hasherMotDePasse("MotDePasse"), "mail1@gmail.com", null, null, null);
		} catch (Exception e) {
			e.printStackTrace();
		} 

		System.out.println("Un utilisateur inexistant ne peut pas ajouter de commentaire:");
		System.out.println(services.commentaire.AjouterCommentaire.ajouterCommentaire("Je suis un jolie commentaire","5","Test_utilisateur_8") );
		System.out.println("S'il manque un des parametres ( message, identifiant et pseudo de l'utilisateur, il ne peut pas ajouter de messages:");
		System.out.println(services.commentaire.AjouterCommentaire.ajouterCommentaire(null,"5","Test_utilisateur_5"));
		System.out.println("Ajout de commentaires:");
		System.out.println(services.commentaire.AjouterCommentaire.ajouterCommentaire("Je suis le message numero un","5","Test_utilisateur_5"));
		System.out.println(services.commentaire.AjouterCommentaire.ajouterCommentaire("Je suis le message numero deux","5","Test_utilisateur_5"));
		System.out.println(services.commentaire.AjouterCommentaire.ajouterCommentaire("Je suis le message numero trois","5","Test_utilisateur_5"));
		System.out.println(services.commentaire.AjouterCommentaire.ajouterCommentaire("Je suis le message numero quatre","5","Test_utilisateur_5"));

		//  Suppression des commentaires et de l'utilisateur 
		try {
			bd.tools.CommentairesTools.supprimerCommentaire("5","Je suis le message numero un");
			bd.tools.CommentairesTools.supprimerCommentaire("5","Je suis le message numero deux");
			bd.tools.CommentairesTools.supprimerCommentaire("5","Je suis le message numero trois");
			bd.tools.CommentairesTools.supprimerCommentaire("5","Je suis le message numero quatre");
			bd.tools.UtilisateursTools.supprimerUtilisateurAvecPseudo("TEST_utilisateur_5");

		} catch (Exception e) {
			e.printStackTrace();
		} 
		System.out.println("Fin du test d'ajout de Commentaires");
	}


	private static void testSupprimerCommentaire() {
		System.out.println("Debut du test de suppression des commentaires");
		System.out.println("On ne peut pas supprimer un commentaire d'un utilisateur n'existant pas:");
		System.out.println(services.commentaire.SupprimerCommentaire.supprimerCommentaire("-500", "je suis un commentaire"));
		
		//Ajout d'utilisateurs et creation de messages
		try {
			bd.tools.UtilisateursTools.ajouterUtilisateur("5", "TEST_utilisateur_5", outils.MesMethodes.hasherMotDePasse("MotDePasse"), "mail1@gmail.com", null, null, null);
			bd.tools.CommentairesTools.ajouterCommentaire("Je suis le message numero un","5","Test_utilisateur_5");
			bd.tools.CommentairesTools.ajouterCommentaire("Je suis le message numero deux","5","Test_utilisateur_5");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		//suite des tests
		System.out.println("On ne peut pas supprimer uncommentaire qui n'existe pas:");
		System.out.println(services.commentaire.SupprimerCommentaire.supprimerCommentaire("5", "je suis un commentaire"));
		System.out.println("Une suppression de commentaire ne fonctionne pas si l'un des paramètre est null:");
		System.out.println(services.commentaire.SupprimerCommentaire.supprimerCommentaire(null, "je suis un commentaire"));
		System.out.println(services.commentaire.SupprimerCommentaire.supprimerCommentaire("5", null));
		System.out.println("Suppression de commentaires:");
		System.out.println(services.commentaire.SupprimerCommentaire.supprimerCommentaire("5", "Je suis le message numero un"));
		System.out.println(services.commentaire.SupprimerCommentaire.supprimerCommentaire("5", "Je suis le message numero deux"));
		
		// Nettoyage des utilisateurs crees 
		try {
			bd.tools.UtilisateursTools.supprimerUtilisateurAvecPseudo("TEST_utilisateur_5");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		System.out.println("Fin du test de suppression de commentaires");
	}
	private static void testListerCommentaires() {
		System.out.println("Debut du test de l'affichage de la liste des commentaires");
		System.out.println("On ne peut pas afficher les commentaire d'un utilisateur qui n'existe pas:");
		System.out.println(services.commentaire.ListerCommentaires.listerCommentaires("-500",0,30));
		
		//Ajout d'utilisateurs et de commentaires
		try {
			bd.tools.UtilisateursTools.ajouterUtilisateur("5", "TEST_utilisateur_5", outils.MesMethodes.hasherMotDePasse("MotDePasse"), "mail1@gmail.com", null, null, null);
			bd.tools.CommentairesTools.ajouterCommentaire("Je suis le message numero un","5","Test_utilisateur_5");
			bd.tools.CommentairesTools.ajouterCommentaire("Je suis le message numero deux","5","Test_utilisateur_5");
			bd.tools.CommentairesTools.ajouterCommentaire("Je suis le message numero trois","5","Test_utilisateur_5");
			bd.tools.CommentairesTools.ajouterCommentaire("Je suis le message numero quatre","5","Test_utilisateur_5");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		//suite des tests
		System.out.println("On affiche la liste des commentaires de l'utilisateur en partant du premier et en allant jusqu'au 5 em:");
		System.out.println(services.commentaire.ListerCommentaires.listerCommentaires("5", 1,5));
		System.out.println("On affiche la liste des commentaires de l'utilisateur en partant du premier et en allant jusqu'au 3 em:");
		System.out.println(services.commentaire.ListerCommentaires.listerCommentaires("5", 1,3));
		System.out.println("On affiche la liste des commentaires de l'utilisateur en partant du 3em et en allant jusqu'au 4 em:");
		System.out.println(services.commentaire.ListerCommentaires.listerCommentaires("5", 3,4));
		
		// Suppression des commentaires et de l'utilisateur 
		try {
			bd.tools.CommentairesTools.supprimerCommentaire("5","Je suis le message numero un");
			bd.tools.CommentairesTools.supprimerCommentaire("5","Je suis le message numero deux");
			bd.tools.CommentairesTools.supprimerCommentaire("5","Je suis le message numero trois");
			bd.tools.CommentairesTools.supprimerCommentaire("5","Je suis le message numero quatre");
			bd.tools.UtilisateursTools.supprimerUtilisateurAvecPseudo("TEST_utilisateur_5");
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		System.out.println("Fin du test de listage de commentaires");
	}


	}

