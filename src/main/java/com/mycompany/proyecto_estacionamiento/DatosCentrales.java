
package com.mycompany.proyecto_estacionamiento;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 * @author grija
 */
public class DatosCentrales {
    
    public static final String STATUS_LIBRE = "LIBRE";
    public static final String STATUS_OCUPADO = "OCUPADO";
    public static final String STATUS_RESERVADO = "RESERVADO";


    public static final List<Usuarios> USUARIOS = new ArrayList<>();
    public static final List<Vehiculos> VEHICULOS = new ArrayList<>();
    public static final List<Areas> AREAS = new ArrayList<>();
    public static final List<Spots> SPOTS = new ArrayList<>();
    public static final Map<String, Ticket> TICKETSaCTIVOS = new HashMap<>(); // 	Una tabla que relaciona la clave con un dato y el hasmap para encontrar datods
    public static final List<Ticket> HISTORIALdeTICKETS     = new ArrayList<>();
    public static final Map<String, String> SPOTyPLACA = new HashMap<>();
    public static final Map<String, String> PLACAySPOT = new HashMap<>();
    public static final java.util.Map<String, java.time.LocalDateTime> RESERVAunRATO = new java.util.HashMap<>(); // key = "AREA#SPOT"
    public static final java.util.Map<String, String> RESERVAdePLACA = new java.util.HashMap<>(); 


    private static final EnumMap<Ticket.MetodoPago, BigDecimal> ComoPago = new EnumMap<>(Ticket.MetodoPago.class);
    static {
        
    ComoPago.put(Ticket.MetodoPago.EFECTIVO, BigDecimal.ZERO);
    ComoPago.put(Ticket.MetodoPago.TARJETA, BigDecimal.ZERO);
    ComoPago.put(Ticket.MetodoPago.TRANSFERENCIA, BigDecimal.ZERO);
    
    }

    
    public static String safe(String s) { 
        return s == null ? "" : s.trim().toUpperCase(); } // evita el nul y deja vacio los esapcios

    public static String Spotllave(String idArea, String idSpot) {
        
    return (idArea == null ? "" : idArea.trim().toUpperCase()) + "#" +(idSpot == null ? "" : idSpot.trim().toUpperCase());
    
     }
    
    public static String normalizarPlaca(String s){
        
    return safe(s).replace("-", "").replace(" ", "");
    }
    
    
    public static String AreaNombre(String n) {
        
    String s = safe(n); //safe prepara el texto para que bno este erorr y lo lim,pia

    if (s.startsWith("MOTO")) {
        return "MOTOS";
        
    } else if (s.startsWith("ESTUDIANT")) {
        return "ESTUDIANTES";
        
    } else if (s.startsWith("CATEDRATIC")) { 
        return "CATEDRATICOS";
    }

    return s;
}
    
    
    
   public static Spots buscarSpotIds(String idArea, String idSpot) {
       
    String a = safe(idArea), sp = safe(idSpot);
    
    for (Spots s : SPOTS) {
     
        if (a.equals(safe(s.getIdArea())) && sp.equals(safe(s.getIdSpots()))) {
            return s;
        }
    }
    return null;
    }

   
    public static Spots SpotvacioArea(String tipoArea) { //buscar Spot Libre Por Tipo de Area
        
    String t = safe(tipoArea);
    
    for (Spots s : SPOTS) {
        if (t.equals(safe(s.getTipoVehiculo())) && STATUS_LIBRE.equalsIgnoreCase(s.getStatus())) {
            return s;
        }
    }
    return null;
    }

  
   
    
    public static Spots SpotLibre(String idArea, String tipoVehiculo) {
    String ida = safe(idArea);
    String tv  = safe(tipoVehiculo);

    for (Spots s : SPOTS) {
        boolean mismaArea = safe(s.getIdArea()).equals(ida);
        boolean mismoTipo = safe(s.getTipoVehiculo()).equals(tv);

        String st = safe(s.getStatus());// normalizatodo y acepta  el libre o free
        boolean libre = st.equals("LIBRE") || st.equals("FREE");

        if (mismaArea && mismoTipo && libre) {
            s.setStatus("OCUPADO");               
            return s;
                }
            }
            return null; // solo cuando no encuentre spots
            }
  

    
   public static Ticket TicketPorSpot(String idArea, String idSpot) {
       
    if (idArea == null || idSpot == null) {
        return null;
    }
        

    String lllave = Spotllave(idArea, idSpot);
    String placa = SPOTyPLACA.get(lllave);

    if (placa == null) {
         return null;
    }
       
    
    return TICKETSaCTIVOS.get(placa);
    
    }
 
    public static void agregarPago(Ticket.MetodoPago metodo, BigDecimal monto) {
        
    if (metodo == null || monto == null) {
        return;
     }

    BigDecimal totalActual = ComoPago.getOrDefault(metodo, BigDecimal.ZERO);
    
    ComoPago.put(metodo, totalActual.add(monto));
    
     }

    public static BigDecimal Total(Ticket.MetodoPago metodo) {
        
    if (metodo == null){
         return BigDecimal.ZERO;
    } 
       
    
          return ComoPago.getOrDefault(metodo, BigDecimal.ZERO);
        
          }
  
  
     public static BigDecimal TotalGeneral() {
         
        BigDecimal total = BigDecimal.ZERO;

        for (BigDecimal valor : ComoPago.values()) {
            total = total.add(valor);
        }

        return total;
            }

      public static void reiniciarTotales() {
          
        for (Ticket.MetodoPago metodo : Ticket.MetodoPago.values())
        ComoPago.put(metodo, BigDecimal.ZERO);
        
            }

    
      public static Vehiculos buscarPorPlaca(String placa){
    String p = normalizarPlaca(placa);
    
    for (Vehiculos v : VEHICULOS) {
        if (normalizarPlaca(v.getPlaca()).equals(p)) return v;
    }
    return null;
}

    
    public static String areaPorNombre(String nombreArea){ // nombre ysu  idArea
    String n = AreaNombre(nombreArea);
        for (Areas a : AREAS) {
            if (safe(a.getNombreA()).equals(n)) return a.getIdArea();
        }
        return null;
    }

   
    
     public static void ocuparSpot(Spots spot, String placa, Ticket ticket) {
    if (spot == null || placa == null || ticket == null){
        return;   
      } 
       

    spot.setStatus("OCUPADO");

    String llavexd = Spotllave(spot.getIdArea(), spot.getIdSpots());

    
    SPOTyPLACA.put(llavexd, placa); // guarda las relaciones
    PLACAySPOT.put(placa, llavexd);
    TICKETSaCTIVOS.put(placa, ticket);
    }
     
     
     
    public static void liberarPorPlaca(String placa) {
      String placaNorm = normalizarPlaca(placa);

      
      String llavesxd = PLACAySPOT.remove(placaNorm); // tener la clave del spot asignado a esta placa
      if (llavesxd == null) return; // Si no esta nada que liberasar

      
      SPOTyPLACA.remove(llavesxd); // borra la relación 

      
      String[] datos = llavesxd.split("#"); // aqui separamos el area y el con el id del spot
      if (datos.length != 2) { //separa en2 partes xd
          return;
      }

      Spots spot = buscarSpotIds(datos[0], datos[1]);  // busca el spot en la lista y lo narca como libre
      if (spot != null) {
          spot.setStatus("LIBRE");
      }

      
      TICKETSaCTIVOS.remove(placaNorm); // quita el ticket  
  }
    
   public static boolean estaDentro(String placa) {
    if (placa == null) return false;
    return TICKETSaCTIVOS.containsKey(normalizarPlaca(placa));
}

public static Ticket getTicketActivo(String placa) {
    if (placa == null) return null;
    return TICKETSaCTIVOS.get(normalizarPlaca(placa));
}
    
// ¿Reserva vigente por placa? Devuelve el spot si sigue vigente
public static Spots spotReservadoDePlaca(String placaNorm) {
    String key = RESERVAdePLACA.get(placaNorm);
    if (key == null) return null;

    java.time.LocalDateTime lim = RESERVAunRATO.get(key);
    if (lim == null || java.time.LocalDateTime.now().isAfter(lim)) {
        // venció: limpiar
        RESERVAunRATO.remove(key);
        RESERVAdePLACA.remove(placaNorm);
        return null;
    }

    String[] p = key.split("#", 2);
    if (p.length != 2) return null;
    return buscarSpotIds(p[0], p[1]);
}

// Crear una reserva sencilla (marca RESERVADO y guarda “hasta”)
public static void crearReservaPorMinutos(String placaNorm, Spots s, int minutos) {
    if (placaNorm == null || s == null) return;
    s.setStatus(STATUS_RESERVADO);
    String key = Spotllave(s.getIdArea(), s.getIdSpots());
    RESERVAdePLACA.put(placaNorm, key);
    RESERVAunRATO.put(key, java.time.LocalDateTime.now().plusMinutes(minutos));
}

// Cancelar la reserva (cuando reingresa y vuelve a ocupar)
public static void cancelarReservaPorPlaca(String placaNorm) {
    String key = RESERVAdePLACA.remove(placaNorm);
    if (key != null) {
        RESERVAunRATO.remove(key);
    }
}





    
    public static void limpiarTodo() {
        USUARIOS.clear();
        VEHICULOS.clear();
        AREAS.clear();
        SPOTS.clear();
    }
}
