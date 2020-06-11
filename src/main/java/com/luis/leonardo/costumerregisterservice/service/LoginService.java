package com.luis.leonardo.costumerregisterservice.service;

import com.luis.leonardo.costumerregisterservice.entity.User;
import com.luis.leonardo.costumerregisterservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService implements UserDetailsService {

    @Autowired
    UserRepository repository;

    @Override
    // Carregando o usuário informado com base no nome
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user =  repository.findByLogin(username);

        if(user.isPresent()){
            return user.get();
        }

        throw new UsernameNotFoundException("Dados inválidos");
    }

}
