package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class TRDoktorListController {
	@FXML
	private ListView<String> agiz, aile, beyin, cocuk, dermatoloji, fizyoloji, gogus, goz;
	//Okunabilirlik icin listeler ayrıldı.
	@FXML
	private ListView<String> dahiliye, jinekoloji, kalp, kulak, noroloji, ortopedi, ruh, uroloji;

	@FXML
	private Button oncekiSayfa, cikis, sonrakiSayfa;
	
	@FXML
	private void oncekiSayfa() throws IOException {
		Main m = new Main();
		m.changeScene("TRDoktorList1.fxml");
	}
	
	@FXML
	private void sonrakiSayfa() throws IOException {
		Main m = new Main();
		m.changeScene("TRDoktorList2.fxml");
	}
	
	@FXML
	private void cikis() throws IOException {
		Main m = new Main();
		m.changeScene("MainTR.fxml");
	}
}
