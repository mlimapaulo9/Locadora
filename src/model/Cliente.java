package model;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import model.utils.Container;
import model.utils.exceptions.AtributoEmUsoException;
import view.Principal;

public class Cliente extends Usuario {
	private String cpf;
	private Endereco endereco;
	private List<Midia> alugados;
	
	public Cliente() {
		this.alugados = new ArrayList<Midia>();
	}

	public String getCPF() {
		return this.cpf;
	}

	public void setCPF(String cpf) {
		if (cpf.isEmpty() || cpf.length() < 11) {
			throw new IllegalArgumentException("CPF inválido! O CPF deve ter 11 digitos númericos.");
		} else if (Cliente.buscarCPF(cpf) != null) {
			throw new AtributoEmUsoException("CPF");
		} else {
			this.cpf = cpf;
		}
	}

	public Endereco getEndereco() {
		return this.endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Midia> getAlugados() {
		return this.alugados;
	}

	public void setAlugados(List<Midia> alugados) {
		this.alugados = alugados;
	}

	public void addAlugado(Midia alugado) {
		this.getAlugados().add(alugado);
	}

	public Midia removeAlugado(int pos) {
		Midia temp;

		temp = this.getAlugados().get(pos);
		this.getAlugados().remove(pos);

		return temp;
	}

	public void setNome(String nome) {
		if (nome.isEmpty() || nome.length() < 4) {
			throw new IllegalArgumentException("Nome de cliente inválido!");
		} else {
			super.nome = nome;
		}
	}

	// FUNÇÕES ESTÁTICAS

	public static Container<Cliente> carregar() {
		Type cliente = new TypeToken<Container<Cliente>>() {
		}.getType();
		Gson gson = new Gson();

		Container<Cliente> func = new Container<Cliente>();
		try (Reader res = new FileReader("src/data/clientes.db")) {
			func = gson.fromJson(res, cliente);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return func;
	}

	public static void salvar() {
		Gson gson = new Gson();

		try (FileWriter writer = new FileWriter("src/data/clientes.db")) {

			gson.toJson(Cliente.getClientes(), writer);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Container<Cliente> getClientes() {
		return Principal.getClientes();
	}

	public static void add(Cliente func) {
		getClientes().add(func);
		getClientes().setQuant(1);
		incLastId(1);
	}

	public static void remove(int pos) {
		getClientes().remove(pos);
		getClientes().setQuant(-1);
	}

	public static Cliente buscarNome(String nome) {

		for (int i = 0; i < getClientes().getQuant(); i++) {
			if (nome.equalsIgnoreCase(getClientes().getLista().get(i).getNome())) {
				return getClientes().getLista().get(i);
			}
		}
		return null;
	}

	public static Cliente buscarCPF(String cpf) {

		if (cpf.length() < 11)
			return null;

		for (int i = 0; i < getClientes().getQuant(); i++) {
			if (cpf.equalsIgnoreCase(getClientes().getLista().get(i).getCPF())) {
				return getClientes().getLista().get(i);
			}
		}
		return null;
	}

	public static int getLastId() {
		return Cliente.getClientes().getLastId();
	}

	public static void incLastId(int lastId) {
		Cliente.getClientes().setLastId(lastId);
	}

}
