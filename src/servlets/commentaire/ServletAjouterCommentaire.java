package servlets.commentaire;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import services.commentaire.AjouterCommentaire;



public class ServletAjouterCOmmentaire extends HttpServlet {
     public void doGet(HttpServletRequest requete, HttpServletResponse reponse) throws ServletException, IOException {
        // Récuperation des paramètres
        String id_auteur = requete.getParameter("id_auteur");
        String contenu = requete.getParameter("contenu"); 

        // Utilisation du service approprié
        JSONObject retour = AjouterCommentaire.ajouterCommentaire(contenu, id_auteur);
        reponse.setContentType("application/json");
        reponse.getWriter().print(retour);
    
     }

}
