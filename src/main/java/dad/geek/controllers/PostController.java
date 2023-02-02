package dad.geek.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextArea;

import dad.geek.App;
import dad.geek.model.Post;
import dad.geek.model.User;
import javafx.beans.binding.Bindings;
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
	
	// view
	
	@FXML
    private Label arrobaLabel;

    @FXML
    private JFXTextArea contentTextArea;

    @FXML
    private FlowPane filterFlow;

    @FXML
    private FlowPane imageFlow;
    
    @FXML
    private ImageView imageBottom;

    @FXML
    private ImageView imageLeft;

    @FXML
    private ImageView imageRight;

    @FXML
    private ImageView profileImage;

    @FXML
    private Label usernameLabel;
    
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
		
		user = App.mysql.getUserObject(post.getUserID());
		
		// bindings
	
		contentTextArea.textProperty().bind(post.postContentProperty());
		usernameLabel.textProperty().bind(user.nicknameProperty());
		arrobaLabel.textProperty().bind(Bindings.concat("@").concat(user.usernameProperty()));
		
		
	}
	
	public BorderPane getView() {
		return view;
	}
	
}
