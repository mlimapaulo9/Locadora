package model;

import java.util.List;

public abstract class Midia {
	protected int id;
	protected String titulo;
	protected int quantidade;
	protected List<Integer> alugadoPor;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
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
	
	public void addAlugador(int id) {
		this.alugadoPor.add(id);
	}
	
	public void removeAlugador(int index) {
		this.alugadoPor.remove(index);
	}
}
