package servlets.authentification;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;



@SuppressWarnings("serial")
public abstract class ServletLogin extends HttpServlet {
	 public void doGet(HttpServletRequest requete, HttpServletResponse reponse) throws ServletException, IOException {
		String pseudo = requete.getParameter("pseudo"); 
		String motDePasse = requete.getParameter("motDePasse"); 
		
		JSONObject retour = services.authentification.Login.login(pseudo, motDePasse);
		reponse.setContentType("application/json");
		reponse.getWriter().print(retour);
	 }

}
