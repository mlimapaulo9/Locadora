package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Login;
import view.Principal;

public class LoginController {
	/*
	 * Tela de Login
	 */
	@FXML private TextField login;
	@FXML private PasswordField senha;
	@FXML private Label incorrectInfo;
	
	private Login model;
	
	@FXML public void initialize() {
		System.out.println("inicializando login");
		this.login.setText("noberto");
		this.senha.setText("123");
	}
	
	@FXML protected void autenticar(ActionEvent event) { 
		model = new Login();
		model.autenticar(login.getText(), senha.getText(), incorrectInfo);
	}
	
}
