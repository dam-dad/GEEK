package dad.geek.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import dad.geek.App;
import dad.geek.model.Filter;
import dad.geek.model.Post;
import dad.geek.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

public class SearchSectionController implements Initializable {
	
	//model
	Filter filter1 = new Filter();
	Filter filter2 = new Filter();
	Filter filter3 = new Filter();
	Filter filter4 = new Filter();
	
	User user = new User();

	
	//view
    @FXML
    private ScrollPane searchResultContainerPane;
    @FXML
    private JFXButton searchButton;
    @FXML
    private JFXComboBox<Filter> searchFiltersComboBox;
    @FXML
    private JFXTextField searchUserText;
    @FXML
    private VBox searchResultContainer;
    @FXML
    private VBox view;
    
    public SearchSectionController() {
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SearchSection.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		user.usernameProperty().bind(searchUserText.textProperty());

		
		
		filter1.setFilterName("Ordenadores");
		filter2.setFilterName("Programación");
		filter3.setFilterName("Videojuegos");
		filter4.setFilterName("Diseño");
		
		searchFiltersComboBox.getItems().add(filter1);
		searchFiltersComboBox.getItems().add(filter2);
		searchFiltersComboBox.getItems().add(filter3);
		searchFiltersComboBox.getItems().add(filter4);


		
	}
	

    @FXML
    void onSearchAction(ActionEvent event) throws Exception {
//    	if (searchUserText.getText().isBlank() && searchFiltersComboBox.getSelectionModel().getSelectedItem().equals(null)) {
//    		searchButton.setDisable(true);
//    	}
    	
//    	if (!searchUserText.getText().isBlank() && searchFiltersComboBox.getSelectionModel().getSelectedItem().equals(null)) {
//    		searchButton.setDisable(false);
    		loadPostNoFilter();
//    	}
    	
//    	if (searchUserText.getText().isBlank() && !searchFiltersComboBox.getSelectionModel().getSelectedItem().equals(null)) {
//    		searchButton.setDisable(false);
//    	}
    }
    
    private VBox loadPostNoFilter() throws Exception {
		try {
			if(user.userInDatabase()) {
				user = App.conexionLocal.getUserObject(user.getUsername());
				System.out.println(user.getUsername());
				for(Post p : App.conexionLocal.getUserPosts(user)) {
					PostController controller = new PostController(p);
					controller.getUserButton().setMouseTransparent(true);
					searchResultContainer.getChildren().add(controller.getView());
					searchResultContainer.getChildren().add(new SplitPane());
					Alert errorAlert = new Alert(AlertType.ERROR);
					errorAlert.setTitle("ERROR");
					errorAlert.setHeaderText("Hubo un error");
					errorAlert.setContentText(user.getUsername());
					errorAlert.initOwner(App.primaryStage);
					errorAlert.initModality(Modality.APPLICATION_MODAL);
					errorAlert.show();
				}
			}
			return searchResultContainer;
		} catch (Exception e) {
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setTitle("ERROR");
			errorAlert.setHeaderText("Hubo un error");
			errorAlert.setContentText(e.getMessage());
			errorAlert.initOwner(App.primaryStage);
			errorAlert.initModality(Modality.APPLICATION_MODAL);
			errorAlert.show();
			return null;
		}
    }
    
	public VBox getView() {
		return view;
	}

	public ScrollPane getsearchResultContainerPane() {
		return searchResultContainerPane;
	}

	public void setsearchResultContainerPane(ScrollPane searchResultContainerPane) {
		this.searchResultContainerPane = searchResultContainerPane;
	}

	public JFXButton getSearchButton() {
		return searchButton;
	}

	public void setSearchButton(JFXButton searchButton) {
		this.searchButton = searchButton;
	}

	public JFXComboBox<Filter> getSearchFiltersComboBox() {
		return searchFiltersComboBox;
	}

	public void setSearchFiltersComboBox(JFXComboBox<Filter> searchFiltersComboBox) {
		this.searchFiltersComboBox = searchFiltersComboBox;
	}

	public JFXTextField getSearchUserText() {
		return searchUserText;
	}

	public void setSearchUserText(JFXTextField searchUserText) {
		this.searchUserText = searchUserText;
	}
	
	
    

}
