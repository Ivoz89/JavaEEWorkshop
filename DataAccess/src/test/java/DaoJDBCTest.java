/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.mycompany.dataaccess.connection.ConnectionProvider;
import com.mycompany.dataaccess.dao.Dao;
import com.mycompany.dataaccess.dao.DaoJDBC;
import com.mycompany.dataaccess.logging.LoggingDecorator;
import com.mycompany.dataaccess.logging.LoggingInterceptor;
import com.mycompany.dataaccess.transactions.TransactionWrapper;
import com.mycompany.model.Account;
import java.sql.Connection;
import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author izielinski
 */
@RunWith(Arquillian.class)
public class DaoJDBCTest {

    @Inject
    Dao dao;

    @Inject
    ConnectionProvider connectionProvider;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(DaoJDBC.class)
                .addClass(H2Mock.class)
                .addClass(TransactionWrapper.class)
                .addClass(LoggingInterceptor.class)
                .addClass(LoggingDecorator.class);
    }

    @Test
    public void testH2Connection() {
        Connection conn = null;
        try {
            conn = connectionProvider.getConnection();
        } catch (Exception ex) {
            System.out.println(ex);
            fail();
        }
    }

    @Test
    public void testCreatingUser() {
        assertNotNull(dao);
        dao.createUserAccount("test", "test");
        Account account = dao.getAccountByLogin("test");
        assertNotNull(account);
    }
}
