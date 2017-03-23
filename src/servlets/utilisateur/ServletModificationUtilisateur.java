package servlets.utilisateur;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import services.utilisateur.ModificationUtilisateur;


@SuppressWarnings("serial")
public class ServletModificationUtilisateur extends HttpServlet {
     public void doGet(HttpServletRequest requete, HttpServletResponse reponse) throws ServletException, IOException {
        // Recuperation des parametres
        String clef = requete.getParameter("clef");
        String motDePasse = requete.getParameter("motDePasse");
        String nouveauPseudo = requete.getParameter("nouveauPseudo");
        String nouveauMotDePasse = requete.getParameter("nouveauMotDePasse");
        String nouvelEmail = requete.getParameter("nouvelEmail");
        String nouveauPrenom = requete.getParameter("nouveauPrenom");
        String nouveauNom = requete.getParameter("nouveauNom");
        String nouvelAnniversaire = requete.getParameter("nouvelAnniversaire");

        // Utilisation du service approprie
        JSONObject retour = ModificationUtilisateur.modificationUtilisateur(clef, motDePasse, nouveauPseudo, nouveauMotDePasse, nouvelEmail, nouveauPrenom, nouveauNom, nouvelAnniversaire);
        reponse.setContentType("application/json");
        reponse.getWriter().print(retour);
     }
}