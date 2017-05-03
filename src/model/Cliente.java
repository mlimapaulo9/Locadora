package model;

import java.util.List;

public class Cliente extends Usuario {
	private int cpf;
	private Endereco endereco;
	private List<Midia> alugados;
	
	public int getCpf() {
		return cpf;
	}
	public void setCpf(int cpf) {
		this.cpf = cpf;
	}
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	public List<Midia> getAlugados() {
		return alugados;
	}
	public void setAlugados(List<Midia> alugados) {
		this.alugados = alugados;
	}
	
	
}
