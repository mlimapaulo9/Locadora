package model;

public abstract class Midia {
	protected String titulo;
	protected int quant;
	protected boolean alugado;
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public int getQuant() {
		return quant;
	}
	public void setQuant(int quant) {
		this.quant = quant;
	}
	public boolean isAlugado() {
		return alugado;
	}
	public void setAlugado(boolean alugado) {
		this.alugado = alugado;
	}
	
}
