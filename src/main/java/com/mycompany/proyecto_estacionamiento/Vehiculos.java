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
public class Vehiculos {
    String Placa ;
    String Carne ;
    String TipoVehiculo ;
    String TipoArea ;

    private static final List<Vehiculos> listaglobal2 = new ArrayList<>();
    
    
    public String getPlaca() {
        return Placa;
    }

    public void setPlaca(String Placa) {
        this.Placa = Placa;
    }

    public String getCarne() {
        return Carne;
    }

    public void setCarne(String Carne) {
        this.Carne = Carne;
    }

    public String getTipoVehiculo() {
        return TipoVehiculo;
    }

    public void setTipoVehiculo(String TipoVehiculo) {
        this.TipoVehiculo = TipoVehiculo;
    }

    public String getTipoArea() {
        return TipoArea;
    }

    public void setTipoArea(String TipoArea) {
        this.TipoArea = TipoArea;
    }
    
     // de el array globall
    public static void agregarUsuario(Vehiculos u) {
        
        listaglobal2.add(u);
    }

    public static List<Vehiculos> getListaVehiculos() {
        return listaglobal2;
    }

    public static void limpiarLista() {
        listaglobal2.clear();
    }

    // bsucar por placa
    public static Vehiculos buscarPorPlaca(String placa) {
        for (Vehiculos u : listaglobal2) {
            if (u.getPlaca().equalsIgnoreCase(placa)) {
                return u;
            }
        }
        return null;
    }
    
}
