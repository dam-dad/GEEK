package dad.geek.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
import javafx.stage.WindowEvent;

public class NewPostDialog implements Initializable {
	
	private Stage stage;
	
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
    	
    	this.stage.setScene(new Scene(
			new NewPostController()
				.setStage(this.stage)
    			.setPosition(((Button) event.getSource()).getId()).getView()));
    	
    	this.stage.getScene().setOnKeyPressed(t -> {
			if(t.getCode() == KeyCode.ESCAPE)
				this.stage.getOnCloseRequest().handle(new WindowEvent(this.stage, WindowEvent.WINDOW_CLOSE_REQUEST));
		});
    	
    }
    
    public NewPostDialog setStage(Stage stage) {
		this.stage = stage;
		return this;
	}
    
    public BorderPane getView() {
		return view;
	}
}
