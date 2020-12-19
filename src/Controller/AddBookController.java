package Controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import Model.Book;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;

public class AddBookController implements Initializable {
	@FXML
	private JFXTextField IbookId;
	@FXML
	private JFXTextField IbookTitle;
	@FXML
	private JFXTextField IbookPrice;
	@FXML
	private JFXTextField IbookPages;
	@FXML
	private JFXTextField IbookQuantity;
	@FXML
	private JFXTextField IbookAuthorName;
	@FXML
	private JFXTextField IbookPublisher;
	@FXML
	private JFXTextField IbookPubYear;
	@FXML
	private TextArea IbookContent;
	@FXML
	private JFXButton addBtn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML
	public void addBook() {
		Book newBook = new Book(IbookId.getText(), IbookTitle.getText(), IbookAuthorName.getText(),
				IbookContent.getText(), Long.parseLong(IbookPrice.getText()), Integer.parseInt(IbookPages.getText()),
				IbookPublisher.getText(), Integer.parseInt(IbookPubYear.getText()),
				Integer.parseInt(IbookQuantity.getText()), 0, 0);
		try {
			java.sql.Connection conn = Connection.main(null);
			String sql = "SELECT BookId FROM Book";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			String id = "";
			while (rs.next()) {
				id = rs.getString("BookId");
				if (newBook.getBookId().equals(id)) {
					try {
						String sql2 = "UPDATE Book SET Quantity = Quantity + " + newBook.getQuantity()
								+ " WHERE BookId = '" + newBook.getBookId() + "'";
						st.execute(sql2);
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				} else {
					try {
						String sql2 = "INSERT INTO Book VALUES('" + newBook.getBookId() + "',N'"
								+ newBook.getBookTitle() + "',N'" + newBook.getAuthorName() + "',N'"
								+ newBook.getContent() + "'," + newBook.getPrice() + "," + newBook.getPages() + ",N'"
								+ newBook.getPublisher() + "'," + newBook.getPubYear() + "," + newBook.getQuantity()
								+ ",0,0" + ");";
						st.execute(sql2);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@FXML
	public void cancel(ActionEvent e) {
		Stage stage = (Stage) IbookContent.getScene().getWindow();
		stage.close();
	}
	
}
