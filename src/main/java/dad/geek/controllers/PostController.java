package dad.geek.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.geek.App;
import dad.geek.model.Post;
import dad.geek.model.User;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;

public class PostController implements Initializable {

	// model

	private Post post;
	private User user;
	private MainController main;

	// view

	@FXML
	private Label usernameLabel; // TODO nicknameLabel
	
	@FXML
    private Label arrobaLabel; // TODO usernameLabel
	
	@FXML
	private Label contentLabel;

	@FXML
	private FlowPane filterFlow;

	@FXML
	private FlowPane imageFlow;

    @FXML
    private ImageView profileImage;
    
    @FXML
    private BorderPane view;
    
	public PostController(Post post) {
		this.post = post;

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PostView_Karim.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// load data

		try {
			user = App.conexionLocal.getUserObject(post.getUserID());
		} catch (Exception e) {
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setTitle("ERROR");
			errorAlert.setHeaderText("Hubo un error");
			errorAlert.setContentText(e.getMessage());
			errorAlert.initOwner(App.primaryStage);
			errorAlert.initModality(Modality.APPLICATION_MODAL);
			errorAlert.show();
		}

		// bindings

		profileImage.imageProperty().bind(user.profileImageProperty());
		contentLabel.textProperty().bind(post.postContentProperty());
		usernameLabel.textProperty().bind(user.nicknameProperty());
		arrobaLabel.textProperty().bind(Bindings.concat("@").concat(user.usernameProperty()));

	}

	@FXML
	void onOpenUserAction(ActionEvent event) {
		main.getUserSectionController().changeUser(user);
	}
	
	public PostController setMainController(MainController parent) {
		this.main = parent;
		return this;
	}

	public BorderPane getView() {
		return view;
	}

}
