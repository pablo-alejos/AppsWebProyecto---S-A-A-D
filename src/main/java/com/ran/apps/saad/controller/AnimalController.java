package com.ran.apps.saad.controller;

import java.util.Optional;

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
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/animal")
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    @Autowired
    private JwtAccessFilter accessFilter;

    @RequestMapping("/new")
    public String getAdmin(Model model, HttpServletRequest request) {
        if (accessFilter.checkCookiesForToken(request)) {
            model.addAttribute("animal", new Animal());
            return "formAnimal";
        }
        return "redirect:/login";
    }

    @RequestMapping(path = { "/update/{id}" })
    public String editAnimalById(Model model, @PathVariable(value = "id", required = true) Integer id,
            HttpServletRequest request) {
        if (accessFilter.checkCookiesForToken(request)) {
            Animal animal = animalService.getAnimalById(id);
            model.addAttribute("animal", animal);
            return "formAnimal";
        }
        return "redirect:/login";
    }

    @GetMapping("/delete/{id}")
    public String deleteAnimal(@PathVariable("id") Integer id, HttpServletRequest request) {
        if (accessFilter.checkCookiesForToken(request)) {
            animalService.deleteAnimal(id);
            return "redirect:/control";
        }
        return "redirect:/login";
    }

    @RequestMapping(path = "/saveAnimal", method = RequestMethod.POST)
    public String saveAnimal(@RequestParam(value = "id", required = false) Optional<Integer> id,
            @RequestParam(value = "type", required = true) String type,
            @RequestParam(value = "race", required = true) String race,
            @RequestParam(value = "color", required = true) String color,
            @RequestParam(value = "fur", required = true) String fur,
            @RequestParam(value = "date", required = true) String date,
            @RequestParam(value = "vaccinated", required = false, defaultValue = "false") boolean vaccinated,
            @RequestParam(value = "adopted", required = false, defaultValue = "false") boolean adopted,
            @RequestParam(value = "responsable", required = false) String responsable,
            @RequestParam(value = "img", required = false) MultipartFile img, HttpServletRequest request) {

        if (accessFilter.checkCookiesForToken(request)) {
            Animal entity;
            if (id.isPresent()) {
                entity = animalService.getAnimalById(id.get());
            } else {
                entity = new Animal();
            }
            entity.setType(type);
            entity.setRace(race);
            entity.setColor(color);
            entity.setFur(fur);
            entity.setDate(date);
            entity.setVaccinated(vaccinated);
            entity.setAdopted(adopted);
            entity.setResponsable(responsable);
            try {
                entity.setImg(img.getBytes());
            } catch (Exception e) {
                System.out.println("SAVE ANIMAL ERROR: >>> " + e);
            }
            animalService.saveAnimal(entity);
            return "redirect:/control";
        }
        return "redirect:/login";
    }

}