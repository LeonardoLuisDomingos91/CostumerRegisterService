package com.luis.leonardo.costumerregisterservice.security;



import com.luis.leonardo.costumerregisterservice.entity.User;
import com.luis.leonardo.costumerregisterservice.repository.UserRepository;
import com.luis.leonardo.costumerregisterservice.service.AuthenticationService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationInterceptor extends OncePerRequestFilter {

    private AuthenticationService authenticationService;

    private UserRepository userRepository;

    public AuthenticationInterceptor(AuthenticationService authenticationService, UserRepository userRepository) {
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
    }

    // O Interceptador será executado antes de ir para o controller em TODAS AS REQUISIÇÕES
    // O Spring Security sabe disso porque isso foi previamente configurado
    // no método SecurityConfigurations.configure

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

       String token = getToken(httpServletRequest);

       if(this.authenticationService.isTokenValid(token)){
           authenticateClient(token);
       }

       // Enviando requisição para o Controller
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    // Não precisa validar o usuário e senha de novo pois foi enviado um token válido
    // Portanto, aqui, eu apenas peço para o Spring autenticar o usuário

    private void authenticateClient(String token) {
        User user = getUserByToken(token);

        if(user != null){
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    public User getUserByToken(String token){
        Long userId = this.authenticationService.getUserId(token);
        return  this.userRepository.findById(userId).get();
    }

    private String getToken(HttpServletRequest request) {

        String token = request.getHeader("Authorization");

        if(token == null || token.isEmpty() || !token.startsWith("Bearer ")){
            return null;
        }

        return token.substring(7, token.length());
    }
}
