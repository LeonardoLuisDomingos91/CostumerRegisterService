package com.luis.leonardo.costumerregisterservice.security;


import com.luis.leonardo.costumerregisterservice.repository.UserRepository;
import com.luis.leonardo.costumerregisterservice.service.AuthenticationService;
import com.luis.leonardo.costumerregisterservice.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

// Habilitando Segurança usando funcionalidades do Spring Security
@EnableWebSecurity

// Informando ao Spring que essa é uma classe de configuração
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginService loginService;

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception{
        return super.authenticationManager();
    }

    // Configurando a autenticação por: Login e Password
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginService).passwordEncoder(new BCryptPasswordEncoder());
    }

    // Encriptando a senha para persistir na base de dados no modelo encriptado

//    public static void main(String[] args) {
//        String adminEncoded = new BCryptPasswordEncoder().encode("admin");
//        System.out.println("Admin encoded: " + adminEncoded);
//
//        String gerente = new BCryptPasswordEncoder().encode("gerente");
//        System.out.println("Gerente: " + gerente);
//
//        String usuario = new BCryptPasswordEncoder().encode("usuario");
//        System.out.println("Usuario: " + usuario);
//    }

    //Configurando a autorização, isto é, qual perfil pode acessar qual URL
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.headers().frameOptions().disable();

        http.authorizeRequests()
                //Habilitando acesso aos recursos públicos (Não precisa de autenticação)
                .antMatchers("/login").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated()
                // CSRF é um tipo de ataque hacker
                // Porque a aplicação é autenticável via usuário pelo token
                // Nesse caso, não precisa-se controlar tal ataque, por isso o CSRF está desabilitado
                .and().csrf().disable()
                // Informando ao Spring Security que a autenticação não será por sessão
                // Mas sim, por token
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors()
                .and()
                // Adicionando o filtro que será executado em todas as requisições
                .addFilterBefore(new AuthenticationInterceptor(authenticationService, userRepository), UsernamePasswordAuthenticationFilter.class);

    }


    //Configurando o CORS da aplicação
    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;

    }
}
