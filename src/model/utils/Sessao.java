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
	public void removerItem(int index) {
		this.getFilmesAlugados().remove(index);
	}
	public List<Integer> getAlbunsAlugados() {
		return albunsAlugados;
	}
	public void setAlbunsAlugados(List<Integer> albunsAlugados) {
		this.albunsAlugados = albunsAlugados;
	}
	public void adicionarAlbum(Integer filmeID) {
		this.getAlbunsAlugados().add(filmeID);
	}
	public void removerAlbum(int index) {
		this.getAlbunsAlugados().remove(index);
	}
}