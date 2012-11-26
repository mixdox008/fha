
package annuaire;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
@WebServlet(urlPatterns={"/RechercherServlet"})
public class RechercherServlet extends HttpServlet {
 private Connection connexion;

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
   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    PrintWriter out = response.getWriter();

    // R�cup�ration du param�tre 'nomPersonne' envoy� � la servlet
    String nom = request.getParameter("nomPersonne");

    out.println("<html>"
              + "<head><title>R�sultat de la recherche</title></head>"
              + "<body>"
              + "<h1>R�sultat de la recherche pour le nom : " + nom + "</h1>"
              + "<p>"
              + "  <a href='/MWA/VisualisationAnnuaireServlet'>Retour...</a>"
              + "</p>");

    Vector contenu = recupPersonnes(nom);

    if(contenu.size() == 0)
    {
      out.println("<p>Aucune entr�e dans l'annuaire ne correspond � ce nom");
    }
    else
    {
      out.println("<table border='1' width='50%' bgcolor='yellow'>"
                + "  <tr>"
                + "    <th>Nom</th>"
                + "    <th>Pr�nom</th>"
                + "    <th>T�l�phone</th>"
                + "  <tr>");

      for(int i=0;i<contenu.size();i++)
      {
        String[] entree = (String[])contenu.elementAt(i);
        out.println("<tr>"
                    + "  <td>" + entree[0] + "</td>"
                    + "  <td>" + entree[1] + "</td>"
                    + "  <td>" + entree[2] + "</td>"
                    + "</tr>");
      }
      out.println("</table>");
    }

    out.println("</body></html>");
  }

 public Vector recupPersonnes(String nom)
  {
    Vector contenu = new Vector();
    // Recuperation de la liste des employ�s
    try
    {
      // Cr�ation du Statement
      Statement stmt = connexion.createStatement();

      // Ex�cution de la requ�te sur la base et r�cup�ration des valeurs au sein du ResultSet
      String requete = "select * from annuaire where nom='" + nom + "'";
      ResultSet rset = stmt.executeQuery(requete);

      // Parcours du ResulSet
      while(rset.next())
      {
        String[] entree = new String[3];

        entree[0] = rset.getString("nom");
        entree[1] = rset.getString("prenom");
        entree[2] = rset.getString("telephone");

        contenu.addElement(entree);
      }

      // Fermeture su Statement
      stmt.close();
    }
    catch(SQLException sqle)
    {
      sqle.printStackTrace();
    }

    return contenu;
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
