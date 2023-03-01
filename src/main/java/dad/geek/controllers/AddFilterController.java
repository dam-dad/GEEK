package dad.geek.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;

import dad.geek.App;
import dad.geek.model.Filter;
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
    public AddFilterController() {
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
	 * @return El filtro seleccionado.
	 * @throws Exception
	 */
	public static Filter getSelectedFilter() throws Exception {
		Filter filter = new Filter();
		filter = App.conexionDB.getAllFilters().get(selectedFilter.get());
		return filter;
	}
	
	/**
	 * @return El ID del filtro seleccionado.
	 * @throws Exception
	 */
	public static long getSelectedFilterID() throws Exception {
		Filter filter = new Filter();
		filter = App.conexionDB.getAllFilters().get(selectedFilter.get());
		return filter.getFilterID();	
	}
	
	/**
	 * @return El nombre del filtro seleccionado.
	 * @throws Exception
	 */
	public static String getSelectedFilterName() throws Exception {
		Filter filter = new Filter();
		filter = App.conexionDB.getAllFilters().get(selectedFilter.get());
		return filter.getFilterName();	
	}
	
	/**
	 * @return El nombre corto del filtro seleccionado.
	 * @throws Exception
	 */
	public static String getSelectedFilterShortName() throws Exception {
		Filter filter = new Filter();
		filter = App.conexionDB.getAllFilters().get(selectedFilter.get());
		return filter.getFilterShortName();	
	}
	
	/**
	 * @return La descripción del filtro seleccionado.
	 * @throws Exception
	 */
	public static String getSelectedFilterDescription() throws Exception {
		Filter filter = new Filter();
		filter = App.conexionDB.getAllFilters().get(selectedFilter.get());
		return filter.getFilterDescription();	
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
	 */
    @FXML
    void onCloseButtonAction(ActionEvent event) {
		this.stage.getOnCloseRequest().handle(new WindowEvent(this.stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }
    
    /**
     * @return
     */
    public VBox getView() {
    	return view;
    }

}
