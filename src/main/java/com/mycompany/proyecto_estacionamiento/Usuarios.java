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
    String profesion;
    String modelo ;
    String tipodeVehiculo;
    String color;
    String placa ;
    
     private static final List<Usuarios> listaglobal = new ArrayList<>();

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getTipodeVehiculo() {
        return tipodeVehiculo;
    }

    public void setTipodeVehiculo(String tipodeVehiculo) {
        this.tipodeVehiculo = tipodeVehiculo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
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
