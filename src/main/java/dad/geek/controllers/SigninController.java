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
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

/**
 * Controlador de la ventana de signin.
 *
 */
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
	private FontIcon nicknameIcon;
	@FXML
	private JFXTextField nicknameText;

	@FXML
	private FontIcon usernameIcon;
	@FXML
	private JFXTextField usernameText;

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
	
	/**
	 * Constructor de la clase SigninController, carga el fxml.
	 */
	public SigninController() {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SigninView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// bindings

		user.usernameProperty().bind(usernameText.textProperty());
		user.nicknameProperty().bind(nicknameText.textProperty());
//		user.mailProperty().bind(mailText.textProperty());

		user.passwordProperty().bindBidirectional(passwordText.textProperty());
		user.passwordProperty().bindBidirectional(passwordPassText.textProperty());

		passwordText.managedProperty().bind(showPasswordCheck.selectedProperty());
		passwordText.visibleProperty().bind(showPasswordCheck.selectedProperty());

		passwordPassText.managedProperty().bind(showPasswordCheck.selectedProperty().not());
		passwordPassText.visibleProperty().bind(showPasswordCheck.selectedProperty().not());

		// listeners

		showPasswordCheck.selectedProperty().addListener(this::onShowPasswordChanged);

	}

	/**
	 * Se ejecuta cada vez que cambie el estado de la propiedad selected del componente {@code JFXCheckBox} si esta seleccionado modifica la propiedad float del {@code Label} passwordText.
	 * @param o
	 * @param ov
	 * @param nv
	 */
	private void onShowPasswordChanged(ObservableValue<? extends Boolean> o, Boolean ov, Boolean nv) {

		if (nv) {
			passwordText.setLabelFloat(true);
		}

	}

	/**
	 * Esconde el label "Este usuario ya est?? registrado, int??ntelo de nuevo.".
	 */
	private void hideLabel() {

		if (noUserFound != null) {
			noUserFound.setVisible(false);
			noUserFound.setManaged(false);
		}

	}
	
	/**
	 * Muestra el label "Este usuario ya est?? registrado, int??ntelo de nuevo.".
	 */
	private void showLabel() {
		noUserFound = new Label("Este usuario ya est?? registrado, int??ntelo de nuevo.");
		noUserFound.setStyle("-fx-text-fill: red;");
		noUserFound.setPadding(new Insets(0, 0, 10, 0));
		((VBox) getView().getChildren().get(0)).getChildren().add(5, noUserFound);
	}

	/**
	 * Se ejecuta cada vez que haces click en cualquier TextField, llama al m??todo {@link LoginController #hideLabel()}.
	 * @param event
	 */
	@FXML
	void onTextClicked(MouseEvent event) {
		hideLabel();
	}

	/**
	 * Se ejecuta cada vez que hace click en el {@code Hyperlink} "??Ya tiene una cuenta?, acceda aqu??.".
	 * @param event
	 */
	@FXML
	void onHasAccountAction(ActionEvent event) {
		App.openScene(new LoginController().getView(), 450, 500);
	}

	/**
	 * Se ejecuta cada vez que le das al {@code JFXButton}, coteja con la base de datos si el usuario introducido existe en la base de datos, 
	 * si es el caso ejecuta el m??todo {@link SigninController#showLabel()}. Si no es el caso ejecuta el m??todo {@link App #openScene(javafx.scene.Parent, double, double)}.
	 * @param event
	 */
	@FXML
	void onSigninAction(ActionEvent event) {
		try {
			if (user.userInDatabase()) {
				showLabel();
			} else {
				user.addUsertoDB();
				App.user = App.conexionDB.getUserObject(this.user.getUsername(), this.user.getPassword());
				App.openScene(new MainController().getView(), 850, 550);
			}
		} catch (Exception e) {
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setTitle("ERROR");
			errorAlert.setHeaderText("Hubo un error");
			errorAlert.setContentText(e.getMessage());
			errorAlert.initOwner(App.primaryStage);
			errorAlert.initModality(Modality.APPLICATION_MODAL);
			errorAlert.show();
		}
	}

	/**
	 * @return
	 */
	public BorderPane getView() {
		return view;
	}

}