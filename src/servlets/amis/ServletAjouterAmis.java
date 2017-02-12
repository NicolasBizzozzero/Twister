package servlets.amis;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import java.io.IOException;

import services.amis.AjouterAmi;

@SuppressWarnings("serial")
public class ServletAjouterAmis extends HttpServlet {
	public void doGet(HttpServletRequest requete, HttpServletResponse reponse) throws ServletException, IOException {	 
		String clef = requete.getParameter("clef"); 
		String id_ami = requete.getParameter("id_ami"); 	
		
		JSONObject retour = AjouterAmi.ajouterAmi(clef, id_ami);
		reponse.setContentType("application/json");
		reponse.getWriter().print(retour);
		
	}
}
