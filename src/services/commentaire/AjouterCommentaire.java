package services.commentaire;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Date;

import org.json.JSONObject;

import services.CodesErreur;
import services.ErrorJSON;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

import bd.tools.UtilisateursTools;
import exceptions.BDException;


public class AjouterCommentaire {
	public static JSONObject ajouterCommentaire(String contenu, String id_auteur, String pseudo) {
		if (! verificationParametres(contenu, id_auteur, pseudo)){
			return ErrorJSON.serviceRefused("L'un des parametres est null", CodesErreur.ERREUR_ARGUMENTS);
		}
		
		try {
			// On vérifie que l'utilisateur existe
			boolean isUser = UtilisateursTools.verificationExistenceId(id_auteur);
			if (! isUser) {
				return ErrorJSON.serviceRefused(String.format("L'utilisateur %s n'existe pas", id_auteur), CodesErreur.ERREUR_UTILISATEUR_INEXISTANT);
			}
			
			// Connexion à la base de données
			Mongo mongo = null;
			mongo = new Mongo("li328.lip6.fr", 27130);
			DB db = mongo.getDB("gr2-2017-bourmaud-bizzozzero");
			DBCollection collection = db.getCollection("Commentaires");
			
			// Création du commentaire
			BasicDBObject commentaire = new BasicDBObject();
			commentaire.put("id_auteur", id_auteur);
			commentaire.put("contenu", contenu);
			commentaire.put("pseudo", pseudo);
			commentaire.put("date", new Date());
			
			// On ajoute le commentaire
			collection.insert(commentaire);
	
			// On renvoie une réponse
			JSONObject reponse = new JSONObject();
			return reponse;
		} catch (UnknownHostException e) {
			return ErrorJSON.serviceRefused("Hote inconnu", CodesErreur.HOTE_INCONNU);
		} catch (InstantiationException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donn�es MySQL (InstantiationException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
		} catch (IllegalAccessException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donn�es MySQL (IllegalAccessException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
		} catch (ClassNotFoundException e) {
			return ErrorJSON.serviceRefused("Erreur lors de la connexion a la base de donn�es MySQL (ClassNotFoundException)", CodesErreur.ERREUR_CONNEXION_BD_MYSQL);
		} catch (SQLException e) {
			return ErrorJSON.serviceRefused("Erreur, requ�te SQL Incorrecte", CodesErreur.ERREUR_SQL);
		} 
	}
	
	   /**
	    * Vérification de la validité des paramètres.
	    * @return : Un boolean à true si les paramètres sont valides.
	    */
		private static boolean verificationParametres(String contenu, String id_auteur, String pseudo) {
			return (contenu != null && id_auteur != null && pseudo != null);
		}
}
