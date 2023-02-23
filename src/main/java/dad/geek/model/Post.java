package dad.geek.model;

import java.time.LocalDateTime;

import dad.geek.utils.DirImages;
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
 * Clase Post, contiene todos los datos del post.
 *
 */
public class Post {

	private LongProperty postID = new SimpleLongProperty();
	private LongProperty userID = new SimpleLongProperty();
	private StringProperty postTitle = new SimpleStringProperty();
	private StringProperty postContent = new SimpleStringProperty();
	private ListProperty<Image> postImage = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ObjectProperty<LocalDateTime> postDate = new SimpleObjectProperty<>();
	private ObjectProperty<DirImages> dirImage = new SimpleObjectProperty<>();
	private ListProperty<Filter> filters = new SimpleListProperty<>(FXCollections.observableArrayList());

	/**
	 * Constructor genérico de la clase {@link Post}.
	 */
	public Post() {
	}

	/**
	 * Constructor de la clase {@link Post}.
	 * @param postID
	 * @param userID
	 * @param postTitle
	 * @param postContent
	 */
	public Post(long postID, long userID, String postTitle, String postContent) {
		setPostID(postID);
		setUserID(userID);
		setPostTitle(postTitle);
		setPostContent(postContent);
	}

	public final LongProperty postIDProperty() {
		return this.postID;
	}

	public final long getPostID() {
		return this.postIDProperty().get();
	}

	public final void setPostID(final long postID) {
		this.postIDProperty().set(postID);
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

	public final ObjectProperty<LocalDateTime> postDateProperty() {
		return this.postDate;
	}

	public final LocalDateTime getPostDate() {
		return this.postDateProperty().get();
	}

	public final void setPostDate(final LocalDateTime postDate) {
		this.postDateProperty().set(postDate);
	}

	public final ListProperty<Filter> filtersProperty() {
		return this.filters;
	}

	public final ObservableList<Filter> getFilters() {
		return this.filtersProperty().get();
	}

	public final void setFilters(final ObservableList<Filter> filters) {
		this.filtersProperty().set(filters);
	}

	public final ObjectProperty<DirImages> dirImageProperty() {
		return this.dirImage;
	}

	public final DirImages getDirImage() {
		return this.dirImageProperty().get();
	}

	public final void setDirImage(final DirImages dirImage) {
		this.dirImageProperty().set(dirImage);
	}

	public final ListProperty<Image> postImageProperty() {
		return this.postImage;
	}

	public final ObservableList<Image> getPostImage() {
		return this.postImageProperty().get();
	}

	public final void setPostImage(final ObservableList<Image> postImage) {
		this.postImageProperty().set(postImage);
	}

}
