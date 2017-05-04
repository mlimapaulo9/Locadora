package model.utils.exceptions;

public class SenhaInvalidaException extends RuntimeException {
	public SenhaInvalidaException() {
		super("A senha deve ter pelo menos 6 caracteres.");
	}
}
