package dad.geek.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.ToggleSwitch;
import org.kordamp.ikonli.javafx.FontIcon;

import dad.geek.App;
import dad.geek.model.Post;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainController implements Initializable {
	
	// controllers

	private UserSectionController userSectionController = new UserSectionController();
	private SearchSectionController searchSectionController = new SearchSectionController();
	
	// view
	
	@FXML
    private VBox postsContainer;
	
	@FXML
    private SplitPane containerPane;

    @FXML
    private FontIcon darkModeIcon;

    @FXML
    private MenuItem darkModeItem;

    @FXML
    private ToggleSwitch darkModeSwitch;

    @FXML
    private MenuItem exitItem;
    
    @FXML
    private MenuItem editUserItem;

    @FXML
    private MenuItem informeItem;

    @FXML
    private MenuItem newPostItem;

    @FXML
    private ScrollPane postContainerPane;

    @FXML
    private VBox userContainer;

    @FXML
    private BorderPane view;

	public MainController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// load data
		
		userContainer.getChildren().add(userSectionController.getView());
		containerPane.getItems().add(searchSectionController.getView());
		containerPane.setDividerPositions(0.25, 0.75);
		
		postContainerPane.setContent(laodPosts());
		
		// listeners
		
		darkModeSwitch.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				darkModeIcon.setIconLiteral("mdi2m-moon-waning-crescent");
			} else {
				darkModeIcon.setIconLiteral("mdi2w-white-balance-sunny");
			}
		});
		
		App.primaryStage.maximizedProperty().addListener((o,ov,nv) -> {
			if(App.primaryStage.isMaximized())
				containerPane.setDividerPositions(0.2, 0.80);
			else {
				containerPane.setDividerPositions(0.25, 0.75);
			}
		});

	}
	
	private VBox laodPosts() {
		postsContainer.getChildren().clear();
		for(Post p : App.conexionLocal.getAllPosts()) {
			postsContainer.getChildren().add(new PostController(p).setMainController(this).getView());
			postsContainer.getChildren().add(new SplitPane());
		}
		return postsContainer;
	}

	@FXML
	void onCreatePostAction(ActionEvent event) {
		Stage window = new Stage();
		window.setTitle("Nuevo Post");
		window.setScene(new Scene(new NewPostDialog().setStage(window).getView()));
		window.setMinHeight(300);
		window.setMinWidth(300);
		window.initOwner(App.primaryStage);
		window.initModality(Modality.APPLICATION_MODAL);
		
		window.getScene().setOnKeyPressed(t -> {
			if(t.getCode() == KeyCode.ESCAPE)
				window.getOnCloseRequest().handle(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
		});
		window.setOnCloseRequest(e -> {
			reloadPosts();
			window.close();
		});
		
		window.show();
	}
	
	@FXML
	void onEditUserAction(ActionEvent event) {
		
		Stage window = new Stage();
		window.setTitle("Editar usuario");
		window.setScene(new Scene(new EditProfileController().setStage(window).getView()));
		window.setMinWidth(300);
		window.setMinHeight(435);
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
	
	private void reloadPosts() {
		App.primaryStage.getScene().setCursor(Cursor.WAIT);
		postContainerPane.setContent(laodPosts());
		App.primaryStage.getScene().setCursor(Cursor.DEFAULT);
	}
	
	@FXML
	void onReloadPostAction(ActionEvent event) {
		reloadPosts();
	}

	@FXML
	void onDarkModeAction(ActionEvent event) {
		if (darkModeSwitch.isSelected())
			darkModeSwitch.setSelected(false);
		else
			darkModeSwitch.setSelected(true);
	}

	@FXML
	void onExitAction(ActionEvent event) {
		App.salir();
	}

	@FXML
	void onGenerateInformeAction(ActionEvent event) {

	}
	
	public BorderPane getView() {
		return view;
	}
	
	public UserSectionController getUserSectionController() {
		return userSectionController;
	}

}
