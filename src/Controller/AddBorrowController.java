package Controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import Model.Book;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AddBorrowController implements Initializable {

	@FXML
	private TextField phieuId;
	@FXML
	private TextField username;
	@FXML
	private TextField deposit;
	@FXML
	private ComboBox<String> userId;
	@FXML
	private ComboBox<String> libId;
	@FXML
	private ComboBox<String> bookName;
	@FXML
	private DatePicker returnDay;
	@FXML
	private TableView<Book> booksView;
	@FXML
	private TableColumn<Book, String> bookIdCol;
	@FXML
	private TableColumn<Book, String> bookTitleCol;
	@FXML
	private TableColumn<Book, Long> priceCol;

	private java.sql.Connection conn;
	private String sql;
	private ResultSet rs;
	private Statement st;

	public ObservableList<Book> bookList;
	List<Book> books = new ArrayList<Book>();
	List<String> userIds = new ArrayList<String>();
	List<String> libIds = new ArrayList<String>();
	List<String> bookIds = new ArrayList<String>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			conn = Connection.main(null);
			st = conn.createStatement();
			initPage();
			init();
		} catch (Exception e) {

		}
	}

	public void initPage() {
		sql = "SELECT AccId FROM Account";
		try {
			rs = st.executeQuery(sql);
			while (rs.next()) {
				String id = rs.getString(1);
				userIds.add(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObservableList<String> listUserId = FXCollections.observableArrayList(userIds);
		userId.setItems(listUserId);
		sql = "SELECT LibId FROM Librarian";
		try {
			rs = st.executeQuery(sql);
			while (rs.next()) {
				String id = rs.getString(1);
				libIds.add(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObservableList<String> listLibId = FXCollections.observableArrayList(libIds);
		libId.setItems(listLibId);
		sql = "SELECT BookTitle FROM Book";
		try {
			rs = st.executeQuery(sql);
			while (rs.next()) {
				String id = rs.getString(1);
				bookIds.add(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObservableList<String> listBookName = FXCollections.observableArrayList(bookIds);
		bookName.setItems(listBookName);
	}

	@FXML
	public void showUsername() {
		sql = "SELECT Name FROM Account WHERE AccId = '" + userId.getValue() + "';";
		try {
			rs = st.executeQuery(sql);
			while (rs.next()) {
				String id = rs.getString(1);
				username.setText(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@FXML
	public void addBook() {

		String bookTitle = bookName.getValue();
		Date returnDate = Date.from(returnDay.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
		if (check(bookTitle, returnDay.getValue())) {
			Book book = getBook(bookTitle);
			bookList.add(book);
			books.add(book);
		}
	}
	
	public void init () {
		bookList = FXCollections.observableArrayList(books);
		bookIdCol.setCellValueFactory(new PropertyValueFactory<Book, String>("bookId"));
		bookTitleCol.setCellValueFactory(new PropertyValueFactory<Book, String>("bookTitle"));
		priceCol.setCellValueFactory(new PropertyValueFactory<Book, Long>("price"));
		booksView.setItems(bookList);
	}

	public boolean check(String name, LocalDate date) {
		if (date.isBefore(LocalDate.now())) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("Ngày hẹn trả phải sau ngày hôm nay!");
			alert.show();
			return false;
		}
		if (books != null) {
			for (Book book : books) {
				if (book.getBookTitle().equals(name)) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setContentText("Sách đã thêm trước đó!");
					alert.show();
					return false;
				}
			}
		}
		return true;
	}
	
	public Book getBook(String name) {
		sql = "SELECT BookId, BookTitle, Price FROM Book WHERE BookTitle = N'" + name + "';";
		Book book = null;
		try {
			rs = st.executeQuery(sql);
			while (rs.next()) {
				book = new Book(rs.getString(1), rs.getString(2), rs.getLong(3));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return book;
	}
	
	@FXML
	public void cancel(ActionEvent e) {
		bookList.removeAll(bookList);
		Stage stage = (Stage) phieuId.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	public void addPhieu() {
		String id = phieuId.getText();
		String uId = userId.getValue();
		String uname = username.getText();
		String lId = libId.getValue();
		String depo = deposit.getText();
		String bname = bookName.getValue();
		LocalDate returnD = returnDay.getValue();
			
		sql = "INSERT INTO PhieuMuon VALUES('" + id + "','" + uId + "',GETDATE(),'" + lId + "'," + depo + ");";
		try {
			System.out.println(sql);
			st.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (Book book : bookList) {
			String bookId = book.getBookId();
			String sql2 = "INSERT INTO Borrow_Book VALUES('" + id + "','" + bookId + "','" + returnD + "',NULL, 0);";
			String sql3 = "UPDATE Book SET IsBorrow = IsBorrow + 1 WHERE BookId = '" + book.getBookId()
			+ "';";
			try {
				System.out.println(sql2);
				System.out.println(sql3);
				st.execute(sql2);
				st.execute(sql3);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setContentText("Thêm thành công!");
		alert.show();
	}
}
