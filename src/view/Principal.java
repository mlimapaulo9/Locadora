package view;

import controller.PesquisarController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Funcionario;
import model.utils.Container;

public class Principal extends Application {
	
	private static Stage palco;
	private static Label subTitulo;
	private static Container<Funcionario> funcionarios;
	private static boolean isAdmin;
	
	public static boolean isAdmin() {
		return Principal.isAdmin;
	}
	public static void setAdmin(boolean isAdmin) {
		Principal.isAdmin = isAdmin;
	}
	public static Container<Funcionario> getFuncionarios() {
		return funcionarios;
	}
	public static Label getSubTitulo() {
		return subTitulo;
	}
	public static void setSubTitulo(Label subTitulo) {
		Principal.subTitulo = subTitulo;
	}
	@Override public void start(Stage palco) throws Exception {
		Principal.setAdmin(false);
		Principal.funcionarios = Funcionario.carregarDoArquivo();
		setPalco(palco);
		this.telaLogin();
		System.out.println(Principal.isAdmin());
	}
	public static void setPalco(Stage palco) {
		Principal.palco = palco;
	}
	public Stage getPalco() {
		return Principal.palco;
	}
	public void telaLogin() throws Exception {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("loginscreen.fxml"));
			
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
	public void telaPesquisa() throws Exception {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Principal.fxml"));
			Pane raiz = loader.load();
			PesquisarController.setSubTitulo(Principal.getSubTitulo());
			Pane pesquisa = FXMLLoader.load(getClass().getResource("../view/Pesquisar.fxml"));
			
			raiz.getChildren().set(1, pesquisa);
			raiz.getChildren().get(0).toFront();
			
			Principal.getSubTitulo().toFront();
			
			Scene cena = new Scene(raiz);

			palco.setScene(cena);
			palco.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void telaCadastroFilmes() throws Exception
	{
		try{
			System.out.println("Cadastro Filme");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Principal.fxml"));
			Pane raiz = loader.load();
			Pane cadastroFilme = FXMLLoader.load(getClass().getResource("../view/CadastroFilme.fxml"));
			
			raiz.getChildren().set(1, cadastroFilme);
			raiz.getChildren().get(0).toFront();
			
			Principal.getSubTitulo().toFront();
			
			Scene cena = new Scene(raiz);
			
			palco.setScene(cena);
			palco.show();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
		}
		
	}
	public static void main(String[] args) {
		launch();
	}
}
