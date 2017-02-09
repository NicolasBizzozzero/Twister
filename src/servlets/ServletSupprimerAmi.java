package servlets.amis;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import java.io.IOException;

import services.amis.SupprimerAmi;

public class ServletSupprimerAmi extends HttpServlet {
		
	public void doGet(HttpServletRequest requete, HttpServletResponse reponse) throws ServletException, IOException {
			 
		String ami1= requete.getParameter("id_ami1"); 
		String ami2= requete.getParameter("id_amis2"); 
			
		JSONObject retour = SupprimerAmi.supprimerAmi(ami1,ami2);
		reponse.setContentType("application/json");
		reponse.getWriter().print(retour);
		
	}


}




