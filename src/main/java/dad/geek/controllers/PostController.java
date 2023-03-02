package dad.geek.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import dad.geek.App;
import dad.geek.model.Filter;
import dad.geek.model.Post;
import dad.geek.model.User;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * Controlador de los posts.
 *
 */
public class PostController implements Initializable {

	// model

	private Post post;
	private User user;
	private MainController main;
	private ImageView postImageView;

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
    private VBox nameContainer;
	
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

	/**
	 * 
	 */
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
		
		if(post.getPostImage() != null) {
			postImageView = new ImageView(post.getPostImage());
			postImageView.setFitWidth(150);
			postImageView.setFitHeight(150);
			postImageView.setVisible(true);
			
			postImageView.setOnMouseClicked(this::onImageClicked);
			
			view.setBottom(postImageView);
			BorderPane.setAlignment(postImageView, Pos.CENTER);
		}

		if(post.getFilters().size() > 0) {
			
			for(Filter f : post.getFilters())
				filterFlow.getChildren().add(new Label("#" + f.getFilterShortName()));
			
		}
		
		// bindings

		profileImage.imageProperty().bind(user.profileImageProperty());
		contentLabel.textProperty().bind(post.postContentProperty());
		nicknameLabel.textProperty().bind(user.nicknameProperty());
		usernameLabel.textProperty().bind(Bindings.concat("@").concat(user.usernameProperty()));
		
		
		// listeners 
		
		contentLabel.setOnMouseClicked(this::onMouseClicked);
		nameContainer.setOnMouseClicked(null);

	}

	/**
	 * Se ejecuta cada vez que se presione la imagen del post si tiene.
	 * Abre una nueva ventana {@code UNDECORATED} y le damos su controlador ({@link ShowImageController}).
	 * @param event
	 */
	private void onImageClicked(MouseEvent event) {
		
		Stage window = new Stage();
		window.initStyle(StageStyle.UNDECORATED);
		window.setTitle("Imagen del post");
		window.setScene(new Scene(new ShowImageController().setImageView(post.getPostImage()).setStage(window).getView()));

		window.initOwner(App.primaryStage);
		window.initModality(Modality.APPLICATION_MODAL);
		
		window.getScene().setOnKeyPressed(t -> {
			if (t.getCode() == KeyCode.ESCAPE)
				window.getOnCloseRequest().handle(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
		});
		window.setOnCloseRequest(e -> {
			window.close();
		});

		window.show();
		
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
	 * Se ejecuta cada vez que se presione la imagen mientras no esté ni en {@link UserSectionController} o en {@link SearchSectionController}.<br/>
	 * Llama a la función {@link PostController#openUser()}.
	 * @param event
	 */
	@FXML
	void onOpenUserAction(ActionEvent event) {
		openUser();
	}
	
	/**
	 * Se ejecuta cada vez que se presione el área cercana al nickname o el username mientras no esté ni en {@link UserSectionController} o en {@link SearchSectionController}.<br/>
	 * Llama a la función {@link PostController#openUser()}.
	 * @param event
	 */
    void onOpenUserClick(MouseEvent event) {
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
		nameContainer.setOnMouseClicked(this::onOpenUserClick);
		return this;
	}

	/**
	 * @return
	 */
	public BorderPane getView() {
		return view;
	}

}
