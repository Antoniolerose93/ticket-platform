package org.ticket_platform.ticket_platform.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.ticket_platform.ticket_platform.model.Operatore;
import org.ticket_platform.ticket_platform.model.Ticket;
import org.ticket_platform.ticket_platform.repository.OperatoreRepository;
import org.ticket_platform.ticket_platform.repository.TicketRepository;
import org.ticket_platform.ticket_platform.security.DatabaseUserDetails;



@Controller
@RequestMapping("/operatori")

public class OperatoreController {

    @Autowired
    private OperatoreRepository operatoriRepository;

    @Autowired
    private TicketRepository ticketRepository;

   @GetMapping({"", "/"})
    public String index(Model model, @AuthenticationPrincipal DatabaseUserDetails userDetails,
    @RequestParam(name="keyword", required = false) String keyword) {

    String ruolo = userDetails.getAuthorities().iterator().next().getAuthority();

    if ("admin".equals(ruolo)) {
    // L'admin vede tutti gli operatori o filtrati per keyword
    if (keyword == null || keyword.isBlank()) {
        model.addAttribute("operatori", operatoriRepository.findAll());
    } else {
        model.addAttribute("operatori",
                operatoriRepository.findByCodiceContainingIgnoreCase(keyword));
    }
    } else {
    // L'operatore mostra solo se stesso
    Optional<Operatore> optionalOperatore = operatoriRepository.findByMail(userDetails.getUsername());
    if (optionalOperatore.isPresent()) {
        Operatore operatore = optionalOperatore.get();
        model.addAttribute("operatori", List.of(operatore));
    }
    }
    return "operatore/index";
    }
    
        
    @GetMapping("/show/{id}") 
    public String show(@PathVariable Integer id, Model model){ 
        Optional<Operatore>operatoreOpt= operatoriRepository.findById(id); 
        
        
        if(operatoreOpt.isPresent()){
            model.addAttribute("operatore", operatoreOpt.get());
            model.addAttribute("empty", false);
        } else{
            model.addAttribute("empty", true); 
        }

    return "operatore/show";
    }
  
    @PostMapping("/show/{id}/stato")
    public String updateStato(@PathVariable Integer id, @RequestParam("stato") String stato,
                              RedirectAttributes redirectAttributes) {

        Optional<Operatore> operatoreOpt = operatoriRepository.findById(id);
        if (operatoreOpt.isPresent()) {
            Operatore operatore = operatoreOpt.get();

            // Controlla se ha ticket assegnati
            List<Ticket> ticketsAssegnati = ticketRepository.findByOperatoreId(operatore.getId());

            if (!ticketsAssegnati.isEmpty()) {
                // L'operatore ha ancora ticket → non può cambiare stato
                redirectAttributes.addFlashAttribute("errorMessage",
                        "Impossibile modificare lo stato: ci sono ancora ticket assegnati.");
                return "redirect:/operatori/show/" + id + "?error=haTicket";
            }

            // Se non ha ticket, può cambiare stato
            operatore.setStato(stato);
            operatoriRepository.save(operatore);
            redirectAttributes.addFlashAttribute("successMessage", "Stato aggiornato con successo.");
        }

        return "redirect:/operatori/show/" + id;
        }


}
