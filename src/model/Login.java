package model;

import view.Principal;

public class Login {

	public static boolean autenticar(String login, String senha) {
		Funcionario func = Funcionario.buscar(login);
		if (func != null && func.autenticar(login, senha)) {
			if (func.getCargo().equalsIgnoreCase("administrador")) {
				Principal.setAdmin(true);
			}
			return true;
		} else {
			return false;
		}
	}
}
