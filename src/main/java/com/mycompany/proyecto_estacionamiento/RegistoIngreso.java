
package com.mycompany.proyecto_estacionamiento;

import javax.swing.JOptionPane;

/**
 *
 * @author grija
 */
public class RegistoIngreso {
   
    public static void registrar(String placa,String tipoVehiculo,String perfil,String tipoTarifa, String metodoPago)    
    {
        
        String placaNorm = Ticket.normalizarPlaca(placa); //  normaliza la plca y verifica la plac
        if (placaNorm.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese una placa");
            return;
        }


        if (DatosCentrales.TICKETSaCTIVOS.containsKey(placaNorm)) {
            JOptionPane.showMessageDialog(null, "Error vehiculo dentro del parqueo.");
            return;
        }

        boolean existe = false;
        
        for (Vehiculos v : DatosCentrales.VEHICULOS) {
            if (placaNorm.equals(Ticket.normalizarPlaca(v.getPlaca()))) {
                existe = true; break;
            }
        }
        if (!existe) {
            JOptionPane.showMessageDialog(null, "Placa no existe.");
            return;
        }
       
        String tipoArea;
        
        if ("MOTO".equalsIgnoreCase(tipoVehiculo)) {
            tipoArea = "MOTO";
        } else {
            tipoArea = "CATEDRATICO".equalsIgnoreCase(perfil) ? "CATEDRATICO" : "ESTUDIANTE";
        }

        Spots spot = DatosCentrales.SpotvacioArea(tipoArea);
        if (spot == null) {
            JOptionPane.showMessageDialog(null, "No hay espacios libres en el area" + tipoArea);
            return;
        }

      
        Ticket.TipoTarifa tarifa = "DIA".equalsIgnoreCase(tipoTarifa)   // Convertir la tarifa de pago unvalor de a enums
                ? Ticket.TipoTarifa.DIA
                : Ticket.TipoTarifa.HORA;

        Ticket.MetodoPago pago;
        switch (metodoPago.toUpperCase()) {
            case "TARJETA" -> pago = Ticket.MetodoPago.TARJETA;
            case "TRANSFERENCIA" -> pago = Ticket.MetodoPago.TRANSFERENCIA;
            default -> pago = Ticket.MetodoPago.EFECTIVO;
        }

       
        Ticket ticket = new Ticket(  // hacer el ticket con el lugar 
                placaNorm,
                tarifa,
                pago,
                tipoVehiculo == null ? "CARRO" : tipoVehiculo.toUpperCase(),
                spot.getIdArea(),
                spot.getIdSpots()
        );

       
        DatosCentrales.ocuparSpot(spot, placaNorm, ticket);  // hacert qye opcupe un stop y loego regstrar ek tikrte  
       
    }
    
    
}
