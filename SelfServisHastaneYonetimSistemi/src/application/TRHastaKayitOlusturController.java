package application;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;

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
	private TextField istenenIsim, istenenTelNo, istenenEPosta, istenenKimlik, istenenSifre, istenenSifreTekrar;
	
	@FXML
	private void switchToMainTR() throws IOException {
		Main m = new Main();
		m.changeScene("MainTR.fxml");
	}
	
	@FXML
	private void kayitOl() {
		String istenenKimlikTxt = istenenKimlik.getText().trim();
		
		try {
			Long.parseLong(istenenKimlikTxt); //Kimlik numarası alanına harf girilmişse NumberFormatException.
			
			if(istenenKimlikTxt.length() != 11) {
				badAttempt.setTextFill(Color.RED);
				badAttempt.setText("Kimlik numaranızın 11 haneli olduğundan emin olunuz.");
			}
			
			else {
				String sifreText = istenenSifre.getText().trim();
				String sifreTekrarText = istenenSifreTekrar.getText().trim();
				
				String isimText = istenenIsim.getText().trim();
				String telNoText = istenenTelNo.getText().trim();
				String epostaText = istenenEPosta.getText().trim();
				
				if(sifreText.isEmpty() || sifreTekrarText.isEmpty() || isimText.isEmpty() || telNoText.isEmpty() || epostaText.isEmpty()) {
					badAttempt.setTextFill(Color.RED);
					badAttempt.setText("Tüm alanları doldurduğunuzdan emin olunuz.");
				}
				else if (!sifreText.equals(sifreTekrarText)) {
					badAttempt.setTextFill(Color.RED);
					badAttempt.setText("Şifreler eşleşmiyor.");
				}

				else {
					String hashedSifre = hashSifre(sifreText);

					if (hastaKaydet(epostaText, isimText, telNoText, istenenKimlikTxt, hashedSifre)) {
						badAttempt.setTextFill(Color.GREEN);
						badAttempt.setText("Kaydınız başarıyla oluşturuldu.");
					} else {
						badAttempt.setTextFill(Color.RED);
						badAttempt.setText("Kayıt sırasında bir hata oluştu.");
					}
				}
			}
		}
		
		catch(NumberFormatException e) {
			badAttempt.setTextFill(Color.RED);
			badAttempt.setText("Kimlik numaranızın harf, sembol vb. karakterler içermediğinden emin olunuz.");
		}
		
	}

	//SHA-256 yöntemi ile şifreleme
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

	private boolean hastaKaydet(String eposta, String isim, String telNo, String kimlikNo, String sifre) {
		String sql = "INSERT INTO hasta (eposta, isim, telNo, kimlikNo, sifre) VALUES (?, ?, ?, ?, ?)";

		try (Connection conn = DatabaseConnection.connect();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, eposta);
			pstmt.setString(2, isim);
			pstmt.setString(3, telNo);
			pstmt.setString(4, kimlikNo);
			pstmt.setString(5, sifre);
			pstmt.executeUpdate();
			return true;
		} catch (Exception e) {
			System.out.println("Hata oluştu: " + e.getMessage());
			return false;
		}
	}
}
