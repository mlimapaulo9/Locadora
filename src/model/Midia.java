package model;

public abstract class Midia {
	protected int id;
	protected String titulo;
	protected int quantidade;
	protected int alugado;

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

	public int getQuant() {
		return quantidade;
	}

	public void setQuant(int quant) {
		this.quantidade = quant;
	}

	public int isAlugado() {
		return alugado;
	}

	public void setAlugado(int alugado) {
		this.alugado = alugado;
	}

}
