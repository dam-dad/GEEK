package dad.geek.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionMySQL {

	private static Connection connMySQL;
	private Statement stmtMySQL;
	private PreparedStatement allPosts, oneUser;
	private ResultSet resultPosts, resultUser;
	
	public ConexionMySQL() {
		
		try {
//			Properties prop = new Properties();
//			prop.load(getClass().getResourceAsStream("/properties/conexiones.properties"));
//			String url = prop.getProperty("SQLHost");
//			String username = prop.getProperty("SQLUsername", "");
//			String password = prop.getProperty("SQLPassword", "");
			String url = "jdbc:mysql://localhost:3306/geek";
			String username = "root";
			String password = "";
			
			connMySQL = DriverManager.getConnection(url, username, password);
			
			allPosts = connMySQL.prepareStatement("select * from posts");
			oneUser = connMySQL.prepareStatement("select * from usuarios where id = ?");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet allPosts() {
		try {
			resultPosts = allPosts.executeQuery();
		} catch (SQLException e) {
			System.out.println("error en allposts");
		}
		return resultPosts;
	}
	
	public ResultSet oneUser(int id) {
		try {
			oneUser.setInt(1, id);
			resultUser = oneUser.executeQuery();
		} catch (SQLException e) {
			System.out.println("error en allposts");
		}
		return resultUser;
	}
	
	public void close() {
		try {
			if(connMySQL != null)
				connMySQL.close();
			if(stmtMySQL != null)
				stmtMySQL.close();
			if(resultPosts != null)
				resultPosts.close();
			
		} catch (SQLException e1) {}
	}
	
}
