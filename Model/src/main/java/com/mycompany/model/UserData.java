/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.model;

/**
 *
 * @author izielinski
 */
public class UserData {
    
    private int id;
    private final String name;
    private final String surname;
    private final long version = 0L;

    public UserData(String name, String surname, int id) {
        this.name = name;
        this.id = id;
        this.surname = surname;
    }

    public UserData(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
