package dad.geek.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import dad.geek.App;
import dad.geek.model.Filter;
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
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class NewPostController implements Initializable {

	// model

	private Stage stage;
	private Post post = new Post();
	private AddFilterController afc = new AddFilterController();
	double prefWidth;
	double prefHeight;

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

	public NewPostController() {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NewPostView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

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

	public NewPostController setStage(Stage stage) {
		this.stage = stage;
		this.stage.setMinWidth(450);
		return this;
	}

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

	@FXML
	void onAddFilterAction(ActionEvent event) {

		Stage window = new Stage();
		window.setTitle("Añadir filtro");
		window.setScene(new Scene(new AddFilterController().setStage(window).getView()));
		window.setMinWidth(268);
		window.setMinHeight(472);
		window.initOwner(App.primaryStage);
		window.initModality(Modality.APPLICATION_MODAL);

		window.getScene().setOnKeyPressed(t -> {
			if (t.getCode() == KeyCode.ESCAPE)
				window.getOnCloseRequest().handle(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
		});
		window.setOnCloseRequest(e -> {
			getFiltersFlowPane();
			window.close();
		});

		window.show();

	}
	
	private FlowPane getFiltersFlowPane() {
		
		App.primaryStage.getScene().setCursor(Cursor.WAIT);
		try {
			filterFlow.getChildren().clear();
//			for (Filter f : App.conexionDB.getAllPosts(reload)) {
				filterFlow.getChildren().add(new Label(afc.getSelectedFilterName()));
//				postsContainer.getChildren().add(new SplitPane());
//			}
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


	@FXML
	void onSendAction(ActionEvent event) {
		post.setPostDate(LocalDateTime.now());
		try {
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

	public BorderPane getView() {
		return view;
	}

}
