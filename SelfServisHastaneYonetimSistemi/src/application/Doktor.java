package application;

public class Doktor extends BaseUser {
	//Degiskenler
	private int sira;
	private int sonSira;
	private String alan;
	//private String odaNo; //id artık oda numarası
	
	//Getter ve Setterlar
	public int getSira() {
		return sira;
	}
	
	public void setSira(int sira) {
		this.sira = sira;
	}
	
	public int getSonSira() {
		return sonSira;
	}

	public void setSonSira(int sonsira) {
		this.sonSira = sonsira;
	}

	public String getAlan() {
		return alan;
	}
	
	public void setAlan(String alan) {
		this.alan = alan;
	}
	
	//Constructor
	public Doktor(int id, String eposta, String isim, String telNo, String alan) {
		super(id, eposta, isim, telNo);
		this.alan = alan;
	}
	
}
