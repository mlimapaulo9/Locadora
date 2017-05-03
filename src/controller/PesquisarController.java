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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import view.Principal;

public class PesquisarController{
	// geral
	@FXML private TabPane tab;
	@FXML private Tab tabFilme;
	@FXML private Tab tabAlbum;
	@FXML private Tab tabCliente;
	private static Label subTitulo;
	
	// tab filme
	@FXML private TextField filmeTitulo;
	@FXML private TextField filmeGenero;
	@FXML private ComboBox<Integer> filmeAno;
	@FXML private Label resFilmeTitulo;
	@FXML private Label resFilmeDiretor;
	@FXML private Label resFilmeGenero;
	@FXML private Label resFilmeAno;	
	@FXML private TextArea resFilmeSinopse;
	
	//tab album
	@FXML private TextField albumTitulo;
	@FXML private TextField albumAutor;
	@FXML private TextField albumEstilo;
	@FXML private Label resAlbumTitulo;
	@FXML private Label resAlbumAutor;
	@FXML private Label resAlbumEstilo;
	
	//tab cliente
	@FXML private TextField clienteNome;
	@FXML private TextField clienteCPF;
	@FXML private Label resClienteNome;
	@FXML private Label resClienteCPF;
	@FXML private Label resClienteEndereco;
	@FXML private Label resClienteCEP;
	@FXML private ListView<String> resClienteAlugados;
	
	public static Label getSubTitulo() {
		return subTitulo;
	}

	public static void setSubTitulo(Label subTitulo) {
		PesquisarController.subTitulo = subTitulo;
	}

	@FXML
	public void initialize() {
		System.out.println("incializando pesquisa");
	
		for (int i = 1950; i <= LocalDate.now().getYear(); i++) {
			filmeAno.getItems().add(i);			
		}
		filmeAno.setValue(LocalDate.now().getYear());
		
		PesquisarController.subTitulo.setText("Pesquisar Filme");
		
		TabChanger tabChanger = new TabChanger();
		tabFilme.setOnSelectionChanged(tabChanger);
		tabAlbum.setOnSelectionChanged(tabChanger);
		tabCliente.setOnSelectionChanged(tabChanger);
		
		tab.getSelectionModel().select(tabFilme);
		
		System.out.println(Principal.isAdmin());
	}	
	
	private class TabChanger implements EventHandler<Event> {

		@Override
		public void handle(Event event) {
			PesquisarController.subTitulo.setText("Pesquisar " + ((Tab)event.getSource()).getText());
		}
	}
	
	@FXML private void pesquisarFilme(ActionEvent event) {
		try {
			resFilmeTitulo.setText(filmeTitulo.getText());
			resFilmeGenero.setText(filmeGenero.getText());
			resFilmeDiretor.setText(filmeGenero.getText());
			resFilmeAno.setText(filmeAno.getValue().toString());
			String tmp = "";
			for (int i = 0; i < 30; i++) { 
				tmp += filmeTitulo.getText() + " "; }
			resFilmeSinopse.setText(tmp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML private void pesquisarAlbum(ActionEvent event) {
		try {
			resFilmeTitulo.setText(filmeTitulo.getText());
			resFilmeGenero.setText(filmeGenero.getText());
			resFilmeDiretor.setText(filmeGenero.getText());
			resFilmeAno.setText(filmeAno.getValue().toString());
			String tmp = "";
			for (int i = 0; i < 30; i++) { 
				tmp += filmeTitulo.getText() + " "; }
			resFilmeSinopse.setText(tmp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML private void pesquisarCliente(ActionEvent event) {
		try {
			resClienteNome.setText(clienteNome.getText());
			resClienteCPF.setText("121485121-21");
			resClienteEndereco.setText("Rua Qualquer, Bairro Algum, 777");
			resClienteCEP.setText("55412-121");
			String tmp = "";
			for (int i = 0; i < 30; i++) { 
				tmp += filmeTitulo.getText() + " "; }
			resFilmeSinopse.setText(tmp);
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

