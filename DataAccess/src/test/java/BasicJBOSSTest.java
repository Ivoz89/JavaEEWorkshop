
import com.mycompany.dataaccess.connection.ConnectionProvider;
import com.mycompany.dataaccess.dao.Dao;
import com.mycompany.dataaccess.dao.DaoJDBC;
import com.mycompany.dataaccess.logging.LoggingDecorator;
import com.mycompany.dataaccess.logging.LoggingInterceptor;
import com.mycompany.dataaccess.transactions.TransactionWrapper;
import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.rules.TestName;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author izielinski
 */
public class BasicJBOSSTest {

    @Inject
    Dao dao;

    @Inject
    ConnectionProvider connectionProvider;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Rule
    public TestName name = new TestName();

    @Rule
    public TestRule watchman = new TestWatcher() {
        @Override
        public Statement apply(Statement base, Description description) {
            return super.apply(base, description);
        }

        @Override
        protected void succeeded(Description description) {
            System.out.println(description.getDisplayName() + " " + "success!\n");
        }

        @Override
        protected void failed(Throwable e, Description description) {
            System.out.println(description.getDisplayName() + " " + e.getClass().getSimpleName() + "\n");
        }

        @Override
        protected void starting(Description description) {
            super.starting(description);
        }

        @Override
        protected void finished(Description description) {
            super.finished(description);
        }
    };

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(DaoJDBC.class)
                .addClass(H2Mock.class)
                .addClass(TransactionWrapper.class)
                .addClass(LoggingInterceptor.class)
                .addClass(LoggingDecorator.class);
    }
}
