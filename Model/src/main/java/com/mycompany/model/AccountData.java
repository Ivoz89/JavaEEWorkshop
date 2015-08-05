/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.model;

import java.math.BigDecimal;

/**
 *
 * @author izielinski
 */
public class AccountData {

    private final int id;
    private final long accountNumber;
    private BigDecimal balance;

    public AccountData(long accountNumber, int id) {
        this.accountNumber = accountNumber;
        balance = BigDecimal.ZERO;
        this.id = id;
    }
    public AccountData(long accountNumber,BigDecimal balance, int id) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.id = id;
    }
    public long getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }
    
    public void modifyBalance(int delta) {
        balance = balance.add(BigDecimal.valueOf(delta));
    }
}
