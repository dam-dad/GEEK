package dad.geek.controllers;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import dad.geek.App;
import dad.geek.db.DBManager;
import dad.geek.model.Post;
import dad.geek.model.User;
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
import javafx.scene.image.Image;
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
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 * Controlador de la ventana principal.
 *
 */
public class MainController implements Initializable {

	// model
	public static final String JRXML_FILE = "/reports/informe.jrxml";

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

	/**
	 * 
	 */
	@SuppressWarnings("static-access")
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// load data

		userContainer.getChildren().add(userSectionController.getView());
		searchContainer.getChildren().add(searchSectionController.getView());
		userContainer.setVgrow(userContainer.getChildren().get(0), Priority.ALWAYS);
		containerPane.setDividerPositions(0.1, 0.9);

		loadPosts(false);

		// listeners

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
	 * Carga los posts recibidos de la función {@link DBManager#getPosts(boolean)}
	 * de la base de datos y los mete dentro de un {@code VBox}.
	 * 
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
	 * Se ejecuta cada vez que se pulse al {@code MenuItem} "Nuevo Post" o se pulse
	 * la combinacion de teclas Cntr + N.<br/>
	 * Muestra una ventana controlada por {@link NewPostController}.
	 * 
	 * @param event
	 */
	@FXML
	void onCreatePostAction(ActionEvent event) {

		Stage window = new Stage();
		window.setTitle("Nuevo Post");
		window.setScene(new Scene(new NewPostController(this).setStage(window).getView()));
		window.getIcons().add(new Image(getClass().getResource("/images/iconooficia.png").toString()));
		window.setMinWidth(450);
		window.setMinHeight(330);
		window.initOwner(App.primaryStage);
		window.initModality(Modality.APPLICATION_MODAL);

		window.getScene().setOnKeyPressed(t -> {
			if (t.getCode() == KeyCode.ESCAPE)
				window.getOnCloseRequest().handle(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
		});
		window.setOnCloseRequest(e -> {
			window.close();
		});

		window.show();

	}

	/**
	 * Se ejecuta cada vez que se pulse el {@code JFXButton} "Editar" el
	 * {@code MenuItem} "Editar usuario" o se pulse la combinacion de teclas Cntr +
	 * E.<br/>
	 * Llama al método {@link UserSectionController #openEditWindow()}
	 * 
	 * @param event
	 */
	@FXML
	void onEditUserAction(ActionEvent event) {
		userSectionController.openEditWindow();
	}

	/**
	 * Llama a la función {@link #loadPosts(boolean)}.
	 */
	protected void reloadPosts() {
		loadPosts(true);
	}

	/**
	 * Se ejecuta cada vez que se pulse el {@code MenuItem} "Recargar Posts" o se
	 * presione la combinacion de teclas F5.<br/>
	 * Llama a la función {@link MainController#reloadPosts()} y a la función
	 * {@link UserSectionController#refreshPosts()}.
	 * 
	 * @param event
	 */
	@FXML
	void onReloadPostAction(ActionEvent event) {
		reloadPosts();
		userSectionController.refreshPosts();
	}

	/**
	 * Se ejecuta cada vez que se le de al {@code MenuItem} "Salir" o se le de a la
	 * combinación de teclas SHIFT + CNTL + S.<br/>
	 * Llama a la función {@link App#salir()}.
	 * 
	 * @param event
	 */
	@FXML
	void onExitAction(ActionEvent event) {
		App.salir();
	}

	/**
	 * Se ejecuta cada vez que se le de al {@code HyperLink} "Mostrar más...".
	 * Coteja con la base de datos si todos los posts han sido cargados, si es el
	 * caso deja de mostrar el {@code HyperLink} "Mostrar más...", si no lo sigue
	 * mostrando, luego ejecuta la función {@link #loadPosts(boolean)}.
	 * 
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

	// TODO Karim, revisa este JavaDoc
	/**
	 * Se ejecuta cada vez que se le de al {@code MenuItem} "Generar Informe" o se pulse la
	 * combinación de teclas SHIFT + CNTL + P.<br/>
	 * Llama a la función {@link App#salir()}.
	 * 
	 * @param event
	 */
	@FXML
	void onGenerateInformeAction(ActionEvent event) {
		List<User> users = new ArrayList<User>();

		try {
			for (Post p : App.conexionDB.getPosts(true)) {
				if (!users.contains(App.conexionDB.getUserObject(p.getUserID())))
					users.add(App.conexionDB.getUserObject(p.getUserID()));
			}

			// compila el informe
			JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream(JRXML_FILE));

			// crea el mapa de parámetros para el informe
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("superUser", "@" + App.user.getUsername());
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			parameters.put("today", formatter.format(new Date()));

			// generamos el informe (combinamos informe + datos)
			JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters,
					new JRBeanCollectionDataSource(users));

			// visualiza el informe generado
			JasperViewer.viewReport(jasperPrint, false);
		} catch (Exception e) {
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setTitle("ERROR");
			errorAlert.setHeaderText("Hubo un error al generar el informe");
			errorAlert.setContentText(e.getMessage());
			errorAlert.initOwner(App.primaryStage);
			errorAlert.initModality(Modality.APPLICATION_MODAL);
			errorAlert.show();
			//e.printStackTrace();
		}
	}

	/**
	 * @return La instancia de {@link UserSectionController}
	 */
	public UserSectionController getUserSectionController() {
		return userSectionController;
	}

	/**
	 * @return
	 */
	public BorderPane getView() {
		return view;
	}

}
