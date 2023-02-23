package dad.geek.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.geek.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Controlador de la ventana para elegir la posicion de imagen.
 *
 */
public class NewPostDialog implements Initializable {

	// model

	private Stage stage;
	private Image newImage;
	private NewPostController newPostController;

	// view

	@FXML
	private Button downButton;

	@FXML
	private ImageView downImage;

	@FXML
	private Button emptyButton;

	@FXML
	private ImageView emptyImage;

	@FXML
	private Button leftButton;

	@FXML
	private ImageView leftImage;

	@FXML
	private Button rightButton;

	@FXML
	private ImageView rightImage;

	@FXML
	private BorderPane view;

	/**
	 * Constructor de la clase NewPostDialog, carga el fxml.
	 */
	public NewPostDialog() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NewPostDialog.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	/**
	 * Se ejecuta cada vez que se seleccione una opcion que no sea "Sin imagen"
	 * @param event
	 */
	@FXML
	void onSelectImage(ActionEvent event) {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Abrir imagen");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "\\Pictures\\"));
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG Files", "*.png", "*.jpg"));

		File selectedFile = fileChooser.showOpenDialog(App.primaryStage);
		if (selectedFile != null) {
			newImage = new Image(selectedFile.toURI().toString());
			newPostController.setPosition(((Button) event.getSource()).getId(), newImage);
			this.stage.getOnCloseRequest().handle(new WindowEvent(this.stage, WindowEvent.WINDOW_CLOSE_REQUEST));
		}

		this.stage.getScene().setOnKeyPressed(t -> {
			if (t.getCode() == KeyCode.ESCAPE)
				this.stage.getOnCloseRequest().handle(new WindowEvent(this.stage, WindowEvent.WINDOW_CLOSE_REQUEST));
		});

	}

	/**
	 * Si se selecciona la opción de "Sin imagen" pedimos que se cierre la ventana y llamamos a la función {@link NewPostController#noImages()}.
	 * @param event
	 */
	@FXML
	void onNoImageAction(ActionEvent event) {
		this.stage.getOnCloseRequest().handle(new WindowEvent(this.stage, WindowEvent.WINDOW_CLOSE_REQUEST));
		newPostController.noImages();
	}

	/**
	 * Recibe el controlador padre {@link NewPostController} y lo guarda.
	 * @param parent
	 * @return A si mismo: {@link NewPostDialog}.
	 */
	public NewPostDialog setParent(NewPostController parent) {
		this.newPostController = parent;
		return this;
	}

	/**
	 * Recibe el {@code Stage} de la ventana que controla éste controlador ({@link NewPostDialog}
	 * @param stage
	 * @return A si mismo: {@link NewPostDialog}
	 */
	public NewPostDialog setStage(Stage stage) {
		this.stage = stage;
		return this;
	}

	public BorderPane getView() {
		return view;
	}
}
