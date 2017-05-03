package model;

import javafx.scene.control.Label;
import view.Principal;

public class Login {
	
	public void autenticar(String login, String senha, Label incorrectInfo){
		Funcionario func = Funcionario.buscar(login);
		
		if (func != null && func.autenticar(login, senha)) {
			try {
				if (func.getCargo().equals("Administrador")) {
					Principal.setAdmin(true);
				}
				Principal.telaPrincipal();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			incorrectInfo.setVisible(true);
		}
	}
}
