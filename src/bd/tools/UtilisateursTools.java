package bd.tools;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;

import bd.Database;

public class UtilisateursTools {
	public static boolean verificationExistencePseudo(String pseudo) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
    	// Connection à la base de données
        Connection connection = Database.getMySQLConnection();

        // Création et execution de la requête
        String requete = String.format("SELECT id FROM Utilisateurs WHERE pseudo=\"%s\";", pseudo);
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
	
	public static boolean verificationExistenceId(String id) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException  {
    	// Connection à la base de données
        Connection connection = Database.getMySQLConnection();
        
        // Création et execution de la requête
        String requete = String.format("SELECT id FROM Utilisateurs WHERE id=\"%s\";", id);
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

	public static void ajouterUtilisateur(String pseudo, String motDePasse, String email, String prenom, String nom, String anniversaire) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
    	// Connection à la base de données
        Connection connection = Database.getMySQLConnection();
        
        // Création et execution de la requête
        String requete = String.format("INSERT INTO Utilisateurs Values (null, \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\");", pseudo, motDePasse, email, prenom, nom, anniversaire);
        Statement statement = connection.createStatement();
        statement.executeUpdate(requete);
        
        // Libération des ressources
        statement.close();
        connection.close();
	}
	
	public static boolean checkPassword(String pseudo, String motDePasse) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
    	// Connection à la base de données
        Connection connection = Database.getMySQLConnection();
        
        // Création et execution de la requête
        String requete = String.format("SELECT id FROM Utilisateurs WHERE pseudo=\"%s\" AND mot_de_passe=\"%s\";", pseudo, motDePasse);
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
	
	public static String getIdUtilisateur(String pseudo) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
    	// Connection à la base de données
        Connection connection = Database.getMySQLConnection();
        
        // Création et execution de la requête
        String requete = String.format("SELECT id FROM Utilisateurs WHERE pseudo=\"%s\"", pseudo);
        Statement statement = connection.createStatement();
        statement.executeQuery(requete);
        
        // Récuperation des données
        ResultSet resultSet = statement.getResultSet();
        String id = resultSet.getString("id");
        
        // Libération des ressources
        resultSet.close();
        statement.close();
        connection.close();
        
        return id;
	}
}
