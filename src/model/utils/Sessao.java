package model.utils;

import java.util.ArrayList;
import java.util.List;

import model.Cliente;
import view.Principal;

public class Sessao {
	private Cliente cliente;
	private List<Integer> filmesAlugados;
	private List<Integer> albunsAlugados;
	private boolean alterou;

	public Sessao(Cliente cliente) {
		setCliente(cliente);
		filmesAlugados = new ArrayList<Integer>(cliente.getFilmesAlugados());
		albunsAlugados = new ArrayList<Integer>(cliente.getAlbunsAlugados());
		alterou = false;
	}

	public Sessao() {
		filmesAlugados = new ArrayList<Integer>();
		albunsAlugados = new ArrayList<Integer>();
		alterou = false;
	}
	public boolean alterou() {
		return alterou;
	}
	public void setAlterou(boolean b) {
		this.alterou = b;
	}
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Integer> getFilmesAlugados() {
		return filmesAlugados;
	}

	public void setFilmesAlugados(List<Integer> filmesAlugados) {
		this.filmesAlugados = filmesAlugados;
		alterou = true;
	}

	public void adicionarFilme(Integer filmeID) {
		this.getFilmesAlugados().add(filmeID);
		alterou = true;
	}

	public void removerFilme(Integer filmeID) {
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

	public List<Integer> getAlbunsAlugados() {
		return albunsAlugados;
	}

	public void setAlbunsAlugados(List<Integer> albunsAlugados) {
		this.albunsAlugados = albunsAlugados;
		alterou = true;
	}

	public void adicionarAlbum(Integer albumID) {
		this.getAlbunsAlugados().add(albumID);
		alterou = true;
	}

	public void removerAlbum(Integer albumID) {
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