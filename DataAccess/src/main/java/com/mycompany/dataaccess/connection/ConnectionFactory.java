/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dataaccess.connection;

import java.sql.Connection;
import java.sql.SQLException;
import javax.enterprise.context.Dependent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author izielinski
 */
@Dependent
public class ConnectionFactory implements ConnectionProvider {
//
//    @Resource(name = "jdbc/test")
//    DataSource dataSource;

    public Connection getConnection() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, NamingException {
        Context ctx = new InitialContext();
        return ((DataSource) ctx.lookup("java:jboss/datasources/test")).getConnection();
    }
}
