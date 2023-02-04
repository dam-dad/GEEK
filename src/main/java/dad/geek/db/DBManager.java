package dad.geek.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DBManager {

	private Connection connPostgre;
	private PreparedStatement allPosts, userFromId, userFromNamePass, createUser, sendPost;
	private ResultSet resultPosts, resultUser;

	public DBManager() {

		try {

			Properties properties = new Properties();
			properties.loadFromXML(getClass().getResourceAsStream("/properties/conexiones.properties"));
			String urlHost = properties.getProperty("SQLHost");
			String userHost = properties.getProperty("SQLUsername");
			String passwordHost = properties.getProperty("SQLPassword");

			connPostgre = DriverManager.getConnection(urlHost, userHost, passwordHost);

			// Queries relacionados con los usuaios
			createUser = connPostgre
					.prepareStatement("INSERT INTO usuarios(nombre, nombreusuario, password) VALUES (?,?,?)");
			userFromId = connPostgre
					.prepareStatement("SELECT * FROM usuarios WHERE id = ?");
			userFromNamePass = connPostgre
					.prepareStatement("SELECT * FROM usuarios WHERE nombreusuario = ? AND password = ?");

			// Queries relacionados con los posts
			allPosts = connPostgre
					.prepareStatement("SELECT * FROM posts ORDER BY id DESC");
			sendPost = connPostgre
					.prepareStatement("INSERT INTO posts(id_usuario, titulo, contenido) VALUES (?,?)");

		} catch (IOException | SQLException e) {
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setTitle("ERROR");
			errorAlert.setHeaderText("Hubo un error");
			errorAlert.setContentText("Hubo un error de tipo SQL o de tipo IO en el constructor de la clase.");
			errorAlert.show();		}

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

	// public void sendPost() {

	// }

}
