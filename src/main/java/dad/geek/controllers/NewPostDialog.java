package dad.geek.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.geek.utils.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class NewPostDialog implements Initializable {
	
	private Stage thisStage;
	
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
	    
	    public NewPostDialog(Stage stage) {
	    	thisStage = stage;
	    	try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NewPostDialog.fxml"));
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
	    	thisStage.setMinWidth(450);
	    	thisStage.setScene(new Scene(new NewPostController(DirImages.DOWN).getView()));
	    }

	    @FXML
	    void onCreatePostEmpty(ActionEvent event) {
	    	thisStage.setMinWidth(450);
	    	thisStage.setScene(new Scene(new NewPostController(DirImages.EMPTY).getView()));
	    }

	    @FXML
	    void onCreatePostLeft(ActionEvent event) {
	    	thisStage.setMinWidth(450);
	    	thisStage.setScene(new Scene(new NewPostController(DirImages.LEFT).getView()));
	    }

	    @FXML
	    void onCreatePostRight(ActionEvent event) {
	    	thisStage.setMinWidth(450);
	    	thisStage.setScene(new Scene(new NewPostController(DirImages.RIGHT).getView()));
	    }
	    
	    public BorderPane getView() {
			return view;
		}
}
