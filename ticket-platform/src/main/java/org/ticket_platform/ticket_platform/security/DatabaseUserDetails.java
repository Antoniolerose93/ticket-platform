package org.ticket_platform.ticket_platform.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.ticket_platform.ticket_platform.model.Operatore;
import org.ticket_platform.ticket_platform.model.Ruolo;


public class DatabaseUserDetails implements UserDetails {

    private String username;

    private String password;

    private Set<GrantedAuthority> authorities;

    public DatabaseUserDetails (Operatore operatore){
        this.username = operatore.getMail();
        this.password = operatore.getPassword();
        this.authorities = new HashSet<>();

        for (Ruolo ruolo:operatore.getRuoli()){
        SimpleGrantedAuthority sGA = new SimpleGrantedAuthority(ruolo.getName());
        this.authorities.add(sGA);
        }
    }

    public Collection<? extends GrantedAuthority> getAuthorities(){
        return authorities;
    }

    @Override
    public String getPassword(){
        return password;

    }

    @Override
    public String getUsername(){
        return username;
    }


}
