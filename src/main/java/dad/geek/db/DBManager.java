package dad.geek.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import dad.geek.model.User;

public class DBManager {

	private Connection connPostgre;
	private PreparedStatement allPosts, userFromId, userFromNamePass, createUser, sendPost;
	private ResultSet resultPosts, resultUser;

	public DBManager() {

		try {

			Properties properties = new Properties();
			properties.load(getClass().getResourceAsStream("/properties/conexiones.properties"));
			String urlHost = properties.getProperty("SQLHost");
			String userHost = properties.getProperty("SQLUsername");
			String passwordHost = properties.getProperty("SQLPassword");

			connPostgre = DriverManager.getConnection(urlHost, userHost, passwordHost);

			// Queries relacionados con los usuaios
			createUser = connPostgre
					.prepareStatement("INSERT INTO usuarios(nombre, nombreusuario, password) VALUES (?,?,?)");
			userFromId = connPostgre
					.prepareStatement("SELECT * FROM usuarios WHERE id = ?");

			userFromNamePass = connPostgre.prepareStatement("SELECT * FROM usuarios WHERE nombre = ? AND password = ?");

			// Queries relacionados con los posts
			allPosts = connPostgre
					.prepareStatement("SELECT * FROM posts ORDER BY id DESC");
			sendPost = connPostgre
					.prepareStatement("INSERT INTO posts(id_usuario, titulo, contenido) VALUES (?,?)");

		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}

		System.out.println("userFromNamePass= " + userFromNamePass);

	}

	public void createUser(String nickname, String username, String password) {

		try {
			createUser.setString(1, nickname);
			createUser.setString(2, username);
			createUser.setString(3, password);
			createUser.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public ResultSet getUserFromDB(int id) {

		try {
			userFromId.setInt(1, id);
			resultUser = userFromId.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultUser;
	}

	public ResultSet getUserFromDB(String username, String password) {
		// FIXME userFromNamePass está instanciado como Null
		try {
			userFromNamePass.setString(1, username);
			userFromNamePass.setString(2, password);
			resultUser = userFromNamePass.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultUser;
	}

	public User sendPost(int userId) {
		try {
			ResultSet posts = getUserFromDB(userId);
			while (posts.next()) {
				return new User(
						// FIXME Cambiar el tipo de dato de la ID de int a long
						posts.getInt("ID"),
						posts.getString("nombre"),
						posts.getString("nombreUsuario"),
						posts.getString("password"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public User getUserObject(String username, String password) {

		try {
			ResultSet posts = getUserFromDB(username, password);
			while (posts.next()) {
				return new User(
						// FIXME Cambiar el tipo de dato de la ID de int a long
						posts.getInt("ID"),
						posts.getString("nombre"),
						posts.getString("nombreUsuario"),
						posts.getString("password"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

}
