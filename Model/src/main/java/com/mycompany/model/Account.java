/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author izielinski
 */
public class Account {

    private final int id;
    private final UserData userData;
    private final AccountData accountData;
    private List<Transaction> transactions;

    public Account(UserData userData, AccountData accountData, int id) {
        this.userData = userData;
        this.accountData = accountData;
        transactions = new ArrayList<>();
        this.id = id;
    }

    public UserData getUserData() {
        return userData;
    }

    public AccountData getAccountData() {
        return accountData;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
    
    public void attachTransaction(Transaction t) {
        transactions.add(t);
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public int getId() {
        return id;
    }
    
    
}
