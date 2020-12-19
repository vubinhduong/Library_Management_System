package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTabPane;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;

public class StatisticController implements Initializable{
	
	@FXML 
	private JFXTabPane tabpane;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	public void setDefaultTab(int i) {
		SingleSelectionModel<Tab> selectionModel = tabpane.getSelectionModel();
		selectionModel.select(i);
	}
}
