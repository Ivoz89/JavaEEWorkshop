/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dataaccess.transactions;

import com.mycompany.dataaccess.connection.ConnectionFactory;
import com.mycompany.dataaccess.logging.Log;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import org.apache.log4j.Logger;

/**
 *
 * @author izielinski
 */
@Dependent
public class TransactionWrapper {

    private static final Logger LOG = Logger.getLogger(TransactionWrapper.class.getName());

    ConnectionFactory connectionFactory;

    @Inject
    public TransactionWrapper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public TransactionWrapper() {
    }

    @Log
    public void wrapQuery(ResultExtractor transaction, String statement, Map<Integer, Pair<ParameterType, Object>> injectors) {
        try (Connection connection = connectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(statement);) {
            for (Integer index : injectors.keySet()) {
                Pair<ParameterType, Object> injection = injectors.get(index);
                injection.getKey().injectParameter(index, injection.getValue(), preparedStatement);
            }
            try (ResultSet rs = preparedStatement.executeQuery()) {
                transaction.extractResult(rs);
            }
        } catch (Exception ex) {
            LOG.error(ex);
        }
    }

    @Log
    public void wrapUpdate(String statement, Map<Integer, Pair<ParameterType, Object>> injectors) {
        Connection connectionForRollback = null;
        try (Connection connection = connectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(statement);) {
            connectionForRollback = connection;
            connection.setAutoCommit(false);
            executeUpdate(connection, preparedStatement, injectors);
            connection.commit();
        } catch (Exception ex) {
            try {
                connectionForRollback.rollback();
            } catch (SQLException ex1) {
                LOG.info(ex1);
            }
            LOG.error(ex);
        }
    }

    @Log
    public int wrapInsert(String statement, Map<Integer, Pair<ParameterType, Object>> injectors) {
        Connection connectionForRollback = null;
        try (Connection connection = connectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);) {
            connectionForRollback = connection;
            connection.setAutoCommit(false);
            executeUpdate(connection, preparedStatement, injectors);
            connection.commit();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            rs.next();
            int generatedKey =  rs.getInt(1);
            return generatedKey;
        } catch (Exception ex) {
            try {
                connectionForRollback.rollback();
            } catch (SQLException ex1) {
                LOG.info(ex1);
            }
            LOG.error(ex);
        }
        return 0;
    }

    @Log
    public void wrapUpdates(List<String> statements, List<Map<Integer, Pair<ParameterType, Object>>> injectorMaps) {
        Connection connectionForRollback = null;
        try (Connection connection = connectionFactory.getConnection()) {
            connectionForRollback = connection;
            connection.setAutoCommit(false);
            for (int i = 0; i < statements.size(); i++) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(statements.get(i))) {
                    executeUpdate(connection, preparedStatement, injectorMaps.get(i));
                }
            }
            connection.commit();
        } catch (Exception ex) {
            try {
                connectionForRollback.rollback();
            } catch (SQLException ex1) {
                LOG.info(ex1);
            }
            LOG.error(ex);
        }
    }

    @Log
    private void executeUpdate(Connection connection, PreparedStatement preparedStatement,
            Map<Integer, Pair<ParameterType, Object>> injectors) throws SQLException {
        for (Integer index : injectors.keySet()) {
            Pair<ParameterType, Object> injection = injectors.get(index);
            injection.getKey().injectParameter(index, injection.getValue(), preparedStatement);
        }
        preparedStatement.executeUpdate();
    }

    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }
}
