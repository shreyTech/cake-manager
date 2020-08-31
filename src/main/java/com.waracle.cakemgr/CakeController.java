package com.waracle.cakemgr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Configuration
@Controller
public class CakeController {

    private static Logger LOGGER = LoggerFactory.getLogger(CakeController.class);

    @Autowired
    private CakeRepository cakeRepo;

    @GetMapping(value = "/index")
    public String getAllCakes(Model model) {
        model.addAttribute("cakes", cakeRepo.findAll());
        model.addAttribute("newCake", new CakeEntity());
        return "index";
    }

    @PostMapping(value = "/index")
    public String createCake(@ModelAttribute CakeEntity newCake, Model model) {
        cakeRepo.save(newCake);
        model.addAttribute("cakes", cakeRepo.findAll());
        model.addAttribute("newCake", new CakeEntity());
        return "index";
    }

}
