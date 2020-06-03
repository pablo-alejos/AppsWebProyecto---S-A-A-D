package com.ran.apps.saad.controller;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ran.apps.saad.filters.JwtAccessFilter;
import com.ran.apps.saad.model.Animal;
import com.ran.apps.saad.model.Store;
import com.ran.apps.saad.model.User;
import com.ran.apps.saad.service.AnimalService;
import com.ran.apps.saad.service.MyUserDetailsService;
import com.ran.apps.saad.service.StoreService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/control")
public class AdminController {

    @Autowired
    private AnimalService animalService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private MyUserDetailsService userService;

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

            List<Store> stores = storeService.getStores();

            List<User> users = userService.getUsers();

            animals.forEach(animal -> {
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
            });
            model.addAttribute("cats", cats);
            model.addAttribute("dogs", dogs);
            model.addAttribute("bunnys", bunnys);
            model.addAttribute("rats", rats);
            model.addAttribute("stores", stores);
            model.addAttribute("users", users);
            return "control";
        }
        return "redirect:/login";
    }

}