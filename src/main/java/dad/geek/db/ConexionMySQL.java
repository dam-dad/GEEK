package dad.geek.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import dad.geek.App;
import dad.geek.model.User;

public class ConexionMySQL {

	private static Connection connMySQL;
	private Statement stmtMySQL;
	private PreparedStatement allPosts, userId, userNamePass, createUser;
	private ResultSet resultPosts, resultUser;
	
	public ConexionMySQL() {
		
		try {
			Properties prop = new Properties();
			prop.load(getClass().getResourceAsStream("/properties/conexiones_local.properties"));
//			String url = prop.getProperty("SQLHost");
//			String username = prop.getProperty("SQLUsername", "root");
//			String password = prop.getProperty("SQLPassword", "");
			String url = prop.getProperty("mysqlurl");
			String username = prop.getProperty("mysqlusername", "root");
			String password = prop.getProperty("mysqlpassword", "");
			
			connMySQL = DriverManager.getConnection(url, username, password);
			
			allPosts = connMySQL.prepareStatement("select * from posts");
			userId = connMySQL.prepareStatement("select * from usuarios where id = ?");
			userNamePass = connMySQL.prepareStatement("select * from usuarios where nombreUsuario = ? and password = ?");
			createUser = connMySQL.prepareStatement("insert into usuarios (nombre, nombreUsuario, password) values (?, ?, ?)");
			
		} catch (SQLException | IOException e) {
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
	
	public ResultSet getUserFromDB(int id) {
		try {
			userId.setInt(1, id);
			resultUser = userId.executeQuery();
		} catch (SQLException e) {
			System.err.println("error en oneuser id");
		}
		return resultUser;
	}
	
	public ResultSet getUserFromDB(String username, String password) {
		try {
			userNamePass.setString(1, username);
			userNamePass.setString(2, password);
			resultUser = userNamePass.executeQuery();
		} catch (SQLException e) {
			System.err.println("error en oneuser login " + username + ":" + password + "\n");
		}
		return resultUser;
	}
	
	public User getUserObject(int userId) {
		
		try {
			ResultSet posts = App.mysql.getUserFromDB(userId);
			while(posts.next()) {
				return new User(
					posts.getInt("ID"),
					posts.getString("nombre"),
					posts.getString("nombreUsuario"),
					posts.getString("password")
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public User getUserObject(String username, String password) {
		
		try {
			ResultSet posts = App.mysql.getUserFromDB(username, password);
			while(posts.next()) {
				return new User(
					posts.getInt("ID"),
					posts.getString("nombre"),
					posts.getString("nombreUsuario"),
					posts.getString("password")
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void createUser(String nickname, String username, String password) {
		
		try {
			createUser.setString(1, nickname);
			createUser.setString(2, username);
			createUser.setString(3, password);
			createUser.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
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
