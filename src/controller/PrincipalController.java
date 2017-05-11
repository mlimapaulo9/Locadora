package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.Album;
import model.Cliente;
import model.Filme;
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
		if (Principal.temSessao() && Principal.getSessao().alterou()) {
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
						Sessao nova = new Sessao(cliente);
						Principal.setSessao(nova);
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
					Sessao nova = new Sessao(cliente);
					Principal.setSessao(nova);
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
		Cliente cliente = Principal.getSessao().getCliente();
		if (Principal.getSessao().alterou()) {
			// atualiza cliente
			cliente.setAlbunsAlugados(Principal.getSessao().getAlbunsAlugados());
			cliente.setFilmesAlugados(Principal.getSessao().getFilmesAlugados());
			// atualiza filmes
			List<Integer> filmes = cliente.getFilmesAlugados();
			List<Integer> removidos = new ArrayList<Integer>();
			if (filmes != null) {
				for (Integer i : filmes) {
					if (i < 0) {
						Filme.buscarID(-i).removeAlugador((int) cliente.getId());
						removidos.add(i);
					} else {
						Principal.log("Fora do if: (" + i + ") " + Filme.buscarID(i).getAlugadores().toString());
						if (!Filme.buscarID(i).getAlugadores().contains(cliente.getId())) {
							Filme.buscarID(i).addAlugador((int) cliente.getId());
							Principal.log("Dentro do if: (" + i + ") " + Filme.buscarID(i).getAlugadores().toString());
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
			Principal.getSessao().setAlterou(false);
			Principal.abrirJanelaAlerta(AlertType.INFORMATION, null, "Operações salvas com sucesso!");
		} else {
			Principal.abrirJanelaAlerta(AlertType.INFORMATION, null, "Nenhuma alteração a ser feita!");
		}
	}

	@FXML
	private void encerrarSessao() throws Exception {
		if (Principal.getSessao().alterou()) {
			Alert alerta = new Alert(AlertType.CONFIRMATION,
					"Há modificações não salvas. Deseja realmente encerrar a sessão atual?", ButtonType.YES,
					ButtonType.NO, ButtonType.CANCEL);
			alerta.showAndWait();

			if (alerta.getResult() == ButtonType.YES) {
				Principal.setSessao(null);
				Tooltip.install(Principal.getImagemConta(), new Tooltip("Nenhuma sessão inicializada."));
				Principal.getImagemConta().setOpacity(0.4);
				Principal.abrirJanelaAlerta(AlertType.INFORMATION, null, "Sessão encerrada!");
				Principal.telaPrincipal();
			}
		} else {
			Principal.setSessao(null);
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
		Principal.setIdSubTela(1);
	}

	@FXML
	private void abreCadastroFuncionarios(ActionEvent event) throws Exception {
		Principal.criarTela("CadastroFuncionario");
		Principal.setSubTitulo("Cadastrar Funcionário");
		Principal.setIdSubTela(2);
	}

	@FXML
	private void abreCadastroClientes(ActionEvent event) throws Exception {
		Principal.criarTela("CadastroCliente");
		Principal.setSubTitulo("Cadastrar Cliente");
		Principal.setIdSubTela(3);
	}

	@FXML
	private void abreCadastroAlbuns(ActionEvent event) throws Exception {
		Principal.criarTela("CadastroAlbum");
		Principal.setSubTitulo("Cadastrar Album");
		Principal.setIdSubTela(4);
	}

	@FXML
	private void abreCadastroFilmes(ActionEvent event) throws Exception {
		Principal.criarTela("CadastroFilme");
		Principal.setSubTitulo("Cadastrar Filme");
		Principal.setIdSubTela(5);
	}
	
	@FXML
	private void abrirSobre() {
		Principal.criarSubTela("Sobre");
	}
}
