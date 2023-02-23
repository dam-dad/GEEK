package dad.geek.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import dad.geek.App;
import dad.geek.model.Post;
import dad.geek.model.User;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;

/**
 * Controlador de los posts.
 *
 */
public class PostController implements Initializable {

	// model

	private Post post;
	private User user;
	private MainController main;

	// view

	@FXML
	private Label nicknameLabel;

	@FXML
	private Label usernameLabel;

	@FXML
	private Label contentLabel;

	@FXML
	private FlowPane filterFlow;

	@FXML
	private FlowPane imageFlow;

	@FXML
	private JFXButton userButton;

	@FXML
	private ImageView profileImage;
	
	@FXML
    private StackPane contentPane;

	@FXML
	private BorderPane view;

	/**
	 * Constructor de la clase PostController, carga el fxml.
	 */
	public PostController(Post post) {
		this.post = post;

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PostView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// load data

		try {
			user = App.conexionDB.getUserObject(post.getUserID());
		} catch (Exception e) {
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setTitle("ERROR");
			errorAlert.setHeaderText("Hubo un error");
			errorAlert.setContentText(e.getMessage());
			errorAlert.initOwner(App.primaryStage);
			errorAlert.initModality(Modality.APPLICATION_MODAL);
			errorAlert.show();
		}

		userButton.setMouseTransparent(true);

		// bindings

		profileImage.imageProperty().bind(user.profileImageProperty());
		contentLabel.textProperty().bind(post.postContentProperty());
		nicknameLabel.textProperty().bind(user.nicknameProperty());
		usernameLabel.textProperty().bind(Bindings.concat("@").concat(user.usernameProperty()));
		
		// listeners 
		
		contentLabel.setOnMouseClicked(this::onMouseClicked);

	}

	/**
	 * Encargado de detectar si se hace doble click en un {@code Label}, si es el caso cambiamos a {@code TextArea}
	 * @param mouseEvent
	 */
	private void onMouseClicked(MouseEvent mouseEvent) {
		
		if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
            if(mouseEvent.getClickCount() == 2){
            	contentLabel.setVisible(false);
                TextArea textarea = new TextArea(contentLabel.getText());
                textarea.setPrefHeight(contentLabel.getHeight() + 10);
                textarea.setEditable(false);
                contentPane.getChildren().add(textarea);

                textarea.setOnKeyPressed(event ->{
                    if(event.getCode().toString().equals("ENTER")) {
                    	contentPane.getChildren().remove(textarea);
                    	contentLabel.setVisible(true);
                    }
                });
                
                textarea.focusedProperty().addListener((o,ov,nv) -> {
                	if(nv != null && nv == false) {
                		contentPane.getChildren().remove(textarea);
                    	contentLabel.setVisible(true);
                	}
                });
            }
        }
		
	}

	/**
	 * Se ejecuta cada vez que se presione la imagen.<br/>
	 * Llama a la función {@link PostController#openUser()}.
	 * @param event
	 */
	@FXML
	void onOpenUserAction(ActionEvent event) {
		openUser();
	}
	
	/**
	 * Se ejecuta cada vez que se presione el área cercana al nickname o el username.<br/>
	 * Llama a la función {@link PostController#openUser()}.
	 * @param event
	 */
	@FXML
    void onOpenUserCkick(MouseEvent event) {
		openUser();
    }
	
	/**
	 * Llama a la función {@link UserSectionController#changeUser(User)}.
	 */
	private void openUser() {
		main.getUserSectionController().changeUser(user);
	}

	/**
	 * Recibe el controlador padre {@link MainController}.
	 * @param parent
	 * @return A sí mismo: {@link PostController}.
	 */
	public PostController setMainController(MainController parent) {
		this.main = parent;
		userButton.setMouseTransparent(false);
		return this;
	}

	public BorderPane getView() {
		return view;
	}

}
