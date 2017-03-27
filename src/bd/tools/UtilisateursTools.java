package bd.tools;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import exceptions.AnniversaireInvalideException;
import bd.Database;

public class UtilisateursTools {
	
	/**
	 * Ajoute un utilisateur dans la table 'Utilisateurs' de la BDD MySQL
	 * @param pseudo : Le pseudo de l'utilisateur a ajouter
	 * @param motDePasse : Le mot de passe de l'utilisateur a ajouter
	 * @param email : L'email de l'utilisateur a ajouter
	 * @param prenom : Le prenom de l'utilisateur a ajouter
	 * @param nom : Le nom de l'utilisateur a ajouter
	 * @param anniversaire : La date d'anniversaire de l'utilisateur a ajouter
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
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
	
	
	/**
	 * Ajoute un utilisateur dans la table 'Utilisateurs' de la BDD MySQL
	 * Cette methode utilise l'ID comme parametre supplementaire pour ajouter un utilisateur.
	 * Elle ne devrait etre utilisee UNIQUEMENT que par les administrateurs, et donc ne pas
	 * etre disponible en tant que service.
	 * @param id : L'ID de l'utilisateur a ajouter
	 * @param pseudo : Le pseudo de l'utilisateur a ajouter
	 * @param motDePasse : Le mot de passe de l'utilisateur a ajouter
	 * @param email : L'email de l'utilisateur a ajouter
	 * @param prenom : Le prenom de l'utilisateur a ajouter
	 * @param nom : Le nom de l'utilisateur a ajouter
	 * @param anniversaire : La date d'anniversaire de l'utilisateur a ajouter
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
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
	
	
	/**
	 * Retourne un boolean correspondant a l'existence de l'email dans
	 * la table 'Utilisateurs' de la BDD MySQL.
	 * @param email : L'email dont on veut verifier l'existence
	 * @return Un boolean a true si l'email est dans la BDD, false sinon
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static boolean checkExistenceEmail(String email) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		// Connection a la base de donnees
        Connection connection = Database.getMySQLConnection();

        // Creation et execution de la requete
        String requete = "SELECT id FROM Utilisateurs WHERE mail=?;";
        PreparedStatement statement = connection.prepareStatement(requete);
        statement.setString(1, email);
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
	
	
	/**
	 * Retourne un boolean correspondant a l'existence de l'ID dans
	 * la table 'Utilisateurs' de la BDD MySQL.
	 * @param id : L'ID dont on veut verifier l'existence
	 * @return Un boolean a true si l'ID est dans la BDD, false sinon
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static boolean checkExistenceId(String id) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException  {
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
	

	/**
	 * Retourne un boolean correspondant a l'existence du pseudo dans
	 * la table 'Utilisateurs' de la BDD MySQL.
	 * @param pseudo : Le pseudo dont on veut verifier l'existence
	 * @return Un boolean a true si le pseudo est dans la BDD, false sinon
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static boolean checkExistencePseudo(String pseudo) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
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
	

	/**
	 * Verifie la correspondance ID/mot-de-passe d'un utilisateur dans
	 * la table 'Utilisateurs' de la BDD MySQL.
	 * @param id : L'ID a verifier
	 * @param motDePasse : Le mot de passe a verifier
	 * @return Un boolean a true si l'ID et le mot de passe correspondent, false sinon
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
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
	
	
	/**
	 * Verifie la correspondance pseudo/mot-de-passe d'un utilisateur dans
	 * la table 'Utilisateurs' de la BDD MySQL.
	 * @param pseudo : Le pseudo a verifier
	 * @param motDePasse : Le mot de passe a verifier
	 * @return Un boolean a true si le pseudo et le mot de passe correspondent, false sinon
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
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
	
	
	/**
	 * Retire un utilisateur de la table 'Utilisateurs' de la BDD MySQL
	 * selon son ID. 
	 * @param id : L'ID de l'utilisateur qu'on veut retirer
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
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
	
	
	/**
	 * Retire un utilisateur de la table 'Utilisateurs' de la BDD MySQL
	 * selon son pseudo. 
	 * @param pseudo : Le pseudo de l'utilisateur qu'on veut retirer
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
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
	

	public static String getIDByPseudo(String pseudo) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
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
	
	public static void modifierPseudo(String id, String nouveauPseudo) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		// Connection a la base de donnees
        Connection connection = Database.getMySQLConnection();
        
        // Creation et execution de la requete
        String requete = "UPDATE Utilisateurs SET pseudo=? WHERE id=?;";
        PreparedStatement statement = connection.prepareStatement(requete);
        statement.setString(1, nouveauPseudo);
        statement.setInt(2, Integer.parseInt(id));
        statement.executeUpdate();
        
        // Liberation des ressources
        statement.close();
        connection.close();
	}

	public static void modifierMotDePasse(String id, String nouveauMotDePasse) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		// Connection a la base de donnees
        Connection connection = Database.getMySQLConnection();
        
        // Creation et execution de la requete
        String requete = "UPDATE Utilisateurs SET mot_de_passe=? WHERE id=?;";
        PreparedStatement statement = connection.prepareStatement(requete);
        statement.setString(1, nouveauMotDePasse);
        statement.setInt(2, Integer.parseInt(id));
        statement.executeUpdate();
        
        // Liberation des ressources
        statement.close();
        connection.close();		
	}

	public static void modifierEmail(String id, String nouvelEmail) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		// Connection a la base de donnees
        Connection connection = Database.getMySQLConnection();
        
        // Creation et execution de la requete
        String requete = "UPDATE Utilisateurs SET mail=? WHERE id=?;";
        PreparedStatement statement = connection.prepareStatement(requete);
        statement.setString(1, nouvelEmail);
        statement.setInt(2, Integer.parseInt(id));
        statement.executeUpdate();
        
        // Liberation des ressources
        statement.close();
        connection.close();	
	}

	public static void modifierPrenom(String id, String nouveauPrenom) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		// Connection a la base de donnees
        Connection connection = Database.getMySQLConnection();
        
        // Creation et execution de la requete
        String requete = "UPDATE Utilisateurs SET prenom=? WHERE id=?;";
        PreparedStatement statement = connection.prepareStatement(requete);
        statement.setString(1, nouveauPrenom);
        statement.setInt(2, Integer.parseInt(id));
        statement.executeUpdate();
        
        // Liberation des ressources
        statement.close();
        connection.close();	
	}

	public static void modifierNom(String id, String nouveauNom) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		// Connection a la base de donnees
        Connection connection = Database.getMySQLConnection();
        
        // Creation et execution de la requete
        String requete = "UPDATE Utilisateurs SET nom=? WHERE id=?;";
        PreparedStatement statement = connection.prepareStatement(requete);
        statement.setString(1, nouveauNom);
        statement.setInt(2, Integer.parseInt(id));
        statement.executeUpdate();
        
        // Liberation des ressources
        statement.close();
        connection.close();
	}

	public static void modifierAnniversaire(String id, String nouvelAnniversaire) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, AnniversaireInvalideException {
		throw new AnniversaireInvalideException(String.format("L'anniversaire \"%s\" est invalide.", nouvelAnniversaire));
		/*
		// Connection a la base de donnees
        Connection connection = Database.getMySQLConnection();
        
        // Creation et execution de la requete
        String requete = "UPDATE Utilisateurs SET anniversaire=? WHERE id=?;";
        PreparedStatement statement = connection.prepareStatement(requete);
        statement.setString(1, nouvelAnniversaire);
        statement.setInt(2, Integer.parseInt(id));
        statement.executeUpdate();
        
        // Liberation des ressources
        statement.close();
        connection.close();
        */
	}


	public static String getPseudoUtilisateur(String id) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		// Connection a la base de donnees
        Connection connection = Database.getMySQLConnection();
        
        // Creation et execution de la requete
        String requete = "SELECT pseudo FROM Utilisateurs WHERE id=?;";
        PreparedStatement statement = connection.prepareStatement(requete);
        statement.setString(1, id);
        statement.executeQuery();
        
        // Recuperation des donnees
        ResultSet resultSet = statement.getResultSet();
        resultSet.next();
        String pseudo = resultSet.getString("pseudo");
        
        // Liberation des ressources
        resultSet.close();
        statement.close();
        connection.close();
        
        return pseudo;
	}
	
	
	/**
	 * Execute une commande DSQL pour remettre a la valeur 1
	 * l'autoincrement de l'ID de la table 'Utilisateurs'.
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static void resetAutoIncrementID() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		// Connection a la base de donnees
        Connection connection = Database.getMySQLConnection();
        
        // Creation et execution de la requete
        String requete = "ALTER TABLE Utilisateurs AUTO_INCREMENT = 1;";
        PreparedStatement statement = connection.prepareStatement(requete);
        statement.executeQuery();
        
        // Liberation des ressources
        statement.close();
        connection.close();
	}
	
	
	/**
	 * Vide entierement le contenu de la table 'Utilisateurs' dans
	 * notre BDD MySQL.
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public static void nettoieTableUtilisateurs() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		// Connection a la base de donnees
        Connection connection = Database.getMySQLConnection();
        
        // Creation et execution de la requete
        String requete = "TRUNCATE TABLE Utilisateurs;";
        PreparedStatement statement = connection.prepareStatement(requete);
        statement.executeQuery();
        
        // Liberation des ressources
        statement.close();
        connection.close();
	}
	
	
	/**
	 * Creer la table 'Utilisateurs' dans notre
	 * BDD MySQL.
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public static void creerTableUtilisateurs() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		// Connection a la base de donnees
        Connection connection = Database.getMySQLConnection();
        
        // Creation et execution de la requete
        String requete = String.format("%s%s%s%s%s%s%s%s%s",
        		"CREATE TABLE Utilisateurs(",
        			"id Integer PRIMARY KEY AUTO_INCREMENT,",
        			"pseudo Varchar(32) UNIQUE,",
        			"mot_de_passe Varchar(64),",
        			"mail Varchar(64) UNIQUE,",
        			"prenom Varchar(64),",
        			"nom Varchar(64),",
        			"anniversaire Date",
        		");");

        PreparedStatement statement = connection.prepareStatement(requete);
        statement.executeQuery();
        
        // Liberation des ressources
        statement.close();
        connection.close();
	}


	/**
	 * Retourne l'ID de l'utilisateur dont l'email est passe en
	 * parametre.
	 * @param email : L'email de l'utilisateur qu'on recherche
	 * @return L'ID de l'utilisateur trouve
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static String getIDByEmail(String email) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		// Connection a la base de donnees
        Connection connection = Database.getMySQLConnection();
        
        // Creation et execution de la requete
        String requete = "SELECT id FROM Utilisateurs WHERE mail=?;";
        PreparedStatement statement = connection.prepareStatement(requete);
        statement.setString(1, email);
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
