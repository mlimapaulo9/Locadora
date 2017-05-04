package controller;

import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Album;
import model.Funcionario;
import view.Principal;

public class AlbumController {
	@FXML
	private TextField titulo;
	@FXML
	private TextField nomeBanda;
	@FXML
	private TextField estilo;
	@FXML
	private ComboBox<Integer> ano;
	@FXML
	private ComboBox<Integer> quant;
	@FXML
	private TextField id;

	@FXML
	private void initialize() {
		Principal.log("Inicializando CadastroAlbum");
		
		for (int i = 1950; i <= LocalDate.now().getYear(); i++) {
			ano.getItems().add(i);
		}
		
		ano.getSelectionModel().selectLast();
		
		for (int i = 1; i <= 5; i++){
			quant.getItems().add(i);
		}
		
		quant.getSelectionModel().selectFirst();
		
		id.setText(Integer.toString(Album.getLastId() + 1));
	}

	@FXML
	private void cadastrar(ActionEvent event) {

	}

	@FXML
	private void limpar(ActionEvent event) {
		titulo.clear();
		;
		nomeBanda.clear();
		;
		estilo.clear();
		;
		ano.getSelectionModel().selectLast();
		quant.getSelectionModel().selectFirst();
	}
}
