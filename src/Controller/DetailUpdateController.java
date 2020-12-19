package Controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import Model.Book;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DetailUpdateController implements Initializable {
	@FXML
	private TextField bookIdField;
	@FXML
	private TextField bookTitleField;
	@FXML
	private TextField authorField;
	@FXML
	private TextField publisherField;
	@FXML
	private TextField priceField;
	@FXML
	private TextField pagesField;
	@FXML
	private TextField pubYearField;
	@FXML
	private TextArea contentField;

	private Book sbook;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public void initDefaultData(Book book) {
		sbook = book;
		bookIdField.setText(book.getBookId());
		bookTitleField.setText(book.getBookTitle());
		authorField.setText(book.getAuthorName());
		publisherField.setText(book.getPublisher());
		priceField.setText(Long.toString(book.getPrice()));
		pagesField.setText(Integer.toString(book.getPages()));
		pubYearField.setText(Integer.toString(book.getPubYear()));
		contentField.setText(book.getContent());
	}

	@FXML
	public void resetDetail() {
		initDefaultData(sbook);
	}

	@FXML
	public void updateBook() {
		String bookId = bookIdField.getText();
		String bookTitle = bookTitleField.getText();
		String authorName = authorField.getText();
		String publisher = publisherField.getText();
		Long price = Long.parseLong(priceField.getText());
		int pages = Integer.parseInt(pagesField.getText());
		int pubYear = Integer.parseInt(pubYearField.getText());
		String content = contentField.getText();

		Book newBook = new Book(bookId, bookTitle, authorName, content, price, pages, publisher, pubYear,
				sbook.getQuantity(), sbook.getIsBorrow(), sbook.getLost());

		if (!sbook.equals(newBook)) {
			try {
				java.sql.Connection conn = Connection.main(null);
				String sql = "UPDATE Book SET BookTitle = N'" + bookTitle + "', AuthorName = N'" + authorName
						+ "', Publisher = N'" + publisher + "', price = '" + price + "', Pages = '" + pages
						+ "', PubYear = '" + pubYear + "', Content = N'" + content + "' WHERE BookId = '" + bookId + "';";
				System.out.println(sql);
				Statement st = conn.createStatement();
				st.execute(sql);
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("Update successfully!");
				alert.show();
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("Update failed");
				alert.show();
			}
		}
	}
}
