package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class TRAcilHastaController {
	@FXML
	private Button geriDonButon;
	
	@FXML
	private void switchToMainTR() throws IOException {
		Main m = new Main();
		m.changeScene("MainTR.fxml");
	}
	
}
