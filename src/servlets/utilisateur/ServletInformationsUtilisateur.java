package servlets.utilisateur;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

@SuppressWarnings("serial")
public class ServletInformationsUtilisateur extends HttpServlet {
	 public void doGet(HttpServletRequest requete, HttpServletResponse reponse) throws ServletException, IOException {
		String pseudo = requete.getParameter("pseudo"); 
		String clef = requete.getParameter("clef");

		JSONObject retour = services.utilisateur.InformationsUtilisateur.informationsUtilisateur(clef, pseudo);
		
		reponse.setContentType("application/json");
        reponse.getWriter().print(retour);
	 }
}
