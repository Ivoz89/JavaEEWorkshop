/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dataaccess;

import com.mycompany.model.Account;
import com.mycompany.model.AccountData;
import com.mycompany.model.UserData;
import com.mycompany.model.Transaction;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.Dependent;

//@Dependent
public class DaoImpl implements Dao {

    private static Map<String, Account> accounts;
    private ConnectionFactory connectionFactory;

    static {
        accounts = new HashMap<String, Account>();
        UserData userData = new UserData("Jan", "Kowalski",1);
        AccountData accountData = new AccountData(5348957,1);
        Account account = new Account(userData, accountData,1);
        accounts.put(userData.getName() + userData.getSurname(), account);
        account.addTransaction(new Transaction(new Date(), new BigDecimal(590),0));
        account.addTransaction(new Transaction(new Date(), new BigDecimal(590),1));
        account.addTransaction(new Transaction(new Date(), new BigDecimal(123),2));
        account.addTransaction(new Transaction(new Date(), new BigDecimal(456),3));
        account.addTransaction(new Transaction(new Date(), new BigDecimal(555),4));
    }

    @Override
    public Account getAccountByLogin(String str) {

        return accounts.get(str);
    }

    @Override
    public void makeTransaction(String login, Transaction transaction) {
    }
    
    @Override
    public List<UserData> getAllUserData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<UserData> getAllAccountData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<UserData> getAllAccounts() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<UserData> getAllTransactions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
