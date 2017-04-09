package servlets.message;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import services.message.AjouterMessage;


@SuppressWarnings("serial")
public class ServletAjouterMessage extends HttpServlet {
     public void doGet(HttpServletRequest requete, HttpServletResponse reponse) throws ServletException, IOException {
	    // Recuperation des parametres
        String clef = requete.getParameter("clef");
        String contenu = requete.getParameter("contenu");
 

        // Utilisation du service approprie
        JSONObject retour=new JSONObject();
		retour = AjouterMessage.ajouterMessage(clef, contenu);
		
        reponse.setContentType("text/html;charset=UTF-8");
        reponse.getWriter().print(retour);
     }
}
