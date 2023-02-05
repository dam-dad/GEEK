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

public class EditProfileController implements Initializable {
	
	// model
	
	private Image newImage;
	
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
		profileImage.imageProperty().bind(App.user.profileImageProperty());
		
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
		}
		
	}

    @FXML
    void onAcceptAction(ActionEvent event) {

    }

    @FXML
    void onCancelAction(ActionEvent event) {

    }

    @FXML
    void onEditNicknameAction(ActionEvent event) {

    }

    @FXML
    void onEditUsernameAction(ActionEvent event) {

    }
	
	public BorderPane getView() {
		return view;
	}
	
}
