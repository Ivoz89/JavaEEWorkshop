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
import com.mycompany.model.Transaction;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Date;
import javax.inject.Inject;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import org.hamcrest.Description;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.everyItem;
import org.hamcrest.TypeSafeMatcher;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertThat;
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
        assertThat(account, is(not(nullValue())));
        assertThat(account.getTransactions(), is(empty()));
        assertThat(account.getUserData(), is(not(nullValue())));
        assertThat(account.getUserData(), is(not(nullValue())));
    }

    private final static int TRANSACTION_COUNT = 5;

    @Test
    public void testTransactions() {
        dao.createUserAccount("test2", "test2");
        for (int i = 0; i < TRANSACTION_COUNT; i++) {
            dao.makeTransaction("test2", new Transaction(new Date(), BigDecimal.ZERO, 0));
        }
        Account account = dao.getAccountByLogin("test2");
        assertThat(account.getTransactions(), everyItem(new TypeSafeMatcher<Transaction>() {

            @Override
            public void describeTo(Description description) {
                System.err.println(description);
            }

            @Override
            protected boolean matchesSafely(Transaction t) {
                return t.getAmount().equals(BigDecimal.ZERO);
            }

        }));
        assertThat(account.getTransactions().size(), is(TRANSACTION_COUNT));
    }
}
