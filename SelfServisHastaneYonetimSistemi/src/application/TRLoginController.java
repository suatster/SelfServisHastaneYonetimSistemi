package application;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.application.Platform;
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
			if(!kimlikNo.matches("^\\d{11}$")) {
				wrongLogin.setText("Kimlik numaranızın 11 haneli olduğundan emin olunuz.");
			}
			
			else {
				String sifre = sifrePasswordField.getText().trim(); //Sifre burada initialize edilmeli, hashleyip kontrol edilecek.
				
				if(sifre.isBlank()) { //TextField boş mu?
					wrongLogin.setTextFill(Color.RED);
					wrongLogin.setText("Şifrenizi girdiğinizden emin olunuz.");
				}
				
				else {
					String hashedSifre = hashSifre(sifre);
					try (Connection conn = DatabaseConnection.connect()) {
						String sql = "SELECT * FROM hasta WHERE kimlikNo = ? AND sifre = ?";
						PreparedStatement pstmt = conn.prepareStatement(sql);
						pstmt.setString(1, kimlikNo);

						
						pstmt.setString(2, hashedSifre);
						ResultSet rs = pstmt.executeQuery();

						if (rs.next()) {
							wrongLogin.setText("Giriş başarılı!");
							wrongLogin.setTextFill(Color.GREEN);
							
							try {
								switchToTRRandevuAl(kimlikNo);
							} catch (IOException e) {
								wrongLogin.setText("Eksik dosya mevcut. Lütfen yetkililerle iletişime geçin.");
								wrongLogin.setTextFill(Color.RED);
								e.printStackTrace();
							}

						} else {
							new Thread(() -> {
							    try {
							        Thread.sleep(500); //sırf ek puan istemek için ekledim ¯\_(ツ)_/¯
							    } catch (InterruptedException e) {
							        e.printStackTrace();
							    }
							    Platform.runLater(() -> {
							        wrongLogin.setText("Kimlik numarası veya şifre hatalı.");
							        wrongLogin.setTextFill(Color.RED);
							    });
							}).start();
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

	private String hashSifre(String sifre) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest(sifre.getBytes());
			StringBuilder hexString = new StringBuilder();

			for (byte b : hash) {
				String hex = Integer.toHexString(0xff & b);
				if (hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private void switchToTRRandevuAl(String kimlikNo) throws IOException {
		Main m = new Main();
		m.changeScene("TRRandevuAl.fxml", kimlikNo);
	}
	
	@FXML
	private void switchToMainTR(ActionEvent event) throws IOException {
		Main m = new Main();
		m.changeScene("MainTR.fxml");
	}
	
	
}
