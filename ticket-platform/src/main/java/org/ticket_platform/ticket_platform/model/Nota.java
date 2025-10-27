package org.ticket_platform.ticket_platform.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class Nota {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String testo;

    @NotNull
    private String autore;
 
    @NotNull
    private LocalDate dataCreazione;

    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    @JsonBackReference
    private Ticket ticket;

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public String getTesto(){
        return testo;
    }

    public void setTesto (String testo){
        this.testo = testo;
    }

    public String getAutore(){
        return autore;
    }

    public void setAutore(String autore){
        this.autore = autore;
    }

    public LocalDate getDataCreazione(){
        return dataCreazione;
    }

    public void setDataCreazione(LocalDate dataCreazione){
        this.dataCreazione = dataCreazione;
    }

    public Ticket getTicket(){
        return ticket;
    }

    public void setTicket(Ticket ticket){
        this.ticket = ticket;
    }

}
