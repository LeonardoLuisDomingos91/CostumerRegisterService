package com.luis.leonardo.costumerregisterservice.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticatedUserUtil {

    private static String getUserAunthenticatedName(){
        //Obtem o nome do usuário logado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return  authentication.getName();
    }

    public static String buildErrorMessage(String[] perfis){
        StringBuilder builder = new StringBuilder("Usuários com permissão para esse recurso: [");

        // Constrói a mensagem informando os perfis que podem acessar o recurso
        for(int i = 0; i < perfis.length; i++ ) {
            if (i == (perfis.length - 1)) {
                builder.append(perfis[i] + "] ");
            } else{
                builder.append(perfis[i] + ", ");
            }
        }

        return builder.toString();
    }

    public static Boolean doesUserHaveAuthorizationToAccessTheResource(String[] perfis){

        String currentPrincipalName = getUserAunthenticatedName();

        // Verifica se o usuário possui o perfil para acessar o recurso
        for(int i = 0; i < perfis.length; i++ ) {
            if(currentPrincipalName.compareToIgnoreCase(perfis[i]) == 0) {
                return Boolean.TRUE;
            }
        }

        return Boolean.FALSE;
    }

}
