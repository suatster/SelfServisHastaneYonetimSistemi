package application;

import java.io.IOException;

import javafx.event.ActionEvent;

/* Bu importlar normalde stage degistirmek icin kullanilmaktaydi. Sadelesmeye gidildi.
 * 
 * import javafx.fxml.FXMLLoader;
 * import javafx.scene.Node;
 * import javafx.scene.Parent;
 * import javafx.scene.Scene;
 * import javafx.stage.Stage;
*/

public class MainTRController {
	/* Bu degiskenler normalde stage degistirmek icin kullanilmaktaydi. Sadelesmeye gidildi.
	 * private Scene scene;
	 * private Stage stage;
	 * private Parent root;
	 */
	
	public void switchToTRLogin(ActionEvent event) throws IOException {
		Main m = new Main();
		m.changeScene("TRLogin.fxml");
		
		/* Asagida stage degistirmek icin kullandigimiz eski metodumuz var.
		 * 
		 * root = FXMLLoader.load(getClass().getResource("TRLogin.fxml"));
		 * stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		 * scene = new Scene(root);
		 * stage.setScene(scene);
		 * stage.show();
		*/
	}
	
	public void switchToTRHastaKayitOlustur(ActionEvent event) throws IOException {
		Main m = new Main();
		m.changeScene("TRHastaKayitOlustur.fxml");
		
		/* Asagida stage degistirmek icin kullandigimiz eski metodumuz var. Sadelesmeye gidildi.
		 * 
		 * root = FXMLLoader.load(getClass().getResource("TRHastaKayitOlustur.fxml"));
		 * stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		 * scene = new Scene(root);
		 * stage.setScene(scene);
		 * stage.show();
		*/
	}
}
