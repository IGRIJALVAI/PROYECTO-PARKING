
package com.mycompany.proyecto_estacionamiento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *
 * @author grija
 */
public class Historico {
    
    
    public String IdTicket;
    public String Placa;
    public String IdArea;
    public String IdSpots;
    public LocalDateTime FechaIngreso;
    public LocalDateTime FechaSalida;
    public String TipoTarifa; // "DIA" o "HORA"
    public BigDecimal Monto;
      public String MetodoPago; 

    // ==== Constructores ====

    public Historico() {
    }

    public Historico(String IdTicket, String Placa, String IdArea, String IdSpots,
                     LocalDateTime FechaIngreso, LocalDateTime FechaSalida,
                     String TipoTarifa, BigDecimal Monto) {
        this.IdTicket = IdTicket;
        this.Placa = Placa;
        this.IdArea = IdArea;
        this.IdSpots = IdSpots;
        this.FechaIngreso = FechaIngreso;
        this.FechaSalida = FechaSalida;
        this.TipoTarifa = TipoTarifa;
        this.Monto = (Monto == null ? BigDecimal.ZERO : Monto);
    }

    // ==== Método auxiliar para crear desde un Ticket existente ====
    public static Historico desdeTicket(Ticket t, BigDecimal monto) {
        return new Historico(
            "T-" + String.format("%04d", DatosCentrales.HISTORICO.size() + 1), // genera ID tipo T-0001
            t.getPlaca(),
            t.getIdArea(),
            t.getIdSpot(),
            t.getHoraIngreso(),
            t.getHoraSalida(),
            t.getTarifa().name(),
            (monto == null ? BigDecimal.ZERO : monto)
        );
    }

    @Override
    public String toString() {
        return String.format(
            "Ticket %s | Placa: %s | Área: %s | Spot: %s | Ingreso: %s | Salida: %s | Tarifa: %s | Monto: Q%s",
            IdTicket, Placa, IdArea, IdSpots,
            FechaIngreso, FechaSalida, TipoTarifa, Monto
        );
    }
    
    
    
    
    
}
