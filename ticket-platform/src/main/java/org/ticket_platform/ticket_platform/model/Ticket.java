package org.ticket_platform.ticket_platform.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "ticket")
public class Ticket {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @NotBlank(message ="L'autore è obbligatorio")
    private String autore;

    @NotNull
    @NotBlank(message="Il titolo è obbligatorio")
    private String titolo;

    @NotBlank(message ="Spiega la tua problematica")
    private String descrizione;
    
    @Column(nullable=false) //non può contenere valori falsi
    private String stato;
  
    @ManyToOne
    @JoinColumn(
            name = "categoria_id", nullable = false)
            private Categoria categoria;
        
    @OneToMany (mappedBy="ticket")
    private List<Nota> note;

    @ManyToOne(optional = false)
    @JoinColumn(name = "operatore_id")
    private Operatore operatore;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public String getTitolo(){
        return titolo;
    }

    public void setTitolo(String titolo){
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public Operatore getOperatore() {
        return operatore;
    }

    public void setOperatore(Operatore operatore) {
        this.operatore = operatore;
    }

    public List<Nota> getNote() {
        return note;
    }

    public void setNote(List<Nota> note) {
       this.note = note;
    }

    public Categoria getCategoria(){
        return categoria;
    }

    public void setCategoria(Categoria categoria){
        this.categoria = categoria;
    }

}
