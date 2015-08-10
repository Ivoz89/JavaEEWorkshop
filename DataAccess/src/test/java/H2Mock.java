
import com.mycompany.dataaccess.connection.ConnectionProvider;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;
import javax.naming.NamingException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author izielinski
 */
@Dependent
public class H2Mock implements ConnectionProvider {

    static {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(H2Mock.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Connection connection = DriverManager.getConnection("jdbc:h2:mem:test;MODE=MYSQL;INIT=RUNSCRIPT FROM 'classpath:test.ddl';DB_CLOSE_DELAY=100");
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(H2Mock.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public Connection getConnection() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, NamingException {
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection("jdbc:h2:mem:test;MODE=MYSQL;DB_CLOSE_DELAY=100");
    }
}
