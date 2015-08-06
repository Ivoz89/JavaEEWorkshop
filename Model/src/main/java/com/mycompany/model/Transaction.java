/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author izielinski
 */
public class Transaction {
    
    private final int id;
    private final Date date;
    private final BigDecimal amount;

    public Transaction(Date date, BigDecimal amount, int id) {
        this.date = date;
        this.id = id;
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" ID = ").append(id);
        stringBuilder.append(" DATE = ").append(date);
        stringBuilder.append(" AMOUNT = ").append(amount.intValue());
        return stringBuilder.toString();
    }
}
