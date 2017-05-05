package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Album;
import model.Cliente;
import model.Filme;
import model.Funcionario;
import model.utils.Container;

public class Principal extends Application {

	private static Stage palco;
	private static Label subTitulo;
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

	public Stage getPalco() {
		return Principal.palco;
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

	public static void criarTela(String fxmlFile) {
		Principal.criarTela(fxmlFile, false);
	}

	public static void criarTela(String fxmlFile, boolean isPrincipal) {
		try {
			FXMLLoader loader = new FXMLLoader(Principal.class.getResource("Principal.fxml"));
			Pane raiz = loader.load();

			if (!isPrincipal) {
				Pane subPane = FXMLLoader.load(Principal.class.getResource(fxmlFile + ".fxml"));
				raiz.getChildren().set(1, subPane);
			}
			raiz.getChildren().get(0).toFront();
			raiz.getChildren().get(0).setLayoutY(30);
			Principal.getSubTitulo().toFront();

			Scene cena = new Scene(raiz);
			palco.setScene(cena);
			palco.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
