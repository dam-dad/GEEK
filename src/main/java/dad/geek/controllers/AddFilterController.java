package dad.geek.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;

import dad.geek.model.Filter;
import dad.geek.model.Post;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class AddFilterController implements Initializable {

	
	private Stage stage;
	private Post post = new Post();
	
	
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
		filterList.itemsProperty().bind(post.filtersProperty());
		
		//filtros
		Filter programacion = new Filter();
		programacion.setFilterName("Programación");		
		Filter videojuegos = new Filter();
		programacion.setFilterName("Videojuegos");		
		Filter musica = new Filter();
		programacion.setFilterName("Música");		
		Filter edicion = new Filter();
		programacion.setFilterName("Edición");		
		Filter hardware = new Filter();
		programacion.setFilterName("Hardware");		
		Filter java = new Filter();
		programacion.setFilterName("Java");		
		Filter css = new Filter();
		programacion.setFilterName("CSS");		
		Filter javascript = new Filter();
		programacion.setFilterName("Javascript");		
		Filter c = new Filter();
		programacion.setFilterName("C");		
		Filter cplusplus = new Filter();
		programacion.setFilterName("C++");		
		Filter python = new Filter();
		programacion.setFilterName("Python");		
		Filter html = new Filter();
		programacion.setFilterName("HTML");		
		Filter sistoperativos = new Filter();
		programacion.setFilterName("Sistemas Operativos");		
		Filter windows = new Filter();
		programacion.setFilterName("Windows");		
		Filter linux = new Filter();
		programacion.setFilterName("Linux");		
		Filter macos = new Filter();
		programacion.setFilterName("MacOS");		
		Filter android = new Filter();
		programacion.setFilterName("Android");		
		Filter seriestv = new Filter();
		programacion.setFilterName("Series de televisión");	
		Filter peliculas = new Filter();
		programacion.setFilterName("Películas");		
		Filter consolas = new Filter();
		programacion.setFilterName("Consolas");
		Filter telmoviles = new Filter();
		programacion.setFilterName("Teléfonos Móviles");		
		Filter tablets = new Filter();
		programacion.setFilterName("Tablets");		
		Filter tv = new Filter();
		programacion.setFilterName("Televisiones");		
		Filter monitores = new Filter();
		programacion.setFilterName("Monitores");		
		Filter ratones = new Filter();
		programacion.setFilterName("Ratones");	
		Filter teclados = new Filter();
		programacion.setFilterName("Teclados");	
		Filter playstation = new Filter();
		programacion.setFilterName("PlayStation");		
		Filter xbox = new Filter();
		programacion.setFilterName("Xbox");		
		Filter ios = new Filter();
		programacion.setFilterName("iOS");		
		Filter nintendo = new Filter();
		programacion.setFilterName("Nintendo");

		post.filtersProperty().addAll(programacion, videojuegos, musica, edicion, hardware, java, css, javascript, c, cplusplus, python, html, sistoperativos, windows, linux, macos, 
									android, seriestv, peliculas, consolas, telmoviles, tablets, tv, monitores, ratones, teclados, nintendo, playstation, xbox, ios);
	}
	
	public VBox getView() {
		return view;
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
