package dad.geek.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ShowImageController implements Initializable {

	private Stage stage;
	
	// view
	
	@FXML
    private ImageView imageView;

    @FXML
    private BorderPane view;

	public ShowImageController() {
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ShowImageView.fxml"));
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
    void onCloseAction(ActionEvent event) {
		stage.getOnCloseRequest().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }
	
	public ShowImageController setStage(Stage stage) {
		this.stage = stage;
		return this;
	}
	
	public ShowImageController setImageView(Image image) {
		this.imageView.setImage(image);
		return this;
	}

	public BorderPane getView() {
		return view;
	}

}
