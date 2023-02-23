package dad.geek.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.ToggleSwitch;
import org.kordamp.ikonli.javafx.FontIcon;

import dad.geek.App;
import dad.geek.db.DBManager;
import dad.geek.model.Post;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Controlador de la ventana principal. 
 *
 */
public class MainController implements Initializable {

	// controllers

	private UserSectionController userSectionController = new UserSectionController();
	private SearchSectionController searchSectionController = new SearchSectionController();
	private BooleanProperty isShowMoreEnabled = new SimpleBooleanProperty(true);

	// view

	@FXML
	private SplitPane containerPane;
	@FXML
	private ScrollPane postContainerPane;
	
	@FXML
	private VBox userContainer;
	@FXML
	private VBox postsContainer;
	@FXML
    private VBox searchContainer;
	
	@FXML
	private FontIcon darkModeIcon;
	@FXML
	private MenuItem darkModeItem;
	@FXML
	private ToggleSwitch darkModeSwitch;

	@FXML
	private MenuItem informeItem;
	@FXML
	private MenuItem editUserItem;
	@FXML
	private MenuItem exitItem;

	@FXML
	private MenuItem newPostItem;


	@FXML
    private Hyperlink showMoreLink;
	
	@FXML
	private BorderPane view;

	/**
	 * Constructor de la clase MainController, carga el fxml.
	 */
	public MainController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("static-access")
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// load data

		userContainer.getChildren().add(userSectionController.getView());
		searchContainer.getChildren().add(searchSectionController.getView());
		userContainer.setVgrow(userContainer.getChildren().get(0), Priority.ALWAYS);
		containerPane.setDividerPositions(0.1, 0.9);

		loadPosts(false);
		
//		AutoUpdateThread thread = new AutoUpdateThread(this);
//		ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
//		exec.scheduleAtFixedRate(thread, 3, 1000, TimeUnit.SECONDS);
		
		// listeners

		darkModeSwitch.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				darkModeIcon.setIconLiteral("mdi2m-moon-waning-crescent");
			} else {
				darkModeIcon.setIconLiteral("mdi2w-white-balance-sunny");
			}
		});

		App.primaryStage.maximizedProperty().addListener((o, ov, nv) -> {
			if (App.primaryStage.isMaximized())
				containerPane.setDividerPositions(0.2, 0.80);
			else {
				containerPane.setDividerPositions(0.25, 0.75);
			}
		});

		App.primaryStage.setOnCloseRequest(e -> {
			e.consume();
//			thread.interrupt();
			App.salir();
		});
		
		// bindings
		
		showMoreLink.managedProperty().bind(isShowMoreEnabled);
		showMoreLink.visibleProperty().bind(isShowMoreEnabled);

	}

	/**
	 * Carga los posts recibidos de la función {@link DBManager#getPosts(boolean)} de la base de datos y los mete dentro de un {@code VBox}.
	 * @param reload
	 * @return
	 */
	public VBox loadPosts(boolean reload) {
		
		App.primaryStage.getScene().setCursor(Cursor.WAIT);
		try {
			postsContainer.getChildren().clear();
			for (Post p : App.conexionDB.getPosts(reload)) {
				postsContainer.getChildren().add(new PostController(p).setMainController(this).getView());
				postsContainer.getChildren().add(new SplitPane());
			}
		} catch (Exception e) {
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setTitle("ERROR");
			errorAlert.setHeaderText("Hubo un error");
			errorAlert.setContentText(e.getMessage());
			errorAlert.initOwner(App.primaryStage);
			errorAlert.initModality(Modality.APPLICATION_MODAL);
			errorAlert.show();
			return null;
		}
		App.primaryStage.getScene().setCursor(Cursor.DEFAULT);
		postContainerPane.setVvalue(0);
		return postsContainer;
	}

	/**
	 * Se ejecuta cada vez que se pulse al {@code MenuItem} "Nuevo Post" o se pulse la combinacion de teclas Cntr + N.<br/>
	 * Muestra una ventana controlada por {@link NewPostController}.
	 * @param event
	 */
	@FXML
	void onCreatePostAction(ActionEvent event) {

		Stage window = new Stage();
		window.setTitle("Nuevo Post");
		window.setScene(new Scene(new NewPostController().setStage(window).getView()));
		window.setMinWidth(450);
		window.setMinHeight(330);
		window.initOwner(App.primaryStage);
		window.initModality(Modality.APPLICATION_MODAL);

		window.getScene().setOnKeyPressed(t -> {
			if (t.getCode() == KeyCode.ESCAPE)
				window.getOnCloseRequest().handle(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
		});
		window.setOnCloseRequest(e -> {
			reloadPosts(); //TODO no me gusta esto, sales sin crear un nuevo post y lo recarga todo por la cara
			window.close();
		});

		window.show();

	}

	/**
	 * Se ejecuta cada vez que se pulse el {@code JFXButton} "Editar" el {@code MenuItem} "Editar usuario" o se pulse la combinacion de teclas Cntr + E.<br/>
	 * Llama al método {@link UserSectionController #openEditWindow()}
	 * @param event
	 */
	@FXML
	void onEditUserAction(ActionEvent event) {
		userSectionController.openEditWindow();
	}

	/**
	 * Llama a la función {@link #loadPosts(boolean)}.
	 */
	private void reloadPosts() {
		loadPosts(true);
	}

	/**
	 * Se ejecuta cada vez que se pulse el {@code MenuItem} "Recargar Posts" o se presione la combinacion de teclas F5.<br/>
	 * Llama a la función {@link MainController#reloadPosts()} y a la función {@link UserSectionController#refreshPosts()}.
	 * @param event
	 */
	@FXML
	void onReloadPostAction(ActionEvent event) {
		reloadPosts();
		userSectionController.refreshPosts();
	}

	/**
	 * Se ejecuta cada vez que se pulse el {@code MenuItem} "Cambiar Claro/Oscuro", el {@code ToggleSwitch} "darkModeSwitch" o la combinación de teclas CNTL + D.<br/>
	 * Si la propiedad selected del {@code ToggleSwitch} "darkModeSwitch" es {@code true} se pone a {@code false} y viceversa.
	 * @param event
	 */
	@FXML
	void onDarkModeAction(ActionEvent event) {
		if (darkModeSwitch.isSelected())
			darkModeSwitch.setSelected(false);
		else
			darkModeSwitch.setSelected(true);
	}

	/**
	 * Se ejecuta cada vez que se le de al {@code MenuItem} "Salir" le de a la combinación de teclas SHIFT + CNTL + S.<br/>
	 * Llama a la función {@link App#salir()}.
	 * @param event
	 */
	@FXML
	void onExitAction(ActionEvent event) {
		App.salir();
	}
	
	/**
	 * Se ejecuta cada vez que se le de al {@code HyperLink} "Mostrar más...".
	 * Coteja con la base de datos si todos los posts han sido cargados, si es el caso deja de mostrar el {@code HyperLink} "Mostrar más...", si no lo sigue mostrando,
	 * luego ejecuta la función {@link #loadPosts(boolean)}.
	 * @param event
	 */
	@FXML
    void onShowMoreAction(ActionEvent event) {
		try {
			isShowMoreEnabled.set(App.conexionDB.isAllPostLoaded());
			loadPosts(false);
		} catch (Exception e) {
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setTitle("ERROR");
			errorAlert.setHeaderText("Hubo un error");
			errorAlert.setContentText(e.getMessage());
			errorAlert.initOwner(App.primaryStage);
			errorAlert.initModality(Modality.APPLICATION_MODAL);
			errorAlert.show();
		}
    }

	//TODO javadoc una vez esté hecho
	@FXML
	void onGenerateInformeAction(ActionEvent event) {

	}

	/**
	 * @return La instancia de {@link UserSectionController}
	 */
	public UserSectionController getUserSectionController() {
		return userSectionController;
	}

	public BorderPane getView() {
		return view;
	}

}
