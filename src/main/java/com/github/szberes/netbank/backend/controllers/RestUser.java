package com.github.szberes.netbank.backend.controllers;

public class RestUser {
    private String id;

    public RestUser(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
