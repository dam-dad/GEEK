package dad.geek;

import java.util.Optional;

import dad.geek.controllers.LoginController;
import dad.geek.db.DBManager;
import dad.geek.model.User;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class App extends Application {

	public static Stage primaryStage;
//	public static ConexionMySQL conexionLocal;
	public static DBManager conexionLocal;
//	public static DBManager conexionRemota = new DBManager();

	public static User user = new User();

//	private MainController controller = new MainController();
	private LoginController controller = new LoginController();

//	@Override
//	public void init() throws Exception {
//		super.init();
//
//		DBManager.conectarDB();
//	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		App.primaryStage = primaryStage;

		primaryStage.setTitle("GEEK");
		primaryStage.setScene(new Scene(controller.getView()));
		primaryStage.show();
		primaryStage.setMinWidth(450);
		primaryStage.setMinHeight(500);
		primaryStage.centerOnScreen();

		try {
//			conexionLocal = new ConexionMySQL();
			conexionLocal = new DBManager();
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

	public static void salir() {
		Alert alerta = new Alert(AlertType.WARNING);
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
	}

}
