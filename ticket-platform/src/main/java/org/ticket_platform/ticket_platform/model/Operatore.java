package org.ticket_platform.ticket_platform.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "operatore")

public class Operatore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

   
    @NotNull
    private String stato;

    @NotNull
    @NotBlank(message ="codice obbligatorio")
    private String codice;
    
    @NotNull
    private String mail;

    @NotNull
    private String password;
       
    @OneToMany (mappedBy="operatore")
    @JsonBackReference
    private List<Ticket> tickets;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "operatore_ruolo",
        joinColumns = @JoinColumn(name = "operatore_id"),
        inverseJoinColumns = @JoinColumn(name = "ruolo_id")
    )
    private List <Ruolo> ruoli;


    public Integer getId() {
    return id;
   }
 

    public void setId(Integer id) {
    this.id = id;
   }

    public String getStato() {
    return stato;
   }


    public void setStato(String stato) {
    this.stato = stato;
   }

    public String getCodice() {
    return codice;
   }


    public void setCodice(String codice) {
    this.codice = codice;
   }

    public String getMail(){
    return mail;
   }

    public void setMail(String mail){
    this.mail = mail;
   }

  

    public String getPassword(){
    return password;
   }

    public void setPassword(String password){
    this.password = password;
   }

    public List<Ticket> getTickets() {
    return tickets;
   }

    public void setTickets(List<Ticket> tickets) {
    this.tickets = tickets;
   }

    public List<Ruolo> getRuoli() {
        return ruoli;
    }

    public void setRuoli(List<Ruolo> ruoli) {
        this.ruoli = ruoli;
    }


}
