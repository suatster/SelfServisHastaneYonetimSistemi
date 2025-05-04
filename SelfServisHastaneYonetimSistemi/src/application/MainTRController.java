package application;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

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
	 * 
	 * private Scene scene;
	 * private Stage stage;
	 * private Parent root;
	 */
	
	@FXML
	private Button siraAl, hastaKayitOlustur, acilHasta, doktorList, randevuList, cikis;
	
	@FXML
	private void switchToTRLogin(ActionEvent event) throws IOException {
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
	
	@FXML
	private void switchToTRHastaKayitOlustur(ActionEvent event) throws IOException {
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
	
	@FXML
	private void switchToTRAcilHasta(ActionEvent event) throws IOException {
		Main m = new Main();
		m.changeScene("TRAcilHasta.fxml");
	}
	
	@FXML
	private void switchToDoktorList() throws IOException {
		Main m = new Main();
		m.changeScene("TRDoktorList1.fxml");
	}
	
	//TODO: private void switchToRandevuList() throws IOException {
	
	@FXML
	private void cikis(ActionEvent event) {
		Platform.exit();
	}
	
}
