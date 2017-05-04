package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import model.Funcionario;
import model.utils.exceptions.*;
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
	private Label lblErros;
	@FXML
	private RadioButton radOperador;
	@FXML
	private RadioButton radAdministrador;
	@FXML
	private Button btnCancelar;

	@FXML
	public void initialize() {
		Principal.log("Inicializando CadastroFuncionario");
		id.setText(Integer.toString(Funcionario.getLastId() + 1));
	}

	@FXML
	public void cadastrar(ActionEvent event) {
		String erros = "";
		boolean temErro = false;
		Funcionario temp = new Funcionario();
		try {
			temp.setNome(nome.getText());
		} catch (AtributoEmUsoException e) {
			erros += e.getMessage() + "\n";
			temErro = true;
		} catch (IllegalArgumentException e) {
			erros += e.getMessage() + "\n";
			temErro = true;
		}
		if (radAdministrador.isSelected()) {
			temp.setCargo("Administrador");
		} else {
			temp.setCargo("Operador");
		}
		try {
			temp.setSenha(senha.getText());
		} catch (IllegalArgumentException e) {
			erros += e.getMessage() + "\n";
			temErro = true;
		}
		temp.setId(Integer.parseInt(id.getText()));

		lblErros.setText(erros);

		if (!temErro) {
			Funcionario.add(temp);
			Funcionario.salvar();
			Principal.log("Salvando...");
			
			lblErros.setStyle("-fx-text-fill: green");
			lblErros.setText("Funcionário cadastrado com sucesso!");
			id.setText(Integer.toString(Funcionario.getLastId() + 1));
			
			this.limpar(event);
		} else {
			lblErros.setStyle("-fx-text-fill: red");
			java.awt.Toolkit.getDefaultToolkit().beep();
		}

		lblErros.setVisible(true);
	}

	@FXML
	private void limpar(ActionEvent event) {
		nome.clear();
		radOperador.setSelected(true);
		senha.clear();
		if (event.getSource().equals(btnCancelar))
			lblErros.setVisible(false);
	}
}
