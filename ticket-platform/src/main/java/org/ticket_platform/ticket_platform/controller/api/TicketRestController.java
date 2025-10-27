package org.ticket_platform.ticket_platform.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ticket_platform.ticket_platform.model.Ticket;
import org.ticket_platform.ticket_platform.repository.TicketRepository;

@RestController
@CrossOrigin
@RequestMapping("/api/tickets")
public class TicketRestController {

    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping
    public List<Ticket> getTickets(@RequestParam(name="keyword", required=false) String keyword){
    if(keyword != null && !keyword.isBlank()){
        // Se c'è una keyword, filtra i ticket
        return ticketRepository.findByTitoloContainingIgnoreCase(keyword);
    } else {
        // Altrimenti ritorna tutti i ticket
        return ticketRepository.findAll();
    }

    }

    @GetMapping("/categoria")
    public List<Ticket> getTicketsByCategoria(@RequestParam(name = "categoriaNome") String categoriaNome) {

        if (categoriaNome != null && !categoriaNome.isBlank()) {
            return ticketRepository.findByCategoriaNomeIgnoreCase(categoriaNome);
        } else {
            return ticketRepository.findAll();
        }
    }
    
    @GetMapping("/stato")
    public List<Ticket> getTicketsByStato(@RequestParam(name = "stato") String stato) {
    return ticketRepository.findByStatoIgnoreCase(stato);
}


}
