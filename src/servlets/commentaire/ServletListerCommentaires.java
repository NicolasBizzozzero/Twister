package servlets.commentaire;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import services.commentaire.ListerCommentaires;


@SuppressWarnings("serial")
public class ServletListerCommentaires extends HttpServlet {
    public void doGet(HttpServletRequest requete, HttpServletResponse reponse) throws ServletException, IOException {
        // Recuperation des parametres
		String clef = requete.getParameter("clef");
		String id_message = requete.getParameter("id_message");
	
		// Utilisation du service approprie
		JSONObject retour = ListerCommentaires.listerCommentaires(clef, id_message);
		reponse.setContentType("text/plain");
		reponse.getWriter().print(retour);
    }
}
