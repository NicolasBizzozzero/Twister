package services.amis;

import org.json.JSONObject;

import exceptions.BDException;

public class TestSupprimerAmi {

	public static void main(String[] args) throws BDException{
		System.out.println("Debut");
		JSONObject res= services.amis.SupprimerAmi.supprimerAmi("5","6") ;
    	System.out.println(res.toString());
		System.out.println("Amities Creee");
		//System.out.println(bd.tools.UtilisateursTools.verificationExistencePseudo("yo"));
		System.out.println("Fin");

	}

}