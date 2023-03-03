package dad.geek.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import dad.geek.App;
import dad.geek.model.Post;
import dad.geek.model.User;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * Controlador del apartado de usuario. 
 *
 */
public class UserSectionController implements Initializable {

	// model

	private BooleanProperty goback = new SimpleBooleanProperty(false);
	private ListProperty<User> users = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ObjectProperty<User> currentUser = new SimpleObjectProperty<>();

	// view

	@FXML
	private VBox postsContainer;

	@FXML
	private JFXButton backButton;

	@FXML
	private JFXButton editButton;

	@FXML
	private ImageView profileImage;
	
	@FXML
	private Label usernameLabel;

	@FXML
	private Label nicknameLabel;

	@FXML
	private ScrollPane postContainerPane;

	@FXML
	private VBox view;

	/**
	 * Constructor de la clase UserSectionController, carga el fxml.
	 */
	public UserSectionController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/UserSectionView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// listeners

		users.sizeProperty().addListener(this::onUsersSizeModified);
		currentUser.addListener(this::onCurrentUserChanged);

		// load data

		users.add(App.user);

		// bindings

		backButton.visibleProperty().bind(goback);
		editButton.visibleProperty().bind(goback.not());

	}

	/**
	 * Se ejecuta cada vez que se le de al {@code JFXButton} "Editar".
	 * Llama a la función {@link #openEditWindow()}
	 * @param event
	 */
	@FXML
	void onEditAction(ActionEvent event) {
		openEditWindow();
	}

	/**
	 * Se ejecuta cada vez que se presione la imagen de perfil en la sección de usuario.
	 * Abre una nueva ventana {@code UNDECORATED} y le damos su controlador ({@link ShowImageController}).
	 * @param event
	 */
	@FXML
	void onProfileImageClicked(MouseEvent event) {

		Stage window = new Stage();
		window.initStyle(StageStyle.UNDECORATED);
		window.setTitle("Imagen de perfil");
		window.setScene(new Scene(new ShowImageController().setImageView(profileImage.getImage()).setStage(window).getView()));
		window.initOwner(App.primaryStage);
		window.initModality(Modality.APPLICATION_MODAL);
		
		window.getScene().setOnKeyPressed(t -> {
			if (t.getCode() == KeyCode.ESCAPE)
				window.getOnCloseRequest().handle(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
		});
		window.setOnCloseRequest(e -> {
			window.close();
		});

		window.show();

	}

	/**
	 * Se ejecuta cada vez que se presione el {@code JFXButton} "Editar".
	 * Abre la ventana de edición de usuario con el controlador {@link EditProfileController}.
	 */
	public void openEditWindow() {

		Stage window = new Stage();
		window.setTitle("Editar usuario");
		window.setScene(new Scene(new EditProfileController().setStage(window).getView()));
		window.getIcons().add(new Image(getClass().getResource("/images/iconooficia.png").toString()));
		window.setMinWidth(350);
		window.setMinHeight(370);
		window.initOwner(App.primaryStage);
		window.initModality(Modality.APPLICATION_MODAL);

		window.getScene().setOnKeyPressed(t -> {
			if (t.getCode() == KeyCode.ESCAPE)
				window.getOnCloseRequest().handle(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
		});
		window.setOnCloseRequest(e -> {
			window.close();
		});

		window.show();

	}

	/**
	 * Sólo posible si está viendo otro usuario, al darle al botón te manda al anterior usuario que estabas viendo.
	 * @param event
	 */
	@FXML
	void onBackAction(ActionEvent event) {
		users.remove(users.size() - 1);
		if (currentUser.get().equals(App.user))
			goback.set(false);
	}

	/**
	 * Se ejecuta cada vez que se modifica el tamaño del {@code ListProperty<}{@link User}{@code >} "users".
	 * Establece el usuario que se está viendo al último de la lista "users".
	 * @param o
	 * @param ov
	 * @param nv
	 */
	private void onUsersSizeModified(ObservableValue<? extends Number> o, Number ov, Number nv) {
		if (nv != null && nv.intValue() >= 1)
			currentUser.set(users.get(nv.intValue() - 1));
	}

	/**
	 * Se ejecuta cada vez que se modifique el {@code ObjectProperty<}{@link User}{@code >} "currentUser".
	 * Quita anteriores bindings y realiza los bindings con el nuevo usuario.
	 * @param o
	 * @param ov
	 * @param nv
	 */
	private void onCurrentUserChanged(ObservableValue<? extends User> o, User ov, User nv) {

		if (ov != null) {
			nicknameLabel.textProperty().unbind();
			usernameLabel.textProperty().unbind();
			profileImage.imageProperty().unbind();
		}

		if (nv != null) {
			nicknameLabel.textProperty().bind(nv.nicknameProperty());
			usernameLabel.textProperty().bind(Bindings.concat("@").concat(nv.usernameProperty()));
			profileImage.imageProperty().bind(nv.profileImageProperty());

			postContainerPane.setContent(loadPosts());
		}

	}

	/**
	 * Recibe nuevo usuario y cambia el anterior al nuevo.
	 * @param user
	 */
	public void changeUser(User user) {

		if (user.equals(App.user))
			goback.set(false);
		else
			goback.set(true);

		if (!currentUser.get().equals(user))
			users.add(user);

	}

	/**
	 * Hace lo mismo que {@link MainController#loadPosts(boolean)} pero en el user section.
	 * @return
	 */
	public VBox loadPosts() {
		try {
			postsContainer.getChildren().clear();
			for (Post p : App.conexionDB.getUserPosts(currentUser.get())) {
				postsContainer.getChildren().add(new PostController(p).getView());
				postsContainer.getChildren().add(new SplitPane());
			}
		} catch (Exception e) {
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setTitle("ERROR");
			errorAlert.setHeaderText("Hubo un error");
			errorAlert.setContentText(e.getMessage());
			errorAlert.initOwner(App.primaryStage);
			errorAlert.initModality(Modality.APPLICATION_MODAL);
			errorAlert.show();
			return null;
		}
		return postsContainer;
	}

	/**
	 * Ejecuta la función {@link #loadPosts()}.
	 */
	public void refreshPosts() {
		App.primaryStage.getScene().setCursor(Cursor.WAIT);
		postContainerPane.setContent(loadPosts());
		App.primaryStage.getScene().setCursor(Cursor.DEFAULT);
	}

	/**
	 * @return
	 */
	public VBox getView() {
		return view;
	}

}
