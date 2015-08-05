/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dataaccess;

import com.mycompany.model.Account;
import com.mycompany.model.Transaction;
import com.mycompany.model.UserData;
import java.util.List;

/**
 *
 * @author izielinski
 */
public interface Dao {
    
    Account getAccountByLogin(String str);
    void makeTransaction(String login, Transaction transaction);
    List<UserData> getAllUserData();
    List<UserData> getAllAccountData();
    List<UserData> getAllAccounts();
    List<UserData> getAllTransactions();
}
