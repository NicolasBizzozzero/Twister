package bd;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import exceptions.BDException;

public class SessionsTools {

	public static boolean estConnecte(String id_utilisateur) throws BDException {
	    try {
	    	// Connection à la base de données
	        Connection connection = Database.getMySQLConnection();
	        String requete = String.format("SELECT * FROM Sessions WHERE id=%s", id_utilisateur);
	        
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
}
