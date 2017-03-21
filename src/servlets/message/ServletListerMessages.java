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
	        String cle = requete.getParameter("cle");
	        int limite = Integer.parseInt(requete.getParameter("limite"));
	        int index_debut = Integer.parseInt(requete.getParameter("index_debut"));

	        // Utilisation du service approprie
	        JSONObject retour = ListerMessages.listerMessages(cle, index_debut, limite);
	        reponse.setContentType("application/json");
	        reponse.getWriter().print(retour);
	    }

}


