package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements Initializable {

	@FXML
	private Button loginBtn;
	@FXML
	private Button exitBtn;
	@FXML
	private TextField usernameField;
	@FXML
	private PasswordField passwordField;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loginBtn.disableProperty().bind(Bindings.createBooleanBinding(() -> usernameField.getText().trim().isEmpty(),
				usernameField.textProperty()));
	}

	@FXML
	public void login(ActionEvent e) {
		if (checkLogin()) {
			Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			currentStage.close();
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/Application/Application.fxml"));
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

	@FXML
	public void exit(ActionEvent e) { 
		Platform.exit();
	}

	public boolean checkLogin() {
		java.sql.Connection conn;
		String sql;
		ResultSet rs;
		String username = usernameField.getText();
		String password = passwordField.getText();
		ArrayList<String> listLib = new ArrayList<String>();
		ArrayList<String> listPass = new ArrayList<String>();
		try {
			conn = Connection.main(null);
			sql = "SELECT * FROM Librarian";
			Statement st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				listLib.add(rs.getString("Username"));
				listPass.add(rs.getString("Password"));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (listLib.contains(username) && listPass.contains(password)) {
			return true;
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("Sai tên đăng nhập hoặc mật khẩu!");
			alert.show();
			return false;
		}
	}
}
