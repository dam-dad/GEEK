package dad.geek.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import dad.geek.model.Filter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class SearchSectionController implements Initializable {
	
	//model
	Filter filter = new Filter();
	
	//view
    @FXML
    private FlowPane filtersFlowPane;
    @FXML
    private JFXButton searchButton;
    @FXML
    private JFXComboBox<Filter> searchFiltersComboBox;
    @FXML
    private JFXTextField searchUserText;
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
		
	}
	

    @FXML
    void onSearchAction(ActionEvent event) {

    }
    
	public VBox getView() {
		return view;
	}

	public FlowPane getFiltersFlowPane() {
		return filtersFlowPane;
	}

	public void setFiltersFlowPane(FlowPane filtersFlowPane) {
		this.filtersFlowPane = filtersFlowPane;
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
