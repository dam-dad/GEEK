package dad.geek.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.geek.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class NewPostDialog implements Initializable {
	
	// model
	
	private Stage stage;
	private Image newImage;
	private NewPostController newPostController;
	
	// view
	
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
    	
    	FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Abrir imagen");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "\\Pictures\\"));
		fileChooser.getExtensionFilters().addAll(
		     new FileChooser.ExtensionFilter("PNG Files", "*.png", "*.jpg")
		);
		
		File selectedFile = fileChooser.showOpenDialog(App.primaryStage);
		if(selectedFile != null) {
			newImage = new Image(selectedFile.toURI().toString());
			newPostController.setPosition(((Button) event.getSource()).getId(), newImage);
			this.stage.getOnCloseRequest().handle(new WindowEvent(this.stage, WindowEvent.WINDOW_CLOSE_REQUEST));
		}
    	
    	this.stage.getScene().setOnKeyPressed(t -> {
			if(t.getCode() == KeyCode.ESCAPE)
				this.stage.getOnCloseRequest().handle(new WindowEvent(this.stage, WindowEvent.WINDOW_CLOSE_REQUEST));
		});
    	
    }
    
    @FXML
    void onNoImageAction(ActionEvent event) {
    	this.stage.getOnCloseRequest().handle(new WindowEvent(this.stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    	newPostController.noImages();
    }
    
    public NewPostDialog setParent(NewPostController parent) {
    	this.newPostController = parent;
    	return this;
    }
    
    public NewPostDialog setStage(Stage stage) {
		this.stage = stage;
		return this;
	}
    
    public BorderPane getView() {
		return view;
	}
}
