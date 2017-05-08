package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Pagination;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Filme;
import model.Album;
import model.Cliente;
import view.Principal;

public class PesquisarController {
	// geral
	@FXML
	private TabPane tab;
	@FXML
	private Tab tabFilme;
	@FXML
	private Tab tabAlbum;
	@FXML
	private Tab tabCliente;
	private static Label subTitulo;
	@FXML
	private Pagination pages;
	@FXML
	private Label lblResultado;

	// tab filme
	@FXML
	private TextField filmeTitulo;
	@FXML
	private TextField filmeGenero;
	@FXML
	private ComboBox<Integer> filmeAno;
	@FXML
	private Label resFilmeTitulo;
	@FXML
	private Label resFilmeDiretor;
	@FXML
	private Label resFilmeGenero;
	@FXML
	private Label resFilmeAno;
	@FXML
	private Label resFilmeQuantidade;
	@FXML
	private Label resFilmeAlugados;
	@FXML
	private Label resFilmeID;
	@FXML
	private CheckBox incluirAno;
	@FXML
	private Button btnAlugarFilme;
	@FXML
	private Button btnDevolverFilme;
	@FXML
	private Button btnPesquisarFilme;
	private static List<Filme> resultadosFilme;

	// tab album
	@FXML
	private TextField albumTitulo;
	@FXML
	private TextField albumAutor;
	@FXML
	private TextField albumEstilo;
	@FXML
	private Label resAlbumTitulo;
	@FXML
	private Label resAlbumAutor;
	@FXML
	private Label resAlbumEstilo;
	@FXML
	private Label resAlbumQuantidade;
	@FXML
	private Label resAlbumAlugados;
	@FXML
	private Label resAlbumID;
	@FXML
	private Button btnAlugarAlbum;
	@FXML
	private Button btnDevolverAlbum;
	@FXML
	private Button btnPesquisarAlbum;
	private static List<Album> resultadosAlbum;

	// tab cliente
	@FXML
	private TextField clienteNome;
	@FXML
	private TextField clienteCPF;
	@FXML
	private Label resClienteNome;
	@FXML
	private Label resClienteCPF;
	@FXML
	private Label resClienteEndereco;
	@FXML
	private Label resClienteCEP;
	@FXML
	private Button btnPesquisarCliente;
	@FXML
	private ListView<String> resClienteAlugados;

	public static Label getSubTitulo() {
		return subTitulo;
	}

	public static void setSubTitulo(Label subTitulo) {
		PesquisarController.subTitulo = subTitulo;
	}

	@FXML
	public void initialize() {
		Principal.log("Inicializando Pesquisa");
		for (int i = 1950; i <= LocalDate.now().getYear(); i++) {
			filmeAno.getItems().add(i);
		}
		filmeAno.setValue(LocalDate.now().getYear());

		incluirAno.setSelected(true);

		TabChanger tabChanger = new TabChanger();
		tabFilme.setOnSelectionChanged(tabChanger);
		tabAlbum.setOnSelectionChanged(tabChanger);
		tabCliente.setOnSelectionChanged(tabChanger);

		tab.getSelectionModel().select(tabFilme);

		pages.setPageCount(5);
		
	}

	private class TabChanger implements EventHandler<Event> {

		@Override
		public void handle(Event event) {
			Principal.setSubTitulo("Pesquisar " + ((Tab) event.getSource()).getText());
			btnPesquisarFilme.setDefaultButton(false);
			btnPesquisarAlbum.setDefaultButton(false);
			btnPesquisarCliente.setDefaultButton(false);
			
			switch (((Tab) event.getSource()).getText()) {
			case "Filme":
				btnPesquisarFilme.setDefaultButton(true);
				break;
			case "Album":
				btnPesquisarAlbum.setDefaultButton(true);
				break;
			case "Cliente":
				btnPesquisarCliente.setDefaultButton(true);
				break;
			}
		}
	}

	@FXML
	private void toggleAno(ActionEvent event) {
		if (incluirAno.isSelected()) {
			filmeAno.setDisable(false);
		} else {
			filmeAno.setDisable(true);
		}
	}

	private void limpaDados(String categoria) {
		switch (categoria.toLowerCase()) {
		case "filme":
			btnAlugarFilme.setDisable(true);
			btnDevolverFilme.setDisable(true);
			resFilmeTitulo.setText(null);
			resFilmeDiretor.setText(null);
			resFilmeGenero.setText(null);
			resFilmeAno.setText(null);
			resFilmeQuantidade.setText(null);
			resFilmeAlugados.setText(null);
			resFilmeID.setText(null);
			break;
		case "album":
			resAlbumTitulo.setText(null);
			resAlbumAutor.setText(null);
			resAlbumEstilo.setText(null);
			break;
		case "cliente":

		}
	}

	private void setaDados(String categoria, Object obj) {
		switch (categoria.toLowerCase()) {
		case "filme":
			Filme filme = (Filme) obj;			
			resFilmeTitulo.setText(filme.getTitulo());
			resFilmeDiretor.setText(filme.getDiretor());
			resFilmeGenero.setText(filme.getGenero());
			resFilmeAno.setText(Integer.toString(filme.getAno()));
			resFilmeQuantidade.setText(Integer.toString(filme.getQuantidade()));
			resFilmeAlugados.setText(Integer.toString(filme.getAlugadores().size()));
			resFilmeID.setText(Integer.toString(filme.getId()));
			atualizaBotoes(filme);
			break;
		case "album":
			Album album = (Album) obj;
			resAlbumTitulo.setText(album.getTitulo());
			resAlbumAutor.setText(album.getNomeBanda());
			resAlbumEstilo.setText(album.getEstilo());
			resAlbumQuantidade.setText(album.getQuantidade().toString());
			resAlbumAlugados.setText(String.valueOf(album.getAlugadores().size()));
			resAlbumID.setText(album.getId().toString());
			atualizaBotoes(album);
			break;
		case "cliente":

		}
	}

	private static List<Filme> getResultadosFilme() {
		return resultadosFilme;
	}

	private static void setResultadosFilme(List<Filme> lista) {
		resultadosFilme = lista;
	}
	private static List<Album> getResultadosAlbum() {
		return resultadosAlbum;
	}

	private static void setResultadosAlbum(List<Album> lista) {
		resultadosAlbum = lista;;
	}
	
	public void atualizaBotoes(Object obj) {
		switch (obj.getClass().getName()) {
		case "model.Filme":
			Filme filme = (Filme) obj;
			if (Principal.temSessao()) {
				if (Principal.getSessao().getFilmesAlugados().contains(filme.getId())) {
					btnDevolverFilme.setDisable(false);
					btnAlugarFilme.setDisable(true);
				} else {
					if (filme.getQuantidade() > filme.getAlugadores().size()) {
						btnAlugarFilme.setDisable(false);
					} else {
						btnAlugarFilme.setDisable(true);
					}
					btnDevolverFilme.setDisable(true);
				}

			}
			break;
		case "model.Album":
			Album album = (Album) obj;
			if (Principal.temSessao()) {
				if (Principal.getSessao().getAlbunsAlugados().contains(album.getId())) {
					btnDevolverAlbum.setDisable(false);
					btnAlugarAlbum.setDisable(true);
				} else {
					if (album.getQuantidade() > album.getAlugadores().size()) {
						btnAlugarAlbum.setDisable(false);
					} else {
						btnAlugarAlbum.setDisable(true);
					}
					btnDevolverAlbum.setDisable(true);
				}

			}
			break;
		case "Cliente":
			
			break;
		}
	}
	
	@FXML
	private void alugar(ActionEvent event) {
		switch (((Button) event.getSource()).getId()) {
		case "btnAlugarFilme":
			Principal.getSessao().adicionarFilme(Integer.parseInt(resFilmeID.getText()));
			atualizaBotoes(Filme.buscarID(Integer.parseInt(resFilmeID.getText())));
			break;
		case "btnAlugarAlbum":
			Principal.getSessao().adicionarAlbum(Integer.parseInt(resAlbumID.getText()));
			atualizaBotoes(Album.buscarID(Integer.parseInt(resAlbumID.getText())));
			break;
		}
		
		Principal.log(Principal.getSessao().toString());
	}

	@FXML
	private void devolver(ActionEvent event) {
		switch (((Button) event.getSource()).getId()) {
		case "btnDevolverFilme":
			Principal.getSessao().removerFilme(Integer.parseInt(resFilmeID.getText()));
			atualizaBotoes(Filme.buscarID(Integer.parseInt(resFilmeID.getText())));
			break;
		case "btnDevolverAlbum":
			Principal.getSessao().removerAlbum(Integer.parseInt(resAlbumID.getText()));
			atualizaBotoes(Album.buscarID(Integer.parseInt(resAlbumID.getText())));
			break;
		case "btnDevolverSelecionados":
			
			break;
		case "btnDevolverTodos":
			
			break;
		}
		
		Principal.log(Principal.getSessao().toString());
	}

	@FXML
	private void pesquisarFilme(ActionEvent event) {
		Principal.log("Pesquisando Filmes...");
		List<Filme> listaResultados = new ArrayList<Filme>();
		List<Filme> listaAuxiliar = new ArrayList<Filme>();
		List<Filme> listaAuxiliar2 = new ArrayList<Filme>();
		listaResultados.clear();
		listaAuxiliar.clear();
		listaAuxiliar2.clear();
		// pesquisa título do filme se o campo título não está vazio
		if (!filmeTitulo.getText().isEmpty()) {
			listaAuxiliar = Filme.buscarTitulo(filmeTitulo.getText().toLowerCase(), true);
			// se encontrou algo, joga na lista de resultados
			if (listaAuxiliar != null && listaAuxiliar.size() > 0) {
				listaResultados = listaAuxiliar;
			}
		}

		// pesquisa gênero do filme se o campo gênero não está vazio
		if (!filmeGenero.getText().isEmpty()) {
			listaAuxiliar = Filme.buscarGenero(filmeGenero.getText().toLowerCase(), true);
			// se encontrou algo
			if (listaAuxiliar != null && listaAuxiliar.size() > 0) {
				// e a lista de resultados tiver entradas também
				if (listaResultados.size() > 0) {
					// para cada filme na lista auxiliar
					for (Filme filme : listaAuxiliar) {
						// verificar se ele faz parte da lista resultados
						if (listaResultados.contains(filme)) {
							// se fizer, adiciona à lista auxiliar 2
							listaAuxiliar2.add(filme);
						}
					}
					/*
					 * preenche a lista de resultados com os filmes que atendem
					 * tanto os critérios de título e gênero
					 */
					if (listaAuxiliar2 != null)
						listaResultados = listaAuxiliar2;
				} else { // se a lista resultado não tive nada
							// simplesmente a redefine com o valor da auxiliar
					listaResultados = listaAuxiliar;
				}
			}
		}

		if (!filmeAno.isDisabled()) { // incluir o ano?
			listaAuxiliar = Filme.buscarAno(filmeAno.getValue(), true);

			// se encontrou algo
			if (listaAuxiliar != null && listaAuxiliar.size() > 0) {
				// e a lista de resultados tiver entradas também
				if (listaResultados.size() > 0) {
					// para cada filme na lista auxiliar
					for (Filme filme : listaAuxiliar) {
						// verificar se ele faz parte da lista resultados
						if (listaResultados.contains(filme)) {
							// se fizer, adiciona à lista auxiliar 2
							listaAuxiliar2.add(filme);
						}
					}
					/*
					 * preenche a lista de resultados com os filmes que atendem
					 * tanto os critérios de título, gênero e ano
					 */
					if (listaAuxiliar2 != null && listaAuxiliar2.size() > 0)
						listaResultados = listaAuxiliar2;
				} else { // se a lista resultado não tive nada
							// simplesmente a redefine com o valor da auxiliar
					listaResultados = listaAuxiliar;
				}
			} else {
				if (listaResultados.size() > 0) {
					listaResultados.clear();
				}
			}
		}

		if (filmeTitulo.getText().isEmpty() && filmeGenero.getText().isEmpty() && filmeAno.isDisabled()) {
			for (Filme filme : Filme.getFilmes().getLista()) {
				listaResultados.add(filme);
			}
		}

		setResultadosFilme(listaResultados);
		// se encontrou algo na pesquisa, seta o label
		if (listaResultados.size() > 0) {
			lblResultado.setStyle("-fx-text-fill: green");
			lblResultado.setText(listaResultados.size() + " resultado(s) encontrado(s).");
			lblResultado.setVisible(true);
			pages.setDisable(false);
			pages.setPageCount(listaResultados.size());
			pages.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) -> {
				List<Filme> temp = getResultadosFilme();
				try {
					this.setaDados(tab.getSelectionModel().getSelectedItem().getText(), temp.get(newIndex.intValue()));
				} catch (IndexOutOfBoundsException e) {
					Principal.log(
							"F-> old: " + oldIndex.toString() + " new: " + newIndex.toString() + " size: " + temp.size());
				}
			});
			this.setaDados("filme", listaResultados.get(0));
		} else {
			lblResultado.setStyle("-fx-text-fill: red");
			lblResultado.setText("Nenhum resultado encontrado.");
			lblResultado.setVisible(true);
			pages.setPageCount(1);
			pages.setDisable(true);
			this.limpaDados("filme");
		}
	}

	@FXML
	private void pesquisarAlbum(ActionEvent event) {
		Principal.log("Pesquisando Albuns...");
		List<Album> listaResultados = new ArrayList<Album>();
		List<Album> listaAuxiliar = new ArrayList<Album>();
		List<Album> listaAuxiliar2 = new ArrayList<Album>();
		listaResultados.clear();
		listaAuxiliar.clear();
		listaAuxiliar2.clear();
		// pesquisa título do filme se o campo título não está vazio
		if (!albumTitulo.getText().isEmpty()) {
			listaAuxiliar = Album.buscarTitulo(albumTitulo.getText().toLowerCase(), true);
			// se encontrou algo, joga na lista de resultados
			if (listaAuxiliar != null && listaAuxiliar.size() > 0) {
				listaResultados = listaAuxiliar;
			}
		}

		// pesquisa gênero do filme se o campo gênero não está vazio
		if (!albumAutor.getText().isEmpty()) {
			listaAuxiliar = Album.buscarBanda(albumAutor.getText().toLowerCase(), false);
			Principal.log(listaAuxiliar.toString());
			// se encontrou algo
			if (listaAuxiliar != null && listaAuxiliar.size() > 0) {
				// e a lista de resultados tiver entradas também
				if (listaResultados.size() > 0) {
					// para cada filme na lista auxiliar
					for (Album album : listaAuxiliar) {
						// verificar se ele faz parte da lista resultados
						if (listaResultados.contains(album)) {
							// se fizer, adiciona à lista auxiliar 2
							listaAuxiliar2.add(album);
						}
					}
					/*
					 * preenche a lista de resultados com os filmes que atendem
					 * tanto os critérios de título e gênero
					 */
					if (listaAuxiliar2 != null)
						listaResultados = listaAuxiliar2;
				} else { // se a lista resultado não tive nada
							// simplesmente a redefine com o valor da auxiliar
					listaResultados = listaAuxiliar;
				}
			} else {
				if (listaResultados.size() > 0) {
					listaResultados.clear();
				}
			}
		}

		if (!albumEstilo.getText().isEmpty()) { // incluir o ano?
			listaAuxiliar = Album.buscarEstilo(albumEstilo.getText().toLowerCase(), false);

			// se encontrou algo
			if (listaAuxiliar != null && listaAuxiliar.size() > 0) {
				// e a lista de resultados tiver entradas também
				if (listaResultados.size() > 0) {
					// para cada filme na lista auxiliar
					for (Album album : listaAuxiliar) {
						// verificar se ele faz parte da lista resultados
						if (listaResultados.contains(album)) {
							// se fizer, adiciona à lista auxiliar 2
							listaAuxiliar2.add(album);
						}
					}
					/*
					 * preenche a lista de resultados com os filmes que atendem
					 * tanto os critérios de título, gênero e ano
					 */
					if (listaAuxiliar2 != null && listaAuxiliar2.size() > 0)
						listaResultados = listaAuxiliar2;
				} else { // se a lista resultado não tive nada
							// simplesmente a redefine com o valor da auxiliar
					listaResultados = listaAuxiliar;
				}
			} else {
				if (listaResultados.size() > 0) {
					listaResultados.clear();
				}
			}
		}

		if (albumTitulo.getText().isEmpty() && albumAutor.getText().isEmpty() && albumEstilo.getText().isEmpty()) {
			for (Album album : Album.getAlbuns().getLista()) {
				listaResultados.add(album);
			}
		}

		setResultadosAlbum(listaResultados);
		// se encontrou algo na pesquisa, seta o label
		if (listaResultados.size() > 0) {
			lblResultado.setStyle("-fx-text-fill: green");
			lblResultado.setText(listaResultados.size() + " resultado(s) encontrado(s).");
			lblResultado.setVisible(true);
			pages.setDisable(false);
			pages.setPageCount(listaResultados.size());
			pages.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) -> {
				List<Album> temp = getResultadosAlbum();
				try {
					this.setaDados(tab.getSelectionModel().getSelectedItem().getText(), temp.get(newIndex.intValue()));
				} catch (IndexOutOfBoundsException e) {
					Principal.log(
							"A-> old: " + oldIndex.toString() + " new: " + newIndex.toString() + " size: " + temp.size());
				}
			});
			this.setaDados("album", listaResultados.get(0));
		} else {
			lblResultado.setStyle("-fx-text-fill: red");
			lblResultado.setText("Nenhum resultado encontrado.");
			lblResultado.setVisible(true);
			pages.setPageCount(1);
			pages.setDisable(true);
			this.limpaDados("album");
		}
	}

	@FXML
	private void pesquisarCliente(ActionEvent event) {
		try {
			resClienteNome.setText(clienteNome.getText());
			resClienteCPF.setText("121485121-21");
			resClienteEndereco.setText("Rua Qualquer, Bairro Algum, 777");
			resClienteCEP.setText("55412-121");
			String tmp = "";
			for (int i = 0; i < 30; i++) {
				tmp += filmeTitulo.getText() + " ";
			}

			List<String> list = new ArrayList<String>();
			ObservableList<String> olist = FXCollections.observableList(list);
			olist.add("O senhor dos Aneis");
			olist.add("Teste");
			resClienteAlugados.setItems((ObservableList<String>) olist);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
