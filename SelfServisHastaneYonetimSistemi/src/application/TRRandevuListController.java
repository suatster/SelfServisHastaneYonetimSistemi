package application;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class TRRandevuListController {
	/** Login’dan atanan hasta kimlikNo **/
	public static String CURRENT_HASTA_KIMLIK;

	/** Bu setter, loader sonrası login’daki TC’yi buraya set etmek için çağrılacak **/
	public void setHastaKimlikNo(String kimlikNo) {
		CURRENT_HASTA_KIMLIK = kimlikNo;
		// Eğer initialize() öncesi çağrılıyorsa, direkt yükle de tetiklenebilir:
		loadAppointments(kimlikNo);
	}

	@FXML private TextField filtSaat, filtAlan, filtDoktor, filtHasta;
	@FXML private TableView<Randevu> randevuTable;
	@FXML private TableColumn<Randevu, String> alan, saat, doktorAdi, hastaAdi;
	@FXML private Button filtreleButon, temizleButon, geriDonButon;

	private final ObservableList<Randevu> masterList = FXCollections.observableArrayList();
	private FilteredList<Randevu> filtRandevuList;

	@FXML
	private void initialize() {
		// Load data
		loadAppointments(CURRENT_HASTA_KIMLIK);

		// Bind columns
		alan    .setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getDoktor().getAlan()));
		saat    .setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getSaat()));
		doktorAdi.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getDoktorIsim()));
		hastaAdi .setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getHastaIsim()));

		// Wrap in FilteredList
		filtRandevuList = new FilteredList<>(masterList, r -> true);
		randevuTable.setItems(filtRandevuList);

		// Button actions
		filtreleButon.setOnAction(e -> applyFilter());
		temizleButon .setOnAction(e -> clearFilter());
		geriDonButon .setOnAction(e -> switchToMainTR(e));
	}

	private void loadAppointments(String kimlikNo) {
		masterList.clear();
		if (kimlikNo == null || kimlikNo.isEmpty()) {
			System.err.println("TC sağlanmadı, randevu yüklenmedi.");
			return;
		}
		String sql =
				"SELECT r.saat, d.alan AS doktor_alan, d.isim AS doktor_isim, h.isim AS hasta_isim " +
						"FROM randevu r " +
						"JOIN doktor d ON r.doktor_id = d.id " +
						"JOIN hasta   h ON r.hasta_kimlikNo = h.kimlikNo " +
						"WHERE r.hasta_kimlikNo = ?";
		try (Connection conn = DatabaseConnection.connect();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, kimlikNo);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Doktor doktor = new Doktor(
							0, "", rs.getString("doktor_isim"), "", rs.getString("doktor_alan")
					);
					Hasta hasta = new Hasta(
							0, "", rs.getString("hasta_isim"), "", kimlikNo, ""
					);
					String saatStr = rs.getString("saat");
					masterList.add(new Randevu(doktor, hasta, saatStr));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void filtrele() {
		applyFilter();
	}

	@FXML
	private void temizle() {
		clearFilter();
	}

	private void applyFilter() {
		String fs = filtSaat  .getText().toLowerCase().trim();
		String fa = filtAlan  .getText().toLowerCase().trim();
		String fd = filtDoktor.getText().toLowerCase().trim();
		String fh = filtHasta .getText().toLowerCase().trim();

		filtRandevuList.setPredicate(r ->
				r.getSaat().toLowerCase().contains(fs) &&
						r.getDoktor().getAlan().toLowerCase().contains(fa) &&
						r.getDoktorIsim().toLowerCase().contains(fd) &&
						r.getHastaIsim().toLowerCase().contains(fh)
		);
	}

	private void clearFilter() {
		filtSaat.clear(); filtAlan.clear(); filtDoktor.clear(); filtHasta.clear();
		filtRandevuList.setPredicate(r -> true);
	}

	@FXML
	private void switchToMainTR(ActionEvent e) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/MainTR.fxml"));
			Parent root = loader.load();
			Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			stage.getScene().setRoot(root);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
