
package com.mycompany.proyecto_estacionamiento;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author grija
 */
public class Conexion_BD {
    
       
          public static Connection conectar() {
        try {
            Connection cn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/parqueo_umg?useSSL=false&serverTimezone=America/Guatemala",
                "root",
                "PEPITO21" 
            );
            System.out.println("ConexiOn exitosa");
            return cn;

        } catch (Exception e) {
            System.out.println("Error en conexion" + e.getMessage());
            return null;
        }
    }
    
    
    
}
