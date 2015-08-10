/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dataaccess.dao;

import com.mycompany.dataaccess.logging.Log;
import com.mycompany.dataaccess.connection.ConnectionFactory;
import com.mycompany.dataaccess.connection.ConnectionProvider;
import com.mycompany.dataaccess.transactions.InjectorsBuilder;
import com.mycompany.dataaccess.transactions.Pair;
import com.mycompany.dataaccess.transactions.ParameterType;
import com.mycompany.dataaccess.transactions.ResultExtractor;
import com.mycompany.dataaccess.transactions.TransactionWrapper;
import com.mycompany.model.Account;
import com.mycompany.model.AccountData;
import com.mycompany.model.Transaction;
import com.mycompany.model.UserData;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import org.apache.log4j.Logger;

@Dependent
public class DaoJDBC implements Dao {

    private static final Logger LOG = Logger.getLogger(DaoJDBC.class.getName());

    private TransactionWrapper transactionWrapper;

    @Inject
    public DaoJDBC(TransactionWrapper transactionWrapper) {
        this.transactionWrapper = transactionWrapper;
    }

    public DaoJDBC() {
    }

    @Log
    @Override
    public Account getAccountByLogin(String str) {
        String statement = "SELECT a.ID AS ACCID, u.ID AS UDID, u.NAME, u.SURNAME, d.ID as ACCOUNT_NO, d.VERSION,"
                + "d.BALANCE FROM ACCOUNT a,USER_DATA u,ACCOUNT_DATA d"
                + " where u.NAME = ? and d.ID = a.ACCOUNT_DATA and u.ID=a.USER_DATA";
        InjectorsBuilder ib = new InjectorsBuilder();
        ib.append(1, ParameterType.STRING, str);
        ResultExtractor<Account> query = new ResultExtractor<Account>() {

            private Account account;

            @Override
            public void extractResult(ResultSet rs) throws SQLException {
                if (rs.next()) {
                    String name = rs.getString("NAME");
                    String surname = rs.getString("SURNAME");
                    int userDataId = rs.getInt("ACCID");
                    UserData userData = new UserData(name, surname, userDataId);
                    int accountNo = rs.getInt("ACCOUNT_NO");
                    BigDecimal balance = rs.getBigDecimal("BALANCE");
                    int version = rs.getInt("VERSION");
                    AccountData accountData = new AccountData(accountNo, balance, accountNo, version);
                    account = new Account(userData, accountData, rs.getInt("ACCID"));
                }
            }

            @Override
            public Account getResult() {
                return account;
            }
        };
        transactionWrapper.wrapQuery(query, statement, ib.toMap());
        Account account = query.getResult();
        if(account == null) {
            return null;
        }
        injectTransactions(account);
        return account;
    }

    private void injectTransactions(Account account) {
        String statement = "SELECT * FROM TRANSACTION t WHERE t.ACCOUNT = ?";
        InjectorsBuilder ib = new InjectorsBuilder();
        ib.append(1, ParameterType.INTEGER, account.getId());
        ResultExtractor<List<Transaction>> query = new ResultExtractor<List<Transaction>>() {

            List<Transaction> transactions = new ArrayList<>();

            @Override
            public void extractResult(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    Date date = rs.getDate("DATE");
                    int amount = rs.getInt("AMOUNT");
                    transactions.add(new Transaction(date, BigDecimal.valueOf(amount), 0));
                }
            }

            @Override
            public List<Transaction> getResult() {
                return transactions;
            }
        };
        transactionWrapper.wrapQuery(query, statement, ib.toMap());
        account.setTransactions(query.getResult());
    }

    @Log
    @Override
    public void makeTransaction(String login, Transaction transaction) {
        Account account = getAccountByLogin(login);
        List<String> statements = new ArrayList<>();
        List<Map<Integer, Pair<ParameterType, Object>>> injectorMaps = new ArrayList<>();
        injectInsertTransaction(transaction, account, statements, injectorMaps);
        injectUpdateBalance(account, transaction.getAmount().intValue(), statements, injectorMaps);
        transactionWrapper.wrapUpdates(statements, injectorMaps);
    }

    private void injectInsertTransaction(Transaction trans, Account account, List<String> statements,
            List<Map<Integer, Pair<ParameterType, Object>>> injectorMaps) {
        String statement = "insert into transaction(account,date,amount) values (?,?,?)";
        InjectorsBuilder ib = new InjectorsBuilder();
        ib.append(1, ParameterType.INTEGER, account.getId());
        ib.append(2, ParameterType.DATE, new java.sql.Date(trans.getDate().getTime()));
        ib.append(3, ParameterType.INTEGER, trans.getAmount().intValue());
        statements.add(statement);
        injectorMaps.add(ib.toMap());
    }

    private void injectUpdateBalance(Account account, int delta, List<String> statements,
            List<Map<Integer, Pair<ParameterType, Object>>> injectorMaps) {
        String statement = "update account_data d, account a SET d.BALANCE=d.BALANCE+?, d.VERSION=d.VERSION+1"
                + " WHERE d.ID=a.ACCOUNT_DATA and a.ID=? and d.VERSION=?";
        InjectorsBuilder ib = new InjectorsBuilder();
        ib.append(1, ParameterType.INTEGER, delta);
        ib.append(2, ParameterType.INTEGER, account.getId());
        ib.append(3, ParameterType.LONG, account.getAccountData().getVersion());
        statements.add(statement);
        injectorMaps.add(ib.toMap());
    }

    @Override
    public void createUserAccount(String name, String surname) {
        UserData userData = new UserData(name, surname);
        AccountData accountData = new AccountData(123456, BigDecimal.valueOf(10000), 0, 0);
        int userDataId = insertUserData(userData);
        int accountDataId = insertAccountData(accountData);
        String statement = "insert into ACCOUNT(USER_DATA,ACCOUNT_DATA) values (?,?)";
        InjectorsBuilder ib = new InjectorsBuilder();
        ib.append(1, ParameterType.INTEGER, userDataId);
        ib.append(2, ParameterType.INTEGER, accountDataId);
        transactionWrapper.wrapInsert(statement, ib.toMap());
    }
    
    private int insertUserData(UserData userData) {
        String statement = "insert into USER_DATA(NAME,SURNAME) values (?,?)";
        InjectorsBuilder ib = new InjectorsBuilder();
        ib.append(1, ParameterType.STRING, userData.getName());
        ib.append(2, ParameterType.STRING, userData.getSurname());
        return transactionWrapper.wrapInsert(statement, ib.toMap());
    }
    private int insertAccountData(AccountData accountData) {
        String statement = "insert into ACCOUNT_DATA(BALANCE,VERSION) values (?,?)";
        InjectorsBuilder ib = new InjectorsBuilder();
        ib.append(1, ParameterType.BIG_DECIMAL, accountData.getBalance());
        ib.append(2, ParameterType.LONG, 0L);
        return transactionWrapper.wrapInsert(statement, ib.toMap());
    }
    
    public TransactionWrapper getTransactionWrapper() {
        return transactionWrapper;
    }

    public void setTransactionWrapper(TransactionWrapper transactionWrapper) {
        this.transactionWrapper = transactionWrapper;
    }
}
