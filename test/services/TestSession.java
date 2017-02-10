package services;

import java.sql.SQLException;

import exceptions.BDException;

public class TestSession {
	public static void main(String[] args) throws BDException {
		try {
			String pseudo = "MegaPizzaSansOlives";
			String mdp = "MonSuperMotDePasse";
			testLogin(pseudo, mdp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void testLogin(String pseudo, String mdp) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		System.out.println(services.authentification.Login.login(pseudo, mdp));
	}
	
	private static boolean testSuppressionUtilisateur(String id, String mdp) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return false;
	}
}
