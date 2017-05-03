package model.utils;

import java.util.ArrayList;
import java.util.List;

public class Container <T> {
	private List<T> lista;
	private int quant;
	
	public Container() {
		this.lista = new ArrayList<T>();
	}
	public List<T> getLista() {
		return lista;
	}
	public void add(T membro) {
		this.lista.add(membro);
	}
	public void remove(int index) {
		this.lista.remove(index);
	}
	public int getQuant() {
		return quant;
	}
	public void setQuant(int quant) {
		this.quant = quant;
	}
}