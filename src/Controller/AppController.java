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
import Model.borrowDetail;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AppController implements Initializable {

	@FXML
	public Text name1;

	@FXML
	private TableView<borrowDetail> borrowsView;
	@FXML
	private TableColumn<borrowDetail, String> idZ;
	@FXML
	private TableColumn<borrowDetail, String> nameZ;
	@FXML
	private TableColumn<borrowDetail, String> day1Z;
	@FXML
	private TableColumn<borrowDetail, String> day2Z;

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
	private ComboBox<String> phieuIdField;
	@FXML
	private Text nameDetail;
	@FXML
	private Text libDetail;
	@FXML
	private Text feeDetail;
	@FXML
	private Text dateDetail;

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
	public ObservableList<borrowDetail> borrowList;
	ObservableList<String> listPublisher = FXCollections.observableArrayList("", "NXB Kim Đồng", "NXB Hội Nhà Văn",
			"NXB Văn học", "NXB Tổng Hợp TPHCM", "NXB IPM", "NXB Trẻ");

	List<Book> books = new ArrayList<Book>();
	List<User> users = new ArrayList<User>();
	List<String> idList = new ArrayList<String>();
	List<borrowDetail> borrows = new ArrayList<borrowDetail>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			conn = Connection.main(null);
			st = conn.createStatement();

			initBookpage(st);
			initUserpage(st);
			initPhieuMuon();

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
	public void addBorrowView(ActionEvent e) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/Application/AddBorrow.fxml"));
		Parent parent = null;
		try {
			parent = loader.load();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Scene scene = new Scene(parent);
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
				alert.show();
			}
			bookList.remove(bookSelected);
			Alert alert2 = new Alert(AlertType.INFORMATION);
			alert.setContentText("Xóa thành công!");
			alert.show();
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
			alert.setContentText("Xóa thành công!");
		}
		alert.show();
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

	public void initPhieuMuon() {
		sql = "SELECT PhieuId FROM PhieuMuon";
		try {
			rs = st.executeQuery(sql);
			while (rs.next()) {
				String id = rs.getString(1);
				idList.add(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObservableList<String> listId = FXCollections.observableArrayList(idList);
		phieuIdField.setItems(listId);
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
	
	@FXML
	public void resetBorrowPage() {
		nameUserField.clear();
		nameDetail.setText(null);
		libDetail.setText(null);
		feeDetail.setText(null);
		dateDetail.setText(null);
		borrows.removeAll(borrows);
		borrowList.removeAll(borrowList);
		initPhieuMuon();
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

	public void setName(String name) {
		name1.setText(name);
	}

	@FXML
	public void showDetail() {
		borrows.removeAll(borrows);
		String id = phieuIdField.getValue();
		sql = " SELECT a.Name, l.Username, pm.BorrowDay, pm.Deposit \r\n"
				+ "  FROM PhieuMuon pm INNER JOIN Account a ON pm.AccId = a.AccId\r\n"
				+ "					INNER JOIN Librarian l ON pm.LibId = l.LibId\r\n" + "  WHERE pm.PhieuId = '" + id
				+ "';";
		System.out.println(sql);
		try {
			rs = st.executeQuery(sql);
			while (rs.next()) {
				nameDetail.setText(rs.getString(1));
				libDetail.setText(rs.getString(2));
				feeDetail.setText(rs.getString(4));
				dateDetail.setText(rs.getString(3));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		sql = "SELECT pm.PhieuId,b.BookId, b.BookTitle, bb.ReturnDay, bb.ReturnedDay\r\n"
				+ "  FROM PhieuMuon pm INNER JOIN Borrow_Book bb ON pm.PhieuId = bb.PhieuId\r\n"
				+ "					INNER JOIN Book b ON b.BookId = bb.BookId\r\n" + "  WHERE pm.PhieuId = '" + id
				+ "';";
		System.out.println(sql);
		try {
			rs = st.executeQuery(sql);
			while (rs.next()) {
				borrowDetail b = new borrowDetail(rs.getString("PhieuId"), rs.getString("BookTitle"),
						rs.getString("ReturnDay"), rs.getString("ReturnedDay"), rs.getString("BookId"));
				borrows.add(b);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		borrowList = FXCollections.observableArrayList(borrows);
		idZ.setCellValueFactory(new PropertyValueFactory<borrowDetail, String>("id"));
		nameZ.setCellValueFactory(new PropertyValueFactory<borrowDetail, String>("name"));
		day1Z.setCellValueFactory(new PropertyValueFactory<borrowDetail, String>("returnDate"));
		day2Z.setCellValueFactory(new PropertyValueFactory<borrowDetail, String>("returnedDate"));
		borrowsView.setItems(borrowList);
	}

	@FXML
	void traSach(ActionEvent e) {
		borrowDetail borrow = borrowsView.getSelectionModel().getSelectedItem();
		if (borrow.getReturnedDate() != null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("Sách đã được trả!");
			alert.show();
			return;
		}
		
		Alert alert = new Alert(AlertType.CONFIRMATION, "Xác nhận trả sách?", ButtonType.YES, ButtonType.NO,
				ButtonType.CANCEL);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.YES) {
			sql = "UPDATE Borrow_Book SET ReturnedDay = GETDATE() WHERE BookId = '" + borrow.getBookId()
					+ "' AND PhieuId = '" + borrow.getId() + "';";
			try {
				st.execute(sql);
			} catch (SQLException e1) {
				Alert alert2 = new Alert(AlertType.INFORMATION);
				alert.setContentText("Có lỗi xảy ra!");
			}
			bookList.remove(borrow);

			sql = "UPDATE Book SET IsBorrow = IsBorrow - 1 WHERE BookId = '" + borrow.getBookId()
					+ "';";
			try {
				st.execute(sql);
			} catch (SQLException e1) {
				Alert alert2 = new Alert(AlertType.INFORMATION);
				alert.setContentText("Có lỗi xảy ra!");
			}

			Alert alert2 = new Alert(AlertType.INFORMATION);
			alert.setContentText("Trả thành công!");
		}
	}

	@FXML
	void baoMat(ActionEvent e) {
		borrowDetail borrow = borrowsView.getSelectionModel().getSelectedItem();

		if (borrow.getReturnedDate() != null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("Sách đã được trả!");
			alert.show();
			return;
		}
		
		int tienPhat = 0;
		sql = "UPDATE Book SET Lost = Lost + 1, IsBorrow = IsBorrow - 1 WHERE BookId = '" + borrow.getBookId()
				+ "';";
		String sql2 = "UPDATE Borrow_Book SET ReturnedDay = GETDATE() WHERE BookId = '" + borrow.getBookId()
		+ "' AND PhieuId = '" + borrow.getId() + "';";
		try {
			st.execute(sql);
			st.execute(sql2);
		} catch (SQLException e1) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("Có lỗi xảy ra!");
			alert.showAndWait();
		}
		bookList.remove(borrow);

		sql = "SELECT Price FROM Book WHERE BookId = '" + borrow.getBookId() + "';";
		try {
			rs = st.executeQuery(sql);
			while (rs.next()) {
				tienPhat = rs.getInt(1);
			}
		} catch (SQLException e1) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("Có lỗi xảy ra!");
			alert.showAndWait();
		}

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setContentText("TIền phạt: " + tienPhat);
		alert.showAndWait();
	}
	
	@FXML
	public void logout() {
		Stage cstage = (Stage) bookIdField.getScene().getWindow();
		cstage.close();
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/Application/Login.fxml"));
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
}
