package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import view.Principal;

public class ClienteController {
	@FXML TextField nome;
	@FXML TextField cpf;
	@FXML TextField logradouro;
	@FXML TextField numero;
	@FXML TextField bairro;
	@FXML TextField cep;
	@FXML TextField id;
	
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
