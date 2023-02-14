package dad.geek.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import dad.geek.model.Post;
import dad.geek.model.User;

public class DBManager {

	private Connection connPostgre;
	private PreparedStatement allPosts, userFromId, userFromNamePass, createUser, sendPost, setUserImage, setNickname,
			userPosts;

	public DBManager() {

		try {

			Properties properties = new Properties();
			properties.load(getClass().getResourceAsStream("/properties/conexiones.properties"));
			String urlHost = properties.getProperty("SQLHost");
			String userHost = properties.getProperty("SQLUsername");
			String passwordHost = properties.getProperty("SQLPassword");

			connPostgre = DriverManager.getConnection(urlHost, userHost, passwordHost);

			allPosts = connPostgre.prepareStatement("select * from posts order by id desc");
			userFromId = connPostgre.prepareStatement("select * from usuarios where id = ?");
			userFromNamePass = connPostgre
					.prepareStatement("select * from usuarios where nombreUsuario = ? and password = ?");
			createUser = connPostgre
					.prepareStatement("insert into usuarios (nombre, nombreUsuario, password) values (?, ?, ?)");
			sendPost = connPostgre.prepareStatement("insert into posts (ID_Usuario, contenido) values (?, ?)");
			setUserImage = connPostgre.prepareStatement("update usuarios set imagen = ? where id = ?");
			setNickname = connPostgre.prepareStatement("update usuarios set nombre = ? where id = ?");
			userPosts = connPostgre.prepareStatement("select * from posts where ID_Usuario = ? order by id desc");

		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
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

	private ResultSet getUserFromDB(long id) throws Exception {
		try {
			userFromId.setLong(1, id);
			return userFromId.executeQuery();
		} catch (SQLException e) {
			throw new Exception("Hubo un error al intentar cargar al usuario desde la base de datos (SQLException).");
		}
	}

	public ResultSet getUserFromDB(String username, String password) throws Exception {

		try {
			userFromNamePass.setString(1, username);
			userFromNamePass.setString(2, password);
			return userFromNamePass.executeQuery();
		} catch (SQLException e) {
			throw new Exception("Hubo un error al intentar cargar al usuario" + username
					+ " desde la base de datos (SQLException).");
		}
	}

	public void sendPost(Post post) throws Exception {

		try {
			sendPost.setLong(1, post.getUserID());
			sendPost.setString(2, post.getPostContent());
			sendPost.executeUpdate();
		} catch (SQLException e) {
			throw new Exception("Hubo un error al intentar enviar un post (SQLException).");
		}

	}

	public User getUserObject(String username, String password) throws Exception {

		try {
			ResultSet posts = getUserFromDB(username, password);
			while (posts.next()) {
				return new User(posts.getLong("ID"), posts.getString("nombre"), posts.getString("nombreUsuario"),
						posts.getString("password"), posts.getString("imagen"));
			}
		} catch (SQLException e) {
			throw new Exception("Hubo un error al intentar cargar al usuario (SQLException).");
		}

		return null;
	}

	public User getUserObject(long userId) throws Exception {

		try {
			ResultSet posts = getUserFromDB(userId);
			while (posts.next()) {
				return new User(posts.getInt("ID"), posts.getString("nombre"), posts.getString("nombreUsuario"),
						posts.getString("password"), posts.getString("imagen"));
			}
		} catch (SQLException e) {
			throw new Exception("Hubo un error al intentar cargar al usuario (SQLException).");
		}

		return null;
	}

	public List<Post> getAllPosts() throws Exception {

		List<Post> result = new ArrayList<>();
		ResultSet posts = allPostsFromDB();

		try {
			while (posts.next()) {

				result.add(new Post(posts.getInt("ID"), posts.getInt("ID_Usuario"), posts.getString("titulo"),
						posts.getString("contenido")));

			}
		} catch (SQLException e) {
			throw new Exception("Hubo un error al intentar cargar todos los posts (SQLException).");
		}

		return result;
	}

	private ResultSet allPostsFromDB() throws Exception {
		try {
			return allPosts.executeQuery();
		} catch (SQLException e) {
			throw new Exception("Hubo un error al cargar los posts desde la base de datos (SQLException).");
		}
	}

	public List<Post> getUserPosts(User user) throws Exception {
		List<Post> result = new ArrayList<>();
		ResultSet posts = getUserPostsFromDB(user);

		try {
			while (posts.next()) {

				result.add(new Post(posts.getInt("ID"), posts.getInt("ID_Usuario"), posts.getString("titulo"),
						posts.getString("contenido")));

			}
		} catch (SQLException e) {
			throw new Exception("Hubo un error al intentar cargar los posts del usuario " + user.getUsername()
					+ " (SQLException).");
		}

		return result;
	}

	public ResultSet getUserPostsFromDB(User user) throws Exception {
		try {
			userPosts.setLong(1, user.getUserID());
			return userPosts.executeQuery();
		} catch (SQLException e) {
			throw new Exception("Hubo un error al intentar cargar los posts del usuario " + user.getUsername()
					+ " desde la base de datos (SQLException).");
		}
	}

	public void setNickname(long id, String nickname) throws Exception {

		try {
			setNickname.setString(1, nickname);
			setNickname.setLong(2, id);
			setNickname.executeUpdate();
		} catch (SQLException e) {
			throw new Exception("Hubo un error al intentar actualizar el apodo de usuario (SQLException).");
		}

	}

	public void setUserImage(long id, String url) throws Exception {

		try {
			setUserImage.setString(1, url);
			setUserImage.setLong(2, id);
			setUserImage.executeUpdate();
		} catch (SQLException e) {
			throw new Exception("Hubo un error al intentar actualizar la imagen de usuario (SQLException).");
		}

	}

	public void close() throws Exception {
		try {
			if (connPostgre != null)
				connPostgre.close();
		} catch (SQLException e1) {
			throw new Exception("Hubo un error al intentar cerrar la conexión con la base de datos (SQLException).");
		}
	}

}
