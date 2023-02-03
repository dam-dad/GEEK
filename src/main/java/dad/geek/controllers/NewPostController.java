package dad.geek.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import dad.geek.model.Post;
import dad.geek.utils.DirImages;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

public class NewPostController implements Initializable {
	
	// model
	
	private Post post = new Post();
	
	// view

	private DirImages posicionImagen;

	@FXML
	private JFXButton addFilterButton;

	@FXML
	private ImageView bottomImage;

	@FXML
	private JFXTextArea contentTextArea;

	@FXML
	private FlowPane filterFlow;

	@FXML
	private ImageView leftImage;

	@FXML
	private ImageView profileImage;

	@FXML
	private ImageView rightImage;

	@FXML
	private JFXButton sendButton;

	@FXML
	private BorderPane view;

	public NewPostController(DirImages image) {

		posicionImagen = image;

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NewPostView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// load data
		
		setPosition();
		
		// bindings
		
		post.postContentProperty().bind(contentTextArea.textProperty());

	}
	
	private void setPosition() {

		switch (posicionImagen) {
		case LEFT:
			leftImage.setImage(new Image("/images/ejemplo.png"));
			leftImage.setFitWidth(200);
			leftImage.setFitHeight(200);
			leftImage.setVisible(true);
			break;
		case RIGHT:
			rightImage.setImage(new Image("/images/ejemplo.png"));
			rightImage.setFitWidth(200);
			rightImage.setFitHeight(200);
			rightImage.setVisible(true);
			break;
		case DOWN:
			bottomImage.setImage(new Image("/images/ejemplo.png"));
			bottomImage.setFitWidth(200);
			bottomImage.setFitHeight(200);
			bottomImage.setVisible(true);
			break;
		case EMPTY:
			break;
		}
		
	}

	public BorderPane getView() {
		return view;
	}

	@FXML
	void onAddFilterAction(ActionEvent event) {

	}

	@FXML
	void onSendAction(ActionEvent event) {
		
	}

}
