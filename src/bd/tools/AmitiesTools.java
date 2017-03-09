package bd.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import bd.Database;
import exceptions.IndexInvalideException;

public class AmitiesTools {
	public static boolean suitDeja(String id_ami1, String id_ami2) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		// Connection a la base de donnees
        Connection connection = Database.getMySQLConnection();
        
        // Creation et execution de la requete
        String requete = "SELECT * FROM Amities WHERE (id_ami1=? AND id_ami2=?);";
        PreparedStatement statement = connection.prepareStatement(requete);
        statement.setInt(1, Integer.parseInt(id_ami1));
        statement.setInt(2, Integer.parseInt(id_ami2));
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
	public static void ajouterAmi(String id_ami1, String id_ami2) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		// Connection a la base de donnees
        Connection connection = Database.getMySQLConnection();
        
        // Creation et execution de la requete
        String requete = "INSERT INTO Amities Values (?, ?, null);";
        PreparedStatement statement = connection.prepareStatement(requete);
        statement.setInt(1, Integer.parseInt(id_ami1));
        statement.setInt(2, Integer.parseInt(id_ami2));
        statement.executeUpdate();
        
        // Liberation des ressources
        statement.close();
        connection.close();
	}
	
	public static void supprimerAmi(String id_ami1,String id_ami2) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		// Connection a la base de donnees
        Connection connection = Database.getMySQLConnection();
        
        // Creation et execution de la requete
        String requete = "DELETE FROM Amities WHERE id_ami1=? AND id_ami2=?;";
        PreparedStatement statement = connection.prepareStatement(requete);
        statement.setInt(1, Integer.parseInt(id_ami1));
        statement.setInt(2, Integer.parseInt(id_ami2));
        statement.executeUpdate();
        
        // Liberation des ressources
        statement.close();
        connection.close();
	}

	/**
	 * Retourne nombre_demandes d'amis de l'utilisateur d'ID id_utilisateur en partant de index_debut.
	 * @param id_utilisateur
	 * @param index_debut : Sa valeur initiale est zero. Indique a partir de quel index on souhaite lister les amis.
	 * @param nombre_demandes : Indique le nombre d'amis desires.
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IndexInvalideException
	 */
	public static JSONObject listerAmis(String id_utilisateur, int index_debut, int nombre_demandes) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IndexInvalideException {
    	if (index_debut < 0) {
    		throw new IndexInvalideException("L'index ne peut pas etre negatif.");
    	}
		
		// Connection a la base de donnees
        Connection connection = Database.getMySQLConnection();
        
        // Creation et execution de la requete
        String requete = "SELECT id_ami2 FROM Amities WHERE id_ami1=? LIMIT ? OFFSET ?;";
        PreparedStatement statement = connection.prepareStatement(requete);
        statement.setInt(1, Integer.parseInt(id_utilisateur));
        statement.setInt(2, nombre_demandes);
        statement.setInt(3, index_debut);
        ResultSet res = statement.executeQuery();
        
        // creation d'un JSONObject dans lequel on met les amis
        JSONObject liste = new JSONObject();
        liste.put("Amis", new JSONArray());	// Liste vide au cas ou l'utilisateur n'a pas d'ami
        while (res.next()){
        	liste.accumulate("Amis", res.getString("id_ami2"));
        }
        
        // Liberation des ressources
        statement.close();
        connection.close();

        return liste;
	}

	
	/**
	 * Retourne tous les id des utilisateurs suivis par id_utilisateur
	 * @param id_utilisateur
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IndexInvalideException
	 */
	public static JSONObject listerTousLesAmis(String id_utilisateur) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		// Connection a la base de donnees
        Connection connection = Database.getMySQLConnection();
        
        // Creation et execution de la requete
        String requete = "SELECT id_ami2 FROM Amities WHERE id_ami1=?;";
        PreparedStatement statement = connection.prepareStatement(requete);
        statement.setInt(1, Integer.parseInt(id_utilisateur));
        ResultSet res = statement.executeQuery();
        
        // creation d'un JSONObject dans lequel on met les amis
        JSONObject liste = new JSONObject();
        liste.put("Amis", new JSONArray());	// Liste vide au cas ou l'utilisateur n'a pas d'ami
        while (res.next()){
        	liste.accumulate("Amis", res.getString("id_ami2"));
        }
        
        // Liberation des ressources
        statement.close();
        connection.close();
        
        return liste;
	}		
}
