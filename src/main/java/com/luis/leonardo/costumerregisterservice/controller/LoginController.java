package com.luis.leonardo.costumerregisterservice.controller;

import com.luis.leonardo.costumerregisterservice.dto.TokenDto;
import com.luis.leonardo.costumerregisterservice.form.LoginForm;
import com.luis.leonardo.costumerregisterservice.service.AuthenticationService;
import com.luis.leonardo.costumerregisterservice.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import javax.validation.Valid;


@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService service;

    @Autowired
    private AuthenticationService authenticationService;

    // Esta classe não é pré-configuarada para fazer Injeção de dependência
    // Portanto, precisa-se configurar manualmente
    // Foi configurado em: SecurityConfigurations.authenticationManager()
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<TokenDto> login(@RequestBody @Valid LoginForm form, UriComponentsBuilder uriBuilder){
        String login = form.getLogin();
        String password = form.getPassword();

        UsernamePasswordAuthenticationToken loginData = new UsernamePasswordAuthenticationToken(login, password);

        try{
            // O Spring irá invocar a classe de serviço
            // Tal classe foi configurada em: SecurityConfigurations.configure
            Authentication authentication = authenticationManager.authenticate(loginData);

            String token = authenticationService.generateToken(authentication);

            // O protocolo HTTP possui especificações de tipos de autenticação
            // Nesse projeto usou-se o BEARER, que significa estar enviando um token juntamente com a requisição
            // O token vai ser retornado ao cliente e o mesmo terá que informá-lo no cabeçalho em todas as requsições
            // O cliente terá que informar também o tipo da autenticação, no caso como dito, o BEARER
            return ResponseEntity.ok(new TokenDto(token, "Bearer"));
        }catch (AuthenticationException e){
            return ResponseEntity.status(401).build();
        }
    }
}
