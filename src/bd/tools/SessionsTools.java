package bd.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import bd.Database;
import exceptions.ClefInexistanteException;
import outils.MesMethodes;
import services.Tailles;

public class SessionsTools {
	public static final int TEMPS_AVANT_DECONNEXION = 3600000; // En millisecondes, = 60 minutes

	
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
			cle = MesMethodes.getStringAleatoire(Tailles.TAILLE_CLEF);
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
        String requete = "UPDATE Sessions SET timestamp=NOW() WHERE clef=?;";
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
	
	
	public static String getIDByClef(String clef) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, ClefInexistanteException {
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
	
	
	public static String getClefById(String id) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, ClefInexistanteException {
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
        	throw new ClefInexistanteException(String.format("L'identifiant %s n'est pas present dans la Base de donnees", id));
        
        String cle = resultSet.getString("clef");
        
        // Liberation des ressources
        resultSet.close();
        statement.close();
        connection.close();
        
        return cle;
	}
	
	
	/**
	 * Retourne true si la session est restee trop longtemps sans activite.
	 * Si le booleen "est_administrateur" de la session est a true, cette
	 * fonction retourne toujours false.
	 * @param clef : Clef de la session dont on veut tester l'inactivite
	 * @return Un boolean a true si l'utilisateur est considere comme inactif, false sinon.
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ClefInexistanteException
	 * @throws ParseException
	 */
	public static boolean estInactifDepuisTropLongtemps(String clef) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, ClefInexistanteException, ParseException {
		// Si l'utilisateur est un admin, on ne le deconnecte pas
		if (estAdministrateur(clef)) {
			return false;
		}
		
		Date derniereActivite = getDateByClef(clef);
//		System.out.println("Date:");
//		System.out.println(derniereActivite);
		long tempsInactivite = MesMethodes.getTempsInactivite(derniereActivite);
//		System.out.println("Temps :");
//		System.out.println(tempsInactivite);
		
		return (tempsInactivite > TEMPS_AVANT_DECONNEXION);
	}
	
	
	private static boolean estAdministrateur(String clef) throws SQLException, ClefInexistanteException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		// Connection a la base de donnees
        Connection connection = Database.getMySQLConnection();
        
        // Creation et execution de la requete
        String requete = "SELECT est_administrateur FROM Sessions WHERE clef=?;";
        PreparedStatement statement = connection.prepareStatement(requete);
        statement.setString(1, clef);
        statement.executeQuery();
        
        // Recuperation des donnees
        ResultSet resultSet = statement.getResultSet();
        boolean contientUnResultat = resultSet.next();
        
        // Si la requete n'a genere aucun resultat, on leve une exception
        if (! contientUnResultat)
        	throw new ClefInexistanteException(String.format("La clef %s n'est pas presente dans la Base de donnees", clef));
        
        boolean resultat = resultSet.getBoolean("est_administrateur");		

        // Liberation des ressources
        resultSet.close();
        statement.close();
        connection.close();
        
        return resultat;
	}


	public static Date getDateByClef(String clef) throws SQLException, ClefInexistanteException, InstantiationException, IllegalAccessException, ClassNotFoundException, ParseException {
		// Connection a la base de donnees
        Connection connection = Database.getMySQLConnection();
        
        // Creation et execution de la requete
        String requete = "SELECT timestamp FROM Sessions WHERE clef=?;";
        PreparedStatement statement = connection.prepareStatement(requete);
        statement.setString(1, clef);
        statement.executeQuery();
        
        // Recuperation des donnees
        ResultSet resultSet = statement.getResultSet();
        boolean contientUnResultat = resultSet.next();
        
        // Si la requete n'a genere aucun resultat, on leve une exception
        if (! contientUnResultat)
        	throw new ClefInexistanteException(String.format("La clef %s n'est pas presente dans la Base de donnees", clef));
        
        String resultat = resultSet.getString("timestamp");
        java.text.SimpleDateFormat parser = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		java.util.Date date  = parser.parse(resultat);		

        // Liberation des ressources
        resultSet.close();
        statement.close();
        connection.close();
        
        return date;
	}
	
	
	/**
	 * Vide entierement le contenu de la table 'Sessions' dans
	 * notre BDD MySQL.
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public static void nettoieTableSessions() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		// Connection a la base de donnees
        Connection connection = Database.getMySQLConnection();
        
        // Creation et execution de la requete
        String requete = "TRUNCATE TABLE Sessions;";
        PreparedStatement statement = connection.prepareStatement(requete);
        statement.executeQuery();
        
        // Liberation des ressources
        statement.close();
        connection.close();
	}
	
	
	/**
	 * Creer la table 'Sessions' dans notre
	 * BDD MySQL.
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public static void creerTableSessions() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		// Connection a la base de donnees
        Connection connection = Database.getMySQLConnection();
        
        // Creation et execution de la requete
        String requete = String.format("%s%s%s%s%s%s%s",
        		"CREATE TABLE Sessions(",
        			"clef Varchar(32) UNIQUE,",
        			"id Integer UNIQUE,",
        			"timestamp Timestamp,",
        			"est_administrateur boolean,",
        			"PRIMARY KEY (id)",
        		");");

        PreparedStatement statement = connection.prepareStatement(requete);
        statement.executeQuery();
        
        // Liberation des ressources
        statement.close();
        connection.close();
	}
}
