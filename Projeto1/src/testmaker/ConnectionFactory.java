/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testmaker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author schaiana
 */
public class ConnectionFactory {
    public ConnectionFactory(){}
    static Connection connection = null;
    static Connection connect(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/MyDataBase","schaiana","senha123"); 
            
        } catch (SQLException exception1) {
             Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, exception1);
         } catch (ClassNotFoundException exception2) {
                Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, exception2);
           }
        
        return connection;
    }
    
}
