package application;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class TRDoktorList1Controller {
	@FXML
	private ListView<String> agiz, aile, beyin, cocuk, dermatoloji, fizyoloji, gogus, goz;
	
	@FXML
	private ObservableList<String> 
				agizObs = FXCollections.observableArrayList(),
				aileObs = FXCollections.observableArrayList(),
				beyinObs = FXCollections.observableArrayList(),
				cocukObs = FXCollections.observableArrayList(),
				dermatolojiObs = FXCollections.observableArrayList(),
				fizyolojiObs = FXCollections.observableArrayList(),
				gogusObs = FXCollections.observableArrayList(),
				gozObs = FXCollections.observableArrayList();
	
	@FXML
	private Button sonrakiSayfa, cikis;

	@FXML
	private void initialize() {
		Doktor[] doktorlar = {
			new Doktor(123, "ahmetc1980@gmail.com", "Dr. Ahmet Çaydanlık", "05123456789", "Agiz"),
			new Doktor(110, "ymine@gmail.com", "Dr. Mine Yavuz", "05123456788", "Goz")
		};


		for (Doktor d : doktorlar) {
			switch (d.getAlan().toLowerCase()) {
			case "agiz":
				agizObs.add(d.getIsim());
				break;
				
			case "aile":
				aileObs.add(d.getIsim());
				break;
			
			case "beyin":
				beyinObs.add(d.getIsim());
				break;
			
			case "cocuk":
				cocukObs.add(d.getIsim());
				break;
			
			case "dermatoloji":
				dermatolojiObs.add(d.getIsim());
				break;
				
			case "fizyoloji":
				fizyolojiObs.add(d.getIsim());
				break;
			
			case "gogus":
				gogusObs.add(d.getIsim());
				break;
			
			case "goz":
				gozObs.add(d.getIsim());
				break;
			}
		}

		agiz.setItems(agizObs);
		aile.setItems(aileObs);
		beyin.setItems(beyinObs); 
		cocuk.setItems(cocukObs);
		dermatoloji.setItems(dermatolojiObs);
		fizyoloji.setItems(fizyolojiObs);
		gogus.setItems(gogusObs);
		goz.setItems(gozObs);
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
