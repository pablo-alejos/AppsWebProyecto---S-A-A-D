package com.ran.apps.saad.filters;

import java.util.Arrays;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.ran.apps.saad.model.AuthenticationRequest;
import com.ran.apps.saad.service.MyUserDetailsService;
import com.ran.apps.saad.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtAccessFilter {

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    /**
     * revisa si el token guardado en el cookie "jwt" es valido
     * 
     * @param request la peticcion http donde buscar el cookie
     * @return true si el token es valido
     */
    public boolean checkCookiesForToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Cookie jwtCookie = new Cookie("jwt", null);
        try {
            for (Cookie cookie : Arrays.asList(cookies)) {
                if (cookie.getName().equals("jwt")) {
                    jwtCookie = cookie;
                    System.out.println("Cookie encontrado!");
                    System.out.println(jwtCookie.getName() + " : " + jwtCookie.getValue());
                    System.out.println("----");
                }
            }
        } catch (java.lang.NullPointerException e) {
            System.out.println("Error leyendo los cookies de esta peticion.");
        }
        if (jwtCookie.getValue() != null) {
            String username = jwtTokenUtil.extractUsername(jwtCookie.getValue());
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtTokenUtil.validateToken(jwtCookie.getValue(), userDetails)) {
                System.out.println("Token valido. Accesso otorgado.");
                System.out.println("----");
                return true;
            } else {
                System.out.println("Token no valido, accesso denegado.");
                System.out.println("----");
                return false;
            }
        } else {
            System.out.println("No se encontro el cookie jwt, accesso denegado.");
            System.out.println("----");
            return false;
        }
    }

    /**
     * 
     * @param authenticationRequest username & passowrd
     * @return un cookie con el token JWT con username & password
     */
    public Cookie enableTokenCookie(AuthenticationRequest authenticationRequest) {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        Cookie cookie = new Cookie("jwt", jwt);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 60);
        System.out.println("Token guardado en el cookie: " + cookie.getName() + " : " + cookie.getValue());
        System.out.println("----");
        return cookie;
    }

    /**
     * 
     * @return un cookie vacio que reemplaza al cookie que contenia el token JWT
     */
    public Cookie disableTokenCookie() {
        Cookie cookie = new Cookie("jwt", "");
        cookie.setMaxAge(0);
        System.out.println("Sesion terminada.");
        System.out.println("----");
        return cookie;
    }

    /**
     * Imprime en consola todos los cookies (nombre,valor,expiracion)
     * 
     * @param request http request con cookies
     */
    public void checkRequestCookies(HttpServletRequest request) {
        try {
            Cookie[] cookies = request.getCookies();
            System.out.println("Todas las cookies de la peticion: ");
            for (Cookie cookie : Arrays.asList(cookies)) {
                System.out.println("Nombre: [" + cookie.getName() + "] Valor: [" + cookie.getValue() + "] Expiracion: ["
                        + cookie.getMaxAge() + "]");
            }
        } catch (java.lang.NullPointerException e) {
            System.out.println("Error leyendo las cookies de esta peticion.");
        }
        System.out.println("----");
    }
}