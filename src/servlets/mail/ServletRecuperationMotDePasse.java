package servlets.mail;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

@SuppressWarnings("serial")
public class ServletRecuperationMotDePasse extends HttpServlet {
    public void doGet(HttpServletRequest requete, HttpServletResponse reponse) throws ServletException, IOException {
	    String email = requete.getParameter("email");

		JSONObject retour = services.mail.RecuperationMotDePasse.recuperationMotDePasse(email);
		
		reponse.setContentType("application/json");
        reponse.getWriter().print(retour);
	}
}
