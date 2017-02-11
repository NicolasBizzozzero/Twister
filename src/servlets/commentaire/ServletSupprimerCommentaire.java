package servlets.commentaire;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import services.commentaire.SupprimerCommentaire;



public class ServletSupprimerCommentaire extends HttpServlet {
     public void doGet(HttpServletRequest requete, HttpServletResponse reponse) throws ServletException, IOException {
	    // Recuperation des parametres
        String id_auteur = requete.getParameter("id_auteur");
        String id_commentaire = requete.getParameter("id_commentaire"); 

        // Utilisation du service approprie
        JSONObject retour = SupprimerCommentaire.supprimerCommentaire(id_auteur, id_commentaire);
        reponse.setContentType("application/json");
        reponse.getWriter().print(retour);
    
     }

}
