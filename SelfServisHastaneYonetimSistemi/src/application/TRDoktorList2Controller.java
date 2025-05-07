package application;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class TRDoktorList2Controller {
	@FXML
	private ListView<String> dahiliye, jinekoloji, kalp, kulak, noroloji, ortopedi, ruh, uroloji;
	
	@FXML
	private ObservableList<String>
				dahiliyeObs = FXCollections.observableArrayList(),
				jinekolojiObs = FXCollections.observableArrayList(),
				kalpObs = FXCollections.observableArrayList(),
				kulakObs = FXCollections.observableArrayList(),
		    	norolojiObs = FXCollections.observableArrayList(),
		    	ortopediObs = FXCollections.observableArrayList(),
		    	ruhObs = FXCollections.observableArrayList(),
		    	urolojiObs = FXCollections.observableArrayList();
	
	@FXML
	private Button oncekiSayfa, cikis;

	@FXML
	private void initialize() {
		Doktor[] doktorlar = {
			new Doktor(245, "finnmcmissile@gmail.com", "Prof. Finn McRoket", "05123456787", "Dahiliye"),
			new Doktor(215, "hudson.eskitayfa@gmail.com", "Ord. Prof. Hudson Hornet", "05123456786", "Uroloji")
		};

		ObservableList<String> dahiliyeObs = FXCollections.observableArrayList();

		for (Doktor d : doktorlar) {
			switch (d.getAlan().toLowerCase()) {
				case "dahiliye":
					dahiliyeObs.add(d.getIsim());
					break;
				
				case "jinekoloji":
					jinekolojiObs.add(d.getIsim());
					break;
				
				case "kalp":
					kalpObs.add(d.getIsim());
					break;
				
				case "kulak":
					kulakObs.add(d.getIsim());
					break;
				
				case "noroloji":
					norolojiObs.add(d.getIsim());
					break;
				
				case "ortopedi":
					ortopediObs.add(d.getIsim());
					break;
				
				case "ruh":
					ruhObs.add(d.getIsim());
					break;
				
				case "uroloji":
					urolojiObs.add(d.getIsim());
					break;
			}
		}
		
		dahiliye.setItems(dahiliyeObs);
		jinekoloji.setItems(jinekolojiObs);
		kalp.setItems(kalpObs);
		kulak.setItems(kulakObs);
		noroloji.setItems(norolojiObs);
		ortopedi.setItems(ortopediObs);
		ruh.setItems(ruhObs);
		uroloji.setItems(urolojiObs);
	}

	@FXML
	private void oncekiSayfa() throws IOException {
		Main m = new Main();
		m.changeScene("TRDoktorList1.fxml");
	}

	@FXML
	private void cikis() throws IOException {
		Main m = new Main();
		m.changeScene("MainTR.fxml");
	}
}
