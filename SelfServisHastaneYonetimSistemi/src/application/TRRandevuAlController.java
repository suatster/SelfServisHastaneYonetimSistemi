package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;

public class TRRandevuAlController {
	
	@FXML
	private Button kayitOlButon;
	
	@FXML
	private ChoiceBox<String> alanChoiceBox, doktorChoiceBox;
	
	@FXML
	private Label kayitDurum;
	
	@FXML
	private Spinner<LocalTime> saatSpinner;

	@FXML
	public DatePicker tarihPicker;

	private String hastaKimlikNo;

	// Doktor isimlerini ID ile eşleştirme
	private HashMap<String, Integer> doktorIsimToIdMap = new HashMap<>();

	public void setHastaKimlikNo(String kimlikNo) {
		this.hastaKimlikNo = kimlikNo;
	}

	public void initialize() {
		initializeSpinner();
		tarihPicker.setValue(LocalDate.now());

		alanChoiceBox.getItems().addAll(getAlanlarFromDB());
		alanChoiceBox.setOnAction(e -> doktorlariYukle(alanChoiceBox.getValue()));
		
		if (!alanChoiceBox.getItems().isEmpty()) {
		    alanChoiceBox.setValue(alanChoiceBox.getItems().get(0)); // İlk alan seçilsin
		    doktorlariYukle(alanChoiceBox.getValue());
		}
		
		tarihPicker.setDayCellFactory(picker -> new DateCell() {
		    @Override
		    public void updateItem(LocalDate date, boolean empty) {
		        super.updateItem(date, empty);
		        setDisable(empty || date.isBefore(LocalDate.now()));
		    }
		});
	}

	public void initializeSpinner() {
		saatSpinner.setValueFactory(new SpinnerValueFactory<LocalTime>() {
	        {
	            setConverter(new StringConverter<LocalTime>() {
	                final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

	                @Override
	                public String toString(LocalTime time) {
	                    return time != null ? time.format(formatter) : "";
	                }

	                @Override
	                public LocalTime fromString(String string) {
	                    return LocalTime.parse(string, formatter);
	                }
	            });

	            setValue(LocalTime.of(10, 0));
	        }

	        @Override
	        public void decrement(int steps) {
	            setValue(getValue().minusMinutes(steps * 5));
	        }

	        @Override
	        public void increment(int steps) {
	            setValue(getValue().plusMinutes(steps * 5));
	        }
	    });
		
//		//Burada alanlar seçilmeden doktor seçilmemeli
//		alanChoiceBox.getItems().addAll("alan1", "alan2", "alan3");
//		alanChoiceBox.setValue("alan1");
//		//seçilen alana göre doktorlar listelenmeli
//		doktorChoiceBox.getItems().addAll("doktor1", "doktor2", "doktor3");
//		doktorChoiceBox.setValue("doktor1");
	}

	private ArrayList<String> getAlanlarFromDB() {
		ArrayList<String> alanlar = new ArrayList<>();
		try (Connection conn = DatabaseConnection.connect();
			 Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery("SELECT DISTINCT alan FROM doktor")) {
			while (rs.next()) {
				alanlar.add(rs.getString("alan"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return alanlar;
	}

	private void doktorlariYukle(String alan) {
		doktorChoiceBox.getItems().clear();
		doktorIsimToIdMap.clear();

		try (Connection conn = DatabaseConnection.connect();
			 PreparedStatement pstmt = conn.prepareStatement("SELECT id, isim FROM doktor WHERE alan = ?")) {
			pstmt.setString(1, alan);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				String isim = rs.getString("isim");
				int id = rs.getInt("id");
				doktorChoiceBox.getItems().add(isim);
				doktorIsimToIdMap.put(isim, id);
			}

			if (!doktorChoiceBox.getItems().isEmpty()) {
				doktorChoiceBox.setValue(doktorChoiceBox.getItems().get(0));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void kayitOl(ActionEvent e) {
		String secilenDoktor = doktorChoiceBox.getValue();
		LocalTime saat = saatSpinner.getValue();
		LocalDate tarih = tarihPicker.getValue();

		if (secilenDoktor == null || tarih == null || saat == null) {
			kayitDurum.setTextFill(Color.RED);
			kayitDurum.setText("Lütfen tüm alanları doldurun.");
			return;
		}

		int doktorId = doktorIsimToIdMap.get(secilenDoktor);

		if (randevuVarMi(doktorId, tarih.toString(), saat.toString())) {
			kayitDurum.setTextFill(Color.RED);
			kayitDurum.setText("Seçilen tarih ve saatte bu doktorun randevusu dolu.");
		} else {
			if (randevuKaydet(doktorId, tarih.toString(), saat.toString())) {
				kayitDurum.setTextFill(Color.GREEN);
				kayitDurum.setText("Randevunuz başarıyla oluşturuldu.");
			} else {
				kayitDurum.setTextFill(Color.RED);
				kayitDurum.setText("Bir hata oluştu.");
			}
		}
	}

	private boolean randevuVarMi(int doktorId, String tarih, String saat) {
		String sql = "SELECT * FROM randevu WHERE doktor_id = ? AND tarih = ? AND saat = ?";
		try (Connection conn = DatabaseConnection.connect();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, doktorId);
			pstmt.setString(2, tarih);
			pstmt.setString(3, saat);
			ResultSet rs = pstmt.executeQuery();
			return rs.next(); // varsa true
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
	}

	private boolean randevuKaydet(int doktorId, String tarih, String saat) {
		String sql = "INSERT INTO randevu (hasta_kimlikNo, doktor_id, tarih, saat) VALUES (?, ?, ?, ?)";
		try (Connection conn = DatabaseConnection.connect();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, hastaKimlikNo);
			pstmt.setInt(2, doktorId);
			pstmt.setString(3, tarih);
			pstmt.setString(4, saat);
			pstmt.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void geriDon() throws IOException {
		Main m = new Main();
		m.changeScene("MainTR.fxml");
	}
}
