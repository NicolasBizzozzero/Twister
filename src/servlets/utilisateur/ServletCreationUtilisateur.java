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
		String login= requete.getParameter("login"); 
		String motDePasse= requete.getParameter("motDePasse"); 
		String nom= requete.getParameter("nom"); 
		String prenom= requete.getParameter("prenom"); 
		String mail= requete.getParameter("mail"); 

		JSONObject retour = CreationUtilisateur.creationUtilisateur(login, mail, motDePasse, nom, prenom);
		reponse.setContentType("application/json");
		reponse.getWriter().print(retour);
	 }
}
// li328.lip6.fr:8280/gr2_Bourmaud_Bizzozzero/utilisateur/creation?login=SuperAlexia&motDePasse=9856&nom=Bourmaud&prenom=Alexia&mail=truc@hotmail.fr
