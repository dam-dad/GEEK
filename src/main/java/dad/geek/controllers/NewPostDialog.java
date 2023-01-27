package dad.geek.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class NewPostDialog implements Initializable {
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
	    
	    public NewPostDialog() {
	    	try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginView.fxml"));
				loader.setController(this);
				loader.load();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	    
	    @Override
		public void initialize(URL location, ResourceBundle resources) {
			
		}

	    @FXML
	    void onCreatePostDown(ActionEvent event) {

	    }

	    @FXML
	    void onCreatePostEmpty(ActionEvent event) {

	    }

	    @FXML
	    void onCreatePostLeft(ActionEvent event) {

	    }

	    @FXML
	    void onCreatePostRight(ActionEvent event) {

	    }
	    
	    public BorderPane getView() {
			return view;
		}
}
