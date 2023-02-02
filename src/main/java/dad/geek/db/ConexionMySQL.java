package dad.geek.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import dad.geek.App;
import dad.geek.model.Post;
import dad.geek.model.User;

public class ConexionMySQL {

	private static Connection connMySQL;
	private Statement stmtMySQL;
	private PreparedStatement allPosts, userFromId, userFromNamePass, createUser;
	private ResultSet resultPosts, resultUser;
	
	public ConexionMySQL() {
		
		try {
			Properties prop = new Properties();
			prop.load(getClass().getResourceAsStream("/properties/conexiones_local.properties"));
			String url = prop.getProperty("mysqlurl");
			String username = prop.getProperty("mysqlusername", "root");
			String password = prop.getProperty("mysqlpassword", "");
			
			connMySQL = DriverManager.getConnection(url, username, password);
			
			allPosts = connMySQL.prepareStatement("select * from posts");
			userFromId = connMySQL.prepareStatement("select * from usuarios where id = ?");
			userFromNamePass = connMySQL.prepareStatement("select * from usuarios where nombreUsuario = ? and password = ?");
			createUser = connMySQL.prepareStatement("insert into usuarios (nombre, nombreUsuario, password) values (?, ?, ?)");
			
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet allPostsFromDB() {
		try {
			resultPosts = allPosts.executeQuery();
		} catch (SQLException e) {
			System.out.println("error en allposts");
		}
		return resultPosts;
	}
	
	public ResultSet getUserFromDB(int id) {
		try {
			userFromId.setInt(1, id);
			resultUser = userFromId.executeQuery();
		} catch (SQLException e) {
			System.err.println("error en oneuser id");
		}
		return resultUser;
	}
	
	public ResultSet getUserFromDB(String username, String password) {
		try {
			userFromNamePass.setString(1, username);
			userFromNamePass.setString(2, password);
			resultUser = userFromNamePass.executeQuery();
		} catch (SQLException e) {
			System.err.println("error en oneuser login " + username + ":" + password + "\n");
		}
		return resultUser;
	}
	
	public List<Post> getAllPosts() {
		
		List<Post> result = new ArrayList<>();
		ResultSet posts = App.mysql.allPostsFromDB();
		
		try {
			while(posts.next()) {
				
				result.add(new Post(
					posts.getInt("ID"),
					posts.getInt("ID_Usuario"),
					posts.getString("titulo"),
					posts.getString("contenido")
				));
				
			}
		} catch (SQLException e) {
			System.err.println("error en getAllPosts");
		}
		
		return result;
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
