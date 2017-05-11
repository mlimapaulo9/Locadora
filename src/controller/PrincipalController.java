package controller;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.Album;
import model.Cliente;
import model.Filme;
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
	private MenuItem menuConfirmarOperacoes;
	@FXML
	private SeparatorMenuItem menuSep;
	@FXML
	private MenuItem menuEncerrarSessao;
	@FXML
	private ImageView imgConta;

	@FXML
	public void initialize() {
		Principal.setSubTitulo((Label) subTitulo);
		Principal.setImagemConta(imgConta);
		Tooltip tp = new Tooltip("Nenhuma sessão inicializada.");
		Tooltip.install(imgConta, tp);
		if (!Principal.isAdmin()) {
			menuFuncionario.setDisable(true);
		}
		if (!Principal.temSessao()) {
			menuConfirmarOperacoes.setDisable(true);
			menuEncerrarSessao.setDisable(true);
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
	private void novaSessao() throws Exception {
		if (Principal.temSessao() && Principal.alterouSessao()) {
			Alert alerta = new Alert(AlertType.CONFIRMATION,
					"Há modificações não salvas. Deseja realmente encerrar a sessão atual?", ButtonType.YES,
					ButtonType.NO, ButtonType.CANCEL);
			alerta.showAndWait();

			if (alerta.getResult() == ButtonType.YES) {
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
						Principal.alteraClienteSessao(cliente);
						tmp = "CPF: " + cliente.getCPF() + "\nNome: " + cliente.getNome();
						Tooltip.install(Principal.getImagemConta(), new Tooltip(tmp));
						Principal.getImagemConta().setOpacity(1);
						sair = true;
					}

					if (!sair) {
						Principal.abrirJanelaAlerta(AlertType.ERROR, null,
								"CPF inválido ou não encontrado, tente novamente!");
					} else {
						Principal.abrirJanelaAlerta(AlertType.INFORMATION, "Sessão inicializada!", tmp);
						Principal.criarTela("Pesquisar");
						menuConfirmarOperacoes.setDisable(false);
						menuEncerrarSessao.setDisable(false);
					}

				} while (!sair);
			}
		} else {
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
					Principal.getSessao();
					Principal.alteraClienteSessao(cliente);
					tmp = "CPF: " + cliente.getCPF() + "\nNome: " + cliente.getNome();
					Tooltip.install(Principal.getImagemConta(), new Tooltip(tmp));
					Principal.getImagemConta().setOpacity(1);
					sair = true;
				}

				if (!sair) {
					Principal.abrirJanelaAlerta(AlertType.ERROR, null,
							"CPF inválido ou não encontrado, tente novamente!");
				} else {
					Principal.abrirJanelaAlerta(AlertType.INFORMATION, "Sessão inicializada!", tmp);
					Principal.criarTela("Pesquisar");
					menuConfirmarOperacoes.setDisable(false);
					menuEncerrarSessao.setDisable(false);
				}

			} while (!sair);
		}
	}

	@FXML
	private void confirmarOperacoes() {
		Cliente cliente = Principal.getClienteSessao();
		if (Principal.alterouSessao()) {
			// atualiza cliente
			cliente.setAlbunsAlugados(Principal.getAlbunsAlugados());
			cliente.setFilmesAlugados(Principal.getFilmesAlugados());
			// atualiza filmes
			List<Integer> filmes = cliente.getFilmesAlugados();
			List<Integer> removidos = new ArrayList<Integer>();
			if (filmes != null) {
				for (Integer i : filmes) {
					if (i < 0) {
						Filme.buscarID(-i).removeAlugador((int) cliente.getId());
						removidos.add(i);
					} else {
						if (!Filme.buscarID(i).getAlugadores().contains(cliente.getId())) {
							Filme.buscarID(i).addAlugador((int) cliente.getId());
						}
					}
				}
				
			}
			cliente.getFilmesAlugados().removeAll(removidos);
			removidos.clear();
			Filme.salvar();
			// atualiza albuns
			List<Integer> albuns = cliente.getAlbunsAlugados();
			if (albuns != null) {
				for (Integer i : albuns) {
					if (i < 0) {
						Album.buscarID(-i).removeAlugador((int) cliente.getId());
						removidos.add(i);
					} else {
						if (!Album.buscarID(i).getAlugadores().contains(cliente.getId()))
							Album.buscarID(i).addAlugador((int) cliente.getId());
					}
				}
			}
			cliente.getAlbunsAlugados().removeAll(removidos);
			Album.salvar();
			Cliente.salvar();
			Principal.alterarSessao(false);
			Principal.abrirJanelaAlerta(AlertType.INFORMATION, null, "Operações salvas com sucesso!");
		} else {
			Principal.abrirJanelaAlerta(AlertType.INFORMATION, null, "Nenhuma alteração a ser feita!");
		}
	}

	@FXML
	private void encerrarSessao() throws Exception {
		if (Principal.alterouSessao()) {
			Alert alerta = new Alert(AlertType.CONFIRMATION,
					"Há modificações não salvas. Deseja realmente encerrar a sessão atual?", ButtonType.YES,
					ButtonType.NO, ButtonType.CANCEL);
			alerta.showAndWait();

			if (alerta.getResult() == ButtonType.YES) {
				Principal.anulaSessaoInstance();
				Tooltip.install(Principal.getImagemConta(), new Tooltip("Nenhuma sessão inicializada."));
				Principal.getImagemConta().setOpacity(0.4);
				Principal.abrirJanelaAlerta(AlertType.INFORMATION, null, "Sessão encerrada!");
				Principal.telaPrincipal();
			}
		} else {
			Principal.anulaSessaoInstance();
			Tooltip.install(Principal.getImagemConta(), new Tooltip("Nenhuma sessão inicializada."));
			Principal.getImagemConta().setOpacity(0.4);
			Principal.abrirJanelaAlerta(AlertType.INFORMATION, null, "Sessão encerrada!");
			Principal.telaPrincipal();
		}
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
	
	@FXML
	private void abrirSobre() {
		Principal.criarSubTela("Sobre");
	}
}
