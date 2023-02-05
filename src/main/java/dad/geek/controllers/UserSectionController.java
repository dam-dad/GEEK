package dad.geek.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import dad.geek.App;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class UserSectionController implements Initializable {
	
	// model
	
	private BooleanProperty goback = new SimpleBooleanProperty(false);

	// view
	
	@FXML
    private JFXButton backButton;
	
	@FXML
    private JFXButton editButton;

    @FXML
    private Label filterNumberLabel;

    @FXML
    private ImageView profileImage;

    @FXML
    private JFXButton showMoreButton;

    @FXML
    private Label nameLabel;

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
		
		// bindings
		
		nameLabel.textProperty().bind(App.user.nicknameProperty());
		usernameLabel.textProperty().bind(Bindings.concat("@").concat(App.user.usernameProperty()));
		profileImage.imageProperty().bind(App.user.profileImageProperty());
		
		backButton.visibleProperty().bind(goback);
		
		
	}
	
	@FXML
    void onBackAction(ActionEvent event) {

    }
	
	@FXML
    void onEditAction(ActionEvent event) {
		
		Stage window = new Stage();
		window.setTitle("Nuevo Post");
		window.setScene(new Scene(new EditProfileController().setStage(window).getView()));
		window.setMinWidth(300);
		window.setMinHeight(400);
		window.initOwner(App.primaryStage);
		window.initModality(Modality.APPLICATION_MODAL);
		
		window.getScene().setOnKeyPressed(t -> {
			if(t.getCode() == KeyCode.ESCAPE)
				window.getOnCloseRequest().handle(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
		});
		window.setOnCloseRequest(e -> {
			window.close();
		});
		
		window.show();
		
    }

    @FXML
    void onShowMoreAction(ActionEvent event) {

    }
	
	public VBox getView() {
		return view;
	}
    
}
