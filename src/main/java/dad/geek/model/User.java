package dad.geek.model;

import java.sql.SQLException;

import dad.geek.App;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class User {
	
	private IntegerProperty userID = new SimpleIntegerProperty();
	private StringProperty nickname = new SimpleStringProperty();
	private StringProperty username = new SimpleStringProperty();
	private StringProperty password = new SimpleStringProperty();
	private StringProperty mail = new SimpleStringProperty(); // TODO lo implementamos con mail al final????????????
	private ObjectProperty<Image> profileImage = new SimpleObjectProperty<>();
	private ListProperty<Post> posts = new SimpleListProperty<>(FXCollections.observableArrayList());
	
	public User() {}
	
	public User(int userID, String nickname, String username, String password) {
		setUserID(userID);
		setNickname(nickname);
		setUsername(username);
		setPassword(password);
	}
	
	public boolean userInDatabase() throws SQLException {
		return App.conexionLocal.getUserFromDB(getUsername(), getPassword()).next();
		// return App.conexionRemota.getUserFromDB(getUsername(), getPassword()).next();
	}
	
	public void addUsertoDB() {
		App.conexionLocal.createUser(getNickname(), getUsername(), getPassword());
		// App.conexionRemota.createUser(getNickname(), getUsername(), getPassword());
	}
	
	public final IntegerProperty userIDProperty() {
		return this.userID;
	}
	
	public final Integer getUserID() {
		return this.userID.get();
	}
	
	public final void setUserID(final int userID) {
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

	public final StringProperty mailProperty() {
		return this.mail;
	}

	public final String getMail() {
		return this.mailProperty().get();
	}

	public final void setMail(final String mail) {
		this.mailProperty().set(mail);
	}

	public final StringProperty nicknameProperty() {
		return this.nickname;
	}
	
	public final String getNickname() {
		return this.nicknameProperty().get();
	}
	
	public final void setNickname(final String nickname) {
		this.nicknameProperty().set(nickname);
	}

	public final ObjectProperty<Image> profileImageProperty() {
		return this.profileImage;
	}
	
	public final Image getProfileImage() {
		return this.profileImageProperty().get();
	}

	public final void setProfileImage(final Image profileImage) {
		this.profileImageProperty().set(profileImage);
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
	
}
