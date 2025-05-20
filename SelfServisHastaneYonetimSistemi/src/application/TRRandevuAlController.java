package application;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
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
	
	
	public void initialize() {
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
		
		//Burada alanlar seçilmeden doktor seçilmemeli
		alanChoiceBox.getItems().addAll("alan1", "alan2", "alan3");
		alanChoiceBox.setValue("alan1");
		//seçilen alana göre doktorlar listelenmeli
		doktorChoiceBox.getItems().addAll("doktor1", "doktor2", "doktor3");
		doktorChoiceBox.setValue("doktor1");
	}
	
	@FXML
	public void kayitOl(ActionEvent e) {
		String istenenSaat = saatSpinner.getValue().toString();
		String istenenAlan = alanChoiceBox.getValue();
		String istenenDoktor = doktorChoiceBox.getValue();
		
		if(istenenSaat.isEmpty() || istenenAlan.isEmpty() || istenenDoktor.isEmpty()) {
			kayitDurum.setTextFill(Color.RED);
			kayitDurum.setText("Alanları doldurduğunuzdan emin olunuz.");
		}
		
		else {
			//database buraya geliyor
			kayitDurum.setTextFill(Color.GREEN);
			kayitDurum.setText("Randevu Oluşturuldu, saat: " + istenenSaat + " alan: " + istenenAlan + " doktor: " + istenenDoktor);
		}
	}
	
	public void geriDon() throws IOException {
		Main m = new Main();
		m.changeScene("MainTR.fxml");
	}
}
