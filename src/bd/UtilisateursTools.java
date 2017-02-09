package bd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;

import outils.MesMethodes;
import exceptions.BDException;

public class UtilisateursTools {
	public static boolean verificationExistenceLogin(String login) {
		try {
	    	// Connection à la base de données
	    	System.out.println("1");
	        Connection connection = Database.getMySQLConnection();
	    	System.out.println("2");
	    	System.out.println(login);
	        String requete = String.format("SELECT id FROM Utilisateurs WHERE pseudo=\"%s\";", login);
	        System.out.println(requete);
	    	System.out.println("3");
	        // Création et execution de la requête
	        Statement statement = connection.createStatement();
	    	System.out.println("4");
	        statement.executeQuery(requete);
	    	System.out.println("5");
	        
	        // Récuperation des données
	        ResultSet resultSet = statement.getResultSet();
	    	System.out.println("6");
	        boolean retour = resultSet.next();
	    	System.out.println("7");
	        
	        // Libération des ressources
	        resultSet.close();
	    	System.out.println("8");
	        statement.close();
	    	System.out.println("9");
	        connection.close();
	    	System.out.println("10");
	        return retour;
		} catch (NullPointerException e) {
			return false;
		} catch (InstantiationException e) {
			return false;
		} catch (IllegalAccessException e) {
			return false;
		} catch (ClassNotFoundException e) {
			return false;
		} catch (SQLException e) {
			return false;
		}
	}
	
	public static boolean verificationExistenceId(String id) throws BDException {
	    try {
	    	// Connection à la base de données
	        Connection connection = Database.getMySQLConnection();
	        String requete = String.format("SELECT id FROM Utilisateurs WHERE id=%s", id);
	        
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

	public static void ajouterUtilisateur(String login, String motDePasse, String email, String nom, String prenom) throws BDException {
	    try {
	    	// Connection à la base de données
	        Connection connection = Database.getMySQLConnection();
	        String requete = String.format("INSERT INTO Utilisateurs Values (%s, %s, %s, %s, %s)", login, motDePasse, email, nom, prenom);
	        
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
	
	public static boolean checkPassword(String login, String motDePasse) throws BDException {
	    try {
	    	// Connection à la base de données
	        Connection connection = Database.getMySQLConnection();
	        String requete = String.format("SELECT id FROM Users WHERE login=%s AND motDePasse=%s", login, motDePasse);
	        
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
	
	public static String getIdUser(String login) throws BDException {
	    try {
	    	// Connection à la base de données
	        Connection connection = Database.getMySQLConnection();
	        String requete = String.format("SELECT id FROM Users WHERE login=%s", login);
	        
	        // Création et execution de la requête
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
	    } catch (Exception e) {
	        throw new BDException("Impossible de se connecter a la base de données");
	    }
	}
	
	public static String insertSession(String identifiant ,boolean isAdmin) throws BDException {
		try {
			// On génère une clef puis on vérifie si elle n'existe pas déjà
			// Tant qu'on n'obtient pas de clef unique, on recommence
			String cle;
			do {
				cle = MesMethodes.getStringAleatoire(32);
			} while(clefExiste(cle));
			
			// On créé une session dans la BDD avec cette clé
	    	// Connection à la base de données
	        Connection connection = Database.getMySQLConnection();
	        String requete = String.format("INSERT INTO Session Values (%s, %s, NOW(), %s)", cle, identifiant, isAdmin);
	        
	        // Création et execution de la requête
	        Statement statement = connection.createStatement();
	        statement.executeUpdate(requete);
	        
	        // Libération des ressources
	        statement.close();
	        connection.close();
			
			// Notre clef est unique, on peut la retourner
			return cle;
		} catch (Exception e) {
	        throw new BDException("Impossible de se connecter a la base de données");
	    }
	}
	
	private static boolean clefExiste(String cle) throws BDException {
	    try {
	    	// Connection à la base de données
	        Connection connection = Database.getMySQLConnection();
	        String requete = String.format("SELECT clef FROM Session WHERE clef=%s", cle);
	        
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
	
	public static void updateTempsCle(String cle) throws BDException {
	    try {
	    	// Connection à la base de données
	        Connection connection = Database.getMySQLConnection();
	        String requete = String.format("UPDATE Session SET timestamp=NOW() WHERE clef=%s", cle);
	        
	        // Création et execution de la requête
	        Statement statement = connection.createStatement();
	        statement.executeUpdate(requete);
	            
	        // Libération des ressources
	        statement.close();
	        connection.close();
	    } catch (Exception e) {
	        throw new BDException("Impossible de se connecter a la base de données");
	    }
	}	

	public static boolean suppressionCle(String cle) throws BDException {
	    try {
	    	// Connection à la base de données
	        Connection connection = Database.getMySQLConnection();
	        String requete = String.format("DELETE FROM Session WHERE clef=%s", cle);
	        
	        // Création et execution de la requête
	        Statement statement = connection.createStatement();
	        statement.executeUpdate(requete);
	        
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
