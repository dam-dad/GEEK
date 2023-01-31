package dad.geek.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextArea;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class PostController_Karim implements Initializable {
	
	@FXML
    private Label arrobaLabel;

    @FXML
    private JFXTextArea contentTextArea;

    @FXML
    private FlowPane filterFlow;

    @FXML
    private FlowPane imageFlow;

    @FXML
    private ImageView profileImage;

    @FXML
    private Label usernameLabel;
    
    @FXML
    private BorderPane view;
	
	public PostController_Karim() {
		
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
	
	}

	public BorderPane getView() {
		return view;
	}
	
}
