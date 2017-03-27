package services;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import exceptions.ClefInexistanteException;

public class TestSession {
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, ClefInexistanteException, InterruptedException, NoSuchAlgorithmException {
		testLogin();
		testLogout();
	}
	
	private static void testLogin() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, ClefInexistanteException, NoSuchAlgorithmException{
		String pseudo = "TEST_utilisateur_1";
		String motDePasse = "motDePasseEnClair77";
		String email = "mail1@gmail.com";
		bd.tools.UtilisateursTools.ajouterUtilisateur(pseudo, outils.MesMethodes.hasherMotDePasse(motDePasse), email, null, null, null);
		
		System.out.println("#####\n# Debut du test de Login\n#####");
		
		System.out.println("\nOn peut se loguer:");
		System.out.println(services.authentification.Login.login(pseudo, motDePasse).toString(4));
		
		System.out.println("\n#####\n# Fin du test de Login\n#####");
		
		String id = bd.tools.UtilisateursTools.getIdUtilisateur(pseudo);
		String clef = bd.tools.SessionsTools.getClefById(id);
		bd.tools.SessionsTools.suppressionCle(clef);
		bd.tools.UtilisateursTools.supprimerUtilisateurAvecId(id);
	}
	
	private static void testLogout() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, ClefInexistanteException, NoSuchAlgorithmException {
		String pseudo = "TEST_utilisateur_1";
		String motDePasse = "motDePasseEnClair77";
		String email = "mail1@gmail.com";
		bd.tools.UtilisateursTools.ajouterUtilisateur(pseudo, outils.MesMethodes.hasherMotDePasse(motDePasse), email, null, null, null);
		String id = bd.tools.UtilisateursTools.getIdUtilisateur(pseudo);
		bd.tools.SessionsTools.insertSession(id, false);
		String clef = bd.tools.SessionsTools.getClefById(id);
		
		System.out.println("#####\n# Debut du test de Logout\n#####");
		
		System.out.println("\nOn peut se logout:");
		System.out.println(services.authentification.Logout.logout(clef).toString(4));
		
		System.out.println("\n#####\n# Fin du test de Logout\n#####");
		
		bd.tools.SessionsTools.suppressionCle(clef);
		bd.tools.UtilisateursTools.supprimerUtilisateurAvecId(id);
	}
}
