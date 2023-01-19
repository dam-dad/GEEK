package dad.geek;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

	private Controller controller = new Controller();
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		primaryStage.setTitle("GEEK");
		primaryStage.setScene(new Scene(controller.getView()));
		primaryStage.show();
		
	}

}
