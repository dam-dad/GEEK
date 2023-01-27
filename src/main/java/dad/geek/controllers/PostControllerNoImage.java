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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

public class PostControllerNoImage implements Initializable {

    @FXML
    private JFXTextArea contentTextArea;

    @FXML
    private FlowPane filterFlow;

    @FXML
    private ImageView profileImage;

    @FXML
    private Label usercodeLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private HBox view;

    public PostControllerNoImage() {
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PostViewNoImage.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	public HBox getView() {
		return view;
	}

	public JFXTextArea getContentTextArea() {
		return contentTextArea;
	}

	public void setContentTextArea(JFXTextArea contentTextArea) {
		this.contentTextArea = contentTextArea;
	}

	public FlowPane getFilterFlow() {
		return filterFlow;
	}

	public void setFilterFlow(FlowPane filterFlow) {
		this.filterFlow = filterFlow;
	}

	public ImageView getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(ImageView profileImage) {
		this.profileImage = profileImage;
	}

	public Label getUsercodeLabel() {
		return usercodeLabel;
	}

	public void setUsercodeLabel(Label usercodeLabel) {
		this.usercodeLabel = usercodeLabel;
	}

	public Label getUsernameLabel() {
		return usernameLabel;
	}

	public void setUsernameLabel(Label usernameLabel) {
		this.usernameLabel = usernameLabel;
	}

}
