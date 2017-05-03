package model;

import javafx.scene.control.Label;
import view.Principal;

public class Login {
	
	private Principal principal;
	
	public void autenticar(String login, String senha, Label incorrectInfo){
		Funcionario func = Funcionario.buscar(login);
		
		if (func != null && func.autenticar(login, senha)) {
			try {
				principal = new Principal();
				if (func.getCargo().equals("Administrador")) {
					System.out.println("olá");
					Principal.setAdmin(true);
				}
				principal.telaPesquisa();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			incorrectInfo.setVisible(true);
		}
	}
}
