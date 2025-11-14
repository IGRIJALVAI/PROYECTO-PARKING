
package com.mycompany.proyecto_estacionamiento;

import java.math.BigDecimal;
import java.sql.SQLException;
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
    public static final List<Historico> HISTORICO = new ArrayList<>();
    public static final Map<String, Ticket> TICKETSaCTIVOS = new HashMap<>(); //Una tabla que relaciona la clave con un dato y el hasmap para encontrar datods
    public static final List<Ticket> HISTORIALdeTICKETS     = new ArrayList<>();
    public static final Map<String, String> SPOTyPLACA = new HashMap<>();
    public static final Map<String, String> PLACAySPOT = new HashMap<>();
    public static final java.util.Map<String, java.time.LocalDateTime> RESERVAunRATO = new java.util.HashMap<>(); 
    public static final java.util.Map<String, String> RESERVAdePLACA = new java.util.HashMap<>(); 
    public static final java.util.Set<String> REINGRESO_DIA_SIN_COBRO = new java.util.HashSet<>();


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
    
    public static String normalizarPlaca(String s){  //quita si tiene guiones 
    return safe(s).replace("-", "").replace(" ", "");
    }
    
    
    public static String AreaNombre(String n) {
    String s = safe(n); //safe prepara el texto para que bno este erorr y lo lim,pia

        if (s.startsWith("MOTO")) { // el startsWith sirve para ver como empiza ek string 
            return "MOTOS";

        } else if (s.startsWith("ESTUDIANT")) {
            return "ESTUDIANTES";

        } else if (s.startsWith("CATEDRATIC")) { 
            return "CATEDRATICOS";
        }
        return s;
    }

    
    
   public static Spots buscarSpotIds(String idArea, String idSpot) {
 
    String busc = safe(idArea), busc2 = safe(idSpot);
    for (Spots s : SPOTS) {
        if (busc.equals(safe(s.getIdArea())) && busc2.equals(safe(s.getIdSpots()))) {
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
            if (normalizarPlaca(v.getPlaca()).equals(p))
                return v;
        }
    return null;
}

    
    public static String areaPorNombre(String nombreArea){ // nombre ysu  idArea
    String n = AreaNombre(nombreArea);
        for (Areas a : AREAS) {
            if (safe(a.getNombreA()).equals(n)) 
                return a.getIdArea();
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
          if (placa == null || placa.isEmpty()) {
              return false;
          }

          String placaNorm;
          try {
              placaNorm = normalizarPlaca(placa);
          } catch (Exception e) {
              return false;
          }

          if (placaNorm == null || placaNorm.isEmpty()) {
              return false;
          }

          try {
              return TICKETSaCTIVOS.containsKey(placaNorm);
          } catch (Exception e) {
              return false;
          }
      }


    public static Ticket getTicketActivo(String placa) {
        
        if (placa == null || placa.isEmpty()) {
            return null;
        }

        String placaNorm;
        try {
            placaNorm = normalizarPlaca(placa);
        } catch (Exception e) {
            return null;
        }

        if (placaNorm == null || placaNorm.isEmpty()) {
            return null;
        }

        try {
            return TICKETSaCTIVOS.get(placaNorm);
        } catch (Exception e) {
            return null;
        }
  }

    

        public static Spots spotReservadoDePlaca(String placaNorm) { // regresa el spot si sigue vigent
            
            if (placaNorm == null || placaNorm.isEmpty()) {
                return null;
            }

            try {
                String recervita = RESERVAdePLACA.get(placaNorm);
                if (recervita == null || recervita.isEmpty()) {
                    return null;
                }

                java.time.LocalDateTime limite = RESERVAunRATO.get(recervita);
                java.time.LocalDateTime ahora = java.time.LocalDateTime.now();


                    if (limite == null || ahora.isAfter(limite)) {  // Si no hay limiote o ya se vencio limpiar y salir
                        RESERVAunRATO.remove(recervita);
                        RESERVAdePLACA.remove(placaNorm);
                        return null;
                    }


                String[] p = recervita.split("#", 2); 
                if (p.length != 2) {
                    return null;
                }
                if (p[0] == null || p[0].isEmpty() || p[1] == null || p[1].isEmpty()) {
                    return null;
                }

                return buscarSpotIds(p[0], p[1]);

    } catch (Exception e) {
      
        return null;
    }
}


 
    public static void crearReservaPorMinutos(String placaNorm, Spots s, int minutos) {
  
                if (placaNorm == null || placaNorm.isEmpty()) {
                    return;
                }
                    if (s == null) {
                        return;
                    }
                        if (minutos <= 0) {
                            return;
                        }

        try {

            s.setStatus(STATUS_RESERVADO); // cmania el estado del spot


            String laves = Spotllave(s.getIdArea(), s.getIdSpots()); // Generar llave del spot
            if (laves == null || laves.isEmpty()) {
                return;
            }


            RESERVAdePLACA.put(placaNorm, laves);  // lo guarda  en mapas
            RESERVAunRATO.put(laves, java.time.LocalDateTime.now().plusMinutes(minutos));

        } catch (Exception e) {

        }
}



    public static void cancelarReservaPorPlaca(String placaNorm) {
   
    if (placaNorm == null || placaNorm.isEmpty()) {
        return;
    }

    try {
        String cancelar = RESERVAdePLACA.remove(placaNorm);

        if (cancelar != null && !cancelar.isEmpty()) {
            RESERVAunRATO.remove(cancelar);
        }

    } catch (Exception e) {
       
    }
}



public static void registrarHistorico(Ticket t, BigDecimal monto) {
    
    if (t == null || monto == null) {
        return;
    }

    try {
        Historico h = Historico.desdeTicket(t, monto);

        if (h != null) {
            HISTORICO.add(h);
        }

    } catch (Exception e) {
          System.out.println("Error" + e.getMessage());
    }
}


public static void actualizarEstadoSpotEnBD(String area, String spot, String estado) {
    try {
        var cn = Conexion_BD.conectar();
        var ps = cn.prepareStatement(
            "UPDATE spots SET status=? WHERE area_id=? AND spot_id=?"
        );

        ps.setString(1, estado);
        ps.setString(2, area);
        ps.setString(3, spot);

        ps.executeUpdate();

        ps.close();
        cn.close();

    } catch (SQLException e) {
        System.out.println("Error al actualizar spot" + e.getMessage());
    }
}

    
    public static void limpiarTodo() {
        USUARIOS.clear();
        VEHICULOS.clear();
        AREAS.clear();
        SPOTS.clear();
    }
}
