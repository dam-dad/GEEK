package dad.geek;

import dad.geek.controllers.LoginController;
import javafx.application.Application;
import javafx.scene.Parent;
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

		App.primaryStage.setMinWidth(450);
		App.primaryStage.setMinHeight(500);
		App.primaryStage.centerOnScreen();
		
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
		stage.centerOnScreen();
		
		App.primaryStage = stage;
		App.primaryStage.show();
		
	}

}
