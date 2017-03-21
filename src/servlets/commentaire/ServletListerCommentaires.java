package servlets.commentaire;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import services.message.ListerMessages;

@SuppressWarnings("serial")
public class ServletListerCommentaires extends HttpServlet{
    public void doGet(HttpServletRequest requete, HttpServletResponse reponse) throws ServletException, IOException {
        return;
    }
}
