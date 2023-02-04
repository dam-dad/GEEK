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

import dad.geek.model.Post;
import dad.geek.model.User;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ConexionMySQL {

	private static Connection connMySQL;
	private Statement stmtMySQL;
	private PreparedStatement allPosts, userFromId, userFromNamePass, createUser, sendPost;
	private ResultSet resultPosts, resultUser;
	
	public ConexionMySQL() {
			Properties prop = new Properties();
			try {
				prop.load(getClass().getResourceAsStream("/properties/conexiones_local.properties"));
			} catch (IOException e) {
				Alert errorAlert = new Alert(AlertType.ERROR);
				errorAlert.setTitle("ERROR");
				errorAlert.setHeaderText("Hubo un error");
				errorAlert.setContentText("Hubo un error de tipo IO en el constructor de la clase.");
				errorAlert.show();
			}
			String url = prop.getProperty("mysqlurl");
			String username = prop.getProperty("mysqlusername", "root");
			String password = prop.getProperty("mysqlpassword", "");
			try {
				connMySQL = DriverManager.getConnection(url, username, password);
				allPosts = connMySQL.prepareStatement("select * from posts order by id desc");
				userFromId = connMySQL.prepareStatement("select * from usuarios where id = ?");
				userFromNamePass = connMySQL.prepareStatement("select * from usuarios where nombreUsuario = ? and password = ?");
				createUser = connMySQL.prepareStatement("insert into usuarios (nombre, nombreUsuario, password) values (?, ?, ?)");
				sendPost = connMySQL.prepareStatement("insert into posts (ID_Usuario, contenido) values (?, ?)");
			} catch (SQLException e) {
				Alert errorAlert = new Alert(AlertType.ERROR);
				errorAlert.setTitle("ERROR");
				errorAlert.setHeaderText("Hubo un error");
				errorAlert.setContentText("Hubo un error de tipo SQL en el constructor de la clase.");
				errorAlert.show();
			}
			
	}
	
	public ResultSet allPostsFromDB() {
		try {
			resultPosts = allPosts.executeQuery();
		} catch (SQLException e) {
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setTitle("ERROR");
			errorAlert.setHeaderText("Hubo un error");
			errorAlert.setContentText("Hubo un error de tipo SQL en allPostsFromDB().");
			errorAlert.show();		}
		return resultPosts;
	}
	
	public ResultSet getUserFromDB(int id) {
		try {
			userFromId.setInt(1, id);
			resultUser = userFromId.executeQuery();
		} catch (SQLException e) {
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setTitle("ERROR");
			errorAlert.setHeaderText("Hubo un error");
			errorAlert.setContentText("Hubo un error de tipo SQL en getUserFromDB(int).");
			errorAlert.show();
		}
		return resultUser;
	}
	
	public ResultSet getUserFromDB(String username, String password) {
		try {
			userFromNamePass.setString(1, username);
			userFromNamePass.setString(2, password);
			resultUser = userFromNamePass.executeQuery();
		} catch (SQLException e) {
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setTitle("ERROR");
			errorAlert.setHeaderText("Hubo un error");
			errorAlert.setContentText("Hubo un error de tipo SQL en getUserFromDB(String, String) " + username + ":" + password + ".");
			errorAlert.show();
		}
		return resultUser;
	}
	
	public List<Post> getAllPosts() {
		
		List<Post> result = new ArrayList<>();
		ResultSet posts = allPostsFromDB();
		
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
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setTitle("ERROR");
			errorAlert.setHeaderText("Hubo un error");
			errorAlert.setContentText("Hubo un error de tipo SQL en getAllPosts().");
			errorAlert.show();
		}
		
		return result;
	}
	
	public User getUserObject(int userId) {
		
		try {
			ResultSet posts = getUserFromDB(userId);
			while(posts.next()) {
				return new User(
					posts.getInt("ID"),
					posts.getString("nombre"),
					posts.getString("nombreUsuario"),
					posts.getString("password")
				);
			}
		} catch (SQLException e) {
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setTitle("ERROR");
			errorAlert.setHeaderText("Hubo un error");
			errorAlert.setContentText("Hubo un error de tipo SQL en getUserObject(int).");
			errorAlert.show();
		}
		
		return null;
	}
	
	public User getUserObject(String username, String password) {
		
		try {
			ResultSet posts = getUserFromDB(username, password);
			while(posts.next()) {
				return new User(
					posts.getInt("ID"),
					posts.getString("nombre"),
					posts.getString("nombreUsuario"),
					posts.getString("password")
				);
			}
		} catch (SQLException e) {
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setTitle("ERROR");
			errorAlert.setHeaderText("Hubo un error");
			errorAlert.setContentText("Hubo un error de tipo SQL en getUserObject(String, String).");
			errorAlert.show();
		}
		
		return null;
	}
	
	public void sendPost(Post post) {
		
		try {
			sendPost.setInt(1, post.getUserID());
			sendPost.setString(2, post.getPostContent());
			sendPost.executeUpdate();
		} catch (SQLException e) {
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setTitle("ERROR");
			errorAlert.setHeaderText("Hubo un error");
			errorAlert.setContentText("Hubo un error de tipo SQL en sendPost(Post).");
			errorAlert.show();
		}
		
	}
	
	public void createUser(String nickname, String username, String password) {
		
		try {
			createUser.setString(1, nickname);
			createUser.setString(2, username);
			createUser.setString(3, password);
			createUser.executeUpdate();
		} catch (Exception e) {
			 Alert errorAlert = new Alert(AlertType.ERROR);
			 errorAlert.setTitle("ERROR");
			 errorAlert.setHeaderText("Hubo un error");
			 errorAlert.setContentText("Hubo un error de tipo SQL en createUser(String, String, String).");
			 errorAlert.show();	
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
			
		} catch (SQLException e1) {
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setTitle("ERROR");
			errorAlert.setHeaderText("Hubo un error");
			errorAlert.setContentText("Hubo un error de tipo SQL en close().");
			errorAlert.show();
		}
	}
	
}
