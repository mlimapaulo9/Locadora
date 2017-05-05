package model;

public class Endereco {
	private String logradouro;
	private String bairro;
	private String numero;
	private String cep;

	public String getLogradouro() {
		return this.logradouro;
	}

	public void setLogradouro(String logradouro) {
		if (logradouro.isEmpty() || logradouro.length() < 4) {
			throw new IllegalArgumentException("Logradouro inválido!");
		} else {
			this.logradouro = logradouro;
		}
	}

	public String getBairro() {
		return this.bairro;
	}

	public void setBairro(String bairro) {
		if (bairro.isEmpty()) {
			throw new IllegalArgumentException("Bairro inválido!");
		} else {
			this.bairro = bairro;
		}
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		int countNum = 0;
		char arr[] = numero.toCharArray();
		for (char c : arr) {
			if (Character.isDigit(c)) {
				countNum++;
			}
		}
		if (countNum == 0 || numero.isEmpty()) {
			throw new IllegalArgumentException("Campo \"Número\" inválido!");
		} else {
			this.numero = numero;
		}
	}

	public String getCEP() {
		return cep;
	}

	public void setCEP(String cep) {
		if (cep.isEmpty() || cep.length() != 8) {
			throw new IllegalArgumentException("CEP inválido! O CEP deve ter 8 digitos númericos.");
		}
		else {
			this.cep = cep;
		}
	}
}
