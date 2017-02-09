package services.utilisateur;

import exceptions.BDException;

public class TestCreationUtilisateur {
	public static void main(String[] args) throws BDException {
		System.out.println("Debut");
		services.utilisateur.CreationUtilisateur.creationUtilisateur("Nico", "jesuisunmdpenclair", "coucaze@yahoo.fr", "Nicolas", null, "1995-01-31") ;
    	System.out.println("UTILISATEUR CREE");
		//System.out.println(bd.tools.UtilisateursTools.verificationExistencePseudo("yo"));
		System.out.println("Fin");
	}
}
