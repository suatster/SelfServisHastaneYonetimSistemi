package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

		randevuObs.addAll(randevulariGetirDBden());
//		//Buradan itibaren db etkilesimleri ile degistirilecek
//		Doktor d1 = new Doktor(159, "vaasm@outlook.com", "Vaas Montenegro", "05123456785", "Cerrahi");
//		Hasta h1 = new Hasta(1, "walter.chemist@hotmail.com", "Walter White", "05123456784", "12345678901", "15a1{eq");
//		Randevu r1 = new Randevu(d1, h1, "10.30");
//
//		Doktor d2 = new Doktor(152, "crazydoctor06@gmail.com", "Gregory House", "05123456783", "Dahiliye");
//		Hasta h2 = new Hasta(2, "johnprice1980@yahoo.com", "John Price", "05123456782", "12345678902", "john_1980");
//		Randevu r2 = new Randevu(d2, h2, "10.00");
//
//		randevuObs.addAll(r1, r2); //r1 ve r2 listeye ekleniyor, database ile değiştirilecek.
		
		//FilteredList ile "sarıyoruz" (wrap)
		filtRandevuList = new FilteredList<>(randevuObs, b -> true);
		
		randevuTable.setItems(filtRandevuList);
	}

	private ArrayList<Randevu> randevulariGetirDBden() {
		ArrayList<Randevu> randevuListesi = new ArrayList<>();

		String sql = "SELECT r.saat, r.tarih, " +
				"d.id AS doktor_id, d.isim AS doktor_isim, d.alan AS doktor_alan, d.email AS doktor_email, d.telefon AS doktor_tel, " +
				"h.id AS hasta_id, h.isim AS hasta_isim, h.email AS hasta_email, h.telefon AS hasta_tel, h.kimlikNo, h.sifre " +
				"FROM randevu r " +
				"JOIN doktor d ON r.doktor_id = d.id " +
				"JOIN hasta h ON r.hasta_kimlikNo = h.kimlikNo";

		try (Connection conn = DatabaseConnection.connect();
			 PreparedStatement stmt = conn.prepareStatement(sql);
			 ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				Doktor doktor = new Doktor(
						rs.getInt("doktor_id"),
						rs.getString("doktor_email"),
						rs.getString("doktor_isim"),
						rs.getString("doktor_tel"),
						rs.getString("doktor_alan")
				);

				Hasta hasta = new Hasta(
						rs.getInt("hasta_id"),
						rs.getString("hasta_email"),
						rs.getString("hasta_isim"),
						rs.getString("hasta_tel"),
						rs.getString("kimlikNo"),
						rs.getString("sifre")
				);

				String saat = rs.getString("saat");

				Randevu randevu = new Randevu(doktor, hasta, saat);
				randevuListesi.add(randevu);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return randevuListesi;
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
