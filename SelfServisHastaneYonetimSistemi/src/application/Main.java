package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class Main extends Application implements IStable{
	private static Stage stg; //changeScene() metodu icin gerekli
	
	@Override
	public void start(Stage primaryStage) {
		try {
			stg = primaryStage; //changeScene() metodu icin gerekli
			Parent root = FXMLLoader.load(getClass().getResource("MainTR.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setResizable(isResizable); //Pencere boyutu degistirilememeli
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void changeScene(String fxml) throws IOException {
		Parent pane = FXMLLoader.load(getClass().getResource(fxml));
		stg.getScene().setRoot(pane);
	}
	
	//Overloading, FXMLLoader zaten başlı başına bir constructor da içerdiğinden bu yöntem uygun görüldü.
	public void changeScene(String fxml, String kimlikNo) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
		Parent pane = loader.load();

		// controllera git
		Object controller = loader.getController();
		if (controller instanceof TRRandevuAlController) {
			((TRRandevuAlController) controller).setHastaKimlikNo(kimlikNo);
		}

		stg.getScene().setRoot(pane);
	}
}
