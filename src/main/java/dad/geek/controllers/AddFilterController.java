package dad.geek.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;

import dad.geek.App;
import dad.geek.model.Filter;
import dad.geek.model.Post;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Controlador de la ventana Añadir filtros
 *
 */
public class AddFilterController implements Initializable {

	//model
	
	private Post post;
	private Stage stage;
	private static IntegerProperty selectedFilter = new SimpleIntegerProperty();

	//view
    @FXML
    private JFXButton closeButton;

    @FXML
    private JFXListView<Filter> filterList;

    @FXML
    private VBox view;
    
    /**
	 * Constructor de la clase AddFilterController, carga el fxml.
	 */
    public AddFilterController(Post post) {
    	
    	this.post = post;
    	
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddFilterView.fxml"));
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
		//listeners
    	selectedFilter.bind(filterList.getSelectionModel().selectedIndexProperty());		
		try {
			filterList.getItems().addAll(App.conexionDB.getAllFilters());
			filterList.getSelectionModel().select(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Recibe el stage y lo guarda para manipularlo.
	 * @param stage
	 * @return
	 */
	public AddFilterController setStage(Stage stage) {
		this.stage = stage;
		return this;
	}

	/**
	 * Se ejecuta cada vez que se le de al botón "Aceptar".<br/>
	 * Se encarga de pedir el cierre de la ventana.
	 * @param event
	 * @throws Exception 
	 */
    @FXML
    void onAceptarButtonAction(ActionEvent event) throws Exception {
    	Filter filter = new Filter();
		filter = App.conexionDB.getAllFilters().get(selectedFilter.get());
		
		if(!post.filtersProperty().contains(filter))
			post.filtersProperty().add(filter);
		
		this.stage.getOnCloseRequest().handle(new WindowEvent(this.stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }
    
    /**
     * @return
     */
    public VBox getView() {
    	return view;
    }

}
