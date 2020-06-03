package com.ran.apps.saad.model;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -6167137524602747137L;
    private final String jwt;

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}