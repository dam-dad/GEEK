package dad.geek;

import dad.geek.controllers.LoginController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

	public static Stage primaryStage;
	
//	private MainController controller = new MainController();
	private LoginController controller = new LoginController();
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		App.primaryStage = primaryStage;
		
		primaryStage.setTitle("GEEK");
		primaryStage.setScene(new Scene(controller.getView()));
		primaryStage.show();
		
	}

}
