package servlets.commentaire;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;


@SuppressWarnings("serial")
public class ServletSupprimerCommentaire extends HttpServlet {
    public void doGet(HttpServletRequest requete, HttpServletResponse reponse) throws ServletException, IOException {
	   // Recuperation des parametres
       String clef = requete.getParameter("clef");
       String id_message = requete.getParameter("id_message");
       String id_commentaire = requete.getParameter("id_commentaire");

       // Utilisation du service approprie
       JSONObject retour = services.commentaire.SupprimerCommentaire.supprimerCommentaire(clef, id_message, id_commentaire);
       reponse.setContentType("application/json");
       reponse.getWriter().print(retour);
   }
}
