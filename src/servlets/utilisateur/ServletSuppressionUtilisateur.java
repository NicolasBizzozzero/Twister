package servlets.utilisateur;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import services.utilisateur.SuppressionUtilisateur;



public class ServletSuppressionUtilisateur extends HttpServlet {
     public void doGet(HttpServletRequest requete, HttpServletResponse reponse) throws ServletException, IOException {
        // Recuperation des parametres
        String id = requete.getParameter("id");
        String motDePasse = requete.getParameter("motDePasse"); 

        // Utilisation du service approprie
        JSONObject retour = SuppressionUtilisateur.suppressionUtilisateur(id, motDePasse);
        reponse.setContentType("application/json");
        reponse.getWriter().print(retour);
    
     }

}
