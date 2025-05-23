package application;

public class Hasta extends BaseUser {
	//Degiskenler
	private String kimlikNo;
	private String sifre;
	
	//Getter ve Setterlar
	public String getKimlikNo() {
		return kimlikNo;
	}
	
	public void setKimlikNo(String kimlikNo) {
		this.kimlikNo = kimlikNo;
	}
	
	public String getSifre() {
		return sifre;
	}
	
	public void setSifre(String sifre) {
		this.sifre = sifre;
	}

	//Constructor
	public Hasta(int id, String eposta, String isim, String telNo, String kimlikNo, String sifre) {
		super(id, eposta, isim, telNo);
		this.kimlikNo = kimlikNo;
		this.sifre = sifre;
	}
	
	@Override
	public String toString() {
		return ("id:" + getId() + " eposta:" + getEposta() + " isim:" + getIsim() + " telNo:" + getTelNo() + " kimlikNo:" + kimlikNo);
	}
}
