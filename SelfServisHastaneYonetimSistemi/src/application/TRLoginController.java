package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
				String sifre = sifrePasswordField.getText().trim(); //Sifre burada initialize edilmeli, hashleyip kontrol edilecek.
				
				if(sifre.isBlank()) { //TextField boş mu?
					wrongLogin.setTextFill(Color.RED);
					wrongLogin.setText("Şifrenizi girdiğinizden emin olunuz.");
				}
				
				else {					

					try (Connection conn = DatabaseConnection.connect()) {
						String sql = "SELECT * FROM hasta WHERE kimlikNo = ? AND sifre = ?";
						PreparedStatement pstmt = conn.prepareStatement(sql);
						pstmt.setString(1, kimlikNo);
						
						//TODO: sifreler şifrelenmiş şeklinde depolanmalı
						pstmt.setString(2, sifre); 
						ResultSet rs = pstmt.executeQuery();

						if (rs.next()) {
							wrongLogin.setText("Giriş başarılı!");
							wrongLogin.setTextFill(Color.GREEN);
							
							try {
								switchToTRRandevuAl();
							} catch (IOException e) {
								wrongLogin.setText("Eksik dosya mevcut. Lütfen yetkililerle iletişime geçin.");
							}

						} else {
							wrongLogin.setText("Kimlik numarası veya şifre hatalı.");
							wrongLogin.setTextFill(Color.RED);
						}
					} catch (SQLException e) {
						wrongLogin.setText("Veritabanı hatası: " + e.getMessage());
						wrongLogin.setTextFill(Color.RED);
						e.printStackTrace();
					}
					

					

				}
			}
			
		}
		
		catch(NumberFormatException e){
			wrongLogin.setText("Lütfen kimlik numaranızda harf, sembol vb. karakterler olmadığından emin olunuz.");
		}
		
	}
	
	private void switchToTRRandevuAl() throws IOException {
		Main m = new Main();
		m.changeScene("TRRandevuAl.fxml");
	}
	
	@FXML
	private void switchToMainTR(ActionEvent event) throws IOException {
		Main m = new Main();
		m.changeScene("MainTR.fxml");
	}
	
	
}
