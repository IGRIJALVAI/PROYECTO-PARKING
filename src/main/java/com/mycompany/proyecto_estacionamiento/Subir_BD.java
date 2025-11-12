/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_estacionamiento;

import javax.swing.JOptionPane;

/**
 *
 * @author grija
 */
public class Subir_BD {
    
    
    /** Llama esto desde tu botón “Subir a BD” */
       public static void subirTodoDesdeDatosCentrales() {
        try {
            if (!DatosCentrales.USUARIOS.isEmpty()) {
                Importar_BD.insertarUsuariosBatch(DatosCentrales.USUARIOS);
            }
            if (!DatosCentrales.AREAS.isEmpty()) {
                Importar_BD.insertarAreasBatch(DatosCentrales.AREAS);
            }
            if (!DatosCentrales.SPOTS.isEmpty()) {
                Importar_BD.insertarSpotsBatch(DatosCentrales.SPOTS);
            }
            if (!DatosCentrales.VEHICULOS.isEmpty()) {
                Importar_BD.insertarVehiculosBatch(DatosCentrales.VEHICULOS);
            }
            JOptionPane.showMessageDialog(null, "Datos subidos a la base de datos ");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al subir" + e.getMessage());
        }
    }
    
    
    
    
    
    
    
}
