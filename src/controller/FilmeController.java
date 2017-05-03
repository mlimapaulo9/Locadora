package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import model.Filme;
import view.Principal;

public class FilmeController {

	@FXML private TextField titulo;	
	@FXML private TextField genero;
	@FXML private TextField diretor;
	@FXML private TextField ano;
	@FXML private Button gravar;
	
	Filme model;
	
	@FXML public void initialize() {
		Principal.log("Inicializando CadastroFilme");
	}
	
	@FXML protected void gravarFilme(ActionEvent event){
		model = new Filme();
		model.gravarFilme(titulo.getText(), genero.getText(), diretor.getText(), ano.getText());
	}
	
	
	
	
	
}
