package servlets.message;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import services.message.ListerMessages;



@SuppressWarnings("serial")
public class ServletListerMessages extends HttpServlet {
    public void doGet(HttpServletRequest requete, HttpServletResponse reponse) throws ServletException, IOException {
	    // Recuperation des parametres
       String clef = requete.getParameter("clef");
       String recherche = requete.getParameter("recherche");
       String id_utilisateur = requete.getParameter("id_utilisateur");
       String id_max = requete.getParameter("id_max");
       String id_min = requete.getParameter("id_min");
       String limite = requete.getParameter("limite");

       // Utilisation du service approprie
       JSONObject retour = ListerMessages.listerMessages(clef, recherche, id_utilisateur, id_max, id_min, limite);
       reponse.setContentType("text/html;charset=UTF-8");
       reponse.getWriter().print(retour);
   }
}
