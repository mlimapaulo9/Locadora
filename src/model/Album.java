package model;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import model.utils.Container;
import model.utils.exceptions.*;
import view.Principal;

public class Album extends Midia {

	private String nomeBanda;
	private String estilo;
	
	public void setTitulo(String titulo) {
		this.setTitulo(titulo, false);
	}
	
	public void setTitulo(String titulo, boolean forceSet) {
		if (titulo.isEmpty()) {
			throw new IllegalArgumentException("Título inválido!");
		} else if (!forceSet && Album.buscarTitulo(titulo) != null) {
			throw new AtributoEmUsoException("Título");
		} else {
			super.titulo = titulo;
		}
	}
	
	public void setNomeBanda(String nomeBanda) {
		this.setNomeBanda(nomeBanda, false);
	}
	
	public void setNomeBanda(String nomeBanda, boolean forceSet) {
		if (nomeBanda.isEmpty()) {
			throw new IllegalArgumentException("Nome de banda/autor inválido!");
		} else if (!forceSet && Album.buscarBanda(nomeBanda) != null) {
			throw new AtributoEmUsoException("Nome de banda/autor");
		} else {
			this.nomeBanda = nomeBanda;
		}
	}
	
	public String getNomeBanda() {
		return nomeBanda;
	}

	public String getEstilo() {
		return estilo;
	}

	public void setEstilo(String estilo) {
		this.setEstilo(estilo, false);
	}
	public void setEstilo(String estilo, boolean forceSet) {
		if (estilo.isEmpty() || estilo.length() < 3) {
			throw new IllegalArgumentException("Estilo inválido!");
		} else {
			this.estilo = estilo;
		}
	}
	
	//FUNÇÕES ESTÁTICAS
	
	public static Container<Album> carregar() {
		Type album = new TypeToken<Container<Album>>() {
		}.getType();
		Gson gson = new Gson();

		Container<Album> func = new Container<Album>();
		try (Reader res = new FileReader("data/albuns.db")) {
			func = gson.fromJson(res, album);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return func;
	}

	public static void salvar() {
		Gson gson = new Gson();

		try (FileWriter writer = new FileWriter("data/albuns.db")) {

			gson.toJson(Album.getAlbuns(), writer);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Container<Album> getAlbuns() {
		return Principal.getAlbuns();
	}

	public static void add(Album func) {
		getAlbuns().add(func);
		getAlbuns().setQuant(1);
		incLastId(1);
	}

	public static void remove(int pos) {
		getAlbuns().remove(pos);
		getAlbuns().setQuant(-1);
	}

	public static Album buscarTitulo(String titulo) {

		for (int i = 0; i < getAlbuns().getQuant(); i++) {
			if (titulo.equalsIgnoreCase(getAlbuns().getLista().get(i).getTitulo())) {
				return getAlbuns().getLista().get(i);
			}
		}
		return null;
	}
	
	public static Album buscarBanda(String banda) {

		for (int i = 0; i < getAlbuns().getQuant(); i++) {
			if (banda.equalsIgnoreCase(getAlbuns().getLista().get(i).getNomeBanda())) {
				return getAlbuns().getLista().get(i);
			}
		}
		return null;
	}
	
	public static Album buscarEstilo(String estilo) {

		for (int i = 0; i < getAlbuns().getQuant(); i++) {
			if (estilo.equalsIgnoreCase(getAlbuns().getLista().get(i).getNomeBanda())) {
				return getAlbuns().getLista().get(i);
			}
		}
		return null;
	}

	public static int getLastId() {
		return Album.getAlbuns().getLastId();
	}

	public static void incLastId(int lastId) {
		Album.getAlbuns().setLastId(lastId);
	}

}
