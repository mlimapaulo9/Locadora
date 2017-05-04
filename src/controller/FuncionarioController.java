package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Funcionario;
import view.Principal;

public class FuncionarioController {
	@FXML
	private TextField nome;
	@FXML
	private TextField cargo;
	@FXML
	private PasswordField senha;
	@FXML
	private TextField id;

	@FXML
	public void initialize() {
		Principal.log("Inicializando CadastroFuncionario");
	}

	@FXML
	public void cadastrar() {
		Funcionario temp = new Funcionario();
		if (cargo.getText() == null) {

		} else {
			temp.setCargo(cargo.getText());
		}
		if (nome.getText() == null) {

		} else {
			temp.setNome(nome.getText());
		}
		if (senha.getText() == null) {

		} else {
			temp.setSenha(senha.getText());
		}
		temp.setId(99);

		Funcionario.add(temp);
		Funcionario.salvar();
		Principal.log("Salvando...");
	}
	
	@FXML
	private void limpar(ActionEvent event) {
		nome.clear();
		cargo.clear();
		senha.clear();
	}
}
