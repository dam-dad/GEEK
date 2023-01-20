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
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class LoginController implements Initializable {
	
	// model
	
	private StringProperty password = new SimpleStringProperty();
	
	// view

	@FXML
    private Hyperlink noAccountLink;

    @FXML
    private JFXButton loginButton;

    @FXML
    private FontIcon passwordImage;

    @FXML
    private JFXTextField passwordText;

    @FXML
    private JFXPasswordField passwordPassText;

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
	
	public LoginController() {
		
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

		// bindings
		
		password.bindBidirectional(passwordText.textProperty());
		password.bindBidirectional(passwordPassText.textProperty());
		
		passwordText.managedProperty().bind(showPasswordCheck.selectedProperty());
		passwordText.visibleProperty().bind(showPasswordCheck.selectedProperty());
		
		passwordPassText.managedProperty().bind(showPasswordCheck.selectedProperty().not());
		passwordPassText.visibleProperty().bind(showPasswordCheck.selectedProperty().not());
		
		// listeners
		
		showPasswordCheck.selectedProperty().addListener(this::onShowPasswordChanged);
		
	}
	
	private void onShowPasswordChanged(ObservableValue<? extends Boolean> o, Boolean ov, Boolean nv) {
		
		if(nv) {
			passwordText.setLabelFloat(true);
		}
		
	}

	@FXML
	void onNoAccountAction(ActionEvent event) {
		App.primaryStage.setScene(new Scene(new SigninController().getView()));
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
