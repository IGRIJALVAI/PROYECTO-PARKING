
package com.mycompany.proyecto_estacionamiento;

import java.time.LocalDateTime;

/**
 *
 * @author grija
 */
public class Ticket {
    
    public enum TipoTarifa { DIA, HORA }
    public enum MetodoPago { EFECTIVO, TARJETA, TRANSFERENCIA } // nuevo tiopo de dato ya que siolo existe esos de alli

    private final String placa;         // normalizada a mayusclus
    private final TipoTarifa tarifa;  // private es solo para ets clase y el finakl es porwuie no se va apoder cmainar de dato si quiero
    private final MetodoPago pago;
    private final String tipoVehiculo;  
    private final String idArea;        
    private final String idSpot;       
    private final LocalDateTime horaIngreso;

    public Ticket(String placa,TipoTarifa tarifa,MetodoPago pago,String tipoVehiculo, String idArea,String idSpot) {

        if (placa == null || placa.trim().isEmpty()) {
            throw new IllegalArgumentException("Placa requerida");
        }
        if (tarifa == null) 
            throw new IllegalArgumentException("Tarifa requerida");
        
        if (pago == null)   
            throw new IllegalArgumentException("Método de pago requerido");
        
        if (idArea == null || idArea.trim().isEmpty()) 
            throw new IllegalArgumentException("Area requerido");
        
        if (idSpot == null || idSpot.trim().isEmpty()) 
            throw new IllegalArgumentException("Spot requerido");
        

        this.placa = normalizarPlaca(placa);
        this.tarifa = tarifa;
        this.pago = pago;
        this.tipoVehiculo = (tipoVehiculo == null) ? "" : tipoVehiculo.trim().toUpperCase();
        this.idArea = idArea.trim().toUpperCase();
        this.idSpot = idSpot.trim().toUpperCase();
        this.horaIngreso = LocalDateTime.now();
    }

    
    public static String normalizarPlaca(String s) { // static no necesita de un objeto y esto quita guiones y esapcios solo deja la placa nitida
        return (s == null) ? "" : s.replace("-", "").replace(" ", "").toUpperCase();
    }

  
    public String getPlaca() { 
        return placa; 
    }
    
    public TipoTarifa getTarifa() {
        return tarifa; 
    }
    
    public MetodoPago getPago() { 
        return pago; 
    }
    
    public String getTipoVehiculo() {
        return tipoVehiculo; 
    }
    
    public String getIdArea() { 
        return idArea; 
    }
    
    public String getIdSpot() {
        return idSpot; 
    }
    
    public LocalDateTime getHoraIngreso() {
        return horaIngreso; 
    }
    
    
    
    
}
