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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

/**
 * Controlador del apartado de búsqueda de posts
 *
 */
public class SearchSectionController implements Initializable {
	
	//model
	private User user = new User();
	private Filter filter = new Filter();
	
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
    
    /**
	 * Constructor de la clase SearchSectionController, carga el fxml.
	 */
    public SearchSectionController() {
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SearchSection.fxml"));
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
		//bindings
		user.usernameProperty().bind(searchUserText.textProperty());
		try {
			searchFiltersComboBox.getItems().addAll(App.conexionDB.getAllFilters());
		} catch (Exception e) {
			e.printStackTrace();
		}
		searchButton.disableProperty().bind(Bindings.isEmpty(searchUserText.textProperty()).and(Bindings.isNull(searchFiltersComboBox.getSelectionModel().selectedItemProperty())));
	}
	

	/**
	 * Busca los posts que coincidan con los filtros seleccionados.
	 * @param event
	 * @throws Exception
	 */
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
    		filter = searchFiltersComboBox.getSelectionModel().getSelectedItem();
    		searchResultContainerPane.setContent(loadPostFilterNoUser());
    		filter = new Filter();
    	}
    	//si hay texto y también algún filtro seleccionado
    	if (!searchUserText.getText().isEmpty() && searchFiltersComboBox.getSelectionModel().getSelectedItem() != null) {
    		user.usernameProperty().bind(searchUserText.textProperty());
    		filter = searchFiltersComboBox.getSelectionModel().getSelectedItem();
    		searchResultContainerPane.setContent(loadPostFilterUser());
    		user.usernameProperty().unbind();
    		filter = new Filter();
    	}
    }
    
    /**
     * Carga los posts si no tienen ningún filtro seleccionado, pero sí un usuario.
     * @return El {@code VBox} con todos los posts cargados.
     * @throws Exception
     */
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

    /**
     * Carga los posts si hay un filtro seleccionado, pero no un usuario.
     * @return El {@code VBox} con todos los posts cargados.
     * @throws Exception
     */
    private VBox loadPostFilterNoUser() throws Exception {
		try {
			searchResultContainer.getChildren().clear();
			for(Post p : App.conexionDB.getAllPostsFromDB()) {
				if(p.getFilters().contains(filter)) {
					searchResultContainer.getChildren().add(new PostController(p).getView());
					searchResultContainer.getChildren().add(new SplitPane());
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
    
    /**
     * Carga los posts si hay tanto un filtro como un usuario seleccionados.
     * @return El {@code VBox} con todos los posts cargados.
     * @throws Exception
     */
    private VBox loadPostFilterUser() throws Exception {
		try {
			searchResultContainer.getChildren().clear();
			if(user.userInDatabase2()) {
				user = App.conexionDB.getUserObject(user.getUsername());
				for(Post p : App.conexionDB.getUserPosts(user)) {
					if(p.getFilters().contains(filter)) {
						searchResultContainer.getChildren().add(new PostController(p).getView());
						searchResultContainer.getChildren().add(new SplitPane());
					}
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
    
    /**
     * @return
     */
	public VBox getView() {
		return view;
	}
	
}