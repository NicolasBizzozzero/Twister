package services.amis;

import org.json.JSONObject;

import exceptions.BDException;

public class TestAjouterAmi {

	public static void main(String[] args) throws BDException{
		System.out.println("Debut");
		System.out.println("ajout amitié 1");
		JSONObject res= services.amis.AjouterAmi.ajouterAmi("5","6") ;
		System.out.println("ajout amitié 2");
		JSONObject res2= services.amis.AjouterAmi.ajouterAmi("7","8") ;
    	System.out.println(res.toString());
		System.out.println("Amities Creee");
		//System.out.println(bd.tools.UtilisateursTools.verificationExistencePseudo("yo"));
		System.out.println("Fin");

	}

}
