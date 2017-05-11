package view;

import java.io.IOException;
import java.util.Optional;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Album;
import model.Cliente;
import model.Filme;
import model.Funcionario;
import model.utils.Container;
import model.utils.Sessao;

public class Principal extends Application {

	private static Stage palco;
	private static Pane subPalco;
	private static Label subTitulo;
	private static ImageView imagemConta;
	private static Container<Funcionario> funcionarios;
	private static Container<Cliente> clientes;
	private static Container<Album> albuns;
	private static Container<Filme> filmes;
	private static Sessao sessao = null;
	private static Integer idSubTela = 0;

	private static boolean isAdmin;
	private static boolean debug = true;
	
	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage palco) throws Exception {
		Principal.setAdmin(false);
		Principal.setSessao(null);
		Principal.funcionarios = Funcionario.carregar();
		Principal.albuns = Album.carregar();
		Principal.filmes = Filme.carregar();
		Principal.clientes = Cliente.carregar();		

		setPalco(palco);
		Principal.telaLogin();
	}
	
	public static void log(String message) {
		if (Principal.debug) {
			System.out.println(message);
		}
	}

	public static boolean isAdmin() {
		return Principal.isAdmin;
	}

	public static void setAdmin(boolean isAdmin) {
		Principal.isAdmin = isAdmin;
	}

	public static Container<Funcionario> getFuncionarios() {
		return Principal.funcionarios;
	}

	public static Container<Album> getAlbuns() {
		return Principal.albuns;
	}

	public static Container<Filme> getFilmes() {
		return Principal.filmes;
	}
	
	public static Container<Cliente> getClientes() {
		return Principal.clientes;
	}
	
	public static Integer getIdSubTela() {
		return idSubTela;
	}

	public static void setIdSubTela(Integer idSubTela) {
		Principal.idSubTela = idSubTela;
	}

	public static ImageView getImagemConta() {
		return Principal.imagemConta;
	}
	
	public static void setImagemConta(ImageView img) {
		Principal.imagemConta = img;
	}

	public static Label getSubTitulo() {
		return Principal.subTitulo;
	}

	public static void setSubTitulo(Label subTitulo) {
		Principal.subTitulo = subTitulo;
	}

	public static void setSubTitulo(String text) {
		Principal.getSubTitulo().setText(text);
	}

	public static void setPalco(Stage palco) {
		Principal.palco = palco;
	}

	public static Stage getPalco() {
		return Principal.palco;
	}
	public static void setSubPalco(Pane palco) {
		Principal.subPalco = palco;
	}

	public static Pane getSubPalco() {
		return Principal.subPalco;
	}
	
	public static Sessao getSessao() {
		return Principal.sessao;
	}
	
	public static boolean temSessao() {
		if (getSessao() != null) {
			return true;
		}
		return false;
	}
	
	public static void setSessao(Sessao sessao) {
		Principal.sessao = sessao;
	}
	
	public static String abrirJanelaCPF() {
		final String titulo = "Iniciar Sessão";
		Dialog<String> dialog = new Dialog<String>();
		dialog = new TextInputDialog();
		dialog.setTitle(titulo);
		dialog.setHeaderText("Entre com o CPF do Cliente (somente números).");
		 
		Optional<String> result = dialog.showAndWait();
		String entrada = "cancelar";
		 
		if (result.isPresent()) {
		 
		    entrada = result.get();
		}
		return entrada;
	}
	
	public static void abrirJanelaAlerta(AlertType tipo, String cabecalho, String mensagem) {
		Alert alerta = new Alert(tipo);
		alerta.setTitle(tipo.toString());
		alerta.setHeaderText(cabecalho);
		alerta.setContentText(mensagem);
		
		alerta.showAndWait();
	}

	public static void telaLogin() throws Exception {
		try {
			FXMLLoader loader = new FXMLLoader(Principal.class.getResource("LoginScreen.fxml"));

			Parent raiz = loader.load();

			Scene cena = new Scene(raiz);

			palco.setTitle("LocaMídia");
			palco.resizableProperty().setValue(Boolean.FALSE);

			palco.setScene(cena);
			palco.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void telaPrincipal() throws Exception{
		FXMLLoader loader = new FXMLLoader(Principal.class.getResource("Principal.fxml"));
		Pane raiz = loader.load();
		
		raiz.getChildren().get(0).toFront();

		Principal.getSubTitulo().toFront();
		Principal.getImagemConta().toFront();
		
		Principal.setSubPalco(raiz);
		
		Scene cena = new Scene(raiz);
		palco.setScene(cena);
		palco.show();
	}
	
	public static void criarTela(String fxmlFile) {
		try {
			Pane raiz = Principal.getSubPalco();
			
			Pane subPane = FXMLLoader.load(Principal.class.getResource(fxmlFile + ".fxml"));
			raiz.getChildren().set(0, subPane);
			raiz.getChildren().get(0).setLayoutY(30);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void criarSubTela(String fxmlFile) {
		try {
			Stage palco = new Stage();
			FXMLLoader loader = new FXMLLoader(Principal.class.getResource(fxmlFile + ".fxml"));
			Pane raiz = loader.load();
			
			palco.setTitle("LocaMídia");
			palco.resizableProperty().setValue(Boolean.FALSE);
			palco.initOwner(Principal.getPalco());
			palco.initModality(Modality.WINDOW_MODAL);			

			Scene cena = new Scene(raiz);
			palco.setScene(cena);
			palco.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
