package dad.geek.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class EditProfileController implements Initializable {
	
	@FXML
    private JFXButton editImageButton;

    @FXML
    private JFXButton editNameButton;

    @FXML
    private JFXButton editUsernameButton;

    @FXML
    private Label nameLabel;

    @FXML
    private ImageView profileImage;

    @FXML
    private Label usernameLabel;

    @FXML
    private VBox view;
	
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
		
		
		
	}
	
	@FXML
	void onEditImageAction(ActionEvent event) {
		
	}
	
	@FXML
	void onEditNameAction(ActionEvent event) {
		
	}
	
	@FXML
	void onEditUsernameAction(ActionEvent event) {
		
	}

	public VBox getView() {
		return view;
	}
	
}
