package dad.geek.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import dad.geek.App;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class EditProfileController implements Initializable {
	
	// model
	
	private Image newImage;
	private Stage stage;
	// view
	
	@FXML
    private JFXButton editImageButton;

    @FXML
    private JFXButton editNicknameButton;

    @FXML
    private JFXButton editUsernameButton;

    @FXML
    private Label nicknameLabel;

    @FXML
    private ImageView profileImage;

    @FXML
    private Label usernameLabel;

    @FXML
    private BorderPane view;
	
	public EditProfileController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EditProfileView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// bindings
		
		nicknameLabel.textProperty().bind(App.user.nicknameProperty());
		usernameLabel.textProperty().bind(Bindings.concat("@").concat(App.user.usernameProperty()));
		profileImage.setImage(App.user.getProfileImage());
		
	}
	
	@FXML
	void onEditImageAction(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Abrir imagen");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "\\Pictures\\"));
		fileChooser.getExtensionFilters().addAll(
		     new FileChooser.ExtensionFilter("PNG Files", "*.png", "*.jpg")
		);
		File selectedFile = fileChooser.showOpenDialog(App.primaryStage);
		if(selectedFile != null) {
			newImage = new Image(selectedFile.toURI().toString());
			profileImage.setImage(newImage);
		}
		
	}

    @FXML
    void onAcceptAction(ActionEvent event) {
    	if(newImage != null)
    		App.user.setProfileImage(newImage);
    	stage.getOnCloseRequest().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    @FXML
    void onCancelAction(ActionEvent event) {
    	stage.getOnCloseRequest().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    @FXML
    void onEditNicknameAction(ActionEvent event) {

    }

    @FXML
    void onEditUsernameAction(ActionEvent event) {

    }
    
    public EditProfileController setStage(Stage stage) {
    	this.stage = stage;
    	return this;
    }
	
	public BorderPane getView() {
		return view;
	}
	
}
