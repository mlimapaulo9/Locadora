package model;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import model.utils.Container;
import view.Principal;

public class Funcionario extends Usuario {

	private String senha;
	private String cargo;

	public static int getLastId() {

		return Funcionario.getFuncionarios().getLastId();
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		if (!senha.isEmpty() && senha.length() < 6) {
			this.senha = senha;
		} else {
			throw new IllegalArgumentException(
					"Campo \"Senha\" inválido! A senha precisa ter pelo menos 6 caracteres.");
		}
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		if (!cargo.isEmpty()) {
			this.cargo = cargo;
		} else {
			throw new IllegalArgumentException("Campo \"Cargo\" inválido!");
		}
	}

	public boolean autenticar(String login, String senha) {
		if (senha.equals(this.getSenha())) {
			return true;
		}
		return false;
	}

	// FUNÇÕES ESTÀTICAS

	public static Container<Funcionario> carregarDoArquivo() {
		Type funcionario = new TypeToken<Container<Funcionario>>() {
		}.getType();
		Gson gson = new Gson();

		Container<Funcionario> func = new Container<Funcionario>();
		try (Reader res = new FileReader("src/data/funcionarios.db")) {
			func = gson.fromJson(res, funcionario);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return func;
	}

	public static void salvar() {
		Gson gson = new Gson();

		try (FileWriter writer = new FileWriter("src/data/funcionarios.db")) {

			gson.toJson(Funcionario.getFuncionarios(), writer);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Container<Funcionario> getFuncionarios() {
		return Principal.getFuncionarios();
	}

	public static void add(Funcionario func) {
		getFuncionarios().add(func);
		getFuncionarios().setQuant(1);
	}

	public static void remove(int pos) {
		getFuncionarios().remove(pos);
		getFuncionarios().setQuant(-1);
	}

	public static Funcionario buscar(String nome) {

		for (int i = 0; i < getFuncionarios().getQuant(); i++) {
			if (nome.equals(getFuncionarios().getLista().get(i).getNome())) {
				return getFuncionarios().getLista().get(i);
			}
		}
		return null;
	}

}