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

public class Album extends Midia {

	private String nomeBanda;
	private String estilo;

	public String getNomeBanda() {
		return nomeBanda;
	}

	public void setNomeBanda(String nomeBanda) {
		this.nomeBanda = nomeBanda;
	}

	public String getEstilo() {
		return estilo;
	}

	public void setEstilo(String estilo) {
		this.estilo = estilo;
	}
	
	//FUNÇÕES ESTÁTICAS
	
	public static Container<Album> carregar() {
		Type album = new TypeToken<Container<Album>>() {
		}.getType();
		Gson gson = new Gson();

		Container<Album> func = new Container<Album>();
		try (Reader res = new FileReader("src/data/albuns.db")) {
			func = gson.fromJson(res, album);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return func;
	}

	public static void salvar() {
		Gson gson = new Gson();

		try (FileWriter writer = new FileWriter("src/data/albuns.db")) {

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

	public static Album buscar(String titulo) {

		for (int i = 0; i < getAlbuns().getQuant(); i++) {
			if (titulo.equals(getAlbuns().getLista().get(i).getTitulo())) {
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
