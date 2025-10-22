/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_estacionamiento;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author grija
 */
public class Usuarios {
    String nombre;
    String Carne;
    String Placa ;
    String Carrera;
    
     private static final List<Usuarios> listaglobal = new ArrayList<>();

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCarne() {
        return Carne;
    }

    public void setCarne(String Carne) {
        this.Carne = Carne;
    }

    public String getPlaca() {
        return Placa;
    }

    public void setPlaca(String Placa) {
        this.Placa = Placa;
    }

    public String getCarrera() {
        return Carrera;
    }

    public void setCarrera(String Carrera) {
        this.Carrera = Carrera;
    }

    
    
    
    
      // de el array globall
    public static void agregarUsuario(Usuarios u) {
        
        listaglobal.add(u);
    }

    public static List<Usuarios> getListaUsuarios() {
        return listaglobal;
    }

    public static void limpiarLista() {
        listaglobal.clear();
    }

    // bsucar por nombre
    public static Usuarios buscarPorNombre(String nombre) {
        for (Usuarios u : listaglobal) {
            if (u.getNombre().equalsIgnoreCase(nombre)) {
                return u;
            }
        }
        return null;
    }
    
}
