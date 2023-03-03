package dad.geek.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import dad.geek.App;
import dad.geek.db.DBManager;
import javafx.beans.property.ListProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

/**
 * Clase User, contiene todos los datos del usuario. 
 *
 */
public class User {
	
	private LongProperty userID = new SimpleLongProperty();
	private StringProperty nickname = new SimpleStringProperty();
	private StringProperty username = new SimpleStringProperty();
	private StringProperty password = new SimpleStringProperty();
	private ObjectProperty<Image> profileImage = new SimpleObjectProperty<>();
	private ObjectProperty<File> profileImageFile = new SimpleObjectProperty<>();
	private ListProperty<Post> posts = new SimpleListProperty<>(FXCollections.observableArrayList());

	/**
	 * Construcor genérico de la clase {@link User}.
	 */
	public User() {
	}

	/**
	 * Construcor de la clase {@link User}.
	 * @param userID
	 * @param nickname
	 * @param username
	 * @param password
	 * @param image
	 * @throws Exception
	 */
	public User(long userID, String nickname, String username, String password, byte[] image) throws Exception {
		// por defecto usamos user.png
		
		File f = new File(getClass().getResource("/images/user.png").getPath());
		setProfileImageFile(f);
		try {
			// si el array de bytes no está vacío lo transformamos a fichero
			if (image != null && image.length > 0) {
				f = new File(App.TEMP_PATH + username + ".png");
				try (FileOutputStream outputStream = new FileOutputStream(f)) {
				    outputStream.write(image);
				}
				setProfileImageFile(f);
				setProfileImage(new Image(new FileInputStream(f)));
			} else {
				setProfileImage(new Image(new FileInputStream(f)));
			}
		} catch (Exception e) {
			try {
				setProfileImage(new Image(getClass().getResource("/images/user.png").getPath()));
			} catch (Exception e1) {}
		}

		setUserID(userID);
		setNickname(nickname);
		setUsername(username);
		setPassword(password);
	}

	/**
	 * Comprueba si el usuario y contraseña está en la base de datos
	 * @return
	 * @throws Exception
	 */
	public boolean userInDatabase() throws Exception {
		return App.conexionDB.getUserFromDB(getUsername(), getPassword()).next();
	}
	
	/**
	 * Hace lo mismo que {@link User #userInDatabase()} pero sólo con el nombre de usuario.
	 * @return
	 * @throws Exception
	 */
	public boolean userInDatabase2() throws Exception {
		return App.conexionDB.getUserFromDB(getUsername()).next();
	}

	/**
	 * Añade un usuario a la base de datos.
	 * @throws Exception
	 */
	public void addUsertoDB() throws Exception {
		App.conexionDB.createUser(this);
	}

	public final LongProperty userIDProperty() {
		return this.userID;
	}

	public final long getUserID() {
		return this.userIDProperty().get();
	}

	public final void setUserID(final long userID) {
		this.userIDProperty().set(userID);
	}

	public final StringProperty usernameProperty() {
		return this.username;
	}

	public final String getUsername() {
		return this.usernameProperty().get();
	}

	public final void setUsername(final String username) {
		this.usernameProperty().set(username);
	}

	public final StringProperty passwordProperty() {
		return this.password;
	}

	public final String getPassword() {
		return this.passwordProperty().get();
	}

	public final void setPassword(final String password) {
		this.passwordProperty().set(password);
	}

	public final StringProperty nicknameProperty() {
		return this.nickname;
	}

	public final String getNickname() {
		return this.nicknameProperty().get();
	}

	/**
	 * Añade el nickname a la base de datos.
	 * @param nickname
	 * @throws Exception
	 */
	public final void setNickname(final String nickname) throws Exception {
		this.nicknameProperty().set(nickname);
		App.conexionDB.setNickname(getUserID(), nickname);
	}

	public final ObjectProperty<Image> profileImageProperty() {
		return this.profileImage;
	}

	public final Image getProfileImage() {
		return this.profileImageProperty().get();
	}

	/**
	 * Añade la imagen de perfil y llama al método {@link DBManager#setUserImage(long, File)}.
	 * @param profileImage
	 * @throws Exception
	 */
	public final void setProfileImage(final Image profileImage) throws Exception {
		this.profileImageProperty().set(profileImage);
		App.conexionDB.setUserImage(getUserID(), getProfileImageFile());
	}

	public final ListProperty<Post> postsProperty() {
		return this.posts;
	}

	public final ObservableList<Post> getPosts() {
		return this.postsProperty().get();
	}

	public final void setPosts(final ObservableList<Post> posts) {
		this.postsProperty().set(posts);
	}

	public final ObjectProperty<File> profileImageFileProperty() {
		return this.profileImageFile;
	}

	public final File getProfileImageFile() {
		return this.profileImageFileProperty().get();
	}
	
	public final void setProfileImageFile(final File profileImageFile) {
		this.profileImageFileProperty().set(profileImageFile);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (getUserID() != other.getUserID())
			return false;
		return true;
	}

}
