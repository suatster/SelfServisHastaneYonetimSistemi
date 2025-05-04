package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class TRLoginController {
	
	@FXML
	private Button geriDonButon;
	
	@FXML
	private TextField kimlikNoTextField;
	
	@FXML
	private PasswordField sifrePasswordField;
	
	@FXML
	private Button girisButon;
	
	@FXML
	private Label wrongLogin;
	
	@FXML
	private void initialize() {
		girisButon.setDefaultButton(true);
	}
	
	@FXML
	private void login(ActionEvent event) {
		String kimlikNo = kimlikNoTextField.getText().trim();
		
		try {
			Long.parseLong(kimlikNo); //Eger sayiya donusturulemezse sembol, harf vb. vardir. Ekrana hata yansitilacak, try-catch
			
			//Girilen sayinin buyuklugu kontrol edilmeli, bu kısım 
			if(kimlikNo.length() != 11) {
				wrongLogin.setText("Kimlik numaranızın 11 haneli olduğundan emin olunuz.");
			}
			
			else {
				String sifre = sifrePasswordField.getText().trim();
				
				if(sifre.isBlank()) { //TextField boş mu?
					wrongLogin.setText("Şifrenizi girdiğinizden emin olunuz.");
				}
				
				else {					
					//TODO:kontrol
					
					wrongLogin.setText("Giriş başarılı!");
					wrongLogin.setTextFill(Color.GREEN);
					
					//TODO: Randevu sayfasina gecis
				}
			}
			
		}
		
		catch(NumberFormatException e){
			wrongLogin.setText("Lütfen kimlik numaranızda harf, sembol vb. karakterler olmadığından emin olunuz.");
		}
		
	}
	
	@FXML
	private void switchToMainTR(ActionEvent event) throws IOException {
		Main m = new Main();
		m.changeScene("MainTR.fxml");
	}
	
}
