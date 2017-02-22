package services;

import org.json.JSONObject;

import exceptions.BDException;

public class TestSession {
	public static void main(String[] args) throws BDException {

		String pseudo = "MegaPizzaSansOlives";
		String mdp = "MonSuperMotDePasse";
		services.utilisateur.CreationUtilisateur.creationUtilisateur(pseudo, mdp, "mail1@gmail.com", null, null, null);
		String clef = testLogin(pseudo, mdp);
		testLogout(clef);
	}
	
	private static String testLogin(String pseudo, String mdp){
		JSONObject retour = services.authentification.Login.login(pseudo, mdp);
		String clef = retour.getString("clef");
		return clef;
	}
	
	private static void testLogout(String clef) {
		System.out.println(services.authentification.Logout.logout(clef));
	}
}
