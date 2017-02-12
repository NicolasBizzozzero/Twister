package servlets.authentification;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import services.authentification.Logout;



public class ServletLogout extends HttpServlet {
	
	 public void doGet(HttpServletRequest requete, HttpServletResponse reponse) throws ServletException, IOException {
		 
		String clef = requete.getParameter("clef");
		
		JSONObject retour = Logout.logout(clef);
		reponse.setContentType("application/json");
		reponse.getWriter().print(retour);
	
	 }

}
