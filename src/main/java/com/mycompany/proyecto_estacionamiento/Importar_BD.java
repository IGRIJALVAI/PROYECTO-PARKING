
package com.mycompany.proyecto_estacionamiento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

/**
 *
 * @author grija
 */
public class Importar_BD {
    
    public static void insertarUsuariosBatch(List<Usuarios> usuarios) throws Exception {
        String sql = """
            INSERT INTO usuarios (nombre, Carne, Placa, Carrera)
            VALUES (?, ?, ?, ?)
            ON DUPLICATE KEY UPDATE
              nombre = VALUES(nombre),
              Placa  = VALUES(Placa),
              Carrera= VALUES(Carrera)
        """;
        try (Connection cn = Conexion_BD.conectar();
             PreparedStatement pst = cn.prepareStatement(sql)) {

            cn.setAutoCommit(false);
            for (Usuarios u : usuarios) {
                pst.setString(1, safe(u.nombre));
                pst.setString(2, safe(u.Carne));
                pst.setString(3, normPlaca(u.Placa));
                pst.setString(4, safe(u.Carrera));
                pst.addBatch();
            }
            pst.executeBatch();
            cn.commit();
        }
    }

 
    public static void insertarAreasBatch(List<Areas> areas) throws Exception {
        String sql = """
            INSERT INTO areas (area_id, nombre, capacidad, tipo_vehiculo)
            VALUES (?, ?, ?, ?)
            ON DUPLICATE KEY UPDATE
              nombre       = VALUES(nombre),
              capacidad    = VALUES(capacidad),
              tipo_vehiculo= VALUES(tipo_vehiculo)
        """;
        try (Connection cn = Conexion_BD.conectar();
             PreparedStatement pst = cn.prepareStatement(sql)) {

            cn.setAutoCommit(false);
            for (Areas a : areas) {
                pst.setString(1, safe(a.IdArea));
                pst.setString(2, safe(a.nombreA));
                pst.setString(3, safe(a.capacidad));
                pst.setString(4, safe(a.TipoVehiculo));
                pst.addBatch();
            }
            pst.executeBatch();
            cn.commit();
        }
    }


    public static void insertarSpotsBatch(List<Spots> spots) throws Exception {
        String sql = """
            INSERT INTO spots (spot_id, area_id, tipo_vehiculo, status)
            VALUES (?, ?, ?, ?)
            ON DUPLICATE KEY UPDATE
              tipo_vehiculo = VALUES(tipo_vehiculo),
              status        = VALUES(status)
        """;
        try (Connection cn = Conexion_BD.conectar();
             PreparedStatement pst = cn.prepareStatement(sql)) {

            cn.setAutoCommit(false);
            for (Spots s : spots) {
                pst.setString(1, safe(s.IdSpots));
                pst.setString(2, safe(s.IdArea));
                pst.setString(3, safe(s.TipoVehiculo));
                pst.setString(4, safe(s.Status));
                pst.addBatch();
            }
            pst.executeBatch();
            cn.commit();
        }
    }

    public static void insertarVehiculosBatch(List<Vehiculos> vehiculos) throws Exception {
        String sql = """
            INSERT INTO vehiculos (placa, tipo_vehiculo, tipo_area)
            VALUES (?, ?, ?)
            ON DUPLICATE KEY UPDATE
              tipo_vehiculo = VALUES(tipo_vehiculo),
              tipo_area     = VALUES(tipo_area)
        """;
        try (Connection cn = Conexion_BD.conectar();
             PreparedStatement pst = cn.prepareStatement(sql)) {

            cn.setAutoCommit(false);
            for (Vehiculos v : vehiculos) {
                pst.setString(1, normPlaca(v.Placa));
                pst.setString(2, safe(v.TipoVehiculo));
                pst.setString(3, safe(v.TipoArea));
                pst.addBatch();
            }
            pst.executeBatch();
            cn.commit();
        }
    }
    
    
    public static void subirHistorico(List<Historico> lista) {
    String sql = """
        INSERT INTO historico
       (ticket_id, placa, area_id, spot_id, fecha_ingreso, fecha_salida, modo_pago, monto)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?)
    """;

    try (Connection cn = Conexion_BD.conectar();
         PreparedStatement ps = cn.prepareStatement(sql)) {

        for (Historico h : lista) {
            ps.setString(1, h.IdTicket);
            ps.setString(2, h.Placa);
            ps.setString(3, h.IdArea);
            ps.setString(4, h.IdSpots);
            ps.setTimestamp(5, java.sql.Timestamp.valueOf(h.FechaIngreso));
            ps.setTimestamp(6, java.sql.Timestamp.valueOf(h.FechaSalida));
            ps.setString(7, h.MetodoPago);
            ps.setBigDecimal(8, h.Monto);
            ps.addBatch();
        }
        ps.executeBatch();
        System.out.println("Historico subido correctamente");

    } catch (Exception e) {
        System.err.println("Error al subir historico: " + e.getMessage());
    }
}

    

  
    private static String safe(String s) { 
        return s == null ? null : s.trim(); 
    }
    
    
    
    private static String normPlaca(String p) {
        return p == null ? null : DatosCentrales.normalizarPlaca(p);
    }
    @SuppressWarnings("unused")
    private static int toInt(String s){ 
        try { 
        return Integer.parseInt(s.trim()); } 
    catch(Exception e){ 
        return 0; } }

    
    
    
    
}
