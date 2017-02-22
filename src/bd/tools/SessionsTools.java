package bd.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bd.Database;
import exceptions.ClefInexistanteException;
import outils.MesMethodes;

public class SessionsTools {

	public static boolean estConnecte(String id) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		// Connection a la base de donnees
        Connection connection = Database.getMySQLConnection();
        
        // Creation et execution de la requete
        String requete = "SELECT * FROM Sessions WHERE id=?;";
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

	public static String insertSession(String identifiant, boolean isAdmin) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		// On genere une clef puis on verifie si elle n'existe pas deja
		// Tant qu'on n'obtient pas de clef unique, on recommence
		String cle;
		do {
			cle = MesMethodes.getStringAleatoire(32);
		} while(SessionsTools.clefExiste(cle));
		
		// On cree une session dans la BDD avec cette clef
		// Connection a la base de donnees
        Connection connection = Database.getMySQLConnection();

        // Creation et execution de la requete
        String requete = "INSERT INTO Sessions Values (?, ?, null, ?);";
        PreparedStatement statement = connection.prepareStatement(requete);
        statement.setString(1, cle);
        statement.setInt(2, Integer.parseInt(identifiant));
        statement.setBoolean(3, isAdmin);
        statement.executeUpdate();

        // Liberation des ressources
        statement.close();
        connection.close();
		
		// Notre clef est unique, on peut la retourner
		return cle;
	}

	public static boolean clefExiste(String cle) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
			// Connection a la base de donnees
	        Connection connection = Database.getMySQLConnection();
	        
	        // Creation et execution de la requete
	        String requete = "SELECT clef FROM Sessions WHERE clef=?;";
	        PreparedStatement statement = connection.prepareStatement(requete);
	        statement.setString(1, cle);
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

	
	public static String clefIdentifiant(String cle) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		// Connection a la base de donnees
        Connection connection = Database.getMySQLConnection();
        
        // Creation et execution de la requete
        String requete = "SELECT id FROM Sessions WHERE clef=?;";
        PreparedStatement statement = connection.prepareStatement(requete);
        statement.setString(1, cle);
        statement.executeQuery();
        
        // Recuperation des donnees
        ResultSet resultSet = statement.getResultSet();
        String identifiant="";
        if (resultSet.next()){
        	identifiant = resultSet.getString("identifiant");
        }
        
        // Liberation des ressources
        resultSet.close();
        statement.close();
        connection.close();
        
        return identifiant;
}

	public static void updateTempsCle(String cle) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		// Connection a la base de donnees
        Connection connection = Database.getMySQLConnection();
        
        // Creation et execution de la requete
        String requete = "UPDATE Session SET timestamp=NOW() WHERE clef=?;";
        PreparedStatement statement = connection.prepareStatement(requete);
        statement.setString(1, cle);
        statement.executeUpdate();
            
        // Liberation des ressources
        statement.close();
        connection.close();
	}

	public static boolean suppressionCle(String clef) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		// Connection a la base de donnees
        Connection connection = Database.getMySQLConnection();
        
        // Creation et execution de la requete
        String requete = "DELETE FROM Sessions WHERE clef=?;";
        PreparedStatement statement = connection.prepareStatement(requete);
        statement.setString(1, clef);
        int nombreDeLignesModifiees = statement.executeUpdate();
        
        // Liberation des ressources
        statement.close();
        connection.close();
        
        // Creation d'une reponse
        return (nombreDeLignesModifiees > 0);
	}
	
	public static String getIDbyClef(String clef) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, ClefInexistanteException {
		// Connection a la base de donnees
        Connection connection = Database.getMySQLConnection();
        
        // Creation et execution de la requete
        String requete = "SELECT id FROM Sessions WHERE clef=?;";
        PreparedStatement statement = connection.prepareStatement(requete);
        statement.setString(1, clef);
        statement.executeQuery();
        
        // Recuperation des donnees
        ResultSet resultSet = statement.getResultSet();
        boolean contientUnResultat = resultSet.next();
        
        // Si la requete n'a genere aucun resultat, on leve une exception
        if (! contientUnResultat)
        	throw new ClefInexistanteException(String.format("La clef %s n'est pas presente dans la Base de donnees", clef));
        
        String id = Integer.toString(resultSet.getInt("id"));
        
        // Liberation des ressources
        resultSet.close();
        statement.close();
        connection.close();
        
        return id;
	}
	
	public static String getClefbyId(String id) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, ClefInexistanteException {
		// Connection a la base de donnees
        Connection connection = Database.getMySQLConnection();
        
        // Creation et execution de la requete
        String requete = "SELECT clef FROM Sessions WHERE id=?;";
        PreparedStatement statement = connection.prepareStatement(requete);
        statement.setInt(1, Integer.parseInt(id));
        statement.executeQuery();
        
        // Recuperation des donnees
        ResultSet resultSet = statement.getResultSet();
        boolean contientUnResultat = resultSet.next();
        
        // Si la requete n'a genere aucun resultat, on leve une exception
        if (! contientUnResultat)
        	throw new ClefInexistanteException(String.format("L'indentifiant %s n'est pas presente dans la Base de donnees", id));
        
        String cle = resultSet.getString("clef");
        
        // Liberation des ressources
        resultSet.close();
        statement.close();
        connection.close();
        
        return cle;
	}
}
