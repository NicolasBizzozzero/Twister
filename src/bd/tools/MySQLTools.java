package bd.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import bd.Database;

public class MySQLTools {
	
	
	/**
	 * Vide entierement le contenu de toutes les tables de
	 * notre BDD MySQL
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public static void nettoieToutesLesTables() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		// Connection a la base de donnees
        Connection connection = Database.getMySQLConnection();
        
        // Creation et execution de la requete
        String requete = "TRUNCATE TABLE Utilisateurs; TRUNCATE TABLE Sessions; TRUNCATE TABLE Amities;";
        PreparedStatement statement = connection.prepareStatement(requete);
        statement.executeQuery();
        
        // Liberation des ressources
        statement.close();
        connection.close();
	}
	
	
	/**
	 * Supprime entierement toutes les tables presentes
	 * dans notre BDD MySQL.
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public static void detruitToutesLesTables() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		// Connection a la base de donnees
        Connection connection = Database.getMySQLConnection();
        
        // Creation et execution de la requete
        String requete = "DROP TABLE Utilisateurs; DROP TABLE Sessions; DROP TABLE Amities;";
        PreparedStatement statement = connection.prepareStatement(requete);
        statement.executeQuery();
        
        // Liberation des ressources
        statement.close();
        connection.close();
	}
	
	
	/**
	 * Creer toutes les tables necessaires dans
	 * notre BDD MySQL.
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public static void creerToutesLesTables() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
	    bd.tools.UtilisateursTools.creerTableUtilisateurs();
	    bd.tools.SessionsTools.creerTableSessions();
	    bd.tools.AmitiesTools.creerTableAmities();
	}
}
