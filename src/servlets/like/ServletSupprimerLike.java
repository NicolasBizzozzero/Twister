package servlets.like;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

@SuppressWarnings("serial")
public class ServletSupprimerLike extends HttpServlet{
	public void doGet(HttpServletRequest requete, HttpServletResponse reponse) throws ServletException, IOException {
		// Recuperation des parametres
		String clef = requete.getParameter("clef");
		String id_message = requete.getParameter("id_message");
		
		// Utilisation du service approprie
		JSONObject retour = new JSONObject();
		retour = services.like.SupprimerLike.supprimerLike(clef, id_message);
		   
		// Ecriture de la reponse
		reponse.setContentType("application/json");
		reponse.getWriter().print(retour);
	}
}
