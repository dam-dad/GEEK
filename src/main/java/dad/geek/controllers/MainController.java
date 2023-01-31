package dad.geek.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.controlsfx.control.ToggleSwitch;
import org.kordamp.ikonli.javafx.FontIcon;

import dad.geek.App;
import dad.geek.utils.Utils;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MainController implements Initializable {
	
	// controllers

	private UserSectionController userSectionController = new UserSectionController();
	private SearchSectionController searchSectionController = new SearchSectionController();
	
	// view
	
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
		
		// TODO provisional
		VBox vbox = new VBox();
		vbox.getChildren().addAll(new PostController_Karim().getView(), new PostController_Karim().getView(), new PostController_Karim().getView());
		postContainerPane.setContent(vbox);
		
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

	public BorderPane getView() {
		return view;
	}

	@FXML
	void onCreatePostAction(ActionEvent event) {
		
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

}
