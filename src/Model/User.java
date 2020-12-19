package Model;

public class User {
	private String accId;
	private String name;
	private String birth;
	private String contact;
	
		
	public User(String accId, String name, String birth, String contact) {
		super();
		this.accId = accId;
		this.name = name;
		this.birth = birth;
		this.contact = contact;
	}
	
	
	public String getAccId() {
		return accId;
	}
	public void setAccId(String accId) {
		this.accId = accId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	
}
