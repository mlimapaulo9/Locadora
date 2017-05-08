package model.utils;

import java.util.List;

import model.Cliente;
import view.Principal;

public class Sessao {
	private Cliente cliente;
	private List<Integer> filmesAlugados;
	private List<Integer> albunsAlugados;
	
	public Sessao(Cliente cliente) {
		this.cliente = cliente;
		filmesAlugados = cliente.getFilmesAlugados();
		albunsAlugados = cliente.getAlbunsAlugados();
		Principal.log(cliente.getNome());
	}
	
	public Sessao() {}
	
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
	}
	public void adicionarFilme(Integer filmeID) {
		this.getFilmesAlugados().add(filmeID);
	}
	public synchronized void removerFilme(Integer filmeID) {
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
	public List<Integer> getAlbunsAlugados() {
		return albunsAlugados;
	}
	public void setAlbunsAlugados(List<Integer> albunsAlugados) {
		this.albunsAlugados = albunsAlugados;
	}
	public void adicionarAlbum(Integer albumID) {
		this.getAlbunsAlugados().add(albumID);
	}
	public synchronized void removerAlbum(Integer albumID) {
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
	
	public String toString() {
		String tmp = "";
		tmp += "Cliente: " + getCliente().getNome();
		tmp += "\nFilmes: " + getFilmesAlugados().toString();
		tmp += "\nAlbuns: " + getAlbunsAlugados().toString();
		return tmp;
	}
}