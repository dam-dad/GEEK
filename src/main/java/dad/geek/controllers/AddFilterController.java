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
		videojuegos.setFilterName("Videojuegos");		
		Filter musica = new Filter();
		musica.setFilterName("Música");		
		Filter edicion = new Filter();
		edicion.setFilterName("Edición");		
		Filter hardware = new Filter();
		hardware.setFilterName("Hardware");		
		Filter java = new Filter();
		java.setFilterName("Java");		
		Filter css = new Filter();
		css.setFilterName("CSS");		
		Filter javascript = new Filter();
		javascript.setFilterName("Javascript");		
		Filter c = new Filter();
		c.setFilterName("C");		
		Filter cplusplus = new Filter();
		cplusplus.setFilterName("C++");		
		Filter python = new Filter();
		python.setFilterName("Python");		
		Filter html = new Filter();
		html.setFilterName("HTML");		
		Filter sistoperativos = new Filter();
		sistoperativos.setFilterName("Sistemas Operativos");		
		Filter windows = new Filter();
		windows.setFilterName("Windows");		
		Filter linux = new Filter();
		linux.setFilterName("Linux");		
		Filter macos = new Filter();
		macos.setFilterName("MacOS");		
		Filter android = new Filter();
		android.setFilterName("Android");		
		Filter seriestv = new Filter();
		seriestv.setFilterName("Series de televisión");	
		Filter peliculas = new Filter();
		peliculas.setFilterName("Películas");		
		Filter consolas = new Filter();
		consolas.setFilterName("Consolas");
		Filter telmoviles = new Filter();
		telmoviles.setFilterName("Teléfonos Móviles");		
		Filter tablets = new Filter();
		tablets.setFilterName("Tablets");		
		Filter tv = new Filter();
		tv.setFilterName("Televisiones");		
		Filter monitores = new Filter();
		monitores.setFilterName("Monitores");		
		Filter ratones = new Filter();
		ratones.setFilterName("Ratones");	
		Filter teclados = new Filter();
		teclados.setFilterName("Teclados");	
		Filter playstation = new Filter();
		playstation.setFilterName("PlayStation");		
		Filter xbox = new Filter();
		xbox.setFilterName("Xbox");		
		Filter ios = new Filter();
		ios.setFilterName("iOS");		
		Filter nintendo = new Filter();
		nintendo.setFilterName("Nintendo");

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
