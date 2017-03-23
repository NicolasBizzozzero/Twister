package servlets.commentaire;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

@SuppressWarnings("serial")
public class ServletAjouterCommentaire extends HttpServlet {
    public void doGet(HttpServletRequest requete, HttpServletResponse reponse) throws ServletException, IOException {
	   // Recuperation des parametres
       String clef = requete.getParameter("clef");
       String id_message = requete.getParameter("id_message");
       String contenu = requete.getParameter("contenu");


       // Utilisation du service approprie
       JSONObject retour=new JSONObject();
		retour = services.commentaire.AjouterCommentaire.ajouterCommentaire(clef, id_message, contenu);
		
       reponse.setContentType("application/json");
       reponse.getWriter().print(retour);
    }
}
