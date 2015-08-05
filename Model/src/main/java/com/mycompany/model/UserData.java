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
    
    private final int id;
    private final String name;
    private final String surname;

    public UserData(String name, String surname, int id) {
        this.name = name;
        this.id = id;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
