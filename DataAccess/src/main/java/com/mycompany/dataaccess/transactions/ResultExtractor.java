/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dataaccess.transactions;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author izielinski
 */
public interface ResultExtractor<E> {
    void extractResult(ResultSet rs) throws SQLException;
    E getResult();
}
