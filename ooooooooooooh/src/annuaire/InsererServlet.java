package annuaire;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
@WebServlet(urlPatterns={"/InsererServlet"})
public class InsererServlet extends HttpServlet {
 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

private Connection connexion=null;

  private static final String CONTENT_TYPE = "text/html";
  //Initialize global variables
  public void init() throws ServletException {
  try{

  connexion = ConnexionMySql.getInstance();

  }
  
   catch(Exception sqle)
    {
      sqle.printStackTrace();
    }



  }

   public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    PrintWriter out = response.getWriter();

    // R�cup�ration des param�tres
    String nom       = request.getParameter("nomPersonne");
    String prenom    = request.getParameter("prenomPersonne");
    String telephone = request.getParameter("telephonePersonne");

    try
    {
      // Cr�ation du Statement
      Statement stmt = connexion.createStatement();

      // Ex�cution de la requ�te sur la base et r�cup�ration des valeurs au sein du ResultSet
      String update = "insert into annuaire values('" + nom + "','" + prenom + "','" + telephone + "')";
      stmt.executeUpdate(update);

      // Fermeture su Statement
      stmt.close();

      out.println("<html>"
                + "<head><title>Insertion</title></head>"
                + "<body>"
                + "<h1>Insertion d'une personne</h1>"
                + "<p>Une nouvelle entr�e : " + nom + " " + prenom + " " + telephone + " a �t� r�alis�e avec succ�s dans l'annuaire</p>"
                + "<p>"
                + "  <a href='/MWA/VisualisationAnnuaireServlet'>Retour...</a>"
                + "</p>");
    }
    catch(SQLException sqle)
    {
      sqle.printStackTrace();
    }
  }


  //Clean up resources
  public void destroy() {
  try
    {
      connexion.close();
    }
    catch(SQLException sqle)
    {
      sqle.printStackTrace();
    }

  }
}
