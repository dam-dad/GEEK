package dad.geek.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import dad.geek.model.Filter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

public class SearchSectionController implements Initializable {
	
	//model
	Filter filter = new Filter();
	
	//view
	@FXML
    private VBox view;
    @FXML
    private JFXListView<?> filtersListView;
    @FXML
    private JFXComboBox<?> searchOptionsComboBox;
    @FXML
    private JFXTextField searchTextFied;
    
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
		
	}
	
	public VBox getView() {
		return view;
	}

	public Filter getFilter() {
		return filter;
	}
	public void setFilter(Filter filter) {
		this.filter = filter;
	}

	public JFXListView<?> getFiltersListView() {
		return filtersListView;
	}
	public void setFiltersListView(JFXListView<?> filtersListView) {
		this.filtersListView = filtersListView;
	}

	public JFXComboBox<?> getSearchOptionsComboBox() {
		return searchOptionsComboBox;
	}
	public void setSearchOptionsComboBox(JFXComboBox<?> searchOptionsComboBox) {
		this.searchOptionsComboBox = searchOptionsComboBox;
	}

	public JFXTextField getSearchTextFied() {
		return searchTextFied;
	}
	public void setSearchTextFied(JFXTextField searchTextFied) {
		this.searchTextFied = searchTextFied;
	}	

}
