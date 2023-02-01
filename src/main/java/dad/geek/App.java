package dad.geek;

import java.util.Optional;

import dad.geek.controllers.LoginController;
import dad.geek.db.ConexionMySQL;
import dad.geek.model.User;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class App extends Application {

	public static Stage primaryStage;
	public static ConexionMySQL mysql = new ConexionMySQL();
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

		App.primaryStage.setMinWidth(450);
		App.primaryStage.setMinHeight(500);
		App.primaryStage.centerOnScreen();
		App.primaryStage.setOnCloseRequest(e -> {
			e.consume();
			App.salir();
		});	

		
	}
	
	public static void openScene(Parent parent, double width, double height) {
		
		Stage stage = App.primaryStage;
		stage.setScene(
			App.primaryStage.isMaximized() ?
			new Scene(parent, App.primaryStage.getWidth()-15, App.primaryStage.getHeight()-35) :
			new Scene(parent)
		);
		App.primaryStage.close();
		stage.setMinWidth(width);
		stage.setMinHeight(height);
		
		App.primaryStage = stage;
		App.primaryStage.show();
		
	}
	
	public static void salir() {
		Alert alerta = new Alert(AlertType.CONFIRMATION);
		alerta.setTitle("Exit");
		alerta.initOwner(App.primaryStage);
		alerta.setHeaderText("Va a salir de la aplicación.");
		alerta.setContentText("¿Seguro que quiere salir?");
		Optional<ButtonType> action = alerta.showAndWait();
		if (action.get() == ButtonType.OK) {
			Platform.exit();
		}
	}

}
