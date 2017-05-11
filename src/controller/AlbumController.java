package controller;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Album;
import model.utils.exceptions.AtributoEmUsoException;
import view.Principal;

public class AlbumController {
	@FXML
	private TextField titulo;
	@FXML
	private TextField nomeBanda;
	@FXML
	private TextField estilo;
	@FXML
	private ComboBox<Integer> quant;
	@FXML
	private TextField id;
	@FXML
	private Label lblErros;
	@FXML
	private Button btnCancelar;

	@FXML
	private void initialize() {
		Principal.log("Inicializando CadastroAlbum");
		
		for (int i = 1; i <= 5; i++){
			quant.getItems().add(i);
		}
		
		quant.getSelectionModel().selectFirst();
		
		id.setText(Integer.toString(Album.getLastId() + 1));
	}

	@FXML
	private void cadastrar(ActionEvent event) {
		String erros = "";
		boolean temErro = false;
		Album temp = new Album();
		
		try {
			temp.setNomeBanda(nomeBanda.getText());
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
				erros += "Esse album já está cadastrado para esse(a) autor/banda!";
				temErro = true;
			}
			finally {
				temp.setNomeBanda(nomeBanda.getText(), true);
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
			temp.setEstilo(estilo.getText());
		} catch (IllegalArgumentException e) {
			erros += e.getMessage() + "\n";
			temErro = true;
		}
		

		lblErros.setText(erros);

		if (!temErro) {
			temp.setQuantidade(quant.getValue());
			temp.setAlugadores(new ArrayList<Integer>());
			
			temp.setId(Integer.parseInt(id.getText()));
			Album.add(temp);
			Album.salvar();
			Principal.log("Salvando...");
			
			lblErros.setStyle("-fx-text-fill: green");
			lblErros.setText("Album cadastrado com sucesso!");
			id.setText(Integer.toString(Album.getLastId() + 1));
			
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
		nomeBanda.clear();
		estilo.clear();
		quant.getSelectionModel().selectFirst();
		if (event.getSource().equals(btnCancelar))
			lblErros.setVisible(false);
	}
}
