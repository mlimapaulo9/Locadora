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
	private List<Integer> filmesAlugados;
	private List<Integer> albunsAlugados;

	public Cliente() {
		this.filmesAlugados = new ArrayList<Integer>();
		this.albunsAlugados = new ArrayList<Integer>();
	}

	public String getCPF() {
		return this.cpf;
	}

	public void setCPF(String cpf) {
		if (cpf.isEmpty() || cpf.length() < 11) {
			throw new IllegalArgumentException("CPF inválido! O CPF deve ter 11 digitos númericos.");
		} else if (Cliente.buscarCPF(cpf, false).size() != 0) {
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
	
	public boolean isFilmesAlugadosAllNegative() {
		for (Integer i : getFilmesAlugados()) {
			if (i > 0) {
				return true;
			}
		}
		return false;
	}
	
	public List<Integer> getFilmesAlugados() {
		return this.filmesAlugados;
	}

	public void setFilmesAlugados(List<Integer> alugados) {
		setFilmesAlugados(alugados, false);
	}

	public void setFilmesAlugados(List<Integer> alugados, boolean forceSet) {
		if (forceSet) {
			this.filmesAlugados = new ArrayList<Integer>();
		}
		this.filmesAlugados = alugados;
	}

	public void addFilmeAlugado(Integer alugado) {
		this.getFilmesAlugados().add(alugado);
	}

	public Integer removeFilmeAlugado(int pos) {
		return this.getFilmesAlugados().remove(pos);
	}
	
	public boolean isAlbunsAlugadosAllNegative() {
		for (Integer i : getAlbunsAlugados()) {
			if (i > 0) {
				return true;
			}
		}
		return false;
	}
	
	public List<Integer> getAlbunsAlugados() {
		return this.albunsAlugados;
	}

	public void setAlbunsAlugados(List<Integer> alugados) {
		setAlbunsAlugados(alugados, false);
	}

	public void setAlbunsAlugados(List<Integer> alugados, boolean forceSet) {
		if (forceSet) {
			this.albunsAlugados = new ArrayList<Integer>();
		}
		this.albunsAlugados = alugados;
	}

	public void addAlbumAlugado(Integer alugado) {
		this.getAlbunsAlugados().add(alugado);
	}

	public Integer removeAlbumAlugado(int pos) {
		return this.getAlbunsAlugados().remove(pos);
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
		try (Reader res = new FileReader("data/clientes.db")) {
			func = gson.fromJson(res, cliente);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return func;
	}

	public static void salvar() {
		Gson gson = new Gson();

		try (FileWriter writer = new FileWriter("data/clientes.db")) {

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

	public static void removeObj(Cliente cliente) {
		getClientes().removeObj(cliente);
		getClientes().setQuant(-1);
	}

	public static Cliente buscarNome(String nome) {
		List<Cliente> lista = buscarNome(nome, false);

		if (lista != null && lista.size() > 0) {
			return lista.get(0);
		} else {
			return null;
		}
	}

	public static List<Cliente> buscarNome(String nome, boolean maisDeUm) {
		List<Cliente> lista = new ArrayList<Cliente>();

		for (int i = 0; i < getClientes().getQuant(); i++) {
			if (getClientes().getLista().get(i).getNome().toLowerCase().contains(nome.toLowerCase())) {
				lista.add(getClientes().getLista().get(i));

				if (!maisDeUm)
					break;
			}
		}
		return lista;
	}

	public static List<Cliente> buscarCPF(String cpf, boolean maisDeUm) {
		List<Cliente> lista = new ArrayList<Cliente>();

		if (!maisDeUm && cpf.length() < 11)
			return lista;

		for (int i = 0; i < getClientes().getQuant(); i++) {
			if (getClientes().getLista().get(i).getCPF().contains(cpf)) {
				lista.add(getClientes().getLista().get(i));

				if (!maisDeUm)
					break;
			}
		}
		return lista;
	}

	public static Cliente buscarID(int id) {
		for (Cliente cliente : getClientes().getLista()) {
			if (cliente.getId() == id) {
				return cliente;
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
