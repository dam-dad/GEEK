package dad.geek.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import dad.geek.model.Filter;
import dad.geek.model.Post;
import dad.geek.model.User;

/**
 * Clase que se encarga de gestionar la conexión con la base de datos. 
 *
 */
public class DBManager {

	private int numberOfPosts = 0;
	private Connection connPostgre;
	private PreparedStatement allPosts, userFromId, userFromNamePass, userFromName, createUser, sendPost, setUserImage, setNickname,
			userPosts, allFilters, userFilters, postFilters, createFilter, totalNumberOfPosts;

	public DBManager() {

		try {

			Properties properties = new Properties();
			properties.load(getClass().getResourceAsStream("/properties/conexiones.properties"));
			String urlHost = properties.getProperty("SQLHost");
			String userHost = properties.getProperty("SQLUsername");
			String passwordHost = properties.getProperty("SQLPassword");

			connPostgre = DriverManager.getConnection(urlHost, userHost, passwordHost);

			allPosts = connPostgre.prepareStatement("select * from posts order by id desc limit ?");
			userFromId = connPostgre.prepareStatement("select * from usuarios where id = ?");
			userFromNamePass = connPostgre.prepareStatement("select * from usuarios where nombreUsuario = ? and password = ?");
			userFromName = connPostgre.prepareStatement("select * from usuarios where nombreUsuario = ?");
			createUser = connPostgre.prepareStatement("insert into usuarios (nombre, nombreUsuario, password) values (?, ?, ?)");
			sendPost = connPostgre.prepareStatement("insert into posts (ID_Usuario, contenido) values (?, ?)");
			setUserImage = connPostgre.prepareStatement("update usuarios set imagen = ? where id = ?");
			setNickname = connPostgre.prepareStatement("update usuarios set nombre = ? where id = ?");
			userPosts = connPostgre.prepareStatement("select * from posts where ID_Usuario = ? order by id desc");
			allFilters = connPostgre.prepareStatement("SELECT * FROM filtros ORDER BY id DESC");
			userFilters = connPostgre.prepareStatement("SELECT * FROM filtrosusuario WHERE id_usuario = ?");
			postFilters = connPostgre.prepareStatement("SELECT * FROM filtrospost WHERE id_post = ?");
			createFilter = connPostgre.prepareStatement("insert into filtros (nombre, shortname, descripcion) values (?, ?, ?)");
			totalNumberOfPosts = connPostgre.prepareStatement("select count(*) from posts");
			
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sube a la base de datos los datos del objeto {@link User} pasado por parámetro.
	 * @param nickname
	 * @param username
	 * @param password
	 */
	public void createUser(User user) {

		try {
			createUser.setString(1, user.getNickname());
			createUser.setString(2, user.getUsername());
			createUser.setString(3, user.getPassword());
			createUser.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public void createFilter(String nombre, String shortname, String descripcion) {

		try {
			createFilter.setString(1, nombre);
			createFilter.setString(2, shortname);
			createFilter.setString(3, descripcion);
			createFilter.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param id
	 * @return Un {@code ResultSet} del usuario con el id facilitados por parámetro.
	 * @throws Exception
	 */
	private ResultSet getUserFromDB(long id) throws Exception {
		try {
			userFromId.setLong(1, id);
			return userFromId.executeQuery();
		} catch (SQLException e) {
			throw new Exception("Hubo un error al intentar cargar al usuario desde la base de datos (SQLException).");
		}
	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @return Un {@code ResultSet} del usuario con el nombre de usuario y la contraseña facilitados por parámetro.
	 * @throws Exception
	 */
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
	
	/**
	 * @param username
	 * @return Un {@code ResultSet} del usuario con el nombre de usuario facilitado por parámetro.
	 * @throws Exception
	 */
	public ResultSet getUserFromDB(String username) throws Exception {
		try {
			userFromName.setString(1, username);
			return userFromName.executeQuery();
		} catch (SQLException e) {
			throw new Exception("Hubo un error al intentar cargar al usuario" + username
					+ " desde la base de datos (SQLException).");
		}
	}

	/**
	 * Sube a la base de datos los datos del objeto {@link Post} pasado por parámetro.
	 * @param post
	 * @throws Exception
	 */
	public void sendPost(Post post) throws Exception {

		try {
			sendPost.setLong(1, post.getUserID());
			sendPost.setString(2, post.getPostContent());
			sendPost.executeUpdate();
		} catch (SQLException e) {
			throw new Exception("Hubo un error al intentar enviar un post (SQLException).");
		}

	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @return Un objeto {@link User} con los datos recibidos de la función {@link #getUserFromDB(String, String)}, siendo los parámetros por orden el nombre de usuario y la contraseña.
	 * @throws Exception
	 */
	public User getUserObject(String username, String password) throws Exception {

		try {
			ResultSet posts = getUserFromDB(username, password);
			while (posts.next()) {
				return new User(
					posts.getLong("ID"),
					posts.getString("nombre"), 
					posts.getString("nombreUsuario"),
					posts.getString("password"), 
					posts.getString("imagen")
				);
			}
		} catch (SQLException e) {
			throw new Exception("Hubo un error al intentar cargar al usuario (SQLException).");
		}

		return null;
	}
	
	/**
	 * 
	 * @param username
	 * @return Un objeto {@link User} con los datos recibidos de la función {@link #getUserFromDB(String)}, siendo {@code String} el nombre de usuario especificado por parámetro.
	 * @throws Exception
	 */
	public User getUserObject(String username) throws Exception {

		try {
			ResultSet posts = getUserFromDB(username);
			while (posts.next()) {
				return new User(
					posts.getLong("ID"), 
					posts.getString("nombre"), 
					posts.getString("nombreUsuario"),
					posts.getString("password"), 
					posts.getString("imagen")
				);
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
				return new User(
					posts.getLong("ID"),
					posts.getString("nombre"), 
					posts.getString("nombreUsuario"),
					posts.getString("password"), 
					posts.getString("imagen")
				);
			}
		} catch (SQLException e) {
			throw new Exception("Hubo un error al intentar cargar al usuario (SQLException).");
		}

		return null;
	}

	/**
	 * @param reload
	 * @return Una {@code List<}{@link Post}{@code >} con los posts recibidos de la función {@link #allPostsFromDB}.
	 * @throws Exception
	 */
	public List<Post> getPosts(boolean reload) throws Exception {

		List<Post> result = new ArrayList<>();
		ResultSet posts = allPostsFromDB(reload);
		try {
			while (posts.next()) {
				result.add(new Post(
					posts.getLong("ID"), 
					posts.getLong("ID_Usuario"), 
					posts.getString("titulo"),
					posts.getString("contenido")
				));
			}
		} catch (SQLException e) {
			throw new Exception("Hubo un error al intentar cargar todos los posts (SQLException).");
		}

		return result;
	}

	/**
	 * @param reload
	 * @return {@code ResultSet} con posts (si reload es true 10, si reload es false "numberOfPosts + 10").
	 * @throws Exception
	 */
	private ResultSet allPostsFromDB(boolean reload) throws Exception {
		try {
			numberOfPosts = reload ? 10 : numberOfPosts + 10;
			allPosts.setInt(1, numberOfPosts);
			return allPosts.executeQuery();
		} catch (SQLException e) {
			throw new Exception("Hubo un error al cargar los posts desde la base de datos (SQLException).");
		}
	}

	/**
	 * @param user
	 * @return Una {@code List<}{@link Post}{@code >} con los posts recibidos de la función {@link #getUserPostsFromDB(User)}, siendo User el usuario facilitado por parámetro.
	 * @throws Exception
	 */
	public List<Post> getUserPosts(User user) throws Exception {
		List<Post> result = new ArrayList<>();
		ResultSet posts = getUserPostsFromDB(user);

		try {
			while (posts.next()) {
				result.add(new Post(
					posts.getLong("ID"),
					posts.getLong("ID_Usuario"),
					posts.getString("titulo"),
					posts.getString("contenido"))
				);
			}
		} catch (SQLException e) {
			throw new Exception("Hubo un error al intentar cargar los posts del usuario " + user.getUsername()
					+ " (SQLException).");
		}

		return result;
	}

	/**
	 * @param user
	 * @return {@code ResultSet} con todos los posts del usuario indicado por parámetro.
	 * @throws Exception
	 */
	public ResultSet getUserPostsFromDB(User user) throws Exception {
		try {
			userPosts.setLong(1, user.getUserID());
			return userPosts.executeQuery();
		} catch (SQLException e) {
			throw new Exception("Hubo un error al intentar cargar los posts del usuario " + user.getUsername()
					+ " desde la base de datos (SQLException).");
		}
	}
	
	/**
	 * Indica si "numberOfPosts" ha superado el número total de post en la base de datos.
	 * @return
	 * @throws Exception
	 */
	public boolean isAllPostLoaded() throws Exception {
		try {
			ResultSet tempTotalNumberOfPosts = totalNumberOfPosts.executeQuery();
			tempTotalNumberOfPosts.next();
			return tempTotalNumberOfPosts.getInt(1) <= numberOfPosts;
		} catch (SQLException e) {
			throw new Exception("Hubo un error al intentar obtener el numero de post totales. Desde la base de datos (SQLException).");
		}
	}

	/**
	 * Establece el nickname del usuario.
	 * @param id
	 * @param nickname
	 * @throws Exception
	 */
	public void setNickname(long id, String nickname) throws Exception {

		try {
			setNickname.setString(1, nickname);
			setNickname.setLong(2, id);
			setNickname.executeUpdate();
		} catch (SQLException e) {
			throw new Exception("Hubo un error al intentar actualizar el apodo de usuario (SQLException).");
		}

	}

	/**
	 * Establece la imagen del usuario.
	 * @param id
	 * @param url
	 * @throws Exception
	 */
	public void setUserImage(long id, String url) throws Exception {

		try {
			setUserImage.setString(1, url);
			setUserImage.setLong(2, id);
			setUserImage.executeUpdate();
		} catch (SQLException e) {
			throw new Exception("Hubo un error al intentar actualizar la imagen de usuario (SQLException).");
		}

	}

	public ResultSet allFiltersFromDB() throws Exception {
		try {
			return allFilters.executeQuery();
		} catch (SQLException e) {
			throw new Exception("Hubo un error al cargar los filtros desde la base de datos (SQLException).");
		}
	}
	
	public List<Filter> getAllFilters() throws Exception {

		List<Filter> result = new ArrayList<>();
		ResultSet filters = allFiltersFromDB();

		try {
			while (filters.next()) {
				result.add(new Filter(
					filters.getLong("id"), 
					filters.getString("nombre"),
					filters.getString("shortname"),
					filters.getString("descripcion")
				));
			}
		} catch (SQLException e) {
			throw new Exception("Hubo un error al intentar cargar todos los posts (SQLException).");
		}

		return result;
	}

	public void getFiltrosPost() {
		// TODO Implementar

	}

	public void getFiltrosUsuario() {
		// TODO Implementar

	}

	/**
	 * Cierra las conexiones de la base de datos.
	 * @throws Exception
	 */
	public void close() throws Exception {
		try {
			if (connPostgre != null)
				connPostgre.close();
		} catch (SQLException e1) {
			throw new Exception("Hubo un error al intentar cerrar la conexión con la base de datos (SQLException).");
		}
	}

	private byte[] transformarImagen(File file) {
		byte[] bytea = null;
		try (FileInputStream fis = new FileInputStream(file)) {
			ArrayList<Byte> byteArrayList = new ArrayList<>();

			int valor;
			while((valor = fis.read()) != -1) {
				byteArrayList.add((byte) valor);
			}

			bytea = new byte[byteArrayList.size()];

			for(int i = 0; i < byteArrayList.size(); i++) {
				bytea[i] = byteArrayList.get(i);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return bytea;
	}

}
