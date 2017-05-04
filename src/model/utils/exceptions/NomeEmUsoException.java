package model.utils.exceptions;

public class NomeEmUsoException extends RuntimeException {

	public NomeEmUsoException() {
		super("Nome já em uso!");
	}

}
