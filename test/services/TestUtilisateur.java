package services;

import java.sql.SQLException;

import services.utilisateur.CreationUtilisateur;
import exceptions.BDException;

public class TestUtilisateur {
	public static void main(String[] args) throws BDException {
		try {
			String pseudo = "SarahTropCoolDu77";
			//String mdp = "PatesCarbonara";
			String mdp = "JeSuisUNMotDePasse";
			String mdp2 = "mdp";		// Mot de passe trop court
			String email = "coucaze@yahoo.fr";
			String prenom = "Alexia";
			String nom = null;
			String anniversaire = "1995-01-31";
			boolean resultat = testCreationUtilisateur(pseudo, mdp, email, prenom, nom, anniversaire);
			System.out.println(resultat);
			
			resultat = testSuppressionUtilisateur("1", mdp);
			System.out.println(resultat);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static boolean testCreationUtilisateur(String pseudo, String mdp, String email, String prenom, String nom, String anniversaire) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		System.out.println("On vérifie que le pseudonyme n'est pas déja prit :");
		if (! bd.tools.UtilisateursTools.verificationExistencePseudo(pseudo)){
			System.out.println("Il n'existe pas. On va l'ajouter");
			System.out.println(services.utilisateur.CreationUtilisateur.creationUtilisateur(pseudo, mdp, email, prenom, nom, anniversaire));
			System.out.println("On vient de l'ajouter, on vérifie son existence :");
			return bd.tools.UtilisateursTools.verificationExistencePseudo(pseudo);
		} else {
			System.out.println("Il existe deja.");
			return false;
		}
	}
	
	private static boolean testSuppressionUtilisateur(String id, String mdp) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return false;
	}
}
