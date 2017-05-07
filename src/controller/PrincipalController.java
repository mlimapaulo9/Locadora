package controller;

import java.util.Optional;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import model.Cliente;
import model.utils.Sessao;
import view.Principal;

public class PrincipalController {
	@FXML
	private MenuBar menuBar;
	@FXML
	private Pane painelPrincipal;
	@FXML
	private Label subTitulo;
	@FXML
	private MenuItem menuFuncionario;

	@FXML
	public void initialize() {
		Principal.setSubTitulo((Label) subTitulo);
		if (!Principal.isAdmin()) {
			menuFuncionario.setVisible(false);
		}
	}

	@FXML
	private void trocarUsuario() throws Exception {
		Principal.setAdmin(false);
		Principal.telaLogin();
	}

	@FXML
	private void sair() {
		Platform.exit();
	}

	@FXML
	private void novaSessao() {
		String resultado;
		boolean sair = false;
		do {
			resultado = Principal.abrirJanelaCPF();

			if (resultado.equals("cancelar"))
				sair = true;

			Cliente cliente = Cliente.buscarCPF(resultado);
			if (cliente != null) {
				Sessao nova = new Sessao(cliente);
				Principal.setSessao(nova);
				sair = true;
			}
		} while (!sair);
	}

	@FXML
	private void confirmarSessao() {
		
	}

	@FXML
	private void cancelarSessao() {
		Principal.setSessao(null);
	}

	@FXML
	private void abrePesquisar(ActionEvent event) throws Exception {
		Principal.criarTela("Pesquisar");
		Principal.setSubTitulo("Pesquisar Filme");

	}

	@FXML
	private void abreCadastroFuncionarios(ActionEvent event) throws Exception {
		Principal.criarTela("CadastroFuncionario");
		Principal.setSubTitulo("Cadastrar Funcionário");
	}

	@FXML
	private void abreCadastroClientes(ActionEvent event) throws Exception {
		Principal.criarTela("CadastroCliente");
		Principal.setSubTitulo("Cadastrar Cliente");
	}

	@FXML
	private void abreCadastroAlbuns(ActionEvent event) throws Exception {
		Principal.criarTela("CadastroAlbum");
		Principal.setSubTitulo("Cadastrar Album");
	}

	@FXML
	private void abreCadastroFilmes(ActionEvent event) throws Exception {
		Principal.criarTela("CadastroFilme");
		Principal.setSubTitulo("Cadastrar Filme");
	}
}
