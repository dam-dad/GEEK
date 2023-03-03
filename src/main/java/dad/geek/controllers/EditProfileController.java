package dad.geek.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import dad.geek.App;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Controlador de la ventana Editar Perfil.
 *
 */
public class EditProfileController implements Initializable {

	// model

	private Image newImage;
	private File imageFile;
	private Stage stage;
	private StringProperty newName = new SimpleStringProperty();
	private BooleanProperty isValidName = new SimpleBooleanProperty(true);

	// view

	@FXML
	private JFXButton editImageButton;

	@FXML
	private JFXButton editNicknameButton;

	@FXML
	private Label nicknameLabel;

	@FXML
	private ImageView profileImage;

	@FXML
	private Label usernameLabel;

	@FXML
	private BorderPane view;

	/**
	 * Constructor de la clase EditProfileController, carga el fxml.
	 */
	public EditProfileController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EditProfileView.fxml"));
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

		newName.set(App.user.getNickname());

		// bindings

		nicknameLabel.textProperty().bind(newName);
		usernameLabel.textProperty().bind(Bindings.concat("@").concat(App.user.usernameProperty()));
		profileImage.setImage(App.user.getProfileImage());

	}

	/**
	 * Se ejecuta al pulsar el botón "editImageButton", abre una ventana FileChooser(solo puedes seleccionar *.png y *.jpg).
	 * Recibe la imagen de este FileChooser y la añade de forma provisional.
	 * @param event
	 */
	@FXML
	void onEditImageAction(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Abrir imagen");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "\\Pictures\\"));
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG Files", "*.png", "*.jpg"));
		imageFile = fileChooser.showOpenDialog(App.primaryStage);
		if (imageFile != null) {
			newImage = new Image(imageFile.toURI().toString());
			profileImage.setImage(newImage);
			App.user.setProfileImageFile(imageFile);
		}

	}

	/**
	 * Se ejecuta cada vez que le das al botón "aceptar", guarda cualquier cambio que se haya realizado.
	 * @param event
	 */
	@FXML
	void onAcceptAction(ActionEvent event) {
		try {
			if (newImage != null)
				App.user.setProfileImage(newImage);
			if (newName != null && !newName.get().trim().equals(""))
				App.user.setNickname(newName.get());
		} catch (Exception e) {
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setTitle("ERROR");
			errorAlert.setHeaderText("Hubo un error");
			errorAlert.setContentText(e.getMessage());
			errorAlert.initOwner(App.primaryStage);
			errorAlert.initModality(Modality.APPLICATION_MODAL);
			errorAlert.show();
		}
		stage.getOnCloseRequest().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	/**
	 * Se ejecuta cada vez que le das al botón "cancelar", pide que se cierre la ventana sin guardar ningún cambio.
	 * @param event
	 */
	@FXML
	void onCancelAction(ActionEvent event) {
		stage.getOnCloseRequest().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	/**
	 * Se ejecuta cada vez qu le das al botón editNicknameButton, abre un TextInputDialog donde introduces tu nuevo nombre.
	 * El botón aceptar sólo estará habilitado si el nombre es válido(entre 4 y 40 carácteres), una vez pulsade se guarda el nombre de forma provisional.
	 * El botón cancelar sólo cierra la ventana, no guarda el nombre.
	 * @param event
	 */
	@FXML
	void onEditNicknameAction(ActionEvent event) {
		isValidName.set(true);
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Cambiar nombre");
		dialog.initOwner(stage);
		dialog.setHeaderText(
				String.format("Tu nombre actual es \"%s\".\nIntroduzca su nuevo nombre.", App.user.getNickname()));
		dialog.setContentText("Nuevo nombre:");
		dialog.getEditor().textProperty().addListener((o, ov, nv) -> {
			if (nv != null && nv.length() > 3 && nv.length() <= 40)
				isValidName.set(false);
			else
				isValidName.set(true);
		});
		dialog.getDialogPane().lookupButton(ButtonType.OK).disableProperty().bind(isValidName);

		Optional<String> result = dialog.showAndWait();

		result.ifPresent(name -> {
			newName.set(name);
		});
	}

	/**
	 * Recibe el stage para poder modificar la ventana o ejecutar un WINDOW_CLOSE_REQUEST.
	 * @param stage
	 * @return
	 */
	public EditProfileController setStage(Stage stage) {
		this.stage = stage;
		return this;
	}

	/**
	 * @return
	 */
	public BorderPane getView() {
		return view;
	}

}
