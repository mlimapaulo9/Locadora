package view;

import java.util.List;
import java.util.Optional;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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

	private static boolean isAdmin;
	private static boolean debug = true;
	
	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage palco) throws Exception {
		Principal.setAdmin(false);
		anulaSessaoInstance();
		
		Principal.funcionarios = Funcionario.carregar();
		Principal.albuns = Album.carregar();
		Principal.filmes = Filme.carregar();
		Principal.clientes = Cliente.carregar();		

		setPalco(palco);
		Principal.telaLogin();
	}
	
	public static boolean isDebugging() {
		return debug;
	}
	
	public static void log(String message) {
		if (Principal.isDebugging()) {
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
	
	
	//Sessao
	public static Sessao getSessao() {
		return Sessao.getInstance();
	}
	
	public static void anulaSessaoInstance() {
		Sessao.anulaInstance();
	}
	
	public static boolean temSessao() {
		if (Sessao.getInstanciaUnica() != null) {
			return true;
		}
		return false;
	}
	public static void alterarSessao(boolean b) {
		Sessao.setAlterou(b);
	}
	public static boolean alterouSessao() {
		return Sessao.alterou();
	}
	
	public static void alteraClienteSessao(Cliente cliente) {
		Sessao.setCliente(cliente);
	}
	
	public static Cliente getClienteSessao() {
		return Sessao.getCliente();
	}
	
	public static List<Integer> getAlbunsAlugados() {
		return Sessao.getAlbunsAlugados();
	}
	public static List<Integer> getFilmesAlugados() {
		return Sessao.getFilmesAlugados();
	}
	public static void removerFilmeSessao(Integer filmeID) {
		Sessao.removerFilme(filmeID);
	}
	public static void adicionarFilmeSessao(Integer filmeID) {
		Sessao.adicionarFilme(filmeID);
	}
	public static void removerAlbumSessao(Integer albumID) {
		Sessao.removerAlbum(albumID);
	}
	public static void adicionarAlbumSessao(Integer albumID) {
		Sessao.adicionarAlbum(albumID);
	}
	
	//FIM SESSAO
	
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
