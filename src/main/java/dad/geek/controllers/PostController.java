package dad.geek.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextArea;

import dad.geek.App;
import dad.geek.model.Post;
import dad.geek.model.User;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

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
	private JFXTextArea contentTextArea;

	@FXML
	private FlowPane filterFlow;

	@FXML
	private FlowPane imageFlow;


    @FXML
    private ImageView profileImage;
    
    @FXML
    private BorderPane view;

	public PostController(Post post, MainController parent) {
		this.post = post;
		this.main = parent;

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

		user = App.conexionLocal.getUserObject(post.getUserID());

		// bindings

		profileImage.imageProperty().bind(user.profileImageProperty());
		contentTextArea.textProperty().bind(post.postContentProperty());
		usernameLabel.textProperty().bind(user.nicknameProperty());
		arrobaLabel.textProperty().bind(Bindings.concat("@").concat(user.usernameProperty()));

	}

	@FXML
	void onOpenUserAction(ActionEvent event) {
		main.getUserSectionController().changeUser(user);
	}

	public BorderPane getView() {
		return view;
	}

}
