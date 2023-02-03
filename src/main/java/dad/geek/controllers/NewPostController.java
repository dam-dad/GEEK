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
import javafx.geometry.Pos;
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
	private JFXTextArea contentTextArea;

	@FXML
	private FlowPane filterFlow;

	@FXML
	private ImageView profileImage;

	@FXML
	private JFXButton sendButton;
	
	@FXML
    private BorderPane contentContainer;

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
		
		ImageView image = new ImageView(new Image("/images/ejemplo.png"));
		image.setFitWidth(200);
		image.setFitHeight(200);
		image.setVisible(true);

		switch (posicionImagen) {
		case LEFT:
			contentContainer.setLeft(image);
			break;
		case RIGHT:
			contentContainer.setRight(image);
			break;
		case DOWN:
			contentContainer.setBottom(image);
			break;
		case EMPTY:
			break;
		}
		
		BorderPane.setAlignment(image, Pos.CENTER);
		
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
