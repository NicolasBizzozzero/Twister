package bd;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.json.JSONObject;

import exceptions.BDException;

public class AmitiesTools {
	public static boolean suitDeja(String id_ami1, String id_ami2) throws BDException {
	    try {
	    	// Connection à la base de données
	        Connection connection = Database.getMySQLConnection();
	        String requete = String.format("SELECT * FROM Amities WHERE (id_ami1=%s AND id_ami2=%s)", id_ami1, id_ami2);
	        
	        // Création et execution de la requête
	        Statement statement = connection.createStatement();
	        statement.executeQuery(requete);
	        
	        // Récuperation des données
	        ResultSet resultSet = statement.getResultSet();
	        boolean retour = resultSet.next();
	        
	        // Libération des ressources
	        resultSet.close();
	        statement.close();
	        connection.close();
	        return retour;
	    } catch (Exception e) {
	        throw new BDException("Impossible de se connecter à la base de données");
	    }
	}
	public static void ajouterAmi(String id_ami1,String id_ami2) throws BDException {

		    try {
		    	// Connection à la base de données
		        Connection connection = Database.getMySQLConnection();
		        String requete = String.format("INSERT INTO Amities Values (%s, %s, NOW())", id_ami1, id_ami2 );
		        
		        // Création et execution de la requête
		        Statement statement = connection.createStatement();
		        statement.executeUpdate(requete);
		        
		        // Libération des ressources
		        statement.close();
		        connection.close();
		    } catch (Exception e) {
		        throw new BDException("Impossible de se connecter à la base de données");
		    }
	}
	
	public static void supprimerAmi(String id_ami1,String id_ami2) throws BDException {
	    try {
	    	// Connection à la base de données
	        Connection connection = Database.getMySQLConnection();
	        String requete = String.format("DELETE FROM Amities WHERE id_ami1=%s AND id_ami2=%s", id_ami1, id_ami2 );
	        
	        // Création et execution de la requête
	        Statement statement = connection.createStatement();
	        statement.executeUpdate(requete);
	        
	        // Libération des ressources
	        statement.close();
	        connection.close();
	    } catch (Exception e) {
	        throw new BDException("Impossible de se connecter à la base de données");
	    }
	}

	public static JSONObject listerAmis(String id_utilisateur, int limite)throws BDException {
	    try {
	    	// Connection à la base de données
	        Connection connection = Database.getMySQLConnection();
	        String requete = String.format("SELECT id_ami2 FROM Amities WHERE id_ami1=%s LIMIT %d ", id_utilisateur, limite);
	        
	        // Création et execution de la requête
	        Statement statement = connection.createStatement();
	        ResultSet res = statement.executeQuery(requete);
	        
	        // création d'un JSONObject dans lequel on met les amis
	        JSONObject liste = new JSONObject();
	        while(res.next()){
	        	liste.accumulate("Amis", res.getString("id_ami2"));
	        }
	        
	        // Libération des ressources
	        statement.close();
	        connection.close();
	        return liste;
	    } catch (Exception e) {
	        throw new BDException("Impossible de se connecter à la base de données");
	    }
	}
		
}
