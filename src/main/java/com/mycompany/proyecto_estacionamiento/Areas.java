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
public class Areas {
    
    String IdArea ;
    String nombreA ;
    String capacidad ;
    String TipoVehiculo ;
    
    private static final List<Areas> listagloba2 = new ArrayList<>();

    public String getId() {
        return IdArea;
    }

    public void setId(String Id) {
        this.IdArea = Id;
    }

    public String getNombreA() {
        return nombreA;
    }

    public void setNombreA(String nombreA) {
        this.nombreA = nombreA;
    }

    public String getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(String capacidad) {
        this.capacidad = capacidad;
    }

    public String getTipoVehiculo() {
        return TipoVehiculo;
    }

    public void setTipoVehiculo(String TipoVehiculo) {
        this.TipoVehiculo = TipoVehiculo;
    }
    
    
   // de el array globall
    public static void agregarrusuario(Areas u) {
        
        listagloba2.add(u);
    }

    public static List<Areas> getListaAreases() {
        return listagloba2;
    }

    public static void limpiarLista() {
        listagloba2.clear();
    }

    // bsucar por placa
    public static Areas buscarPorArea(String placa) {
        for (Areas u : listagloba2) {
            if (u.getNombreA().equalsIgnoreCase(placa)) {
                return u;
            }
        }
        return null;
    } 
    
    
    
    
    
    
    
}
