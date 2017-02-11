package servlets.utilisateur;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import services.utilisateur.CreationUtilisateur;


public class ServletCreationUtilisateur extends HttpServlet {
	 public void doGet(HttpServletRequest requete, HttpServletResponse reponse) throws ServletException, IOException { 
		String pseudo = requete.getParameter("pseudo"); 
		String motDePasse = requete.getParameter("motDePasse");
		String prenom = requete.getParameter("prenom"); 
		String nom = requete.getParameter("nom");
		String email = requete.getParameter("email");
		String anniversaire = requete.getParameter("anniversaire");

		JSONObject retour = CreationUtilisateur.creationUtilisateur(pseudo, motDePasse, email, prenom, nom, anniversaire);
		reponse.setContentType("application/json");
		reponse.getWriter().print(retour);
	 }
}
