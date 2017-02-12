package servlets.amis;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import java.io.IOException;

import services.amis.ListerAmis;

@SuppressWarnings("serial")
public class ServletListerAmis extends HttpServlet {
		
	public void doGet(HttpServletRequest requete, HttpServletResponse reponse) throws ServletException, IOException {
			 
		String utilisateur= requete.getParameter("id_utilisateur"); 
		int index_debut= Integer.parseInt(requete.getParameter("index_debut"));
		int nombre_demandes= Integer.parseInt(requete.getParameter("nombre_demandes"));
		
			
		JSONObject retour = ListerAmis.listerAmis(utilisateur, index_debut, nombre_demandes);
		reponse.setContentType("application/json");
		reponse.getWriter().print(retour);
		
	}

}
