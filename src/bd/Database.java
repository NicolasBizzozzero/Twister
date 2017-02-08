package bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Database {
	private DataSource dataSource;
	private static Database database;

	/**
	 * Creer une database
	 * @param name nom de la database
	 * @throws SQLException
	 */
	public Database(String name) throws SQLException {
		try {
			dataSource = (DataSource) new InitialContext().lookup("java:comp/env/" + name);
		} catch (NamingException e) {
			throw new SQLException(name + " is missing in JNDI ! : " + e.getMessage());
		}
	}
	
	/**
	 * creer une connexion avec la database
	 * @return la connexion qui vient d'être créée
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
	
	/**
	 * donne une connexion a la database 
	 */
	public static Connection getMySQLConnection() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		if (DBStatic.mysql_pooling == false) {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			return DriverManager.getConnection("jdbc:mysql://" + DBStatic.mysql_host + "/" + DBStatic.mysql_db, DBStatic.mysql_username, DBStatic.mysql_password);
		} else {
			if (database == null) {
				database = new Database("jdbc/db");
			}
			
			return database.getConnection();
		}
	}
}
