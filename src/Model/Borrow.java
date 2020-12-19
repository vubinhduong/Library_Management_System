package Model;

public class Borrow {
	private String phieuId;
	private String accId;
	private String name;
	private String bookId;
	private String bookTitle;
	private String borrowDay;
	private String libId;
	private String libName;
	private int deposit;
	private int state;
	private String returnDay;
	private String returnedDay;
	
	
	public Borrow(String phieuId, String accId, String name, String bookId, String bookTitle, String borrowDay,
			String libId, String libName, int deposit, int state, String returnDay, String returnedDay) {
		super();
		this.phieuId = phieuId;
		this.accId = accId;
		this.name = name;
		this.bookId = bookId;
		this.bookTitle = bookTitle;
		this.borrowDay = borrowDay;
		this.libId = libId;
		this.libName = libName;
		this.deposit = deposit;
		this.state = state;
		this.returnDay = returnDay;
		this.returnedDay = returnedDay;
	}


	public String getPhieuId() {
		return phieuId;
	}


	public void setPhieuId(String phieuId) {
		this.phieuId = phieuId;
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


	public String getBookId() {
		return bookId;
	}


	public void setBookId(String bookId) {
		this.bookId = bookId;
	}


	public String getBookTitle() {
		return bookTitle;
	}


	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}


	public String getBorrowDay() {
		return borrowDay;
	}


	public void setBorrowDay(String borrowDay) {
		this.borrowDay = borrowDay;
	}


	public String getLibId() {
		return libId;
	}


	public void setLibId(String libId) {
		this.libId = libId;
	}


	public String getLibName() {
		return libName;
	}


	public void setLibName(String libName) {
		this.libName = libName;
	}


	public int getDeposit() {
		return deposit;
	}


	public void setDeposit(int deposit) {
		this.deposit = deposit;
	}


	public int getState() {
		return state;
	}


	public void setState(int state) {
		this.state = state;
	}


	public String getReturnDay() {
		return returnDay;
	}


	public void setReturnDay(String returnDay) {
		this.returnDay = returnDay;
	}


	public String getReturnedDay() {
		return returnedDay;
	}


	public void setReturnedDay(String returnedDay) {
		this.returnedDay = returnedDay;
	}
	
	
	
}
