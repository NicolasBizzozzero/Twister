package servlets.authentification;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import services.authentification.Login;



public class ServletLogin extends HttpServlet {
	
	 public void doGet(HttpServletRequest requete, HttpServletResponse reponse) throws ServletException, IOException {
		 
		String login= requete.getParameter("login"); 
		String motDePasse= requete.getParameter("motDePasse"); 
		
		JSONObject retour = Login.login(login,motDePasse);
		reponse.setContentType("application/json");
		reponse.getWriter().print(retour);
	
	 }

}
