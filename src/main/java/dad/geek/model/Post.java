package dad.geek.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Post {

	private IntegerProperty postID = new SimpleIntegerProperty();
	private IntegerProperty userID = new SimpleIntegerProperty();
	private StringProperty postTitle = new SimpleStringProperty();
	private StringProperty postContent = new SimpleStringProperty();
	private ObjectProperty<Image> postImage = new SimpleObjectProperty<>();
	
	public Post() {	}
	
	public Post(int postID, int userID, String postTitle, String postContent) {
		setPostID(postID);
		setUserID(userID);
		setPostTitle(postTitle);
		setPostContent(postContent);
	}
	
	public final IntegerProperty postIDProperty() {
		return this.postID;
	}
	
	public final void setPostID(final int postID) {
		this.postIDProperty().set(postID);
	}
	
	public final int getPostID() {
		return this.postID.get();
	}
	
	public final IntegerProperty userIDProperty() {
		return this.userID;
	}
	
	public final int getUserID() {
		return this.userIDProperty().get();
	}
	
	public final void setUserID(final int userID) {
		this.userIDProperty().set(userID);
	}
	
	public final StringProperty postTitleProperty() {
		return this.postTitle;
	}
	
	public final String getPostTitle() {
		return this.postTitleProperty().get();
	}
	
	public final void setPostTitle(final String postTitle) {
		this.postTitleProperty().set(postTitle);
	}
	
	public final StringProperty postContentProperty() {
		return this.postContent;
	}
	
	public final String getPostContent() {
		return this.postContentProperty().get();
	}
	
	public final void setPostContent(final String postContent) {
		this.postContentProperty().set(postContent);
	}
	
	public final ObjectProperty<Image> postImageProperty() {
		return this.postImage;
	}
	
	public final Image getPostImage() {
		return this.postImageProperty().get();
	}
	
	public final void setPostImage(final Image postImage) {
		this.postImageProperty().set(postImage);
	}
	
}
