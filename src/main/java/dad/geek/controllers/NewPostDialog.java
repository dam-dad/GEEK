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
import javafx.scene.input.KeyCode;
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
    void onCreatePost(ActionEvent event) {
    	thisStage.setScene(new Scene(new NewPostController(thisStage).setPosition(((Button) event.getSource()).getId()).getView()));
    	thisStage.getScene().setOnKeyPressed(t -> {
			if(t.getCode() == KeyCode.ESCAPE)
				thisStage.close();
		});
    }
    
    public BorderPane getView() {
		return view;
	}
}
