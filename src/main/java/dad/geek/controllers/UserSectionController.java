package dad.geek.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import dad.geek.App;
import dad.geek.model.Post;
import dad.geek.model.User;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class UserSectionController implements Initializable {
	
	// model
	
	private BooleanProperty goback = new SimpleBooleanProperty(false);
	private ListProperty<User> users = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ObjectProperty<User> currentUser = new SimpleObjectProperty<>();

	// view
	
	@FXML
    private VBox postsContainer;
	
	@FXML
    private JFXButton backButton;
	
	@FXML
    private JFXButton editButton;

    @FXML
    private Label filterNumberLabel;

    @FXML
    private ImageView profileImage;

    @FXML
    private JFXButton showMoreButton;
    
    @FXML
    private Label usernameLabel;

    @FXML
    private Label nicknameLabel;
    
    @FXML
    private ScrollPane postContainerPane;
    
    @FXML
    private VBox view;

	
	public UserSectionController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/UserSectionView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// listeners
		
		users.sizeProperty().addListener(this::onUsersSizeModified);
		currentUser.addListener(this::onCurrentUserChanged);
		
		// load data
		
		users.add(App.user);
		
		// bindings

		backButton.visibleProperty().bind(goback);
		editButton.visibleProperty().bind(goback.not());
		
	}
	
	@FXML
    void onEditAction(ActionEvent event) {
		
		openEditWindow();
		
    }
	
	public void openEditWindow() {
		
		Stage window = new Stage();
		window.setTitle("Editar usuario");
		window.setScene(new Scene(new EditProfileController().setStage(window).getView()));
		window.setMinWidth(300);
		window.setMinHeight(435);
		window.initOwner(App.primaryStage);
		window.initModality(Modality.APPLICATION_MODAL);
		
		window.getScene().setOnKeyPressed(t -> {
			if(t.getCode() == KeyCode.ESCAPE)
				window.getOnCloseRequest().handle(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
		});
		window.setOnCloseRequest(e -> {
			window.close();
		});
		
		window.show();
		
	}

    @FXML
    void onShowMoreAction(ActionEvent event) {
    	
    }
    
    @FXML
    void onBackAction(ActionEvent event) {
    	
		users.remove(users.size()-1);
		if (currentUser.get().equals(App.user))
			goback.set(false);
    	
    }
	
    private void onUsersSizeModified(ObservableValue<? extends Number> o, Number ov, Number nv) {
		
		if(nv != null && nv.intValue() >= 1)
			currentUser.set(users.get(nv.intValue()-1));
		
	}
	
	private void onCurrentUserChanged(ObservableValue<? extends User> o, User ov, User nv) {
		
		if(ov != null) {
			nicknameLabel.textProperty().unbind();
			usernameLabel.textProperty().unbind();
			profileImage.imageProperty().unbind();
		}
		
		if(nv != null) {
			nicknameLabel.textProperty().bind(nv.nicknameProperty());
			usernameLabel.textProperty().bind(Bindings.concat("@").concat(nv.usernameProperty()));
			profileImage.imageProperty().bind(nv.profileImageProperty());
			
			postContainerPane.setContent(laodPosts());
		}
    	
	}
	
    public void changeUser(User user) {
    	
    	if (user.equals(App.user))
    		goback.set(false);
    	else
    		goback.set(true);
    	
    	if(!currentUser.get().equals(user))
    		users.add(user);
    	
    }
    
    private VBox laodPosts() {
		postsContainer.getChildren().clear();
		for(Post p : App.conexionLocal.getUserPosts(currentUser.get())) {
			postsContainer.getChildren().add(new PostController(p).getView());
			postsContainer.getChildren().add(new SplitPane());
		}
		return postsContainer;
	}
	
	public VBox getView() {
		return view;
	}
    
}
