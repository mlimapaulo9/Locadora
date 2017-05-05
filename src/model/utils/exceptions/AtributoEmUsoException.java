package model.utils.exceptions;

@SuppressWarnings("serial")
public class AtributoEmUsoException extends RuntimeException {

	public AtributoEmUsoException(String atributo) {
		super(atributo + " já está em uso!");
	}

}
