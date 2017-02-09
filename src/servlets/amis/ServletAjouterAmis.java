package servlets.amis;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import java.io.IOException;

import services.amis.AjouterAmi;

public class ServletAjouterAmis extends HttpServlet {
		
	public void doGet(HttpServletRequest requete, HttpServletResponse reponse) throws ServletException, IOException {
			 
		String ami1= requete.getParameter("id_ami1"); 
		String ami2= requete.getParameter("id_ami2"); 
			
		JSONObject retour = AjouterAmi.ajouterAmi(ami1,ami2);
		reponse.setContentType("application/json");
		reponse.getWriter().print(retour);
		
	}


}
