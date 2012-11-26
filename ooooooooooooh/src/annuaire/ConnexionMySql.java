package annuaire;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnexionMySql {

    private static Connection connection;
    private Statement st;
    private ResultSet rs;
    private String sql;
	public ConnexionMySql(){
		seConnecter();
	}
    public static Connection getInstance() {
        if (connection == null) {
                new ConnexionMySql();
        }
        return connection;
}
    
	public boolean seConnecter(){
	
		String url="jdbc:mysql://localhost:3306/annuairebd";
		String login="";
		String mdp="";
		
		try {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
			return false;
			
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		try {
		 connection=DriverManager.getConnection(url,login,mdp);
			 
		} catch (SQLException e1) {
			
			e1.printStackTrace();
			return false;
		}
		return true;
	}
	
	}
