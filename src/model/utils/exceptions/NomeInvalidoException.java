package model.utils.exceptions;

public class NomeInvalidoException extends RuntimeException {
	public NomeInvalidoException() {
		super("Nome inválido!");
	}
}