package Model;

public class Author {
	private String name;
	private int nob;
	
	public Author() {
		super();
	}
	
	public Author(String name, int nob) {
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
