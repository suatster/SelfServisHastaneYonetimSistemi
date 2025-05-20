package application;

import java.io.IOException;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class TRRandevuListController {
	private FilteredList<Randevu> filtRandevuList;
	
	@FXML
	private TextField filtSaat, filtAlan, filtDoktor, filtHasta;
	
	@FXML
	private TableView<Randevu> randevuTable;
	
	@FXML
	private TableColumn<Randevu, String> alan, saat, doktorAdi, hastaAdi;
	
	@FXML
	private ObservableList<Randevu> randevuObs = FXCollections.observableArrayList();
	
	@FXML
	private Button filtreleButon, geriDonButon, temizleButon;
	
	@FXML
	private void switchToMainTR() throws IOException {
		Main m = new Main();
		m.changeScene("MainTR.fxml");
	}
	
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
		
		randevuObs.addAll(r1, r2); //r1 ve r2 listeye ekleniyor, database ile değiştirilecek.
		
		//FilteredList ile "sarıyoruz" (wrap)
		filtRandevuList = new FilteredList<>(randevuObs, b -> true);
		
		randevuTable.setItems(filtRandevuList);
	}
	
	
	@FXML
	private void filtrele() {
	    String saatFilter = filtSaat.getText().toLowerCase().trim();
	    String alanFilter = filtAlan.getText().toLowerCase().trim();
	    String doktorFilter = filtDoktor.getText().toLowerCase().trim();
	    String hastaFilter = filtHasta.getText().toLowerCase().trim();

	    filtRandevuList.setPredicate(r -> {
	        boolean matchSaat = r.getSaat().toLowerCase().contains(saatFilter);
	        boolean matchAlan = r.getDoktor().getAlan().toLowerCase().contains(alanFilter);
	        boolean matchDoktor = r.getDoktorIsim().toLowerCase().contains(doktorFilter);
	        boolean matchHasta = r.getHastaIsim().toLowerCase().contains(hastaFilter);

	        return matchSaat && matchAlan && matchDoktor && matchHasta;
	    });
	}
	
	@FXML
	private void temizle() {
	    filtSaat.clear();
	    filtAlan.clear();
	    filtDoktor.clear();
	    filtHasta.clear();
	    filtRandevuList.setPredicate(r -> true);
	}


}
