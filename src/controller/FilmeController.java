package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import model.Filme;
import model.utils.exceptions.AtributoEmUsoException;
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
	private Label lblErros;
	@FXML
	private Button btnCancelar;

	@FXML
	public void initialize() {
		Principal.log("Inicializando CadastroFilme");
		
		for (int i = 1950; i <= LocalDate.now().getYear(); i++) {
			ano.getItems().add(i);
		}
		
		ano.getSelectionModel().selectLast();
		
		for (int i = 1; i <= 5; i++) {
			quant.getItems().add(i);
		}
		
		quant.getSelectionModel().selectFirst();
		
		id.setText(Integer.toString(Filme.getLastId() + 1));
	}

	@FXML
	private void cadastrar(ActionEvent event) {
		String erros = "";
		boolean temErro = false;
		Filme temp = new Filme();
		
		try {
			temp.setDiretor(diretor.getText());
		} catch (IllegalArgumentException e) {
			erros += e.getMessage() + "\n";
			temErro = true;
		} catch (AtributoEmUsoException e) {
			try {
				temp.setTitulo(titulo.getText());
			} catch (IllegalArgumentException e2) {
				erros += e2.getMessage() + "\n";
				temErro = true;
			} catch (AtributoEmUsoException e2) {
				erros += "Esse filme já está cadastrado para esse(a) diretor(a)! \n";
				temErro = true;
			}
			finally {
				temp.setDiretor(diretor.getText(), true);
			}
		}
		if (temp.getTitulo() == null) {
			try {
				temp.setTitulo(titulo.getText(), true);
			} catch (IllegalArgumentException e) {
				erros += e.getMessage() + "\n";
				temErro = true;
			}
		}
		try {
			temp.setGenero(genero.getText());
		} catch (IllegalArgumentException e) {
			erros += e.getMessage() + "\n";
			temErro = true;
		}
		

		lblErros.setText(erros);

		if (!temErro) {
			temp.setQuantidade(quant.getValue());
			temp.setAlugadores(new ArrayList<Integer>());
			temp.setAno(ano.getValue());
			
			temp.setId(Integer.parseInt(id.getText()));
			Filme.add(temp);
			Filme.salvar();
			Principal.log("Salvando...");
			
			lblErros.setStyle("-fx-text-fill: green");
			lblErros.setText("Filme cadastrado com sucesso!");
			id.setText(Integer.toString(Filme.getLastId() + 1));
			
			this.limpar(event);
		} else {
			lblErros.setStyle("-fx-text-fill: red");
			java.awt.Toolkit.getDefaultToolkit().beep();
		}

		lblErros.setVisible(true);		
	}

	@FXML
	private void limpar(ActionEvent event) {
		titulo.clear();
		genero.clear();
		diretor.clear();
		ano.getSelectionModel().selectLast();
		quant.getSelectionModel().selectFirst();
		if (event.getSource().equals(btnCancelar))
			lblErros.setVisible(false);
	}

}
