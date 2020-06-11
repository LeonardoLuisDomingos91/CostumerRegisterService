package com.luis.leonardo.costumerregisterservice.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class LoginForm {

    // Tal anotação diz que para o que foi recebido na request ser válido, o atributo nao deve ser
    // Nulo nem vazio
    @NotNull
    @NotEmpty
    private String login;

    // Tal anotação diz que para o que foi recebido na request ser válido, o atributo nao deve ser
    // Nulo nem vazio
    @NotNull
    @NotEmpty
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
