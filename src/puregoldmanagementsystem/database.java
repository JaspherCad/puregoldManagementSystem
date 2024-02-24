/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puregoldmanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Jaspher
 */
public class database {
    public static Connection connectDb(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventorypg", "root", "");
            System.out.println(connect);
            System.out.println("etteeteateat");

            return connect;
        }catch(Exception e){e.printStackTrace();}
        return null;
    }
}
