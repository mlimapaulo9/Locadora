package model;

public abstract class Midia {
	protected int id;
	protected String titulo;
	protected int quantidade;
	protected int alugados;

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

	public int getAlugados() {
		return this.alugados;
	}

	public void setAlugados(int alugados) {
		if (alugados >= 0 && alugados <= this.getQuantidade())
		{
			this.alugados = alugados;
		}
		else {
			throw new IllegalArgumentException("Número de itens alugados não pode exceder a quantidade disponível.");
		}		
	}

}
