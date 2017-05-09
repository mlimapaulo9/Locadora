package controller;

import java.util.List;
import java.util.Optional;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
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
	private ImageView imgConta;

	@FXML
	public void initialize() {
		Principal.setSubTitulo((Label) subTitulo);
		Principal.setImagemConta(imgConta);
		Tooltip tp = new Tooltip("Nenhuma sessão inicializada.");
		Tooltip.install(imgConta, tp);
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
			String tmp = "";
			resultado = Principal.abrirJanelaCPF();

			if (resultado.equals("cancelar")) {
				sair = true;
				break;
			}

			List<Cliente> clienteList = Cliente.buscarCPF(resultado, false);
			
			Cliente cliente;
			try {
				cliente = clienteList.get(0);
			} catch (IndexOutOfBoundsException e) {
				cliente = null;
			}
			if (cliente != null) {
				Sessao nova = new Sessao(cliente);
				Principal.setSessao(nova);
				tmp = "CPF: " + cliente.getCPF() + "\nNome: " + cliente.getNome();
				Tooltip.install(Principal.getImagemConta(), new Tooltip(tmp));
				Principal.getImagemConta().setOpacity(1);
				sair = true;
			}

			if (!sair) {
				Principal.abrirJanelaAlerta(AlertType.ERROR, null, "CPF inválido ou não encontrado, tente novamente!");
			} else {
				Principal.abrirJanelaAlerta(AlertType.INFORMATION, "Sessão inicializada!", tmp);
			}

		} while (!sair);
	}

	@FXML
	private void confirmarSessao() {

	}

	@FXML
	private void cancelarSessao() {
		Principal.setSessao(null);
		Tooltip.install(Principal.getImagemConta(), new Tooltip("Nenhuma sessão inicializada."));
		Principal.getImagemConta().setOpacity(0.4);
		Principal.abrirJanelaAlerta(AlertType.INFORMATION, null, "Sessão encerrada!");
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
