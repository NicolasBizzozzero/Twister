package services;

import java.sql.SQLException;

import services.utilisateur.CreationUtilisateur;
import exceptions.BDException;

public class TestUtilisateur {
	public static void main(String[] args) throws BDException {
		try {
			String pseudo = "PseudoNul75_5";
			//String mdp = "PatesCarbonara";
			String mdp = "JeSuisUNMotDePass\"econtenantun";
			String mdp2 = "JeSuisUNMotDeP''\"'\"'#asse//\\\\contenantun";
			String email = "weeeeeeee@gmail.com";
			String prenom = "Alexia";
			String nom = null;
			String anniversaire = "1995-01-31";
			System.out.println(testCreationUtilisateur(pseudo, mdp2, email, prenom, nom, anniversaire));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static boolean testCreationUtilisateur(String pseudo, String mdp, String email, String prenom, String nom, String anniversaire) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		System.out.println("On verifie que le pseudonyme n'est pas deja prit :");
		if (! bd.tools.UtilisateursTools.verificationExistencePseudo(pseudo)){
			System.out.println("Il n'existe pas. On va l'ajouter");
			System.out.println(services.utilisateur.CreationUtilisateur.creationUtilisateur(pseudo, mdp, email, prenom, nom, anniversaire));
			System.out.println("On vient de l'ajouter, on verifie son existence :");
			return bd.tools.UtilisateursTools.verificationExistencePseudo(pseudo);
		} else {
			System.out.println("Il existe deja.");
			return false;
		}
	}
	
	/*private static boolean testCreation1000Utilisateur() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		System.out.println("On verifie que le pseudonyme n'est pas deja prit :");
		if (! bd.tools.UtilisateursTools.verificationExistencePseudo()){
			System.out.println("Il n'existe pas. On va l'ajouter");
			System.out.println(services.utilisateur.CreationUtilisateur.creationUtilisateur(pseudo, mdp, email, prenom, nom, anniversaire));
			System.out.println("On vient de l'ajouter, on verifie son existence :");
			return bd.tools.UtilisateursTools.verificationExistencePseudo(pseudo);
		} else {
			System.out.println("Il existe deja.");
			return false;
		}
	}*/
	
	private static boolean testSuppressionUtilisateur(String id, String mdp) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return false;
	}
}
