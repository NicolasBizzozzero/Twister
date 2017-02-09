package test;
 
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bd.Database;
import exceptions.BDException;

public class MytestSQL {
	public static void main(String[] args) throws BDException {
		System.out.println("Debut");
		services.utilisateur.CreationUtilisateur.creationUtilisateur("yo", "vvdvd", "cycebuhcbuycbcybcoucaze", "dzdddzad", "cgevzucguvkac") ;
    	System.out.println("UTILISATEUR CREE");
		System.out.println(bd.UtilisateursTools.verificationExistenceLogin("yo"));
		System.out.println("Fin");
	}

}