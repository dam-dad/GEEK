package dad.geek.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import dad.geek.utils.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

public class NewPostController implements Initializable {

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
		switch (posicionImagen) {
		case LEFT:
			leftImage.setImage(new Image("/images/ejemplo.png"));
			leftImage.setFitWidth(100);
			leftImage.setFitHeight(100);
			leftImage.setVisible(true);
			break;
		case RIGHT:
			rightImage.setImage(new Image("/images/ejemplo.png"));
			rightImage.setFitWidth(100);
			rightImage.setFitHeight(100);
			rightImage.setVisible(true);
			break;
		case DOWN:
			bottomImage.setImage(new Image("/images/ejemplo.png"));
			bottomImage.setFitWidth(100);
			bottomImage.setFitHeight(100);
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
