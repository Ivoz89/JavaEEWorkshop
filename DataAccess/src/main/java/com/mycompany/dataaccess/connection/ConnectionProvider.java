/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dataaccess.connection;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author izielinski
 */
public interface ConnectionProvider {
    
    public Connection getConnection() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, NamingException;
}
