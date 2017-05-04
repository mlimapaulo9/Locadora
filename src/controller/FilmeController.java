package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import model.Filme;
import view.Principal;

public class FilmeController {

	@FXML
	private TextField titulo;
	@FXML
	private TextField genero;
	@FXML
	private TextField diretor;
	@FXML
	private ComboBox<Integer> ano;
	@FXML
	private ComboBox<Integer> quant;
	@FXML
	private TextField id;

	@FXML
	public void initialize() {
		Principal.log("Inicializando CadastroFilme");
	}

	@FXML
	private void cadastrar(ActionEvent event) {

	}

	@FXML
	private void limpar(ActionEvent event) {
		titulo.clear();
		genero.clear();
		diretor.clear();
		ano.getSelectionModel().selectLast();
		quant.getSelectionModel().selectFirst();
	}

}
