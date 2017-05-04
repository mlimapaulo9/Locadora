package model.utils.exceptions;

public class AtributoEmUsoException extends RuntimeException {

	public AtributoEmUsoException(String atributo) {
		super(atributo + " já está em uso!");
	}

}
