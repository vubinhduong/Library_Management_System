package Controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXToggleButton;

import Model.Author;
import Model.Book;
import Model.Borrow;
import Model.Publisher;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class StatisticController implements Initializable{
	
	@FXML
	private TableView<Book> booksView;
	@FXML
	private TableColumn<User, String> bookIdCol;
	@FXML
	private TableColumn<User, String> bookTitleCol;
	@FXML
	private TableColumn<User, Integer> isBorrowCol;
	@FXML
	private TableColumn<User, Integer> availableCol;
	@FXML
	private TableColumn<User, String> authorNameCol;
	@FXML
	private TableColumn<User, Long> priceCol;
	@FXML
	private TableColumn<User, String> publisherCol;
	
	@FXML
	private JFXComboBox<String> genreField;
	@FXML
	private JFXComboBox<String> authorField;
	@FXML
	private JFXComboBox<String> publisherField;
	
	@FXML 
	private JFXTabPane tabpane;
	
	@FXML
	private TableView<Author> authorView;
	@FXML
	private TableColumn<Author, String> nameAuCol;
	@FXML
	private TableColumn<Author, Integer> nobAuCol;
	
	@FXML
	private TableView<Publisher> pubView;
	@FXML
	private TableColumn<Publisher, String> namePubCol;
	@FXML
	private TableColumn<Publisher, Integer> nobPubCol;
	
	@FXML
	private TableView<User> userView;
	@FXML
	private TableColumn<User, String> userIdCol2;
	@FXML
	private TableColumn<User, String> usernameCol;
	@FXML
	private TableColumn<User, String> birthCol;
	@FXML
	private TableColumn<User, String> contactCol;
	
	
	@FXML
	private TableView<Borrow> borrowsView;
	@FXML
	private TableColumn<Borrow, String> phieuIdCol;
	@FXML
	private TableColumn<Borrow, String> userIdCol;
	@FXML
	private TableColumn<Borrow, String> bookTitleCol2;
	@FXML
	private TableColumn<Borrow, String> borrowDayCol;
	@FXML
	private TableColumn<Borrow, String> libIdCol;
	@FXML
	private TableColumn<Borrow, String> returnDayCol;
	@FXML
	private TableColumn<Borrow, String> returnedDayCol;
	
	@FXML
	private JFXToggleButton isBorrowedBtn;
	@FXML
	private JFXToggleButton isOverdatedBtn;
	
	private java.sql.Connection conn;
	private String sql;
	private ResultSet rs;
	private Statement st;
	
	public ObservableList<Book> bookList;
	public ObservableList<Author> auList;
	public ObservableList<Publisher>pubList;
	public ObservableList<User>userList;
	public ObservableList<Borrow> borrowList;
	List<Book> books = new ArrayList<Book>();
	List<Author> authors = new ArrayList<Author>();
	List<Publisher> publishers = new ArrayList<Publisher>();
	List<User> users = new ArrayList<User>();
	List<Borrow> borrows = new ArrayList<Borrow>();
	
	ObservableList<String> listGenres = FXCollections.observableArrayList("Truyện teen", "Tuổi học trò",
			"Nước ngoài", "Tiểu thuyết", "Lãng mạn", "Cổ tích", "Nước ngoài", "Phù thủy", "Top bán chạy");
	ObservableList<String> listAuthor = FXCollections.observableArrayList("Nguyễn Nhật Ánh", "Rosie Nguyễn",
			"Paulo Coelho", "Dale Carnegie", "Shinkai Makoto", "Hector Malot", "Hemingway", "J.K.Rowling");
	ObservableList<String> listPublisher = FXCollections.observableArrayList("NXB Kim Đồng", "NXB Hội Nhà Văn",
			"NXB Văn học", "NXB Tổng Hợp TPHCM", "NXB IPM", "NXB Trẻ");
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			conn = Connection.main(null);
			st = conn.createStatement();
			initBookpage(st);
			initAuPage(st);
			initPubPage(st);
			initUserPage(st);
			initBorrowPage(st);
			
			genreField.setItems(listGenres);
			authorField.setItems(listAuthor);
			publisherField.setItems(listPublisher);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setDefaultTab(int i) {
		SingleSelectionModel<Tab> selectionModel = tabpane.getSelectionModel();
		selectionModel.select(i);
	}
	
	public void initBookpage(Statement st) {
		sql = "SELECT * FROM Book";
		try {
			rs = st.executeQuery(sql);

			while (rs.next()) {
				Book book = new Book(rs.getString("BookId"), rs.getString("BookTitle"), rs.getString("AuthorName"),
						rs.getString("Content"), rs.getLong("Price"), rs.getInt("Pages"), rs.getString("Publisher"),
						rs.getInt("PubYear"), rs.getInt("Quantity"), rs.getInt("IsBorrow"), rs.getInt("Lost"));
				books.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		bookList = FXCollections.observableArrayList(books);
		bookIdCol.setCellValueFactory(new PropertyValueFactory<User, String>("bookId"));
		bookTitleCol.setCellValueFactory(new PropertyValueFactory<User, String>("bookTitle"));
		isBorrowCol.setCellValueFactory(new PropertyValueFactory<User, Integer>("isBorrow"));
		availableCol.setCellValueFactory(new PropertyValueFactory<User, Integer>("available"));
		authorNameCol.setCellValueFactory(new PropertyValueFactory<User, String>("authorName"));
		priceCol.setCellValueFactory(new PropertyValueFactory<User, Long>("price"));
		publisherCol.setCellValueFactory(new PropertyValueFactory<User, String>("publisher"));
		booksView.setItems(bookList);
	}
	
	public void initAuPage(Statement st) {
		sql = "SELECT AuthorName, SUM(Quantity) AS SL FROM Book GROUP BY AuthorName";
		try {
			rs = st.executeQuery(sql);

			while (rs.next()) {
				Author author = new Author(rs.getString(1), rs.getInt(2));
				authors.add(author);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		auList = FXCollections.observableArrayList(authors);
		nameAuCol.setCellValueFactory(new PropertyValueFactory<Author, String>("name"));
		nobAuCol.setCellValueFactory(new PropertyValueFactory<Author, Integer>("nob"));
		authorView.setItems(auList);
	}
	
	public void initPubPage(Statement st) {
		sql = "SELECT Publisher, SUM(Quantity) AS SL FROM Book GROUP BY Publisher";
		try {
			rs = st.executeQuery(sql);

			while (rs.next()) {
				Publisher publisher = new Publisher(rs.getString(1), rs.getInt(2));
				publishers.add(publisher);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		pubList = FXCollections.observableArrayList(publishers);
		namePubCol.setCellValueFactory(new PropertyValueFactory<Publisher, String>("name"));
		nobPubCol.setCellValueFactory(new PropertyValueFactory<Publisher, Integer>("nob"));
		pubView.setItems(pubList);
	}
	
	public void initUserPage(Statement st) {
		sql = "SELECT * FROM Account";
		try {
			rs = st.executeQuery(sql);

			while (rs.next()) {
				User user = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		userList = FXCollections.observableArrayList(users);
		userIdCol2.setCellValueFactory(new PropertyValueFactory<User, String>("accId"));
		usernameCol.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
		birthCol.setCellValueFactory(new PropertyValueFactory<User, String>("birth"));
		contactCol.setCellValueFactory(new PropertyValueFactory<User, String>("contact"));
		userView.setItems(userList);
	}
	
	public void initBorrowPage(Statement st) {
		sql = "SELECT p.PhieuId, p.AccId, a.Name, bk.BookId, BookTitle, BorrowDay, p.LibId, Username, Deposit, State, ReturnDay, ReturnedDay\r\n"
				+ "FROM Borrow_Book bk, Account a, Librarian l, Book b, PhieuMuon p\r\n"
				+ "WHERE (bk.BookId = b.BookId) AND (bk.PhieuId = p.PhieuId) AND (p.AccId = a.AccId) AND (p.LibId = l.LibId)";
		System.out.println(sql);
		try {
			rs = st.executeQuery(sql);
			while (rs.next()) {
				Borrow borrow = new Borrow(rs.getString("PhieuId"), rs.getString("AccId"), rs.getString("Name"),
						rs.getString("BookId"), rs.getString("BookTitle"), rs.getString("BorrowDay"),
						rs.getString("LibId"), rs.getString("Username"), rs.getInt("Deposit"), rs.getInt("State"),
						rs.getString("ReturnDay"), rs.getString("ReturnedDay"));
				borrows.add(borrow);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		borrowList = FXCollections.observableArrayList(borrows);
		phieuIdCol.setCellValueFactory(new PropertyValueFactory<Borrow, String>("phieuId"));
		userIdCol.setCellValueFactory(new PropertyValueFactory<Borrow, String>("accId"));
		bookTitleCol2.setCellValueFactory(new PropertyValueFactory<Borrow, String>("bookTitle"));
		borrowDayCol.setCellValueFactory(new PropertyValueFactory<Borrow, String>("borrowDay"));
		libIdCol.setCellValueFactory(new PropertyValueFactory<Borrow, String>("libId"));
		returnDayCol.setCellValueFactory(new PropertyValueFactory<Borrow, String>("returnDay"));
		returnedDayCol.setCellValueFactory(new PropertyValueFactory<Borrow, String>("returnedDay"));
		borrowsView.setItems(borrowList);
	}
	
	@FXML
	public void printBook() {
		XSSFWorkbook workbook = new XSSFWorkbook();
	    XSSFSheet sheet = workbook.createSheet("1");
	    int rowNum = 0;
	    for (Book book : bookList) {
	      Row row = sheet.createRow(rowNum++);
	      Cell cell1 = row.createCell(0);
	      cell1.setCellValue(book.getBookId());
	      Cell cell2 = row.createCell(1);
	      cell2.setCellValue(book.getBookTitle());
	      Cell cell3 = row.createCell(2);
	      cell3.setCellValue(book.getAvailable());
	      Cell cell4 = row.createCell(3);
	      cell4.setCellValue(book.getIsBorrow());
	      Cell cell5 = row.createCell(4);
	      cell5.setCellValue(book.getAuthorName());
	      Cell cell6 = row.createCell(5);
	      cell6.setCellValue(book.getPrice());
	      Cell cell7 = row.createCell(6);
	      cell7.setCellValue(book.getPublisher());
	    }
	    Row row2 = sheet.createRow(rowNum++);
	    Cell cell8 = row2.createCell(7);
	    cell8.setCellValue("Ngày in:");
	    Cell cell9 = row2.createCell(8);
	    cell8.setCellValue("2021-1-8");
	    try {
	      FileOutputStream outputStream = new FileOutputStream("Book-List.xlsx");
	      workbook.write(outputStream);
	      workbook.close();
	      Process process = Runtime.getRuntime().exec("cmd /c start Book-List.xlsx");
	    } catch (FileNotFoundException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	}
	
	@FXML
	public void printAuthor() {
		XSSFWorkbook workbook = new XSSFWorkbook();
	    XSSFSheet sheet = workbook.createSheet("2");
	    int rowNum = 0;
	    for (Author author : auList) {
	      Row row = sheet.createRow(rowNum++);
	      Cell cell1 = row.createCell(0);
	      cell1.setCellValue(author.getName());
	      Cell cell2 = row.createCell(1);
	      cell2.setCellValue(author.getNob());
	    }
	    try {
	      FileOutputStream outputStream = new FileOutputStream("Author-List.xlsx");
	      workbook.write(outputStream);
	      workbook.close();
	    } catch (FileNotFoundException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	}
	
	@FXML
	public void printPub() {
		XSSFWorkbook workbook = new XSSFWorkbook();
	    XSSFSheet sheet = workbook.createSheet("3");
	    int rowNum = 0;
	    for (Publisher publisher : pubList) {
	      Row row = sheet.createRow(rowNum++);
	      Cell cell1 = row.createCell(0);
	      cell1.setCellValue(publisher.getName());
	      Cell cell2 = row.createCell(1);
	      cell2.setCellValue(publisher.getNob());
	    }
	    try {
	      FileOutputStream outputStream = new FileOutputStream("Publisher-List.xlsx");
	      workbook.write(outputStream);
	      workbook.close();
	    } catch (FileNotFoundException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	}
	
	@FXML
	public void printUser() {
		XSSFWorkbook workbook = new XSSFWorkbook();
	    XSSFSheet sheet = workbook.createSheet("4");
	    int rowNum = 0;
	    for (User user : userList) {
	      Row row = sheet.createRow(rowNum++);
	      Cell cell1 = row.createCell(0);
	      cell1.setCellValue(user.getAccId());
	      Cell cell2 = row.createCell(1);
	      cell2.setCellValue(user.getName());
	      Cell cell3 = row.createCell(2);
	      cell3.setCellValue(user.getBirth());
	      Cell cell4 = row.createCell(3);
	      cell4.setCellValue(user.getContact()); 
	    }
	    try {
	      FileOutputStream outputStream = new FileOutputStream("User-List.xlsx");
	      workbook.write(outputStream);
	      workbook.close();
	    } catch (FileNotFoundException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	}
	
	@FXML
	public void borrow() {
		XSSFWorkbook workbook = new XSSFWorkbook();
	    XSSFSheet sheet = workbook.createSheet("5");
	    int rowNum = 0;
	    for (Borrow borrow : borrowList) {
	      Row row = sheet.createRow(rowNum++);
	      Cell cell1 = row.createCell(0);
	      cell1.setCellValue(borrow.getPhieuId());
	      Cell cell2 = row.createCell(1);
	      cell2.setCellValue(borrow.getAccId());
	      Cell cell3 = row.createCell(2);
	      cell3.setCellValue(borrow.getBookTitle());
	      Cell cell4 = row.createCell(3);
	      cell4.setCellValue(borrow.getLibId());
	      Cell cell5 = row.createCell(4);
	      cell5.setCellValue(borrow.getBorrowDay());
	      Cell cell6 = row.createCell(5);
	      cell6.setCellValue(borrow.getReturnDay());
	      Cell cell7 = row.createCell(6);
	      cell7.setCellValue(borrow.getReturnedDay());
	    }
	    try {
	      FileOutputStream outputStream = new FileOutputStream("Borrow-List.xlsx");
	      workbook.write(outputStream);
	      workbook.close();
	    } catch (FileNotFoundException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	}
	
	@FXML
	public void searchBookByGenre() {
		String genre = "";
		if (!genreField.getSelectionModel().isEmpty()) {
			genre = genreField.getValue();
		}
		books.removeAll(books);
		sql = "SELECT Book.* FROM Book INNER JOIN Book_Genre ON Book.BookId = Book_Genre.BookId WHERE Genre like N'%" + genre + "%';";
		System.out.println(sql);
		try {
			rs = st.executeQuery(sql);

			while (rs.next()) {
				Book book = new Book(rs.getString("BookId"), rs.getString("BookTitle"), rs.getString("AuthorName"),
						rs.getString("Content"), rs.getLong("Price"), rs.getInt("Pages"), rs.getString("Publisher"),
						rs.getInt("PubYear"), rs.getInt("Quantity"), rs.getInt("IsBorrow"), rs.getInt("Lost"));
				books.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		bookList.removeAll(bookList);
		bookList.addAll(books);
	}
	
	@FXML
	public void searchBookByAuthor() {
		String author = "";
		if (!authorField.getSelectionModel().isEmpty()) {
			author = authorField.getValue();
		}
		books.removeAll(books);
		sql = "SELECT * FROM Book WHERE AuthorName like N'%" + author + "%';";
		System.out.println(sql);
		try {
			rs = st.executeQuery(sql);

			while (rs.next()) {
				Book book = new Book(rs.getString("BookId"), rs.getString("BookTitle"), rs.getString("AuthorName"),
						rs.getString("Content"), rs.getLong("Price"), rs.getInt("Pages"), rs.getString("Publisher"),
						rs.getInt("PubYear"), rs.getInt("Quantity"), rs.getInt("IsBorrow"), rs.getInt("Lost"));
				books.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		bookList.removeAll(bookList);
		bookList.addAll(books);
	}
	
	@FXML
	public void searchBookByPublisher() {
		String publisher = "";
		if (!publisherField.getSelectionModel().isEmpty()) {
			publisher = publisherField.getValue();
		}
		books.removeAll(books);
		sql = "SELECT * FROM Book WHERE Publisher like N'%" + publisher + "%';";
		System.out.println(sql);
		try {
			rs = st.executeQuery(sql);

			while (rs.next()) {
				Book book = new Book(rs.getString("BookId"), rs.getString("BookTitle"), rs.getString("AuthorName"),
						rs.getString("Content"), rs.getLong("Price"), rs.getInt("Pages"), rs.getString("Publisher"),
						rs.getInt("PubYear"), rs.getInt("Quantity"), rs.getInt("IsBorrow"), rs.getInt("Lost"));
				books.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		bookList.removeAll(bookList);
		bookList.addAll(books);
	}
	
	@FXML
	public void resetBookPage() {
		genreField.getItems().clear();
		authorField.getItems().clear();
		publisherField.getItems().clear();
		initBookpage(st);
	}
	
	@FXML
	public void resetBorrowPage() {
		borrows.removeAll(borrows);
		initBorrowPage(st);
	}
	
	@FXML
	public void isBorrowed(ActionEvent e) {
		if (isBorrowedBtn.isSelected()) {
			borrows.removeAll(borrows);
			for (Borrow borrow : borrowList) {
				if (borrow.getState() == 0) {
					borrows.add(borrow);
				}
			}
			borrowList.removeAll(borrowList);
			borrowList.addAll(borrows);
		} else {
			resetBorrowPage();
		}
	}

	public void isOverdated(ActionEvent e) {
		if (isOverdatedBtn.isSelected()) {
			borrows.removeAll(borrows);
			for (Borrow borrow : borrowList) {
				LocalDate returnDay = LocalDate.parse(borrow.getReturnDay());
				LocalDate now = LocalDate.now();
				if (returnDay.isBefore(now) && borrow.getState() == 0) {
					borrows.add(borrow);
				}
			}
			borrowList.removeAll(borrowList);
			borrowList.addAll(borrows);
		} else {
			resetBorrowPage();
		}
	}
	
	@FXML
	public void cancel(ActionEvent e) {
		Stage stage = (Stage) authorField.getScene().getWindow();
		stage.close();
	}
}
