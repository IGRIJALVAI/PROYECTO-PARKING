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
              if (!DatosCentrales.HISTORICO.isEmpty()) {
           
            java.util.List<Historico> listos = new java.util.ArrayList<>();
            for (Historico h : DatosCentrales.HISTORICO) {
                if (h.FechaSalida != null) listos.add(h);
            }
            if (!listos.isEmpty()) {
                Importar_BD.subirHistorico(listos);
            }
        }
            JOptionPane.showMessageDialog(null, "Datos subidos a la base de datos ");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al subir" + e.getMessage());
        }
    }
    
    
    
    
    
    
    
}
