package application;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TRLoginController {
	public static String LOGGED_IN_TC;
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
			Long.parseLong(kimlikNo);
			if (kimlikNo.length() != 11) {
				wrongLogin.setText("Kimlik numaranızın 11 haneli olduğundan emin olunuz.");
				wrongLogin.setTextFill(Color.RED);
				return;
			}

			String sifre = sifrePasswordField.getText().trim();
			if (sifre.isBlank()) {
				wrongLogin.setText("Şifrenizi girdiğinizden emin olunuz.");
				wrongLogin.setTextFill(Color.RED);
				return;
			}

			String hashedSifre = hashSifre(sifre);
			try (Connection conn = DatabaseConnection.connect()) {
				String sql = "SELECT * FROM hasta WHERE kimlikNo = ? AND sifre = ?";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, kimlikNo);
				pstmt.setString(2, hashedSifre);
				ResultSet rs = pstmt.executeQuery();

				if (rs.next()) {
					LOGGED_IN_TC = kimlikNo;
					wrongLogin.setText("Giriş başarılı!");
					wrongLogin.setTextFill(Color.GREEN);

					// --- Burayı değiştiriyoruz: ---
					FXMLLoader loader = new FXMLLoader(
							getClass().getResource("/application/TRRandevuAl.fxml")
					);
					Parent root = loader.load();

					// 1) Controller’ı alıp TC’yi set et
					TRRandevuAlController ctrl = loader.getController();
					ctrl.setHastaKimlikNo(kimlikNo);

					// 2) Sahneyi değiştir
					Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
					stage.getScene().setRoot(root);
					// ---------------------------------
				} else {
					wrongLogin.setText("Kimlik numarası veya şifre hatalı.");
					wrongLogin.setTextFill(Color.RED);
				}
			}
		} catch (NumberFormatException e) {
			wrongLogin.setText("Kimlik numaranızda sadece rakam olduğundan emin olunuz.");
			wrongLogin.setTextFill(Color.RED);
		} catch (SQLException e) {
			wrongLogin.setText("Veritabanı hatası: " + e.getMessage());
			wrongLogin.setTextFill(Color.RED);
			e.printStackTrace();
		} catch (IOException e) {
			wrongLogin.setText("Ekran yüklenemedi. Lütfen yetkililerle iletişime geçin.");
			wrongLogin.setTextFill(Color.RED);
			e.printStackTrace();
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
	

	
	@FXML
	private void switchToMainTR(ActionEvent event) throws IOException {
		Main m = new Main();
		m.changeScene("MainTR.fxml");
	}
	
	
}
