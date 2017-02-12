package servlets.commentaire;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import services.commentaire.AjouterCommentaire;



public class ServletAjouterCommentaire extends HttpServlet {
     public void doGet(HttpServletRequest requete, HttpServletResponse reponse) throws ServletException, IOException {
	    // Recuperation des parametres
        String cle = requete.getParameter("cle");
        String contenu = requete.getParameter("contenu");
 

        // Utilisation du service approprie
        JSONObject retour=new JSONObject();
			try {
				retour = AjouterCommentaire.ajouterCommentaire(cle, contenu);
			} catch (InstantiationException e) {				
				retour.append("Erreur", e);
			} catch (IllegalAccessException e) {
				retour.append("Erreur", e);
			} catch (ClassNotFoundException e) {
				retour.append("Erreur", e);
			} catch (SQLException e) {
				retour.append("Erreur", e);
			}
		
        reponse.setContentType("application/json");
        reponse.getWriter().print(retour);
     }

}
