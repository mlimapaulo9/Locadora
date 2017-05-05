package model;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.time.LocalDate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import model.utils.Container;
import model.utils.exceptions.AtributoEmUsoException;
import view.Principal;

public class Filme extends Midia {

	private String genero;
	private String diretor;
	private int ano;

	public String getGenero() {
		return this.genero;
	}

	public void setGenero(String genero) {
		this.setGenero(genero, false);
	}

	public void setGenero(String genero, boolean forceSet) {
		if (genero.isEmpty() || genero.length() < 3) {
			throw new IllegalArgumentException("Gênero inválido!");
		} else {
			this.genero = genero;
		}
	}

	public void setDiretor(String diretor) {
		this.setDiretor(diretor, false);
	}

	public void setDiretor(String diretor, boolean forceSet) {
		if (diretor.isEmpty()) {
			throw new IllegalArgumentException("Diretor inválido!");
		} else if (!forceSet && Filme.buscarDiretor(diretor) != null) {
			throw new AtributoEmUsoException("Nome do Diretor");
		} else {
			this.diretor = diretor;
		}
	}

	public String getDiretor() {
		return this.diretor;
	}

	public int getAno() {
		return this.ano;
	}

	public void setAno(int ano) {
		if (ano < 1950 || ano > LocalDate.now().getYear()) {
			throw new IllegalArgumentException("Ano inválido!");
		} else {
			this.ano = ano;
		}
	}

	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.setTitulo(titulo, false);
	}

	public void setTitulo(String titulo, boolean forceSet) {
		if (titulo.isEmpty()) {
			throw new IllegalArgumentException("Título inválido!");
		} else if (!forceSet && Filme.buscarTitulo(titulo) != null) {
			throw new AtributoEmUsoException("Título");
		} else {
			super.titulo = titulo;
		}
	}

	// FUNÇÕES ESTÁTICAS

	public static Container<Filme> carregar() {
		Type filme = new TypeToken<Container<Filme>>() {
		}.getType();
		Gson gson = new Gson();

		Container<Filme> func = new Container<Filme>();
		try (Reader res = new FileReader("src/data/filmes.db")) {
			func = gson.fromJson(res, filme);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return func;
	}

	public static void salvar() {
		Gson gson = new Gson();

		try (FileWriter writer = new FileWriter("src/data/filmes.db")) {

			gson.toJson(Filme.getFilmes(), writer);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Container<Filme> getFilmes() {
		return Principal.getFilmes();
	}

	public static void add(Filme func) {
		getFilmes().add(func);
		getFilmes().setQuant(1);
		incLastId(1);
	}

	public static void remove(int pos) {
		getFilmes().remove(pos);
		getFilmes().setQuant(-1);
	}

	public static Filme buscarTitulo(String titulo) {

		for (int i = 0; i < getFilmes().getQuant(); i++) {
			if (titulo.equalsIgnoreCase(getFilmes().getLista().get(i).getTitulo())) {
				return getFilmes().getLista().get(i);
			}
		}
		return null;
	}

	public static Filme buscarDiretor(String diretor) {

		for (int i = 0; i < getFilmes().getQuant(); i++) {
			if (diretor.equalsIgnoreCase(getFilmes().getLista().get(i).getDiretor())) {
				return getFilmes().getLista().get(i);
			}
		}
		return null;
	}

	public static Filme buscarGenero(String genero) {

		for (int i = 0; i < getFilmes().getQuant(); i++) {
			if (genero.equalsIgnoreCase(getFilmes().getLista().get(i).getGenero())) {
				return getFilmes().getLista().get(i);
			}
		}
		return null;
	}

	public static int getLastId() {
		return Filme.getFilmes().getLastId();
	}

	public static void incLastId(int lastId) {
		Filme.getFilmes().setLastId(lastId);
	}

}
