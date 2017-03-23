package servlets.utilisateur;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import services.utilisateur.CreationUtilisateur;


@SuppressWarnings("serial")
public class ServletCreationUtilisateur extends HttpServlet {
	 public void doGet(HttpServletRequest requete, HttpServletResponse reponse) throws ServletException, IOException {
		String pseudo = requete.getParameter("pseudo"); 
		String motDePasse = requete.getParameter("motDePasse");
		String email = requete.getParameter("email");
		String prenom = requete.getParameter("prenom"); 
		String nom = requete.getParameter("nom");
		String anniversaire = requete.getParameter("anniversaire");

		ServletContext context = getServletContext();
		File file = new File(context.getRealPath("/python_scripts/main.py"));
//		ProcessBuilder pb = new ProcessBuilder("python", file.getAbsolutePath());
//		Process p = pb.start();
//		ProcessBuilder pb2 = new ProcessBuilder("python3", file.getAbsolutePath());
//		Process p2 = pb2.start();
//		ProcessBuilder pb3 = new ProcessBuilder("/usr/bin/python", file.getAbsolutePath());
//		Process p3 = pb3.start();
//		ProcessBuilder pb4 = new ProcessBuilder("/usr/bin/python3", file.getAbsolutePath());
//		Process p4 = pb4.start();
		String line = "/usr/bin/python3 " + file.getAbsolutePath();
		
		reponse.setContentType("application/json");
		reponse.getWriter().print(file.getAbsolutePath() + "\nWWW\n");
	 }
}
