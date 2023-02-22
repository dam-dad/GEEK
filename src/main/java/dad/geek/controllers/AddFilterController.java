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
    
    public AddFilterController() {
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddFilterView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//listeners
    	selectedFilter.bind(filterList.getSelectionModel().selectedIndexProperty());		
		try {
			filterList.getItems().addAll(App.conexionDB.getAllFilters());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public VBox getView() {
		return view;
	}
	
	public static String getSelectedFilterName() throws Exception {
		Filter filter = new Filter();
		filter = App.conexionDB.getAllFilters().get(selectedFilter.get());
		return filter.getFilterName();	
	}
	
	public AddFilterController setStage(Stage stage) {
		this.stage = stage;
		return this;
	}

    @FXML
    void onCloseButtonAction(ActionEvent event) {
		this.stage.getOnCloseRequest().handle(new WindowEvent(this.stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }



}
