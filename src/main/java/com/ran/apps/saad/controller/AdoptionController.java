package com.ran.apps.saad.controller;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ran.apps.saad.filters.JwtAccessFilter;
import com.ran.apps.saad.model.Animal;
import com.ran.apps.saad.service.AnimalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class AdoptionController {

    @Autowired
    private AnimalService animalService;

    @Autowired
    private JwtAccessFilter accessFilter;

    @RequestMapping
    public String getAnimals(Model model, HttpServletRequest request) {
        if (accessFilter.checkCookiesForToken(request)) {
            List<Animal> animals = animalService.getAnimals();
            List<Animal> cats = new ArrayList<Animal>();
            List<Animal> dogs = new ArrayList<Animal>();
            List<Animal> bunnys = new ArrayList<Animal>();
            List<Animal> rats = new ArrayList<Animal>();
            animals.forEach(animal -> {
                if (!animal.isAdopted()) {
                    animal.setStr(Base64.getEncoder().encodeToString(animal.getImg()));
                    if (animal.getType().equals("cat")) {
                        cats.add(animal);
                    }
                    if (animal.getType().equals("dog")) {
                        dogs.add(animal);
                    }
                    if (animal.getType().equals("bunny")) {
                        bunnys.add(animal);
                    }
                    if (animal.getType().equals("rat")) {
                        rats.add(animal);
                    }
                }
            });
            model.addAttribute("cats", cats);
            model.addAttribute("dogs", dogs);
            model.addAttribute("bunnys", bunnys);
            model.addAttribute("rats", rats);
            return "index";
        }
        return "redirect:/login";
    }

    @RequestMapping(path = "/adoptIt", method = RequestMethod.POST)
    public String getAnimal(@RequestParam(value = "responsable", required = true) String name,
    @RequestParam(value = "date", required = true) String date,
            @RequestParam(value = "id") Integer id, HttpServletRequest request) {
        if (accessFilter.checkCookiesForToken(request)) {
            Animal animal = animalService.getAnimalById(id);
            animal.setAdopted(true);
            animal.setResponsable(name);
            animal.setDate(date);
            animalService.saveAnimal(animal);
            return "redirect:/";
        }
        return "redirect:/login";
    }

    @GetMapping("/adopt/{id}")
    public String getAdopt(Model model, @PathVariable("id") Integer id, HttpServletRequest request) {
        if (accessFilter.checkCookiesForToken(request)) {
            Animal animal = animalService.getAnimalById(id);
            model.addAttribute("animal", animal);
            return "adopt";
        }
        return "redirect:/login";
    }

}