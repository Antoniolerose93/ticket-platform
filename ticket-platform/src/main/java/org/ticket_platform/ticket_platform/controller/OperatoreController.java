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
import org.ticket_platform.ticket_platform.model.Operatore;
import org.ticket_platform.ticket_platform.repository.OperatoreRepository;
import org.ticket_platform.ticket_platform.security.DatabaseUserDetails;



@Controller
@RequestMapping("/operatori")

public class OperatoreController {

    @Autowired
    private OperatoreRepository operatoriRepository;

    @GetMapping({"","/"})
public String index(Model model, @AuthenticationPrincipal DatabaseUserDetails userDetails,
                    @RequestParam(name="keyword", required = false) String keyword) {

    List<Operatore> operatori;

    // Se admin mostra tutti gli operatori
    if (userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("admin"))) {
        operatori = (keyword == null || keyword.isBlank())
                ? operatoriRepository.findAll()
                : operatoriRepository.findByCodiceContainingIgnoreCase(keyword);
    } else { // altrimenti mostra solo se stesso
        Operatore operatore = operatoriRepository.findByMail(userDetails.getUsername()).orElse(null);
        operatori = List.of(operatore);
    }

    model.addAttribute("operatori", operatori);
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
    public String updateStato(@PathVariable Integer id, @RequestParam("stato") String stato){
        Optional<Operatore> operatoreOpt = operatoriRepository.findById(id);
        if(operatoreOpt.isPresent()){
            Operatore operatore = operatoreOpt.get();
            operatore.setStato(stato);
            operatoriRepository.save(operatore);
        }    
            return "redirect:/operatori/show/" + id;

    }


}
