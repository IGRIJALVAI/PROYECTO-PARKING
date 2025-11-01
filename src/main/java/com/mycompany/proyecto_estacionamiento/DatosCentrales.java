
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

    public static final List<Usuarios> USUARIOS = new ArrayList<>();
    public static final List<Vehiculos> VEHICULOS = new ArrayList<>();
    public static final List<Areas> AREAS = new ArrayList<>();
    public static final List<Spots> SPOTS = new ArrayList<>();
    public static final Map<String, Ticket> TICKETSaCTIVOS = new HashMap<>(); // 	Una tabla que relaciona la clave con un dato y el hasmap para encontrar datods
    public static final List<Ticket> HISTORIALdeTICKETS     = new ArrayList<>();
    public static final Map<String, String> SPOTyPLACA = new HashMap<>();
    public static final Map<String, String> PLACAySPOT = new HashMap<>();


    private static final EnumMap<Ticket.MetodoPago, BigDecimal> TotalesPago= // usa un map emun que usa el enum como indice y big prar dato exacto
            new EnumMap<>(Ticket.MetodoPago.class);

    static {
        for (Ticket.MetodoPago mp : Ticket.MetodoPago.values()) {
            TotalesPago.put(mp, BigDecimal.ZERO); // al arracan se pone en 0 todo
        }
    }

    
    public static String safe(String s) { 
        return s == null ? "" : s.trim().toUpperCase(); } // evita el nul y deja vacio los esapcios

    public static String spotKey(String idArea, String idSpot) { // crea una llacve para mis espacipos  y safe limpia y no null
        return safe(idArea) + "#" + safe(idSpot); // ekl numeral solo es la fiormna de separar
    }

   
    public static Spots buscarSpotIds(String idArea, String idSpot) { // busca el objeto spot por  los id
        String a = safe(idArea), sp = safe(idSpot);
        for (Spots s : SPOTS) {
            if (a.equals(safe(s.getIdArea())) && sp.equals(safe(s.getIdSpots()))) {
                return s;
            }
        }
        return null;
    }

   
    public static Spots buscarSpotLibrePorTipoArea(String tipoArea) {  // Busca el primer spot vacio con su  tipo se parzaca a su tipo area 
        String t = safe(tipoArea);
        for (Spots s : SPOTS) {
            if (t.equals(safe(s.getTipoVehiculo())) && "LIBRE".equalsIgnoreCase(s.getStatus())) {
                return s;
            }
        }
        return null;
    }

  
    public static synchronized void ocuparSpot(Spots s, String placaNorm, Ticket ticket) { // marcamos ligbre o ocupado
        if (s == null || placaNorm == null || ticket == null) return;
        s.setStatus("OCUPADO");
        String key = spotKey(s.getIdArea(), s.getIdSpots());
        SPOTyPLACA.put(key, placaNorm);
        PLACAySPOT.put(placaNorm, key);
        TICKETSaCTIVOS.put(placaNorm, ticket);
    }

  
    public static synchronized void liberarPorPlaca(String placaNorm) { //  por la plca me librea el espod y lo deja libre y moeve el tiket
        if (placaNorm == null) return;
        String key = PLACAySPOT.remove(placaNorm);
        if (key != null) {
            SPOTyPLACA.remove(key);
            String[] p = key.split("#", 2);
            if (p.length == 2) {
                Spots s = buscarSpotIds(p[0], p[1]);
                if (s != null) s.setStatus("LIBRE");
            }
        }
        TICKETSaCTIVOS.remove(placaNorm);
    }

    
    public static Ticket getTicketPorSpot(String idArea, String idSpot) { // devuielve el tiket de su spot
        String key = spotKey(idArea, idSpot);
        String placa = SPOTyPLACA.get(key);
        return (placa == null) ? null : TICKETSaCTIVOS.get(placa);
    }

 
    public static synchronized void agregarPago(Ticket.MetodoPago metodo, BigDecimal monto) { // total de su pago
        if (metodo == null || monto == null) return;
        TotalesPago.put(metodo, TotalesPago.get(metodo).add(monto));
    }

    public static synchronized BigDecimal getTotal(Ticket.MetodoPago metodo) {
        return TotalesPago.getOrDefault(metodo, BigDecimal.ZERO);
    }

    public static synchronized BigDecimal getTotalGeneral() {
        BigDecimal total = BigDecimal.ZERO;
        for (BigDecimal v : TotalesPago.values()) total = total.add(v);
        return total;
    }

    public static synchronized void reiniciarTotales() {
        for (Ticket.MetodoPago mp : Ticket.MetodoPago.values()) {
            TotalesPago.put(mp, BigDecimal.ZERO);
        }
    }

    public static void limpiarTodo() {
        USUARIOS.clear();
        VEHICULOS.clear();
        AREAS.clear();
        SPOTS.clear();
    }
}
