package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class TRHastaKayitOlusturController {
	@FXML
	private Button kayitOlButon, geriDonButon;
	
	@FXML
	private Label badAttempt;
	
	@FXML
	private TextField istenenKimlik, istenenSifre, istenenSifreTekrar;
	
	@FXML
	private void switchToMainTR() throws IOException {
		Main m = new Main();
		m.changeScene("MainTR.fxml");
	}
	
	@FXML
	private void kayitOl() {
		String istenenKimlikTxt = istenenKimlik.getText();
		
		try {
			Long.parseLong(istenenKimlikTxt); //Kimlik numarası alanına harf girilmişse NumberFormatException.
			
			if(istenenKimlikTxt.length() != 11) {
				badAttempt.setTextFill(Color.RED);
				badAttempt.setText("Kimlik numaranızın 11 haneli olduğundan emin olunuz.");
			}
			
			else {
				String sifreText = istenenSifre.getText();
				
				if(sifreText.isEmpty()) {
					badAttempt.setTextFill(Color.RED);
					badAttempt.setText("Şifre alanı boş olmamalı.");
				}
				
				else {
					//database kayıtları buraya yapılmalı ve sifre sifrelenerek depolanmalı
					//TRLoginControllerda da sifrelenmeli
					badAttempt.setTextFill(Color.GREEN);
					badAttempt.setText("Kaydınız oluşturuldu.");
				}
			}
		}
		
		catch(NumberFormatException e) {
			badAttempt.setTextFill(Color.RED);
			badAttempt.setText("Kimlik numaranızın harf, sembol vb. karakterler içermediğinden emin olunuz.");
		}
		
	}
}
