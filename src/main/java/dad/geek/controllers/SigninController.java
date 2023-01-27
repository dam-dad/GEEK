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
import dad.geek.model.User;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class SigninController implements Initializable {

	// model

	private User user = new User();
	
	// view
	
	private Label noUserFound;
	
	@FXML
	private ImageView welcomeImage;
	@FXML
	private Label welcomeLabel;

	@FXML
	private FontIcon usernameIcon;
	@FXML
	private JFXTextField usernameText;

	@FXML
	private FontIcon mailIcon;
	@FXML
	private JFXTextField mailText;

	@FXML
	private FontIcon passwordImage;
	@FXML
	private JFXTextField passwordText;
	@FXML
	private JFXPasswordField passwordPassText;

	@FXML
	private JFXCheckBox showPasswordCheck;
	
	@FXML
	private Hyperlink hasAccountLink;
	
	@FXML
	private JFXButton registerButton;

	@FXML
	private BorderPane view;

	public SigninController() {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SigninView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// bindings
		
		user.usernameProperty().bind(usernameText.textProperty());
		user.mailProperty().bind(mailText.textProperty());

		user.passwordProperty().bindBidirectional(passwordText.textProperty());
		user.passwordProperty().bindBidirectional(passwordPassText.textProperty());

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
	
	private void hideLabel() {
		
		if(noUserFound != null) {
			noUserFound.setVisible(false);
			noUserFound.setManaged(false);
		}
		
	}
	
	@FXML
    void onTextClicked(MouseEvent event) {
		hideLabel();
    }

	@FXML
	void onSigninAction(ActionEvent event) {
		//TODO validar informacion
//		if(user.userInDatabase()) {
//			noUserFound = new Label("Éste usuario ya está registrado, inténtelo de nuevo.");
//			noUserFound.setStyle("-fx-text-fill: red;");
//			noUserFound.setPadding(new Insets(0, 0, 10, 0));
//			((VBox) getView().getChildren().get(0)).getChildren().add(5, noUserFound);
//		} else {
//			App.primaryStage.setScene(new Scene(new MainController().getView()));
//		}
		
		App.primaryStage.setScene(new Scene(new MainController().getView()));
		App.primaryStage.centerOnScreen();
		
	}

	@FXML
	void onHasAccountAction(ActionEvent event) {
		App.primaryStage.setScene(new Scene(new LoginController().getView()));
	}

	public BorderPane getView() {
		return view;
	}

}
