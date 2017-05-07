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
	private CheckBox incluirAno;

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

		Principal.log(Integer.toString(pages.getPageCount()));
		Principal.log(Integer.toString(pages.getMaxPageIndicatorCount()));

	}

	private class TabChanger implements EventHandler<Event> {

		@Override
		public void handle(Event event) {
			Principal.setSubTitulo("Pesquisar " + ((Tab) event.getSource()).getText());
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
			resFilmeTitulo.setText(null);
			resFilmeDiretor.setText(null);
			resFilmeGenero.setText(null);
			resFilmeAno.setText(null);
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
			resFilmeTitulo.setText(((Filme) obj).getTitulo());
			resFilmeDiretor.setText(((Filme) obj).getDiretor());
			resFilmeGenero.setText(((Filme) obj).getGenero());
			resFilmeAno.setText(Integer.toString(((Filme) obj).getAno()));
			break;
		case "album":
			resAlbumTitulo.setText(((Album) obj).getTitulo());
			resAlbumAutor.setText(((Album) obj).getNomeBanda());
			resAlbumEstilo.setText(((Album) obj).getEstilo());
			break;
		case "cliente":

		}
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

		Principal.log("T: " + Integer.toString(listaResultados.size()));
		
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
			}
			else {
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

		// se encontrou algo na pesquisa, seta o label
		if (listaResultados.size() > 0) {
			lblResultado.setStyle("-fx-text-fill: green");
			lblResultado.setText(listaResultados.size() + " resultado(s) encontrado(s).");
			lblResultado.setVisible(true);
			pages.setDisable(false);
			pages.setPageCount(listaResultados.size());
			final List<Filme> temp = listaResultados;
			pages.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) -> {
				this.setaDados(tab.getSelectionModel().getSelectedItem().getText(), temp.get(newIndex.intValue()));
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
		Principal.log(Filme.buscarTitulo("Pulp Fiction", true).get(0).getDiretor());
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
