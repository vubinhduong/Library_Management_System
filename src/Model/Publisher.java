package Model;

public class Publisher {
	private String name;
	private int nob;
		
	public Publisher(String name, int nob) {
		super();
		this.name = name;
		this.nob = nob;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNob() {
		return nob;
	}
	public void setNob(int nob) {
		this.nob = nob;
	}
	
}
