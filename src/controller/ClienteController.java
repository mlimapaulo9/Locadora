package controller;

import java.util.ArrayList;
import java.util.function.UnaryOperator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import model.Cliente;
import model.Endereco;
import model.utils.exceptions.AtributoEmUsoException;
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
	private Button btnCancelar;

	@FXML
	private void initialize() {
		Principal.log("Inicializando CadastroCliente");
		UnaryOperator<Change> filter = control -> {
			String text = control.getText();

			if (text.matches("[0-9]*")) {
				return control;
			}
			return null;
		};
		TextFormatter<String> textFormatter = new TextFormatter<>(filter);
		cpf.setTextFormatter(textFormatter);
		TextFormatter<String> textFormatter2 = new TextFormatter<>(filter);
		cep.setTextFormatter(textFormatter2);

		id.setText(Integer.toString(Cliente.getLastId() + 1));
	}

	@FXML
	private void cadastrar(ActionEvent event) {
		String erros = "";
		boolean temErro = false;
		Cliente temp = new Cliente();
		try { // setar o nome
			temp.setNome(nome.getText());
		} catch (IllegalArgumentException e) {
			erros += e.getMessage() + "\n";
			temErro = true;
		}
		try { // setar o cpf
			temp.setCPF(cpf.getText());
		} catch (IllegalArgumentException e) {
			erros += e.getMessage() + "\n";
			temErro = true;
		} catch (AtributoEmUsoException e) {
			erros += e.getMessage() + "\n";
			temErro = true;
		}
		Endereco tempEnd = new Endereco();
		try { // setar o logradouro
			tempEnd.setLogradouro(logradouro.getText());
		} catch (IllegalArgumentException e) {
			erros += e.getMessage() + "\n";
			temErro = true;
		}
		try { // setar o numero
			tempEnd.setNumero(numero.getText());
		} catch (IllegalArgumentException e) {
			erros += e.getMessage() + "\n";
			temErro = true;
		}
		try { // setar o bairro
			tempEnd.setBairro(bairro.getText());
		} catch (IllegalArgumentException e) {
			erros += e.getMessage() + "\n";
			temErro = true;
		}
		try { // setar o CEP
			tempEnd.setCEP(cep.getText());
		} catch (IllegalArgumentException e) {
			erros += e.getMessage() + "\n";
			temErro = true;
		}

		lblErros.setText(erros);

		if (!temErro) {
			temp.setEndereco(tempEnd);
			temp.setFilmesAlugados(new ArrayList<Integer>());
			temp.setAlbunsAlugados(new ArrayList<Integer>());
			temp.setId(Integer.parseInt(id.getText()));

			Cliente.add(temp);
			Cliente.salvar();
			Principal.log("Salvando...");

			lblErros.setStyle("-fx-text-fill: green");
			lblErros.setText("Cliente cadastrado com sucesso!");
			id.setText(Integer.toString(Cliente.getLastId() + 1));

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
		cpf.clear();
		logradouro.clear();
		numero.clear();
		bairro.clear();
		cep.clear();
		if (event.getSource().equals(btnCancelar))
			lblErros.setVisible(false);
	}
}
