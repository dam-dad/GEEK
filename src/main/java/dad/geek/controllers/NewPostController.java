package dad.geek.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import dad.geek.App;
import dad.geek.model.Post;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class NewPostController implements Initializable {
	
	// model
	
	private Stage stage;
	private Post post = new Post();
	
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
	
	public NewPostController setPosition(String posicionImagen) {
		
		ImageView image = new ImageView(new Image("/images/ejemplo.png"));
		image.setFitWidth(200);
		image.setFitHeight(200);
		image.setVisible(true);

		switch (posicionImagen) {
		case "leftButton":
			contentContainer.setLeft(image);
			break;
		case "rightButton":
			contentContainer.setRight(image);
			break;
		case "downButton":
			contentContainer.setBottom(image);
			break;
		case "emptyButton":
			break;
		}
		
		BorderPane.setAlignment(image, Pos.CENTER);
		
		return this;
		
	}

	@FXML
	void onAddFilterAction(ActionEvent event) {
		
	}

	@FXML
	void onSendAction(ActionEvent event) {
		App.conexionLocal.sendPost(post);
		this.stage.getOnCloseRequest().handle(new WindowEvent(this.stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}
	
	public BorderPane getView() {
		return view;
	}

}
