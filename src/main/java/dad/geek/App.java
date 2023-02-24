package dad.geek;

import java.io.File;
import java.util.Optional;

import dad.geek.controllers.LoginController;
import dad.geek.controllers.SigninController;
import dad.geek.db.DBManager;
import dad.geek.model.User;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Clase que se encarga de ejecutar la aplicación.
 */
public class App extends Application {
	
	public static final String TEMP_PATH = System.getProperty("user.dir").toString() + "\\src\\main\\resources\\temp\\";

	public static Stage primaryStage;
	public static DBManager conexionDB;

	public static User user = new User();

	private LoginController controller = new LoginController();

	/**
	 * Se ejecuta al comienzo, crea la primera ventana {@link LoginController} y conecta con la base de datos
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {

		App.primaryStage = primaryStage;

		primaryStage.setTitle("GEEK");
		primaryStage.setScene(new Scene(controller.getView()));
		primaryStage.show();
		primaryStage.getIcons().add(new Image(getClass().getResource("/images/iconooficia.png").toString()));
		primaryStage.setMinWidth(450);
		primaryStage.setMinHeight(500);
		primaryStage.centerOnScreen();

		try {
			conexionDB = new DBManager();
		} catch (Exception e1) {
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setTitle("ERROR");
			errorAlert.setHeaderText("Hubo un error");
			errorAlert.setContentText(e1.getMessage());
			errorAlert.initOwner(App.primaryStage);
			errorAlert.initModality(Modality.APPLICATION_MODAL);
			errorAlert.show();
		}

	}
	
	/**
	 * Se encarga de crear una escena en una nueva ventana
	 * @param parent
	 * - El componente que lo llama
	 * @param width
	 * - El ancho deseado
	 * @param height
	 * - La altura deseada
	 */
	public static void openScene(Parent parent, double width, double height) {

		Stage stage = App.primaryStage;
		stage.setScene(App.primaryStage.isMaximized()
				? new Scene(parent, App.primaryStage.getWidth() - 15, App.primaryStage.getHeight() - 35)
				: new Scene(parent));
		App.primaryStage.close();
		stage.setMinWidth(width);
		stage.setMinHeight(height);

		App.primaryStage = stage;
		App.primaryStage.show();

	}

	/**
	 * Se ejecuta cada vez que se desee salir de la aplicación principal(no se ejecuta ni en {@link LoginController} ni en {@link SigninController}
	 */
	public static void salir() {
		Alert alerta = new Alert(AlertType.CONFIRMATION);
		alerta.setTitle("Exit");
		alerta.initOwner(App.primaryStage);
		alerta.setHeaderText("Va a salir de la aplicación.");
		alerta.setContentText("¿Seguro que quiere salir?");
		Optional<ButtonType> action = alerta.showAndWait();
		action.ifPresent(a -> {
			if (action.get() == ButtonType.OK) {
				Platform.exit();
			}
		});
		
		deleteFolder(new File(TEMP_PATH));
	}
	
	private static void deleteFolder(File folder) {
	    File[] files = folder.listFiles();
	    if(files!=null) { //some JVMs return null for empty dirs
	        for(File f: files) {
	            if(f.isDirectory()) {
	                deleteFolder(f);
	            } else {
	                f.delete();
	            }
	        }
	    }
	}

}
