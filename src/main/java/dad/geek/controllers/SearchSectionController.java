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
import javafx.beans.binding.Bindings;
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
		//bindings
		user.usernameProperty().bind(searchUserText.textProperty());
		
		searchButton.disableProperty().bind(Bindings.isEmpty(searchUserText.textProperty()).and(Bindings.isNull(searchFiltersComboBox.getSelectionModel().selectedItemProperty())));
	}
	

    @FXML
    void onSearchAction(ActionEvent event) throws Exception {
    	//si hay texto pero no hay ningún filtro seleccionado
    	if (!searchUserText.getText().isEmpty() && searchFiltersComboBox.getSelectionModel().getSelectedItem() == null) {
    		user.usernameProperty().bind(searchUserText.textProperty());
    		searchResultContainerPane.setContent(loadPostNoFilter());
    		user.usernameProperty().unbind();
    	}
    	//si no hay texto pero sí hay un filtro seleccionado
    	if (searchUserText.getText().isEmpty() && searchFiltersComboBox.getSelectionModel().getSelectedItem() != null) {
    		
    	}
    	//si hay texto y también algún filtro seleccionado
    	if (!searchUserText.getText().isEmpty() && searchFiltersComboBox.getSelectionModel().getSelectedItem() != null) {

    	}
    }
    
    private VBox loadPostNoFilter() throws Exception {
		try {
			searchResultContainer.getChildren().clear();
			if(user.userInDatabase2()) {
				user = App.conexionDB.getUserObject(user.getUsername());
				for(Post p : App.conexionDB.getUserPosts(user)) {
					searchResultContainer.getChildren().add(new PostController(p).getView());
					searchResultContainer.getChildren().add(new SplitPane());
				}
			}
			else {
				Alert sinUsuarioAlert = new Alert(AlertType.ERROR);
				sinUsuarioAlert.setTitle("NO USERS");
				sinUsuarioAlert.setHeaderText("No hay usuarios");
				sinUsuarioAlert.setContentText("No se ha encontrado el usuario con el nombre: " + searchUserText.getText());
				sinUsuarioAlert.initOwner(App.primaryStage);
				sinUsuarioAlert.initModality(Modality.APPLICATION_MODAL);
				sinUsuarioAlert.show();
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