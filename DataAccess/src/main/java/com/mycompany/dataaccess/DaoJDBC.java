/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dataaccess;

import com.mycompany.model.Account;
import com.mycompany.model.AccountData;
import com.mycompany.model.Transaction;
import com.mycompany.model.UserData;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DaoJDBC implements Dao {


    private final ConnectionFactory connectionFactory;

    @Inject
    public DaoJDBC(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public Account getAccountByLogin(String str) {
        ResultSet rs = null;
        try (Connection connection = connectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT a.ID,u.NAME, u.SURNAME, d.ID as ACCOUNT_NO, d.BALANCE FROM ACCOUNT a,USER_DATA u,ACCOUNT_DATA d "
                        + "where u.NAME = ? and d.ID = a.ACCOUNT_DATA");) {
            preparedStatement.setString(1, str);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                String name = rs.getString("NAME");
                String surname = rs.getString("SURNAME");
                UserData userData = new UserData(name, surname,1);
                int accountNo = rs.getInt("ACCOUNT_NO");
                BigDecimal balance = rs.getBigDecimal("BALANCE");
                AccountData accountData = new AccountData(accountNo, balance,1);
                Account account = new Account(userData, accountData,1);
                int accountID = rs.getInt("ID");
                selectTransactions(account, connection, accountID);
                return account;
            } else {
                throw new Exception("NO USER!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    private void selectTransactions(Account account, Connection connection, int accountId) throws SQLException {
        ResultSet rs = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM TRANSACTION t WHERE t.ACCOUNT = ?");) {
            preparedStatement.setInt(1, accountId);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Date date = rs.getDate("DATE");
                int amount = rs.getInt("AMOUNT");
                account.attachTransaction(new Transaction(date, BigDecimal.valueOf(amount),0));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            rs.close();
        }
    }

    @Override
    public void makeTransaction(String login, Transaction transaction) {
        try (Connection connection = connectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT a.ID from ACCOUNT a, USER_DATA ad "
                        + "where ad.NAME = ? and ad.ID = a.ACCOUNT_DATA");) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int accountId = resultSet.getInt("ID");
                PreparedStatement preparedStatement2 = connection.prepareStatement("insert into transaction(account,date,amount) values (?,?,?)");
                preparedStatement2.setInt(1, accountId);
                preparedStatement2.setDate(2, new java.sql.Date(transaction.getDate().getTime()));
                preparedStatement2.setInt(3, transaction.getAmount().intValue());
                preparedStatement2.executeUpdate();
                PreparedStatement preparedStatement3 = connection.prepareStatement("update account_data SET BALANCE=BALANCE+? WHERE ID=?");
                preparedStatement3.setInt(1, transaction.getAmount().intValue());
                preparedStatement3.setInt(2, 1);
                preparedStatement3.executeUpdate();
                preparedStatement2.close();
                preparedStatement3.close();
            } else {
                throw new Exception("TRANSACTION HAS INVALID USER!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
