package org.ticket_platform.ticket_platform.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.ticket_platform.ticket_platform.model.Operatore;
import org.ticket_platform.ticket_platform.repository.OperatoreRepository;

@Service
public class DatabaseUserDetailsService implements UserDetailsService{

    @Autowired
    private OperatoreRepository operatoriRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Operatore> operatoreOpt = operatoriRepository.findByMail(username);
        if(operatoreOpt.isPresent()){
            return new DatabaseUserDetails(operatoreOpt.get());
        } else {
            throw new UsernameNotFoundException("Operatore non trovato");
        }
    }

}
