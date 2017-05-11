package model.utils;

import java.util.ArrayList;
import java.util.List;

import model.Cliente;

public class Sessao {
	private static Cliente cliente;
	private static List<Integer> filmesAlugados;
	private static List<Integer> albunsAlugados;
	private static boolean alterou;
	private static Sessao instanciaUnica;

	public Sessao() {
		filmesAlugados = new ArrayList<Integer>();
		albunsAlugados = new ArrayList<Integer>();
		alterou = false;
	}
	
	public static void anulaInstance() {
		instanciaUnica = null;
	}
	
	public static Sessao getInstance() {
		if (instanciaUnica == null) {
			instanciaUnica = new Sessao();
		}
		return instanciaUnica;
	}
	
	public static Sessao getInstanciaUnica() {
		return instanciaUnica;
	}
	public static boolean alterou() {
		return alterou;
	}
	public static void setAlterou(boolean b) {
		Sessao.alterou = b;
	}
	public static Cliente getCliente() {
		return cliente;
	}

	public static void setCliente(Cliente cliente) {
		Sessao.cliente = cliente;
		filmesAlugados = new ArrayList<Integer>(cliente.getFilmesAlugados());
		albunsAlugados = new ArrayList<Integer>(cliente.getAlbunsAlugados());
		alterou = false;
	}

	public static List<Integer> getFilmesAlugados() {
		return filmesAlugados;
	}

	public static void setFilmesAlugados(List<Integer> filmesAlugados) {
		Sessao.filmesAlugados = filmesAlugados;
		alterou = true;
	}

	public static void adicionarFilme(Integer filmeID) {
		getFilmesAlugados().add(filmeID);
		alterou = true;
	}

	public static void removerFilme(Integer filmeID) {
		if (filmeID == -1) {
			List<Integer> temp = new ArrayList<Integer>();
			setFilmesAlugados(temp);
		} else {
			Integer remover = null;
			for (Integer id : getFilmesAlugados()) {
				if (id == filmeID) {
					remover = filmeID;
					break;
				}
			}
			if (remover != null) {
				getFilmesAlugados().remove(remover);
			}
		}
		alterou = true;
	}

	public static List<Integer> getAlbunsAlugados() {
		return albunsAlugados;
	}

	public static void setAlbunsAlugados(List<Integer> albunsAlugados) {
		Sessao.albunsAlugados = albunsAlugados;
		alterou = true;
	}

	public static void adicionarAlbum(Integer albumID) {
		getAlbunsAlugados().add(albumID);
		alterou = true;
	}

	public static void removerAlbum(Integer albumID) {
		if (albumID == -1) {
			List<Integer> temp = new ArrayList<Integer>();
			setAlbunsAlugados(temp);
		} else {
			Integer remover = null;
			for (Integer id : getAlbunsAlugados()) {
				if (id == albumID) {
					remover = albumID;
					break;
				}
			}
			if (remover != null) {
				getAlbunsAlugados().remove(remover);
			}
		}
		alterou = true;
	}

	public String toString() {
		String tmp = "";
		tmp += "Cliente: " + getCliente().getNome();
		tmp += "\nFilmes: " + getFilmesAlugados().toString();
		tmp += "\nAlbuns: " + getAlbunsAlugados().toString();
		return tmp;
	}
}