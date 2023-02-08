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

public class ConexionMySQL {

	private static Connection connMySQL;
	private Statement stmtMySQL;
	private PreparedStatement allPosts, userFromId, userFromNamePass, createUser, sendPost, setUserImage, setNickname;
	private ResultSet resultPosts, resultUser;
	
	public ConexionMySQL() {
		
		try {
			Properties prop = new Properties();
			prop.load(getClass().getResourceAsStream("/properties/conexiones_local.properties"));
			String url = prop.getProperty("mysqlurl");
			String username = prop.getProperty("mysqlusername", "root");
			String password = prop.getProperty("mysqlpassword", "");
			
			connMySQL = DriverManager.getConnection(url, username, password);
			
			allPosts = connMySQL.prepareStatement("select * from posts order by id desc");
			userFromId = connMySQL.prepareStatement("select * from usuarios where id = ?");
			userFromNamePass = connMySQL.prepareStatement("select * from usuarios where nombreUsuario = ? and password = ?");
			createUser = connMySQL.prepareStatement("insert into usuarios (nombre, nombreUsuario, password) values (?, ?, ?)");
			sendPost = connMySQL.prepareStatement("insert into posts (ID_Usuario, contenido) values (?, ?)");
			setUserImage = connMySQL.prepareStatement("update usuarios set imagen = ? where id = ?");
			setNickname = connMySQL.prepareStatement("update usuarios set nombre = ? where id = ?");
			
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
	
	public ResultSet getUserFromDB(long id) {
		try {
			userFromId.setLong(1, id);
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
			System.err.println("error en getAllPosts");
		}
		
		return result;
	}
	
	public User getUserObject(long userId) {
		
		try {
			ResultSet posts = getUserFromDB(userId);
			while(posts.next()) {
				return new User(
					posts.getInt("ID"),
					posts.getString("nombre"),
					posts.getString("nombreUsuario"),
					posts.getString("password"),
					posts.getString("imagen")
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
					posts.getString("password"),
					posts.getString("imagen")
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void sendPost(Post post) {
		
		try {
			sendPost.setLong(1, post.getUserID());
			sendPost.setString(2, post.getPostContent());
			sendPost.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
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
	
	public void setUserImage(long id, String url) {
		
		try {
			setUserImage.setString(1, url);
			setUserImage.setLong(2, id);
			setUserImage.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void setNickname(long id, String nickname) {
		
		try {
			setNickname.setString(1, nickname);
			setNickname.setLong(2, id);
			setNickname.executeUpdate();
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
