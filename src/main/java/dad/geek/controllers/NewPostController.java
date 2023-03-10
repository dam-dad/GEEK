package dad.geek.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import dad.geek.App;
import dad.geek.db.DBManager;
import dad.geek.model.Post;
import javafx.beans.binding.Bindings;
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
import javafx.stage.FileChooser;
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
	private Image newImage;
	private File newImageFile;

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
		
		//listeners
		
		sendButton.disableProperty().bind(contentTextArea.textProperty().isEmpty());

	}

	/**
	 * Recibe un {@code Stage} y lo asigna al {@code Stage} local estableciendo la anchura m??nima a 450. 
	 * @param stage
	 * @return A si mismo: {@link NewPostController}.
	 */
	public NewPostController setStage(Stage stage) {
		this.stage = stage;
		this.stage.setMinWidth(450);
		return this;
	}

	/**
	 * Se ejecuta cada vez que le des al bot??n superior derecho, 
	 * abre una ventana controlada por {@link AddFilterController} con los filtros posibles que se pueden seleccionar.
	 * @param event
	 */
	@FXML
	void onAddFilterAction(ActionEvent event) {

		Stage window = new Stage();
		window.setTitle("A??adir filtro");
		window.setScene(new Scene(new AddFilterController(post).setStage(window).getView()));
		window.getIcons().add(new Image(getClass().getResource("/images/iconooficia.png").toString()));
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
	 * Se ejecuta cada vez que se cierre la ventana de "A??adir filtro".<br/>
	 * Crea el {@code Label} y lo a??ade al {@code FlowPane} con un listener que al hacer click sobre el filtro salga una ventana de alerta preguntando si lo quieres eliminal.
	 * @return
	 */
	private FlowPane getFiltersFlowPane() {
		
		App.primaryStage.getScene().setCursor(Cursor.WAIT);
		try {
			if(filterFlow.getChildren().size() != post.getFilters().size()) {
				Label label = new Label();
				label.setText(post.filtersProperty().get(post.filtersProperty().getSize()-1).getFilterName());
			
			
				// listeners
				
				label.setOnMouseClicked(MouseEvent  -> {
					Alert deleteAlert = new Alert(AlertType.CONFIRMATION);
					deleteAlert.setTitle("??BORRAR?");
					deleteAlert.setHeaderText("??Desea borrar el filtro " + label.getText() + "?");
					deleteAlert.initOwner(App.primaryStage);
					deleteAlert.initModality(Modality.APPLICATION_MODAL);
					Optional<ButtonType> result = deleteAlert.showAndWait();
					if (result.get() == ButtonType.OK) {
						post.deleteFilter(label.getText());
						filterFlow.getChildren().remove(label);
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
		App.primaryStage.getScene().setCursor(Cursor.DEFAULT);
		return filterFlow;
	}


	/**
	 * Se ejecuta cuando se presiona el {@code JFXButton} "Enviar". Ejecuta la funci??n {@link DBManager#sendPost(Post)}.
	 * @param event
	 */
	@FXML
	void onSendAction(ActionEvent event) {
		post.setPostDate(LocalDateTime.now());
		try {
			post.setPostImage(newImage);
			App.conexionDB.sendPost(post, newImageFile);
			parent.onReloadPostAction(event);
		} catch (Exception e) {
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setTitle("ERROR");
			errorAlert.setHeaderText("Hubo un error");
			errorAlert.setContentText(e.getMessage());
			errorAlert.initOwner(App.primaryStage);
			errorAlert.initModality(Modality.APPLICATION_MODAL);
			errorAlert.show();
			e.printStackTrace();
		}
		this.stage.getOnCloseRequest().handle(new WindowEvent(this.stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	/**
	 * Crea una ventana controlada por {@link NewPostDialog}.
	 * @param event
	 */
	@FXML
	void onAddImage(ActionEvent event) {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Abrir imagen");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "\\Pictures\\"));
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG Files", "*.png", "*.jpg"));

		File selectedFile = fileChooser.showOpenDialog(App.primaryStage);
		if (selectedFile != null) {
			newImageFile = selectedFile;
			newImage = new Image(selectedFile.toURI().toString());
			
			showImage();
			
		}

	}
	
	/**
	 * Muestra la imagen de forma provisional
	 */
	private void showImage() {
		
		ImageView imageView = new ImageView(newImage);
		imageView.setFitWidth(200);
		imageView.setFitHeight(200);
		imageView.setVisible(true);
		
		contentContainer.setBottom(imageView);
		view.setPrefHeight((view.getPrefHeight() == prefHeight) ? view.getPrefHeight() + 250 : view.getPrefHeight());

		stage.setMinWidth(view.getPrefWidth());
		stage.setMinHeight(view.getPrefHeight());
		stage.centerOnScreen();
		BorderPane.setAlignment(imageView, Pos.CENTER);
		
	}

	/**
	 * @return
	 */
	public BorderPane getView() {
		return view;
	}

}
