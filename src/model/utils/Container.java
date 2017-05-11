package model.utils;

import java.util.ArrayList;
import java.util.List;

public class Container<T> {
	private List<T> lista;
	private int quant;
	private int lastId;

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
	
	public void removeObj(T obj) {
		this.lista.remove(obj);
	}

	public int getQuant() {
		return quant;
	}

	public void setQuant(int quant) {
		this.setQuant(quant, false);
	}

	public void setQuant(int quant, boolean forceSet) {
		if (forceSet) {
			this.quant = quant;
		} else {
			this.quant += quant;
		}
	}

	public int getLastId() {
		return lastId;
	}

	public void setLastId(int lastId) {
		this.setLastId(lastId, false);
	}

	public void setLastId(int lastId, boolean reset) {
		if (reset) {
			this.lastId = lastId;
		} else {
			this.lastId += lastId;
		}
	}
}