
package annuaire;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
@WebServlet(urlPatterns={"/VisualisationAnnuaireServlet"})
public class VisualisationAnnuaireServlet extends HttpServlet {
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
  public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    PrintWriter out = response.getWriter();
    out.println("<html>"
              + "<head><title>Visualisation de l'annuaire</title></head>"
              + "<body>"
              + "<h1>Visualisation de l'annuaire</h1>"
              + "<p>"
              + "  <a href='/ModuleWebAnnuaire/recherche.html'>Rechercher une personne par son nom</a><br>"
              + "  <a href='/ModuleWebAnnuaire/inserer.html'>Insérer une nouvelle personne dans l'annuaire</a>"
              + "</p>");

    out.println("<table border='1' width='50%' bgcolor='yellow'>"
              + "  <tr>"
              + "    <th>Nom</th>"
              + "    <th>Prénom</th>"
              + "    <th>Téléphone</th>"
              + "  <tr>");

    Vector contenu = recupContenu();

    for(int i=0;i<contenu.size();i++)
    {
      String[] entree = (String[])contenu.elementAt(i);
      out.println("<tr>"
                + "  <td>" + entree[0] + "</td>"
                + "  <td>" + entree[1] + "</td>"
                + "  <td>" + entree[2] + "</td>"
                + "</tr>");
    }

    out.println("</table></body></html>");
  }
  public Vector recupContenu()
  {
    Vector contenu = new Vector();
    // Recuperation de la liste des employés
    try
    {
      // Création du Statement
      Statement stmt = connexion.createStatement();

      // Exécution de la requête sur la base et récupération des valeurs au sein du ResultSet
      String requete = "SELECT * FROM annuaire";

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
