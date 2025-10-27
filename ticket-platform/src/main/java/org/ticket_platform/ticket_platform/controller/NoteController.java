package org.ticket_platform.ticket_platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.ticket_platform.ticket_platform.model.Nota;
import org.ticket_platform.ticket_platform.repository.NotaRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("note")
public class NoteController {

    @Autowired 
    private NotaRepository notaRepository; 
    
    
    @PostMapping("/create")
    public String create(@Valid @ModelAttribute ("note") Nota note, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("nota", note);
            return "create/edit";
        }
        notaRepository.save(note);
        return "redirect:/tickets/show/" + note.getTicket().getId();
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id")Integer id, Model model){
        Nota note = notaRepository.findById(id).get();
        model.addAttribute("editMode", true);
        model.addAttribute("nota", note);
        return "/note/edit";
    }

    @PostMapping("/edit/{id}")

    public String update (@Valid @ModelAttribute ("note") Nota note, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("editMode", true);
            return "/note/edit";
        }
        notaRepository.save(note);
        return "redirect:/tickets/show/" + note.getTicket().getId();
    }

    @PostMapping("/delete/{id}") 
   
    public String delete(@PathVariable("id")Integer id, @RequestParam("ticketId") Integer ticketId){
    notaRepository.deleteById(id);
    return "redirect:/tickets/show/" + ticketId;
    }
}
