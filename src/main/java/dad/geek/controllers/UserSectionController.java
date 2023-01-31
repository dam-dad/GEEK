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

public class UserSectionController implements Initializable {

	// view
	
	@FXML
    private JFXButton editButton;

    @FXML
    private Label filterNumberLabel;

    @FXML
    private ImageView profileImage;

    @FXML
    private JFXButton showMoreButton;

    @FXML
    private Label usercodeLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private VBox view;

	
	public UserSectionController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/UserSectionView.fxml"));
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
    void onBackAction(ActionEvent event) {

    }
	
	@FXML
    void onEditAction(ActionEvent event) {

    }

    @FXML
    void onShowMoreAction(ActionEvent event) {

    }
	
	public VBox getView() {
		return view;
	}
    
}
