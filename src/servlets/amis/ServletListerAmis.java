package servlets.amis;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import java.io.IOException;

import services.amis.ListerAmis;

public class ServletListerAmis extends HttpServlet {
		
	public void doGet(HttpServletRequest requete, HttpServletResponse reponse) throws ServletException, IOException {
			 
		String utilisateur= requete.getParameter("id_utilisateur"); 
		String index_debut= requete.getParameter("index_debut");
		String nombre_demandes= requete.getParameter("nombre_demandes");
		
			
		JSONObject retour = ListerAmis.listerAmis(utilisateur, index_debut, nombre_demandes);
		reponse.setContentType("application/json");
		reponse.getWriter().print(retour);
		
	}

}
