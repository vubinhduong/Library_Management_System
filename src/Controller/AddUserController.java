package Controller;

import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

import com.jfoenix.controls.JFXDatePicker;

import Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddUserController {
	@FXML
	private TextField userIdField;
	@FXML
	private TextField nameField;
	@FXML
	private JFXDatePicker birthField;
	@FXML
	private TextArea contactField;

	@FXML
	public void addUser() {
		String userId = "";
		String name = "";
		LocalDate birth = null;
		String birthday = null;
		String contact = null;
		if (userIdField.getText().trim().isEmpty() || nameField.getText().trim().isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("UserId and Name cannot be null!");
			alert.show();
			return;
		}
		userId = userIdField.getText();
		name = nameField.getText();
		if (birthField.getValue() != null) {
			birth = birthField.getValue();
			birthday = birth.toString();
		}
		if (!contactField.getText().trim().isEmpty()) {
			contact = contactField.getText();
		}

		try {
			java.sql.Connection conn = Connection.main(null);
			String sql = "SELECT AccId FROM Account";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			String id = "";
			while (rs.next()) {
				id = rs.getString("AccId");
				if (userId.equals(id)) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setContentText("User đã tồn tại!");
					alert.show();
					break;
				}
			}
			try {
				String sql2;
				if (birthday == null) {
					sql2 = "INSERT INTO Account VALUES('" + userId + "',N'" + name + "'," + "null" + ",N'" + contact
							+ "');";
				} else {
					sql2 = "INSERT INTO Account VALUES('" + userId + "',N'" + name + "','" + birthday + "',N'"
							+ contact + "');";
				}
				System.out.println(sql2);
				st.execute(sql2);
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("Add successfully!");
				alert.show();
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText("ERROR!");
				alert.show();
				e.printStackTrace();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void cancel() {
		Stage stage = (Stage) userIdField.getScene().getWindow();
		stage.close();
	}
}
