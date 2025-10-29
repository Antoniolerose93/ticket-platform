package org.ticket_platform.ticket_platform.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.ticket_platform.ticket_platform.model.Categoria;
import org.ticket_platform.ticket_platform.model.Nota;
import org.ticket_platform.ticket_platform.model.Ticket;
import org.ticket_platform.ticket_platform.repository.CategoriaRepository;
import org.ticket_platform.ticket_platform.repository.NotaRepository;
import org.ticket_platform.ticket_platform.repository.OperatoreRepository;
import org.ticket_platform.ticket_platform.repository.TicketRepository;
import org.ticket_platform.ticket_platform.security.DatabaseUserDetails;

import jakarta.validation.Valid;

@Controller 
@RequestMapping ("/tickets")
public class TicketController {


    @Autowired
    
    private TicketRepository ticketRepository;

    @Autowired

    private CategoriaRepository categorieRepository;

    @Autowired
    private OperatoreRepository operatoriRepository;

    @Autowired

    private NotaRepository noteRepository;

    @GetMapping("/")
    public String index(Model model, @RequestParam(name="keyword", required = false) String keyword,
                    @AuthenticationPrincipal DatabaseUserDetails userDetails) {

    List<Ticket> tickets;

    // Se admin mostra tutti i ticket
    if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("admin"))) {
        // Admin  visualizza tutti i ticket o filtra per titolo
        if (keyword == null || keyword.isBlank()) {
            tickets = ticketRepository.findAll();
        } else {
            tickets = ticketRepository.findByTitoloContainingIgnoreCase(keyword);
        }
    } else {
        // Operatore visualizza solo i ticket assegnati a lui
        if (keyword == null || keyword.isBlank()) {
            tickets = ticketRepository.findByOperatoreMail(userDetails.getUsername());
        } else {
            tickets = ticketRepository.findByOperatoreMailAndTitoloContainingIgnoreCase(
                userDetails.getUsername(), keyword);
        }
    }

    model.addAttribute("list", tickets);
    return "/tickets/index";
    }

    @GetMapping("/show/{id}")
        public String show(@PathVariable("id") Integer id, Model model){
            Optional <Ticket> optionalTicket = ticketRepository.findById(id);
            if(optionalTicket.isPresent()) {
                model.addAttribute("ticket", optionalTicket.get());
                model.addAttribute("empty", false);
            }  else {
                model.addAttribute ("empty", true);
            
            }

            return "/tickets/show";
            }

    @PostMapping("/show/{id}")
    public String aggiornaStato(@PathVariable ("id") Integer id, @RequestParam("stato") String stato){
        Optional<Ticket> optionalTicket = ticketRepository.findById(id);
        if(optionalTicket.isPresent()) {
            Ticket ticket = optionalTicket.get();
            ticket.setStato(stato);
            ticketRepository.save(ticket);
            return "redirect:/tickets/show/" + id;
        } else{
            return "redirect:/tickets/";
        }

        }
    
    @GetMapping("/create")
        public String create (Model model) {
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("categoria", categorieRepository.findAll());
        model.addAttribute("operatore", operatoriRepository.findByStato("libero")); // <--- aggiungi questo
        return "/tickets/create";
        }

    @PostMapping("/create") @Valid
        public String save(@Valid @ModelAttribute("ticket") Ticket formTicket,
        BindingResult bindingResult, Model model , RedirectAttributes redirectAttributes) {
  
        Optional<Categoria> optCat = categorieRepository.findById(formTicket.getCategoria().getId());
        if(optCat.isEmpty()) { 
      
        bindingResult.addError(new ObjectError("categoria", "Questa categoria non esiste"));
        }   
        
        if(bindingResult.hasErrors()) {
            model.addAttribute("categoria", categorieRepository.findAll());
            model.addAttribute("operatore", operatoriRepository.findByStato("libero"));
            return "/tickets/create";
        } 
       
        formTicket.setCategoria(optCat.get());
        formTicket.setStato("In corso");
        
        ticketRepository.save(formTicket);
        redirectAttributes.addFlashAttribute("successMessage", "Ticket inserito con successo");
        return "redirect:/tickets/";
        }  


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable ("id") Integer id, Model model) {
        Optional <Ticket> optPizza = ticketRepository.findById(id);
        Ticket ticket = optPizza.get();
        model.addAttribute("ticket", ticket);
        model.addAttribute("categoria", categorieRepository.findAll());
        return "/tickets/edit";
        }

    @PostMapping("/edit/{id}") 
    public String update(@Valid @ModelAttribute("ticket") Ticket formTicket, BindingResult bindingResult,Model model) {    
        
       
        Ticket oldTicket = ticketRepository.findById(formTicket.getId()).get();
       
        if(!oldTicket.getTitolo().equals(formTicket.getTitolo())) { 
        bindingResult.addError(new ObjectError("nome", "Non si può cambiare il nome del ticket"));
        }
        if(!oldTicket.getDescrizione().equals(formTicket.getDescrizione())) {
            bindingResult.addError(new ObjectError("dettagli", "Non si può cambiare la descrizione"));
        }

        if(!oldTicket.getAutore().equals(formTicket.getAutore())) {
            bindingResult.addError(new ObjectError("autore", "Non si può cambiare l'autore"));
        }
        
        if (bindingResult.hasErrors()) {
            return "/tickets/edit";
        }    

        ticketRepository.save(formTicket); 
      
        
        return "redirect:/tickets/";

        }

    @PostMapping("/delete/{id}")
        public String delete (@PathVariable("id") Integer id){
        Ticket ticket = ticketRepository.findById(id).get();
        for (Nota noteToDelete : ticket.getNote()){
            noteRepository.delete(noteToDelete);
        }
        ticketRepository.deleteById(id);

        return "redirect:/tickets/";
        }

    @GetMapping("/{id}/note")
        public String offer(@PathVariable("id") Integer id, Model model) {
        Nota note = new Nota();
        note.setTicket(ticketRepository.findById(id).get());
        model.addAttribute("nota", note);
        model.addAttribute("editMode", false); 
        return "/note/edit";
        }




}
