package Model;

public class borrowDetail {
	private String id;
	private String name;
	private String returnDate;
	private String returnedDate;
	private String bookId;
	
	public borrowDetail(String id, String name, String returnDate, String returnedDate, String bookId) {
		super();
		this.id = id;
		this.name = name;
		this.returnDate = returnDate;
		this.returnedDate = returnedDate;
		this.bookId = bookId;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}
	public String getReturnedDate() {
		return returnedDate;
	}
	public void setReturnedDate(String returnedDate) {
		this.returnedDate = returnedDate;
	}
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	
	
}
