package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
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
	}
	
	@FXML
	private void cadastrar(ActionEvent event) {
		
	}
	
	@FXML
	private void limpar(ActionEvent event) {
		titulo.clear();;
		nomeBanda.clear();;
		estilo.clear();;
		ano.getSelectionModel().selectLast();
		quant.getSelectionModel().selectFirst();
	}
}
