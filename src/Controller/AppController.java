package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;

import Model.Book;
import Model.Borrow;
import Model.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AppController implements Initializable {

	@FXML
	private TabPane tabPane;
	@FXML
	private TableView<User> usersView;
	@FXML
	private TableColumn<User, String> accIdCol;
	@FXML
	private TableColumn<User, String> nameCol;
	@FXML
	private TableColumn<User, String> birthCol;
	@FXML
	private TableColumn<User, String> contactCol;
	@FXML
	private Text numberOfUsers;

	@FXML
	private TextField userIdField;
	@FXML
	private TextField nameUserField;
	@FXML
	private JFXDatePicker birthFromField;
	@FXML
	private JFXDatePicker birthToField;

	@FXML
	private TableView<Book> booksView;
	@FXML
	private TableColumn<User, String> bookIdCol;
	@FXML
	private TableColumn<User, String> bookTitleCol;
	@FXML
	private TableColumn<User, Integer> quantityCol;
	@FXML
	private TableColumn<User, Integer> isBorrowCol;
	@FXML
	private TableColumn<User, Integer> lostCol;
	@FXML
	private TableColumn<User, Integer> availableCol;
	@FXML
	private TableColumn<User, String> authorNameCol;
	@FXML
	private TableColumn<User, Long> priceCol;
	@FXML
	private TableColumn<User, Integer> pagesCol;
	@FXML
	private TableColumn<User, String> publisherCol;
	@FXML
	private TableColumn<User, Integer> pubYearCol;

	@FXML
	private JFXTextField bookIdField;
	@FXML
	private JFXTextField bookTitleField;
	@FXML
	private JFXTextField authorField;
	@FXML
	private JFXTextField priceFromField;
	@FXML
	private JFXTextField priceToField;
	@FXML
	private JFXComboBox<String> publisherField;
	@FXML
	private JFXToggleButton outOfStockBtn;

	@FXML
	private TableView<Borrow> borrowsView;
	@FXML
	private TableColumn<Borrow, String> phieuIdCol;
	@FXML
	private TableColumn<Borrow, String> usernameCol;
	@FXML
	private TableColumn<Borrow, String> bookTitleCol2;
	@FXML
	private TableColumn<Borrow, String> borrowDayCol;
	@FXML
	private TableColumn<Borrow, String> librarianCol;
	@FXML
	private TableColumn<Borrow, Integer> depositCol;
	@FXML
	private TableColumn<Borrow, Integer> stateCol;
	@FXML
	private TableColumn<Borrow, String> returnDayCol;
	@FXML
	private TableColumn<Borrow, String> returnedDayCol;

	@FXML
	private TextField phieuIdField;
	@FXML
	private TextField userIdBField;
	@FXML
	private JFXToggleButton isBorrowedBtn;
	@FXML
	private JFXToggleButton isReturnedBtn;
	@FXML
	private JFXToggleButton isOverdatedBtn;

	@FXML
	private GridPane bookGrid;
	@FXML
	private GridPane genresGrid;
	@FXML
	private GridPane authorGrid;
	@FXML
	private GridPane publisherGrid;

	private java.sql.Connection conn;
	private String sql;
	private ResultSet rs;
	private Statement st;

	public ObservableList<User> userList;
	public ObservableList<Book> bookList;
	public ObservableList<Borrow> borrowList;
	ObservableList<String> listPublisher = FXCollections.observableArrayList("", "NXB Kim Đồng", "NXB Hội Nhà Văn",
			"NXB Văn học", "NXB Tổng Hợp TPHCM", "NXB IPM", "NXB Trẻ");

	List<Book> books = new ArrayList<Book>();
	List<User> users = new ArrayList<User>();
	List<Borrow> borrows = new ArrayList<Borrow>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			conn = Connection.main(null);
			st = conn.createStatement();

			initBookpage(st);
			initUserpage(st);
			initBorrowPage(st);

			publisherField.setItems(listPublisher);

			booksView.setRowFactory(tv -> {
				TableRow<Book> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (!row.isEmpty())) {
						Book selectedBook = row.getItem();
						detailAndUpdate(selectedBook);
					}
				});
				return row;
			});

		} catch (SQLException e) {
			e.printStackTrace();
		}

		bookStatis();
		genresStatis();
		authorStatis();
		publisherStatis();
	}

	@FXML
	public void addBookView(ActionEvent e) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/Application/AddBook.fxml"));
		Parent parent = null;
		try {
			parent = loader.load();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Scene scene = new Scene(parent);
		scene.getStylesheets().addAll(this.getClass().getResource("/Application/style.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}

	public void addUserView(ActionEvent e) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/Application/AddUser.fxml"));
		Parent parent = null;
		try {
			parent = loader.load();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Scene scene = new Scene(parent);
		scene.getStylesheets().addAll(this.getClass().getResource("/Application/style.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	public void statisticView(ActionEvent e) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/Application/BookStatistic.fxml"));
		Parent parent = null;
		try {
			parent = loader.load();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Scene scene = new Scene(parent);
		scene.getStylesheets().addAll(this.getClass().getResource("/Application/style3.css").toExternalForm());
		stage.setScene(scene);
		StatisticController sc = loader.getController();
		int i = tabPane.getSelectionModel().getSelectedIndex();
		if (i > 1) {
			i = i + 2;
		}
		sc.setDefaultTab(i);
		stage.show();
	}

	@FXML
	void deleteBook(ActionEvent e) {
		Alert alert = new Alert(AlertType.CONFIRMATION, "Xác nhận xóa?", ButtonType.YES, ButtonType.NO,
				ButtonType.CANCEL);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.YES) {
			Book bookSelected = booksView.getSelectionModel().getSelectedItem();
			sql = "DELETE FROM Book WHERE BookId = '" + bookSelected.getBookId() + "';";
			try {
				st.execute(sql);
			} catch (SQLException e1) {
				Alert alert2 = new Alert(AlertType.INFORMATION);
				alert.setContentText("Xóa thất bại");
			}
			bookList.remove(bookSelected);
			Alert alert2 = new Alert(AlertType.INFORMATION);
			alert.setContentText("Xóa thành công!");
		}
	}

	@FXML
	void deleteUser(ActionEvent e) {
		Alert alert = new Alert(AlertType.CONFIRMATION, "Xác nhận xóa?", ButtonType.YES, ButtonType.NO,
				ButtonType.CANCEL);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.YES) {
			User userSelected = usersView.getSelectionModel().getSelectedItem();
			sql = "DELETE FROM Account WHERE AccId = '" + userSelected.getAccId() + "';";
			System.out.println(sql);
			try {
				st.execute(sql);
			} catch (SQLException e1) {
				Alert alert2 = new Alert(AlertType.INFORMATION);
				alert.setContentText("Xóa thất bại");
				e1.printStackTrace();
			}
			userList.remove(userSelected);
			Alert alert2 = new Alert(AlertType.INFORMATION);
			alert.setContentText("Xóa thành công!");
		}
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
		quantityCol.setCellValueFactory(new PropertyValueFactory<User, Integer>("quantity"));
		isBorrowCol.setCellValueFactory(new PropertyValueFactory<User, Integer>("isBorrow"));
		lostCol.setCellValueFactory(new PropertyValueFactory<User, Integer>("lost"));
		availableCol.setCellValueFactory(new PropertyValueFactory<User, Integer>("available"));
		authorNameCol.setCellValueFactory(new PropertyValueFactory<User, String>("authorName"));
		priceCol.setCellValueFactory(new PropertyValueFactory<User, Long>("price"));
		pagesCol.setCellValueFactory(new PropertyValueFactory<User, Integer>("pages"));
		publisherCol.setCellValueFactory(new PropertyValueFactory<User, String>("publisher"));
		pubYearCol.setCellValueFactory(new PropertyValueFactory<User, Integer>("pubYear"));
		booksView.setItems(bookList);
	}

	public void initUserpage(Statement st) {
		sql = "SELECT * FROM Account";
		try {
			rs = st.executeQuery(sql);
			while (rs.next()) {
				User user = new User(rs.getString("AccId"), rs.getString("Name"), rs.getString("Birth"),
						rs.getString("Contact"));
				users.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		userList = FXCollections.observableArrayList(users);
		accIdCol.setCellValueFactory(new PropertyValueFactory<User, String>("accId"));
		nameCol.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
		birthCol.setCellValueFactory(new PropertyValueFactory<User, String>("birth"));
		contactCol.setCellValueFactory(new PropertyValueFactory<User, String>("contact"));
		usersView.setItems(userList);

		numberOfUsers.setText(Integer.toString(users.size()));
	}

	public void initBorrowPage(Statement st) {
		sql = "SELECT p.PhieuId, p.AccId, a.Name, bk.BookId, BookTitle, BorrowDay, p.LibId, Username, Deposit, State, ReturnDay, ReturnedDay\r\n"
				+ "FROM Borrow_Book bk, Account a, Librarian l, Book b, PhieuMuon p\r\n"
				+ "WHERE (bk.BookId = b.BookId) AND (bk.PhieuId = p.PhieuId) AND (p.AccId = a.AccId) AND (p.LibId = l.LibId)";
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
		usernameCol.setCellValueFactory(new PropertyValueFactory<Borrow, String>("name"));
		bookTitleCol2.setCellValueFactory(new PropertyValueFactory<Borrow, String>("bookTitle"));
		borrowDayCol.setCellValueFactory(new PropertyValueFactory<Borrow, String>("borrowDay"));
		librarianCol.setCellValueFactory(new PropertyValueFactory<Borrow, String>("libName"));
		depositCol.setCellValueFactory(new PropertyValueFactory<Borrow, Integer>("deposit"));
		stateCol.setCellValueFactory(new PropertyValueFactory<Borrow, Integer>("state"));
		returnDayCol.setCellValueFactory(new PropertyValueFactory<Borrow, String>("returnDay"));
		returnedDayCol.setCellValueFactory(new PropertyValueFactory<Borrow, String>("returnedDay"));
		borrowsView.setItems(borrowList);
	}

	@FXML
	public void resetBookpage() {
		bookIdField.clear();
		bookTitleField.clear();
		authorField.clear();
		publisherField.getItems().clear();
		priceToField.clear();
		priceFromField.clear();
		books.removeAll(books);
		initBookpage(st);
	}

	@FXML
	public void resetUserpage() {
		userIdField.clear();
		nameUserField.clear();
		birthFromField.setValue(null);
		birthToField.setValue(null);
		users.removeAll(users);
		initUserpage(st);
	}

	public void resetBorrowpage() {
		borrows.removeAll(borrows);
		initBorrowPage(st);
	}

	@FXML
	public void searchBook() {
		String bookId = "";
		String bookTitle = "";
		String authorName = "";
		int priceFrom = 0;
		int priceTo = 999999;
		String publisher = "";

		if (isNullTextField(bookIdField)) {
			bookId = bookIdField.getText();
		}
		if (isNullTextField(bookTitleField)) {
			bookTitle = bookTitleField.getText();
		}
		if (isNullTextField(authorField)) {
			authorName = authorField.getText();
		}
		if (isInteger(priceFromField.getText())) {
			priceFrom = Integer.parseInt(priceFromField.getText());
		}
		if (isInteger(priceToField.getText())) {
			priceTo = Integer.parseInt(priceToField.getText());
		}
		if (!publisherField.getSelectionModel().isEmpty()) {
			publisher = publisherField.getValue();
		}

		books.removeAll(books);
		sql = "SELECT * FROM Book WHERE BookId like '%" + bookId + "%' AND BookTitle like N'%" + bookTitle
				+ "%' AND AuthorName like N'%" + authorName + "%' AND Price BETWEEN " + priceFrom + " AND " + priceTo
				+ " AND Publisher like N'%" + publisher + "%';";
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
	public void searchUser() {
		String userId = "";
		String userName = "";
		LocalDate birthFrom = LocalDate.of(1900, 1, 1);
		LocalDate birthTo = LocalDate.of(2900, 1, 1);

		if (isNullTextField(userIdField)) {
			userId = userIdField.getText();
		}
		if (isNullTextField(nameUserField)) {
			userName = nameUserField.getText();
		}
		if (birthFromField.getValue() != null) {
			birthFrom = birthFromField.getValue();
		}
		if (birthToField.getValue() != null) {
			birthTo = birthToField.getValue();
		}

		String birthFromString = birthFrom.toString();
		String birthToString = birthTo.toString();

		users.removeAll(users);
		sql = "SELECT * FROM Account WHERE AccId like '%" + userId + "%' AND Name like N'%" + userName
				+ "%' AND Birth BETWEEN '" + birthFromString + "' AND '" + birthToString + "';";
		System.out.println(sql);
		try {
			rs = st.executeQuery(sql);
			while (rs.next()) {
				User user = new User(rs.getString("AccId"), rs.getString("Name"), rs.getString("Birth"),
						rs.getString("Contact"));
				users.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		userList.removeAll(userList);
		userList.addAll(users);
	}

	@FXML
	public void searchBorrow() {
		String phieuId = "";
		String userId = "";

		if (isNullTextField(phieuIdField)) {
			phieuId = phieuIdField.getText();
		}
		if (isNullTextField(userIdBField)) {
			userId = userIdBField.getText();
		}

		borrows.removeAll(borrows);
		sql = "SELECT p.PhieuId, p.AccId, a.Name, bk.BookId, BookTitle, BorrowDay, p.LibId, Username, Deposit, State, ReturnDay, ReturnedDay FROM Borrow_Book bk, Account a, Librarian l, Book b, PhieuMuon p WHERE (bk.BookId = b.BookId) AND (bk.PhieuId = p.PhieuId) AND (p.AccId = a.AccId) AND (p.LibId = l.LibId) "
				+ "and p.PhieuId like '%" + phieuId + "%' AND p.AccId like N'%" + userId + "';";
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
		borrowList.removeAll(borrowList);
		borrowList.addAll(borrows);
	}

	public void detailAndUpdate(Book selectedBook) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/Application/BookDetail.fxml"));
		Parent parent = null;
		try {
			parent = loader.load();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		DetailUpdateController duc = loader.getController();
		duc.initDefaultData(selectedBook);
		Scene scene = new Scene(parent);
		scene.getStylesheets().addAll(this.getClass().getResource("/Application/style.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}

	public boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isNullTextField(TextField textField) {
		return !textField.getText().trim().isEmpty();
	}

	@FXML
	public void outOfStock(ActionEvent e) {
		if (outOfStockBtn.isSelected()) {
			books.removeAll(books);
			for (Book book : bookList) {
				if ((book.getQuantity() - book.getLost() - book.getIsBorrow()) == 0) {
					books.add(book);
				}
			}
			bookList.removeAll(bookList);
			bookList.addAll(books);
		} else {
			resetBookpage();
		}
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
			resetBorrowpage();
		}
	}

	@FXML
	public void isReturned(ActionEvent e) {
		if (isReturnedBtn.isSelected()) {
			borrows.removeAll(borrows);
			for (Borrow borrow : borrowList) {
				if (borrow.getState() == 1) {
					borrows.add(borrow);
				}
			}
			borrowList.removeAll(borrowList);
			borrowList.addAll(borrows);
		} else {
			resetBorrowpage();
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
			resetBorrowpage();
		}
	}

	@FXML
	public void bookStatis() {
		int i = 1;
		int j = 0;
		String[] sql = { "SELECT COUNT(BookId) FROM Book", "SELECT SUM(Quantity) FROM Book",
				"SELECT SUM(IsBorrow) FROM Book", "SELECT SUM(Quantity) - SUM(IsBorrow) - SUM(Lost) FROM Book",
				"SELECT COUNT(PhieuId) FROM Borrow_Book WHERE GETDATE() > ReturnDay AND State = 0", };
		try {
			for (String string : sql) {
				rs = st.executeQuery(string);
				rs.next();
				String sum = rs.getString(1);
				Label label = new Label(sum);
				label.setFont(new Font("Arial", 19));
				bookGrid.add(label, i, j);
				if (j == 2) {
					i = 3;
					j = 0;
				} else {
					j++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void genresStatis() {
		int i = 0;
		int j = 0;
		sql = "SELECT bg.Genre, SUM(Quantity) FROM Book b INNER JOIN Book_Genre bg ON b.BookId = bg.BookId GROUP BY bg.Genre";
		try {
			rs = st.executeQuery(sql);
			int rows = 0;
			while (rs.next()) {
				rows++;
			}
			rows = rows / 2;
			for (int k = 0; k < rows; k++) {
				RowConstraints row = new RowConstraints(60);
				genresGrid.getRowConstraints().add(row);
			}
			rs = st.executeQuery(sql);
			while (rs.next()) {
				String genre = rs.getString(1);
				String sum = rs.getString(2);
				Label label1 = new Label(genre);
				Label label2 = new Label(sum);
				label1.setFont(new Font("Arial", 19));
				label2.setFont(new Font("Arial", 19));
				genresGrid.add(label1, i, j);
				genresGrid.add(label2, i + 1, j);
				if (j == rows - 1) {
					i = 2;
					j = -1;
				}
				j++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void authorStatis() {
		int i = 0;
		int j = 0;
		sql = "SELECT AuthorName, SUM(Quantity) FROM Book GROUP BY AuthorName";
		try {
			rs = st.executeQuery(sql);
			int rows = 0;
			while (rs.next()) {
				rows++;
			}
			rows = rows / 2;
			for (int k = 0; k < rows; k++) {
				RowConstraints row = new RowConstraints(60);
				authorGrid.getRowConstraints().add(row);
			}
			rs = st.executeQuery(sql);
			while (rs.next()) {
				String author = rs.getString(1);
				String sum = rs.getString(2);
				Label label1 = new Label(author);
				Label label2 = new Label(sum);
				label1.setFont(new Font("Arial", 19));
				label2.setFont(new Font("Arial", 19));
				authorGrid.add(label1, i, j);
				authorGrid.add(label2, i + 1, j);
				if (j == rows - 1) {
					i = 2;
					j = -1;
				}
				j++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void publisherStatis() {
		int i = 0;
		int j = 0;
		sql = "SELECT Publisher, SUM(Quantity) FROM Book GROUP BY Publisher";
		try {
			rs = st.executeQuery(sql);
			int rows = 0;
			while (rs.next()) {
				rows++;
			}
			rows = rows / 2;
			for (int k = 0; k < rows; k++) {
				RowConstraints row = new RowConstraints(60);
				publisherGrid.getRowConstraints().add(row);
			}
			rs = st.executeQuery(sql);
			while (rs.next()) {
				String publisher = rs.getString(1);
				String sum = rs.getString(2);
				Label label1 = new Label(publisher);
				Label label2 = new Label(sum);
				label1.setFont(new Font("Arial", 19));
				label2.setFont(new Font("Arial", 19));
				publisherGrid.add(label1, i, j);
				publisherGrid.add(label2, i + 1, j);
				if (j == rows - 1) {
					i = 2;
					j = -1;
				}
				j++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
