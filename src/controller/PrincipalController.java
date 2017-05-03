package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import view.Principal;

public class PrincipalController {
	@FXML private MenuBar menuBar;
	@FXML private Pane painelPrincipal;
	@FXML private Label subTitulo;
	@FXML private MenuItem menuFilmes; 
	
	
	
	public String getSubTitulo() {
		return this.subTitulo.getText();
	}

	public void setSubTitulo(String subTitulo) {
		this.subTitulo.setText(subTitulo);
	}

	@FXML public void initialize() {
		System.out.println("inicializando principal");
		Principal.setSubTitulo((Label) subTitulo);
		subTitulo.setLayoutX(260);
	}
	
	@FXML protected void sair() {
		Platform.exit();
	}
	
	@FXML protected void abreCadastroFilmes(ActionEvent event) throws Exception	{
		Principal.telaCadastroFilmes();
	}
}
