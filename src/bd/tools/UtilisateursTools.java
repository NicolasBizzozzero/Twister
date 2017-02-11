package bd.tools;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import bd.Database;

public class UtilisateursTools {
	public static boolean verificationExistencePseudo(String pseudo) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		// Connection a la base de donnees
        Connection connection = Database.getMySQLConnection();

        // Creation et execution de la requete
        String requete = "SELECT id FROM Utilisateurs WHERE pseudo=?;";
        PreparedStatement statement = connection.prepareStatement(requete);
        statement.setString(1, pseudo);
        statement.executeQuery();
        
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
        String requete = "SELECT id FROM Utilisateurs WHERE id=?;";
        PreparedStatement statement = connection.prepareStatement(requete);
        statement.setInt(1, Integer.parseInt(id));
        statement.executeQuery();
        
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
        String requete = "INSERT INTO Utilisateurs Values (null, ?, ?, ?, ?, ?, ?);";
        PreparedStatement statement = connection.prepareStatement(requete);
        statement.setString(1, pseudo);
        statement.setString(2, motDePasse);
        statement.setString(3, email);
        statement.setString(4, prenom);
        statement.setString(5, nom);
        statement.setString(6, anniversaire);
        statement.executeUpdate();

        // Liberation des ressources
        statement.close();
        connection.close();
	}
	
	public static void ajouterUtilisateur(String id, String pseudo, String motDePasse, String email, String prenom, String nom, String anniversaire) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		// Connection a la base de donnees
        Connection connection = Database.getMySQLConnection();
        
        // Creation et execution de la requete
        String requete = "INSERT INTO Utilisateurs Values (?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement statement = connection.prepareStatement(requete);
        statement.setInt(1, Integer.parseInt(id));
        statement.setString(2, pseudo);
        statement.setString(3, motDePasse);
        statement.setString(4, email);
        statement.setString(5, prenom);
        statement.setString(6, nom);
        statement.setString(7, anniversaire);
        statement.executeUpdate();

        // Liberation des ressources
        statement.close();
        connection.close();
	}
	
	public static void supprimerUtilisateurAvecId(String id) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		// Connection a la base de donnees
        Connection connection = Database.getMySQLConnection();
        
        // Creation et execution de la requete
        String requete = "DELETE FROM Utilisateurs WHERE id=?;";
        PreparedStatement statement = connection.prepareStatement(requete);
        statement.setInt(1, Integer.parseInt(id));
        statement.executeUpdate();

        // Liberation des ressources
        statement.close();
        connection.close();
	}
	
	public static void supprimerUtilisateurAvecPseudo(String pseudo) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		// Connection a la base de donnees
        Connection connection = Database.getMySQLConnection();
        
        // Creation et execution de la requete
        String requete = "DELETE FROM Utilisateurs WHERE pseudo=?;";
        PreparedStatement statement = connection.prepareStatement(requete);
        statement.setString(1, pseudo);
        statement.executeUpdate();

        // Liberation des ressources
        statement.close();
        connection.close();
	}
	
	public static boolean checkMotDePasseAvecPseudo(String pseudo, String motDePasse) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		// Connection a la base de donnees
        Connection connection = Database.getMySQLConnection();
        
        // Creation et execution de la requete
        String requete = "SELECT id FROM Utilisateurs WHERE pseudo=? AND mot_de_passe=?;";
        PreparedStatement statement = connection.prepareStatement(requete);
        statement.setString(1, pseudo);
        statement.setString(2, motDePasse);
        statement.executeQuery();
        
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
        String requete = "SELECT id FROM Utilisateurs WHERE id=? AND mot_de_passe=?;";
        PreparedStatement statement = connection.prepareStatement(requete);
        statement.setInt(1, Integer.parseInt(id));
        statement.setString(2, motDePasse);
        statement.executeQuery();
        
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
        String requete = "SELECT id FROM Utilisateurs WHERE pseudo=?;";
        PreparedStatement statement = connection.prepareStatement(requete);
        statement.setString(1, pseudo);
        statement.executeQuery();
        
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
