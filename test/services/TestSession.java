package services;

import java.sql.SQLException;

import org.json.JSONObject;

import exceptions.BDException;
import exceptions.ClefInexistanteException;

public class TestSession {
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, ClefInexistanteException, InterruptedException {
		String pseudo = "MegaPizzaSansOlives";
		String mdp = "MonSuperMotDePasse";
		services.utilisateur.CreationUtilisateur.creationUtilisateur(pseudo, mdp, "mail1@gmail.com", null, null, null);		
		String clef = testLogin(pseudo, mdp);
		
		//java.util.concurrent.TimeUnit.MINUTES.sleep(1);
		System.out.println(bd.tools.SessionsTools.getTempsDInactivite(clef));
		System.out.println(bd.tools.SessionsTools.getTempsDInactivite(clef));
		//java.util.concurrent.TimeUnit.MINUTES.sleep(2);
		System.out.println(bd.tools.SessionsTools.getTempsDInactivite(clef));
		System.out.println(bd.tools.SessionsTools.getTempsDInactivite(clef));

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
