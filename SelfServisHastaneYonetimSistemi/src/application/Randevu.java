package application;

public class Randevu {
	//Degiskenler
	private Doktor doktor;
	private Hasta hasta;
	
	//Getter ve Setterlar
	public Doktor getDoktor() {
		return doktor;
	}
	
	public void setDoktor(Doktor doktor) {
		this.doktor = doktor;
	}
	
	public Hasta getHasta() {
		return hasta;
	}
	
	public void setHasta(Hasta hasta) {
		this.hasta = hasta;
	}

	//Constructor
	public Randevu(Doktor doktor, Hasta hasta) {
		super();
		this.doktor = doktor;
		this.hasta = hasta;

		doktor.setSonSira(doktor.getSonSira() + 1); //Son sirayi 1 artir, yeni ekliyoruz.
		hasta.setSiraNo(doktor.getSonSira() + 1); //Hastanin sirasini ayarla.
	}
	
}
