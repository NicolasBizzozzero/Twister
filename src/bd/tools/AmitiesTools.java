package bd.tools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONObject;

import bd.Database;
import exceptions.BDException;
import exceptions.IndexInvalideException;

public class AmitiesTools {
	public static boolean suitDeja(String id_ami1, String id_ami2) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		// Connection à la base de données
        Connection connection = Database.getMySQLConnection();
        
        // Création et execution de la requête
        String requete = String.format("SELECT * FROM Amities WHERE (id_ami1=\"%s\" AND id_ami2=\"%s\");", id_ami1, id_ami2);
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
	}
	public static void ajouterAmi(String id_ami1,String id_ami2) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
    	// Connection à la base de données
        Connection connection = Database.getMySQLConnection();
        
        // Création et execution de la requête
        String requete = String.format("INSERT INTO Amities Values (\"%s\", \"%s\", null);", id_ami1, id_ami2 );
        Statement statement = connection.createStatement();
        statement.executeUpdate(requete);
        
        // Libération des ressources
        statement.close();
        connection.close();
	}
	
	public static void supprimerAmi(String id_ami1,String id_ami2) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
    	// Connection à la base de données
        Connection connection = Database.getMySQLConnection();
        
        // Création et execution de la requête
        String requete = String.format("DELETE FROM Amities WHERE id_ami1=\"%s\" AND id_ami2=\"%s\";", id_ami1, id_ami2 );
        Statement statement = connection.createStatement();
        statement.executeUpdate(requete);
        
        // Libération des ressources
        statement.close();
        connection.close();
	}

	/**
	 * Affiche nombre_demandes d'amis de l'utilisateur d'ID id_utilisateur en partant de index_debut.
	 * @param id_utilisateur
	 * @param index_debut : Sa valeur initiale est zero. Indique  � partir de quel index on souhaite lister les amis.
	 * @param nombre_demandes : Indique le nombre d'amis d�sir�s.
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IndexInvalideException
	 */
	public static JSONObject listerAmis(String id_utilisateur, int index_debut, int nombre_demandes) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IndexInvalideException {
    	if (index_debut < 0) {
    		throw new IndexInvalideException("L'index ne peut pas negatif.");
    	}
		
		// Connection à la base de données
        Connection connection = Database.getMySQLConnection();
        
        // Création et execution de la requête
        String requete = String.format("SELECT id_ami2 FROM Amities WHERE id_ami1=\"%s\" LIMIT %d OFFSET %d;", nombre_demandes, index_debut);
        Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery(requete);
        
        // création d'un JSONObject dans lequel on met les amis
        JSONObject liste = new JSONObject();
        while (res.next()){
        	liste.accumulate("Amis", res.getString("id_ami2"));
        }
        
        // Libération des ressources
        statement.close();
        connection.close();
        
        return liste;
	}
		
}
