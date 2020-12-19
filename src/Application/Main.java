package Application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			System.out.println(javafx.scene.text.Font.getFamilies());
			Parent root = FXMLLoader.load(getClass().getResource("App.fxml"));
			Scene scene = new Scene(root);
//			scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Vũ Bình Dương - 20183903");
			primaryStage.show();
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}
}


//--module-path " D:\New folder\JavaFx\javafx-sdk-11.0.2\lib" --add-modules javafx.controls,javafx.fxml