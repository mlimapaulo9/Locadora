package model.utils.exceptions;

public class NomeEmUsoException extends RuntimeException {

	public NomeEmUsoException() {
		super("Nome de usuário já está em uso!");
	}

}
