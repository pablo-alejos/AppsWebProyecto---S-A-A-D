package com.ran.apps.saad.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.ran.apps.saad.filters.JwtAccessFilter;
import com.ran.apps.saad.model.AuthenticationRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class AccessController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtAccessFilter accessFilter;

    @GetMapping("/login")
    public String getLogin() {
    return "login";
    }

    @RequestMapping(value = "/login_jwt", method = RequestMethod.POST)
    public String postLogin(@RequestParam(value = "userName", required = true) String userName,
            @RequestParam(value = "password", required = true) String password, HttpServletResponse response)
            throws Exception {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest(userName, password);
        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Usuario o contraseña incorrectos.", e);
        }
        Cookie cookie = accessFilter.enableTokenCookie(authenticationRequest);
        response.addCookie(cookie);
        return "redirect:/";
    }
    

    @GetMapping("/signout")
    public String logout(HttpServletResponse response) {
        response.addCookie(accessFilter.disableTokenCookie());
        return "redirect:/login";
    }

    @GetMapping("/auth")
    public String auth() {
        return "auth";
    }

}