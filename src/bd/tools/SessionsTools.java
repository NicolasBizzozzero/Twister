package bd.tools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bd.Database;
import exceptions.BDException;
import outils.MesMethodes;

public class SessionsTools {

	public static boolean estConnecte(String id_utilisateur) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
    	// Connection à la base de données
        Connection connection = Database.getMySQLConnection();
        
        // Création et execution de la requête
        String requete = String.format("SELECT * FROM Sessions WHERE id=\"%s\";", id_utilisateur);
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

	public static String insertSession(String identifiant ,boolean isAdmin) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
	       System.out.println("0");
		// On génère une clef puis on vérifie si elle n'existe pas déjà
		// Tant qu'on n'obtient pas de clef unique, on recommence
		String cle;
		do {
			cle = MesMethodes.getStringAleatoire(32);
		} while(SessionsTools.clefExiste(cle));
		
		// On créé une session dans la BDD avec cette clé
    	// Connection à la base de données
        Connection connection = Database.getMySQLConnection();

        // Création et execution de la requête
        String requete = String.format("INSERT INTO Sessions Values (\"%s\", %d, null, %b);", cle, Integer.parseInt(identifiant), isAdmin);
        Statement statement = connection.createStatement();
        statement.executeUpdate(requete);
        
        // Libération des ressources
        statement.close();
        connection.close();
		
		// Notre clef est unique, on peut la retourner
		return cle;
	}

	static boolean clefExiste(String cle) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
	    	// Connection à la base de données
	        Connection connection = Database.getMySQLConnection();
	        
	        // Création et execution de la requête
	        String requete = String.format("SELECT clef FROM Sessions WHERE clef=\"%s\";", cle);
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

	public static void updateTempsCle(String cle) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		// Connection à la base de données
        Connection connection = Database.getMySQLConnection();
        
        // Création et execution de la requête
        String requete = String.format("UPDATE Session SET timestamp=NOW() WHERE clef=\"%s\";", cle);
        Statement statement = connection.createStatement();
        statement.executeUpdate(requete);
            
        // Libération des ressources
        statement.close();
        connection.close();
	}

	public static boolean suppressionCle(String clef) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
    	// Connection à la base de données
        Connection connection = Database.getMySQLConnection();
        
        // Création et execution de la requête
        String requete = String.format("DELETE FROM Session WHERE clef=\"%s\";", clef);
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
	}
}
