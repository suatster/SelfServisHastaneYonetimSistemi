package application;

public abstract class BaseUser {
	//Degiskenler
	private int id;
	private String eposta;
	private String isim;
	private String telNo;
	
	//Getter ve Setterlar
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getEposta() {
		return eposta;
	}
	
	public void setEposta(String eposta) {
		this.eposta = eposta;
	}
	
	public String getIsim() {
		return isim;
	}
	
	public void setIsim(String isim) {
		this.isim = isim;
	}
	
	public String getTelNo() {
		return telNo;
	}
	
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
	
	//Constructor
	public BaseUser(int id, String eposta, String isim, String telNo) {
		this.id = id;
		this.eposta = eposta;
		this.isim = isim;
		this.telNo = telNo;
	}
	
	//toString() function for future debugging purposes
	public String toString() {
		return ("id:" + id + " eposta:" + eposta + " isim:" + isim + " telNo:" + telNo);
	}
	
}
