
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dataaccess.logging;

import com.mycompany.dataaccess.dao.Dao;
import com.mycompany.model.Account;
import com.mycompany.model.Transaction;
import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import org.apache.log4j.Logger;

/**
 *
 * @author izielinski
 */
@Decorator
public abstract class LoggingDecorator implements Dao {

    private static final Logger LOG = Logger.getLogger(LoggingDecorator.class.getName());

    @Inject
    @Delegate
    @Any
    Dao dao;

    @Override
    public Account getAccountByLogin(String str) {
        LOG.info("OBTAINING ACCOUNT FOR LOGIN " + str);
        return dao.getAccountByLogin(str);
    }

    @Override
    public void makeTransaction(String login, Transaction transaction) {
        LOG.info("MAKING TRANSACTION " + transaction + " FOR LOGIN " + login);
        dao.makeTransaction(login, transaction);
    }
}
