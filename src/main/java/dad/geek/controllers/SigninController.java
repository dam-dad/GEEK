package dad.geek.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.kordamp.ikonli.javafx.FontIcon;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import dad.geek.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class SigninController implements Initializable {

	@FXML
    private Hyperlink hasAccountLink;

    @FXML
    private FontIcon mailIcon;

    @FXML
    private JFXPasswordField mailText;

    @FXML
    private FontIcon passwordImage;

    @FXML
    private JFXPasswordField passwordText;

    @FXML
    private JFXButton registerButton;

    @FXML
    private JFXCheckBox showPasswordCheck;

    @FXML
    private FontIcon usernameIcon;

    @FXML
    private JFXTextField usernameText;

    @FXML
    private ImageView welcomeImage;

    @FXML
    private Label welcomeLabel;
    
    @FXML
    private BorderPane view;

	public SigninController() {
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Signin.fxml"));
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
    void onLoginAction(ActionEvent event) {

    }

    @FXML
    void onHasAccountAction(ActionEvent event) {
    	App.primaryStage.setScene(new Scene(new LoginController().getView()));
    }

    @FXML
    void onShowPassword(ActionEvent event) {

    }
	
	public BorderPane getView() {
		return view;
	}

}
