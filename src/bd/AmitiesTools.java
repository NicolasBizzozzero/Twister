package bd;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import exceptions.BDException;

public class AmitiesTools {
	public static boolean sontAmis(String id_ami1, String id_ami2) throws BDException {
	    try {
	    	// Connection à la base de données
	        Connection connection = Database.getMySQLConnection();
	        String requete = String.format("SELECT * FROM Amities WHERE (id_ami1=%s AND id_ami2=%s) OR (id_ami1=%s AND id_ami2=%s)", id_ami1, id_ami2, id_ami2, id_ami1);
	        
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
	        throw new BDException("Impossible de se connecter a la base de données");
	    }
	}
}
