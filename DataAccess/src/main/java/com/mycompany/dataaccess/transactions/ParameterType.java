/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dataaccess.transactions;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author izielinski
 */
public enum ParameterType {

    INTEGER {
                @Override
                public void injectParameter(int index, Object o, PreparedStatement preparedStatement) throws SQLException {
                    preparedStatement.setInt(index, (Integer) o);
                }
            }, STRING {
                @Override
                public void injectParameter(int index, Object o, PreparedStatement preparedStatement) throws SQLException {
                    preparedStatement.setString(index, (String) o);
                }
            }, BIG_DECIMAL {
                @Override
                public void injectParameter(int index, Object o, PreparedStatement preparedStatement) throws SQLException {
                    preparedStatement.setBigDecimal(index, (BigDecimal) o);
                }
            }, DATE {
                @Override
                public void injectParameter(int index, Object o, PreparedStatement preparedStatement) throws SQLException {
                    preparedStatement.setDate(index, (Date) o);
                }
            }, LONG {
                @Override
                public void injectParameter(int index, Object o, PreparedStatement preparedStatement) throws SQLException {
                    preparedStatement.setLong(index, (Long) o);
                }
            };

    public abstract void injectParameter(int index, Object o, PreparedStatement preparedStatement) throws SQLException;

}
