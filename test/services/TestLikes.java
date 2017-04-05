package services;

import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import exceptions.ClefInexistanteException;

public class TestLikes {
	public static void main(String[] args) throws Exception {
		ajouterLike();
	}
	
	private static void ajouterLike() throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchAlgorithmException, SQLException, ClefInexistanteException, UnknownHostException {
		String pseudo = "TEST_utilisateur_1";
		String motDePasse = "motDePasseEnClair77";
		String email = "mail1@gmail.com";
		bd.tools.UtilisateursTools.ajouterUtilisateur(pseudo, outils.MesMethodes.hasherMotDePasse(motDePasse), email, null, null, null);
		services.authentification.Login.login(pseudo, motDePasse);
		String id_utilisateur = bd.tools.UtilisateursTools.getIDByPseudo(pseudo);
		String clef = bd.tools.SessionsTools.getClefById(id_utilisateur);
		services.message.AjouterMessage.ajouterMessage(clef, "Ceci est un message");
		Integer id_message = (Integer) bd.tools.MessagesTools.getNombreDeMessages();
		
		System.out.println("#####\n# Debut du test de  AjouterLike\n#####");
		
		System.out.println("On verifie qu'un utilisateur ne peut pas liker deux fois le meme commentaire de meme type");
		System.out.println(services.like.AjouterLike.ajouterLike(clef, id_message.toString(), "0"));
		System.out.println(bd.tools.MessagesTools.getTousLesMessages().toString(4));
		System.out.println(services.like.AjouterLike.ajouterLike(clef, id_message.toString(), "0"));
		System.out.println(bd.tools.MessagesTools.getTousLesMessages().toString(4));
		
		System.out.println("\n#####\n# Fin du test de AjouterLike\n#####");
		
		bd.tools.SessionsTools.suppressionCle(clef);
		bd.tools.UtilisateursTools.supprimerUtilisateurAvecId(id_utilisateur);
	}
}
