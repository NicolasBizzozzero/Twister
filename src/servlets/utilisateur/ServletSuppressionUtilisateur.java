package servlets.utilisateur;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import services.utilisateur.SuppressionUtilisateur;


@SuppressWarnings("serial")
public class ServletSuppressionUtilisateur extends HttpServlet {
     public void doGet(HttpServletRequest requete, HttpServletResponse reponse) throws ServletException, IOException {
        // Recuperation des parametres
        String clef = requete.getParameter("clef");
        String motDePasse = requete.getParameter("motDePasse"); 

        // Utilisation du service approprie
        JSONObject retour = SuppressionUtilisateur.suppressionUtilisateur(clef, motDePasse);
        reponse.setContentType("application/json");
        reponse.getWriter().print(retour);
     }
}
