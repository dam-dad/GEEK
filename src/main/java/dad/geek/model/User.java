package dad.geek.model;

import dad.geek.App;
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

public class User {

	private LongProperty userID = new SimpleLongProperty();
	private StringProperty nickname = new SimpleStringProperty();
	private StringProperty username = new SimpleStringProperty();
	private StringProperty password = new SimpleStringProperty();
	private StringProperty mail = new SimpleStringProperty(); // TODO lo implementamos con mail al final????????????
	private ObjectProperty<Image> profileImage = new SimpleObjectProperty<>();
	private ListProperty<Post> posts = new SimpleListProperty<>(FXCollections.observableArrayList());

	public User() {
	}

	public User(long userID, String nickname, String username, String password, String image) throws Exception {

		try {
			if (image != null && !image.trim().equals(""))
				setProfileImage(new Image(image));
			else {
				setProfileImage(new Image(getClass().getResource("/images/user.png").toURI().toString()));
			}
		} catch (Exception e) {
			try {
				setProfileImage(new Image(getClass().getResource("/images/user.png").toURI().toString()));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

		setUserID(userID);
		setNickname(nickname);
		setUsername(username);
		setPassword(password);
	}

	public boolean userInDatabase() throws Exception {
		return App.conexionDB.getUserFromDB(getUsername(), getPassword()).next();
//		return App.conexionRemota.getUserFromDB(getUsername(), getPassword()).next();
	}
	
	public boolean userInDatabase2() throws Exception {
		return App.conexionDB.getUserFromDB(getUsername()).next();
//		return App.conexionRemota.getUserFromDB(getUsername()).next();
	}

	public void addUsertoDB() throws Exception {
		App.conexionDB.createUser(getNickname(), getUsername(), getPassword());
//		App.conexionRemota.createUser(getNickname(), getUsername(), getPassword());
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

	public final void setProfileImage(final Image profileImage) throws Exception {
		this.profileImageProperty().set(profileImage);
		App.conexionDB.setUserImage(getUserID(), profileImage.getUrl());
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
