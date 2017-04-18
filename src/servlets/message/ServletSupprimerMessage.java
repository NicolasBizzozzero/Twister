package servlets.message;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import services.message.SupprimerMessage;



@SuppressWarnings("serial")
public class ServletSupprimerMessage extends HttpServlet {
     public void doGet(HttpServletRequest requete, HttpServletResponse reponse) throws ServletException, IOException {
	    // Recuperation des parametres
        String clef = requete.getParameter("clef");
        String id_message = requete.getParameter("id_message"); 

        // Utilisation du service approprie
        JSONObject retour = SupprimerMessage.supprimerMessage(clef, id_message);
        reponse.setContentType("application/json");
        reponse.getWriter().print(retour);
    }
}
