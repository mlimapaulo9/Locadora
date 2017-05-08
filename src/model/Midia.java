package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Midia {
	protected Integer id;
	protected String titulo;
	protected Integer quantidade;
	protected List<Integer> alugadoPor = new ArrayList<Integer>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		if (quantidade > 0 && quantidade <= 5)
		{
			this.quantidade = quantidade;
		}
		else {
			throw new IllegalArgumentException("Quantidade não pode exceder 5.");
		}	
	}

	public List<Integer> getAlugadores() {
		return this.alugadoPor;
	}

	public void setAlugadores(List<Integer> alugados) {
		if (alugados.size() >= 0 && alugados.size() <= this.getQuantidade())
		{
			this.alugadoPor = alugados;
		}
		else {
			throw new IllegalArgumentException("Número de itens alugados não pode exceder a quantidade disponível.");
		}		
	}
	
	public void addAlugador(Integer id) {
		this.alugadoPor.add(id);
	}
	
	public void removeAlugador(Integer index) {
		this.alugadoPor.remove(index);
	}
}
