package application;

import javafx.collections.ObservableList;
import javafx.stage.Stage;

public class Randevu {
	//Degiskenler
	private Doktor doktor;
	private Hasta hasta;
	private String saat;
	
	
	//Getter ve Setterlar
	public Doktor getDoktor() {
		return doktor;
	}
	
	public String getDoktorIsim() {
		return doktor.getIsim();
	}
	
	public void setDoktor(Doktor doktor) {
		this.doktor = doktor;
	}
	
	public Hasta getHasta() {
		return hasta;
	}
	
	public String getHastaIsim() {
		return hasta.getIsim();
	}
	
	public void setHasta(Hasta hasta) {
		this.hasta = hasta;
	}
	
	public String getSaat() {
		return saat;
	}

	public void setSaat(String saat) {
		this.saat = saat;
	}

	//Constructor
	public Randevu(Doktor doktor, Hasta hasta, String saat) {
		this.doktor = doktor;
		this.hasta = hasta;
		this.saat = saat;


	}
	
}
