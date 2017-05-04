package model;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import model.utils.Container;

public class Filme extends Midia {

	private String genero;
	private String diretor;
	private int ano;

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getDiretor() {
		return diretor;
	}

	public void setDiretor(String diretor) {
		this.diretor = diretor;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public void gravarFilme(String titulo, String genero, String diretor, String ano) {
		Type filme = new TypeToken<Filme>() {
		}.getType();
		Gson gson = new Gson();

		Filme f = new Filme();
		try (Writer w = new FileWriter("src/data/filmes.db")) {
			gson.toJson(f.titulo);
			gson.toJson(f.genero);
			gson.toJson(f.ano);
			gson.toJson(f.diretor);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
