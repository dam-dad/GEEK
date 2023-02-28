package dad.geek.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import org.apache.commons.collections.bag.SynchronizedSortedBag;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import dad.geek.App;
import dad.geek.db.DBManager;
import dad.geek.model.Filter;
import dad.geek.model.Post;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Controlador de la ventana de nuevo post. 
 *
 */
public class NewPostController implements Initializable {

	// model

	private Stage stage;
	private Post post = new Post();
	double prefWidth;
	double prefHeight;
	private MainController parent;
	private ListProperty<Filter> fal = new SimpleListProperty<>(FXCollections.observableArrayList());
//	ArrayList<Label> lal = new ArrayList<Label>();
	private ListProperty<Label> lal = new SimpleListProperty<>(FXCollections.observableArrayList());



	// view

	@FXML
	private BorderPane contentContainer;

	@FXML
	private ImageView profileImage;

	@FXML
	private Label nicknameLabel;

	@FXML
	private Label usernameLabel;

	@FXML
	private FlowPane filterFlow;

	@FXML
	private JFXButton addFilterButton;

	@FXML
	private JFXTextArea contentTextArea;

	@FXML
	private JFXButton sendButton;

	@FXML
	private BorderPane view;

	/**
	 * Constructor de la clase NewPostController, carga el fxml.
	 */
	public NewPostController(MainController parent) {

		try {
			this.parent = parent;
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NewPostView.fxml"));
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

		// load data

		post.setUserID(App.user.getUserID());
		prefWidth = view.getPrefWidth();
		prefHeight = view.getPrefHeight();

		// bindings

		nicknameLabel.textProperty().bind(App.user.nicknameProperty());
		usernameLabel.textProperty().bind(Bindings.concat("@").concat(App.user.usernameProperty()));
		post.postContentProperty().bind(contentTextArea.textProperty());
		profileImage.imageProperty().bind(App.user.profileImageProperty());

	}

	/**
	 * Recibe un {@code Stage} y lo asigna al {@code Stage} local estableciendo la anchura mínima a 450. 
	 * @param stage
	 * @return A si mismo: {@link NewPostController}.
	 */
	public NewPostController setStage(Stage stage) {
		this.stage = stage;
		this.stage.setMinWidth(450);
		return this;
	}

	/**
	 * Recibe un {@code String} con la posición de la imagen y un {@code Image} que se desea poner en el post.
	 * @param posicionImagen
	 * @param image
	 * @return A si mismo: {@link NewPostController}.
	 */
	public NewPostController setPosition(String posicionImagen, Image image) {

		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(200);
		imageView.setFitHeight(200);
		imageView.setVisible(true);

		switch (posicionImagen) {
		case "leftButton":
			contentContainer.setLeft(imageView);
			view.setPrefWidth(
					(view.getPrefWidth() <= prefWidth + 200) ? view.getPrefWidth() + 200 : view.getPrefWidth());
			break;
		case "rightButton":
			contentContainer.setRight(imageView);
			view.setPrefWidth(
					(view.getPrefWidth() <= prefWidth + 200) ? view.getPrefWidth() + 200 : view.getPrefWidth());
			break;
		case "downButton":
			contentContainer.setBottom(imageView);
			view.setPrefHeight(
					(view.getPrefHeight() == prefHeight) ? view.getPrefHeight() + 250 : view.getPrefHeight());
			break;
		}

		post.getPostImage().add(image);

		stage.setMinWidth(view.getPrefWidth());
		stage.setMinHeight(view.getPrefHeight());
		stage.centerOnScreen();
		BorderPane.setAlignment(imageView, Pos.CENTER);

		return this;

	}

	/**
	 * Quita todas las imagenes que se han subido al post que se está creando.
	 */
	public void noImages() {

		view.setPrefWidth(450);
		view.setPrefHeight(330);

		stage.setWidth(view.getPrefWidth());
		stage.setHeight(view.getPrefHeight());
		stage.setMinWidth(view.getPrefWidth());
		stage.setMinHeight(view.getPrefHeight());
		contentContainer.setLeft(null);
		contentContainer.setRight(null);
		contentContainer.setBottom(null);
		post.getPostImage().clear();
		stage.centerOnScreen();

	}

	/**
	 * Se ejecuta cada vez que le des al botón superior derecho, 
	 * abre una ventana controlada por {@link AddFilterController} con los filtros posibles que se pueden seleccionar.
	 * @param event
	 */
	@FXML
	void onAddFilterAction(ActionEvent event) {

		Stage window = new Stage();
		window.setTitle("Añadir filtro");
		window.setScene(new Scene(new AddFilterController().setStage(window).getView()));
		window.setMinWidth(268);
		window.setMinHeight(472);
		window.initOwner(App.primaryStage);
		window.initModality(Modality.APPLICATION_MODAL);

		window.getScene().setOnKeyPressed(t -> { // TODO no funciona
			if (t.getCode() == KeyCode.ESCAPE)
				window.getOnCloseRequest().handle(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
		});
		window.setOnCloseRequest(e -> {
			getFiltersFlowPane();
			window.close();
		});

		window.show();

	}
	
	/**
	 * Se ejecuta cada vez que se cierre la ventana de "Añadir filtro".<br/>
	 * Crea el {@code Label} y lo añade al {@code FlowPane} con un listener que al hacer click sobre el filtro salga una ventana de alerta preguntando si lo quieres eliminal.
	 * @return
	 */
	private FlowPane getFiltersFlowPane() {
		
		IntegerProperty ip = new SimpleIntegerProperty();
		
		App.primaryStage.getScene().setCursor(Cursor.WAIT);
		try {
			Label label = new Label();
			label.setText(AddFilterController.getSelectedFilterName());
			lal.add(label);
			System.out.println("Labels: " + lal);

			ip.bind(lal.sizeProperty());
			label.idProperty().bind(ip.asString());
			System.out.println("ID: " + label.getId());
			
			Filter filter = new Filter();
			filter = AddFilterController.getSelectedFilter();
			fal.add(filter);
			System.out.println("Filtros: " + fal);
			
			// listeners
			label.setOnMouseClicked(MouseEvent  -> {
				Alert deleteAlert = new Alert(AlertType.CONFIRMATION);
				deleteAlert.setTitle("¿BORRAR?");
				deleteAlert.setHeaderText("¿Desea borrar el filtro " + label.getText() + "?");
				deleteAlert.initOwner(App.primaryStage);
				deleteAlert.initModality(Modality.APPLICATION_MODAL);
				Optional<ButtonType> result = deleteAlert.showAndWait();
				
				if (result.get() == ButtonType.OK) {
					filterFlow.getChildren().remove(label);
					lal.remove(label);
					System.out.println("Labels: " + lal);
					fal.remove(Integer.parseInt(label.getId())-1);
					System.out.println("Filtros: " + fal);
				} else {
					deleteAlert.close();
				}
			});
			
			label.setOnMouseEntered(MouseEvent -> {
				label.setFont(Font.font("System", FontWeight.BOLD, 12));
				label.setUnderline(true);
			});
			
			label.setOnMouseExited(MouseEvent -> {
				label.setFont(Font.font("System", FontWeight.NORMAL, 12));
				label.setUnderline(false);
			});
			
			filterFlow.getChildren().add(label);
			
		} catch (Exception e) { // TODO si no seleccionas nada salta error
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setTitle("ERROR");
			errorAlert.setHeaderText("Hubo un error");
			errorAlert.setContentText(e.getMessage());
			errorAlert.initOwner(App.primaryStage);
			errorAlert.initModality(Modality.APPLICATION_MODAL);
			errorAlert.show();
			return null;
		}
		App.primaryStage.getScene().setCursor(Cursor.DEFAULT);
		return filterFlow;
	}


	/**
	 * Se ejecuta cuando se presiona el {@code JFXButton} "Enviar". Ejecuta la función {@link DBManager#sendPost(Post)}.
	 * @param event
	 */
	@FXML
	void onSendAction(ActionEvent event) {
		post.setPostDate(LocalDateTime.now());
		try {
			parent.onReloadPostAction(event);
			App.conexionDB.sendPost(post);
		} catch (Exception e) {
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setTitle("ERROR");
			errorAlert.setHeaderText("Hubo un error");
			errorAlert.setContentText(e.getMessage());
			errorAlert.initOwner(App.primaryStage);
			errorAlert.initModality(Modality.APPLICATION_MODAL);
			errorAlert.show();
		}
		this.stage.getOnCloseRequest().handle(new WindowEvent(this.stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	/**
	 * Crea una ventana controlada por {@link NewPostDialog}.
	 * @param event
	 */
	@FXML
	void onAddImage(ActionEvent event) {

		Stage window = new Stage();
		window.setTitle("Nuevo Post");
		window.setScene(new Scene(new NewPostDialog().setStage(window).setParent(this).getView()));
		window.setMinHeight(300);
		window.setMinWidth(300);
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
	 * @return
	 */
	public BorderPane getView() {
		return view;
	}

}
