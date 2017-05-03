package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import view.Principal;

public class PrincipalController {
	@FXML private MenuBar menuBar;
	@FXML private Pane painelPrincipal;
	@FXML private Label subTitulo;
	@FXML private MenuItem menuFuncionario;

	@FXML public void initialize() {
		Principal.log("Inicializando Principal");
		Principal.setSubTitulo((Label) subTitulo);
		if (!Principal.isAdmin()) {
			menuFuncionario.setVisible(false);
		}		
	}
	
	@FXML protected void sair() {
		Platform.exit();
	}
	
	@FXML protected void abrePesquisar(ActionEvent event) throws Exception	{
		Principal.telaPesquisa();
		Principal.setSubTitulo("Pesquisar Filme");

	}
	
	@FXML protected void abreCadastroFuncionarios(ActionEvent event) throws Exception {
		Principal.telaCadastroFuncionarios();
		Principal.setSubTitulo("Cadastrar Funcionário");
	}
	
	@FXML protected void abreCadastroFilmes(ActionEvent event) throws Exception	{
		Principal.telaCadastroFilmes();
		Principal.setSubTitulo("Cadastrar Filme");
	}
}
