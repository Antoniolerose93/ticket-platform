package org.ticket_platform.ticket_platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.ticket_platform.ticket_platform.repository.CategoriaRepository;

@Controller

@RequestMapping("/categorie") 

public class CategorieController {


    @Autowired
    
    private CategoriaRepository categorieRepository;

    @GetMapping()
    public String index(Model model){
        model.addAttribute("categoria", categorieRepository.findAll());
        return "categorie/index";
    }


}
