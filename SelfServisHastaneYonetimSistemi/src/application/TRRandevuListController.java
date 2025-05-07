package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TRRandevuListController {
	@FXML
	private TableView<Randevu> randevuTable;
	
	@FXML
	private TableColumn<Randevu, String> alan, saat, doktorAdi, hastaAdi;
	
	@FXML
	private ObservableList<Randevu> randevuObs = FXCollections.observableArrayList();
	
	@FXML
	private void initialize() {
		alan.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDoktor().getAlan()));
		saat.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSaat()));
		doktorAdi.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDoktorIsim()));
		hastaAdi.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getHastaIsim()));
		
		//Buradan itibaren db etkilesimleri ile degistirilecek
		Doktor d1 = new Doktor(159, "vaasm@outlook.com", "Vaas Montenegro", "05123456785", "Cerrahi");
		Hasta h1 = new Hasta(1, "walter.chemist@hotmail.com", "Walter White", "05123456784", "12345678901", "15a1{eq");
		Randevu r1 = new Randevu(d1, h1, "10.30");
		
		Doktor d2 = new Doktor(152, "crazydoctor06@gmail.com", "Gregory House", "05123456783", "Dahiliye");
		Hasta h2 = new Hasta(2, "johnprice1980@yahoo.com", "John Price", "05123456782", "12345678902", "john_1980");
		Randevu r2 = new Randevu(d2, h2, "10.00");
		
		randevuObs.add(r1); randevuObs.add(r2);
		
		randevuTable.setItems(randevuObs);
	}
}
