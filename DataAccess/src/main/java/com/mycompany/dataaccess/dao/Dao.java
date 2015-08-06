/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dataaccess.dao;

import com.mycompany.dataaccess.logging.Log;
import com.mycompany.model.Account;
import com.mycompany.model.Transaction;

/**
 *
 * @author izielinski
 */
@Log
public interface Dao {
    
    Account getAccountByLogin(String str);
    void makeTransaction(String login, Transaction transaction);
    void createUserAccount(String name, String surname);
}
