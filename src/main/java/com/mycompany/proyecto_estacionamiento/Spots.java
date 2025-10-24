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
public class Spots {
    
    String IdSpots ;
    String IdArea ;
    String TipoVehiculo ;
    String Status ;
    
      private static final List<Spots> listagloba3 = new ArrayList<>();

    public String getIdSpots() {
        return IdSpots;
    }

    public void setIdSpots(String IdSpots) {
        this.IdSpots = IdSpots;
    }

    public String getIdArea() {
        return IdArea;
    }

    public void setIdArea(String IdArea) {
        this.IdArea = IdArea;
    }

    public String getTipoVehiculo() {
        return TipoVehiculo;
    }

    public void setTipoVehiculo(String TipoVehiculo) {
        this.TipoVehiculo = TipoVehiculo;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }
    
        // de el array globall
        public static void agregarSpot(Spots u) {

            listagloba3.add(u);
        }

        public static List<Spots> getListaSpots() {
            return listagloba3;
        }

        public static void limpiarLista() {
            listagloba3.clear();
        }

        // bsucar por id
        public static Spots buscarPoSpots(String placa) {
            for (Spots u : listagloba3) {
                if (u.getIdSpots().equalsIgnoreCase(placa)) {
                    return u;
                }
            }
            return null;
   
            }
    
}
