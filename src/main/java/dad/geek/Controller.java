package dad.geek;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.kordamp.ikonli.javafx.FontIcon;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class Controller implements Initializable {

	@FXML
    private Hyperlink forgotLink;

    @FXML
    private JFXButton loginButton;

    @FXML
    private FontIcon passwordImage;

    @FXML
    private JFXPasswordField passwordText;

    @FXML
    private JFXCheckBox showPasswordCheck;

    @FXML
    private FontIcon usernameIcon;

    @FXML
    private JFXTextField usernameText;

    @FXML
    private BorderPane view;

    @FXML
    private ImageView welcomeImage;

    @FXML
    private Label welcomeLabel;
	
	public Controller() {
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
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
	void onForgotAction(ActionEvent event) {
		
	}
	
	@FXML
	void onLoginAction(ActionEvent event) {
		
	}
	
	@FXML
	void onShowPassword(ActionEvent event) {
		
	}
	
	public BorderPane getView() {
		return view;
	}

}
