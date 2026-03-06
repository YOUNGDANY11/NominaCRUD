/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.nominaa.conexion;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Dany
 */
public class ConexionDB {
    public static Connection conectar(){
        Connection con = null;
        
        try{
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/nomina",
                    "nomina",
                    "1234"
            );
        }catch(Exception e){
            System.out.println(e);
        }
        
        return con;
    }
}
