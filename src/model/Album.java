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
		} else if (!forceSet && Album.buscarTitulo(titulo, false).size() != 0) {
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
		} else if (!forceSet && Album.buscarBanda(nomeBanda, false).size() != 0) {
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

	// FUNÇÕES ESTÁTICAS

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

	public static List<Album> buscarTitulo(String titulo, boolean maisDeUm) {
		List<Album> lista = new ArrayList<Album>();

		for (int i = 0; i < getAlbuns().getQuant(); i++) {
			if (getAlbuns().getLista().get(i).getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
				lista.add(getAlbuns().getLista().get(i));

				if (!maisDeUm)
					break;
			}
		}
		return lista;
	}

	public static List<Album> buscarBanda(String banda, boolean maisDeUm) {
		List<Album> lista = new ArrayList<Album>();

		for (int i = 0; i < getAlbuns().getQuant(); i++) {
			if (getAlbuns().getLista().get(i).getNomeBanda().toLowerCase().contains(banda.toLowerCase())) {
				lista.add(getAlbuns().getLista().get(i));

				if (!maisDeUm)
					break;
			}
		}
		return lista;
	}

	public static List<Album> buscarEstilo(String estilo, boolean maisDeUm) {
		List<Album> lista = new ArrayList<Album>();

		for (int i = 0; i < getAlbuns().getQuant(); i++) {
			if (getAlbuns().getLista().get(i).getEstilo().toLowerCase().contains(estilo.toLowerCase())) {
				lista.add(getAlbuns().getLista().get(i));

				if (!maisDeUm)
					break;
			}
		}
		return lista;
	}

	public static Album buscarID(int id) {
		for (Album album : getAlbuns().getLista()) {
			if (album.getId() == id) {
				return album;
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
