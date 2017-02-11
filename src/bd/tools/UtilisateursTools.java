package bd.tools;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;

import bd.Database;

public class UtilisateursTools {
	public static boolean verificationExistencePseudo(String pseudo) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		// Connection a la base de donnees
        Connection connection = Database.getMySQLConnection();

        // Creation et execution de la requete
        String requete = String.format("SELECT id FROM Utilisateurs WHERE pseudo=\"%s\";", pseudo);
        Statement statement = connection.createStatement();
        statement.executeQuery(requete);
        
        // Recuperation des donnees
        ResultSet resultSet = statement.getResultSet();
        boolean retour = resultSet.next();
        
        // Liberation des ressources
        resultSet.close();
        statement.close();
        connection.close();
        
        return retour;
	}
	
	public static boolean verificationExistenceId(String id) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException  {
		// Connection a la base de donnees
        Connection connection = Database.getMySQLConnection();
        
        // Creation et execution de la requete
        String requete = String.format("SELECT id FROM Utilisateurs WHERE id=%s;", id);
        Statement statement = connection.createStatement();
        statement.executeQuery(requete);
        
        // Recuperation des donnees
        ResultSet resultSet = statement.getResultSet();
        boolean retour = resultSet.next();
        
        // Liberation des ressources
        resultSet.close();
        statement.close();
        connection.close();
 
        return retour;
	}

	public static void ajouterUtilisateur(String pseudo, String motDePasse, String email, String prenom, String nom, String anniversaire) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		// Connection a la base de donnees
        Connection connection = Database.getMySQLConnection();
        
        // Creation et execution de la requete
        String requete = String.format("INSERT INTO Utilisateurs Values (null, \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\");", pseudo, motDePasse, email, prenom, nom, anniversaire);
        Statement statement = connection.createStatement();
        statement.executeUpdate(requete);

        
        // Liberation des ressources
        statement.close();
        connection.close();
	}
	
	
	public static void supprimerUtilisateur(String id) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		// Connection a la base de donnees
        Connection connection = Database.getMySQLConnection();
        
        // Creation et execution de la requete
        String requete = String.format("DELETE FROM Utilisateurs WHERE id=%s;", id);
        Statement statement = connection.createStatement();
        statement.executeUpdate(requete);

        // Liberation des ressources
        statement.close();
        connection.close();
	}

	
	public static boolean checkMotDePasseAvecPseudo(String pseudo, String motDePasse) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		// Connection a la base de donnees
        Connection connection = Database.getMySQLConnection();
        
        // Creation et execution de la requete
        String requete = String.format("SELECT id FROM Utilisateurs WHERE pseudo=\"%s\" AND mot_de_passe=\"%s\";", pseudo, motDePasse);
        Statement statement = connection.createStatement();
        statement.executeQuery(requete);
        
        // Recuperation des donnees
        ResultSet resultSet = statement.getResultSet();
        boolean retour = resultSet.next();
        
        // Liberation des ressources
        resultSet.close();
        statement.close();
        connection.close();

        return retour;
	}
	
	public static boolean checkMotDePasseAvecId(String id, String motDePasse) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		// Connection a la base de donnees
        Connection connection = Database.getMySQLConnection();
        
        // Creation et execution de la requete
        String requete = String.format("SELECT id FROM Utilisateurs WHERE id=%s AND mot_de_passe=\"%s\";", id, motDePasse);
        Statement statement = connection.createStatement();
        statement.executeQuery(requete);
        
        // Recuperation des donnees
        ResultSet resultSet = statement.getResultSet();
        boolean retour = resultSet.next();
        
        // Liberation des ressources
        resultSet.close();
        statement.close();
        connection.close();

        return retour;
	}
	
	public static String getIdUtilisateur(String pseudo) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		// Connection a la base de donnees
        Connection connection = Database.getMySQLConnection();
        
        // Creation et execution de la requete
        String requete = String.format("SELECT id FROM Utilisateurs WHERE pseudo=\"%s\";", pseudo);
        Statement statement = connection.createStatement();
        statement.executeQuery(requete);
        
        // Recuperation des donnees
        ResultSet resultSet = statement.getResultSet();
        resultSet.next();
        String id = Integer.toString(resultSet.getInt("id"));
        
        // Liberation des ressources
        resultSet.close();
        statement.close();
        connection.close();
        
        return id;
	}
}
