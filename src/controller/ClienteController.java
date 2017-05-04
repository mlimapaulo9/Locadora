package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import view.Principal;

public class ClienteController {
	@FXML
	private TextField nome;
	@FXML
	private TextField cpf;
	@FXML
	private TextField logradouro;
	@FXML
	private TextField numero;
	@FXML
	private TextField bairro;
	@FXML
	private TextField cep;
	@FXML
	private TextField id;
	@FXML
	private Label lblErros;

	@FXML
	private void initialize() {
		Principal.log("Inicializando CadastroCliente");
	}

	@FXML
	private void cadastrar(ActionEvent event) {

	}

	@FXML
	private void limpar(ActionEvent event) {
		nome.clear();
		cpf.clear();
		logradouro.clear();
		numero.clear();
		bairro.clear();
		cep.clear();
	}
}
