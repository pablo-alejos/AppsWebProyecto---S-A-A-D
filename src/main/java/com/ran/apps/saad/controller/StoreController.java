package com.ran.apps.saad.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.ran.apps.saad.filters.JwtAccessFilter;
import com.ran.apps.saad.model.Store;
import com.ran.apps.saad.service.StoreService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @Autowired
    private JwtAccessFilter accessFilter;

    @RequestMapping("/stores")
    public String getStore(Model model, HttpServletRequest request) {
        if (accessFilter.checkCookiesForToken(request)) {
            List<Store> stores = storeService.getStores();
            model.addAttribute("stores", stores);
            return "stores";
        }
        return "redirect:/login";
    }

    @RequestMapping("/new")
    public String getAdmin(Model model, HttpServletRequest request) {
        if (accessFilter.checkCookiesForToken(request)) {
            Store store = new Store();
            model.addAttribute("store", store);
            return "storeForm";
        }
        return "redirect:/login";
    }

    @RequestMapping(path = { "/update/{id}" })
    public String editStoreById(Model model, @PathVariable(value = "id", required = true) Integer id,
            HttpServletRequest request) {
        if (accessFilter.checkCookiesForToken(request)) {
            Store store = storeService.getStoreById(id);
            model.addAttribute("store", store);
            return "storeForm";
        }
        return "redirect:/login";
    }

    @GetMapping("/delete/{id}")
    public String deleteStore(@PathVariable("id") Integer id, HttpServletRequest request) {
        if (accessFilter.checkCookiesForToken(request)) {
            storeService.deleteStore(id);
            return "redirect:/control";
        }
        return "redirect:/login";
    }

    @RequestMapping(path = "/saveStore", method = RequestMethod.POST)
    public String saveStore(@RequestParam(value = "id", required = false) Optional<Integer> id,
            @RequestParam(value = "nombre", required = true) String nombre,
            @RequestParam(value = "direccion", required = true) String direccion, HttpServletRequest request) {
        if (accessFilter.checkCookiesForToken(request)) {
            Store entity;
            if (id.isPresent()) {
                entity = storeService.getStoreById(id.get());
            } else {
                entity = new Store();
            }
            entity.setNombre(nombre);
            entity.setDireccion(direccion);
            storeService.saveStore(entity);
            return "redirect:/control";
        }
        return "redirect:/login";
    }

}
