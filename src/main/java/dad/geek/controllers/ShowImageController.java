package dad.geek.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Controlador de la ventana que se encarga de mostrar la imagen.
 *
 */
public class ShowImageController implements Initializable {

	// model
	
	private Stage stage;
	private double xOffset;
	private double yOffset;

	// view

	@FXML
	private ImageView imageView;

	@FXML
	private BorderPane view;

	/**
	 * Constructor de la clase ShowImageController, carga el fxml.
	 */
	public ShowImageController() {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ShowImageView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// bindings
		
	}

	/**
	 * Se ejecuta cada vez que se pulse el {@code JFXButton} "X".<br/>
	 * Pide el cierre de la ventana.
	 * @param event
	 */
	@FXML
	void onCloseAction(ActionEvent event) {
		stage.getOnCloseRequest().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}
	
	/**
	 * Se ejecuta cada vez que se presiona el click izquierdo del ratón sobre de la barra superior de la ventana.<br/>
	 * Establece el valor xOffset e yOffset como la distancia al origen de la ventana(punto de referencia).
	 * @param event
	 */
	@FXML
    void onMousePressed(MouseEvent event) {
		xOffset = stage.getX() - event.getScreenX();
        yOffset = stage.getY() - event.getScreenY();
    }

	/**
	 * Se ejecuta siempre que se mantenga presionado y arrastre el click izquierdo del ratón sobre la barra superior de la ventana.<br/>
	 * Establece el valor x e y de la ventana a la suma de el punto de referencia más el punto en el que está el ratón.
	 * @param event
	 */
	@FXML
    void onMouseDragged(MouseEvent event) {
		stage.setX(event.getScreenX() + xOffset);
		stage.setY(event.getScreenY() + yOffset);
    }
	
	
	/**
	 * Recibe el {@code Stage} de la ventana que controla.
	 * @param stage
	 * @return A sí mismo: {@link ShowImageController}.
	 */
	public ShowImageController setStage(Stage stage) {
		this.stage = stage;
		return this;
	}

	/**
	 * Recibe la imagen a mostrar y la asigna al {@code ImageView}.
	 * @param image
	 * @return A sí mismo: {@link ShowImageController}.
	 */
	public ShowImageController setImageView(Image image) {
		this.imageView.setImage(image);
		return this;
	}

	public BorderPane getView() {
		return view;
	}

}
