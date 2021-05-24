package Model;

public class Book {
	private String bookId;
	private String bookTitle;
	private String authorName;
	private String content;
	private long price;
	private int pages;
	private String publisher;
	private int pubYear;
	private int quantity;
	private int isBorrow;
	private int lost;
	private int available;
	
	
	
	public Book(String bookId, String bookTitle, long price) {
		super();
		this.bookId = bookId;
		this.bookTitle = bookTitle;
		this.price = price;
	}


	public Book(String bookId, String bookTitle, String authorName, String content, long price, int pages,
			String publisher, int pubYear, int quantity, int isBorrow, int lost) {
		super();
		this.bookId = bookId;
		this.bookTitle = bookTitle;
		this.authorName = authorName;
		this.content = content;
		this.price = price;
		this.pages = pages;
		this.publisher = publisher;
		this.pubYear = pubYear;
		this.quantity = quantity;
		this.isBorrow = isBorrow;
		this.lost = lost;
		this.available = quantity - lost - isBorrow;
	}


	public Book() {
		
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

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public int getPubYear() {
		return pubYear;
	}

	public void setPubYear(int pubYear) {
		this.pubYear = pubYear;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getIsBorrow() {
		return isBorrow;
	}

	public void setIsBorrow(int isBorrow) {
		this.isBorrow = isBorrow;
	}

	public int getLost() {
		return lost;
	}

	public void setLost(int lost) {
		this.lost = lost;
	}


	public int getAvailable() {
		return available;
	}


	public void setAvailable(int available) {
		this.available = available;
	}
	
}
