package servlets.commentaire;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import services.commentaire.ListerCommentaires;



public class ServletListerCommentaires extends HttpServlet {
	    public void doGet(HttpServletRequest requete, HttpServletResponse reponse) throws ServletException, IOException {
	        // Recuperation des parametres
	        String id_utilisateur = requete.getParameter("id_utilisateur");
	        int limite = Integer.parseInt(requete.getParameter("limite"));
	        int index_debut = Integer.parseInt(requete.getParameter("index_debut"));

	        // Utilisation du service approprie
	        JSONObject retour = ListerCommentaires.listerCommentaires(id_utilisateur, index_debut, limite);
	        reponse.setContentType("application/json");
	        reponse.getWriter().print(retour);
	    
	    }

}


