
package com.mycompany.proyecto_estacionamiento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author grija
 */
public class Descargar_BD {
    
      public static void cargarUsuarios() {
        DatosCentrales.USUARIOS.clear();

        String sql = "SELECT nombre, Carne, Placa, Carrera FROM usuarios";

        try (Connection cn = Conexion_BD.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuarios u = new Usuarios();
                u.setNombre(rs.getString("nombre"));
                u.setCarne(rs.getString("Carne"));
                u.setPlaca(rs.getString("Placa"));
                u.setCarrera(rs.getString("Carrera"));
                DatosCentrales.USUARIOS.add(u);
            }

            System.out.println("Usuarios cargados desde la base de datos.");

        } catch (Exception e) {
            System.out.println("Error al cargar usuarios: " + e.getMessage());
        }
    }


    public static void cargarAreas() {
        DatosCentrales.AREAS.clear();

        String sql = "SELECT area_id, nombre, capacidad, tipo_vehiculo FROM areas";

        try (Connection cn = Conexion_BD.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Areas a = new Areas();
                a.setIdArea(rs.getString("area_id"));
                a.setNombreA(rs.getString("nombre"));
                a.setCapacidad(rs.getString("capacidad"));
                a.setTipoVehiculo(rs.getString("tipo_vehiculo"));
                DatosCentrales.AREAS.add(a);
            }

            System.out.println("Areas cargadas desde la base de datos.");

        } catch (Exception e) {
            System.out.println("Error al cargar areas" + e.getMessage());
        }
    }


    public static void cargarSpots() {
       DatosCentrales.SPOTS.clear();

String sql = "SELECT spot_id, area_id, tipo_vehiculo, status FROM spots";

try (Connection cn = Conexion_BD.conectar();
     PreparedStatement ps = cn.prepareStatement(sql);
     ResultSet rs = ps.executeQuery()) {

    while (rs.next()) {
        Spots s = new Spots();
        s.setIdSpots(rs.getString("spot_id"));
        s.setIdArea(rs.getString("area_id"));
        s.setTipoVehiculo(rs.getString("tipo_vehiculo"));

        String st = rs.getString("status");
        if (st == null) st = "";
        st = st.trim().toUpperCase();

        if (st.equals("FREE")) st = DatosCentrales.STATUS_LIBRE;
        if (st.equals("OCCUPIED")) st = DatosCentrales.STATUS_OCUPADO;
        if (st.equals("RESERVED")) st = DatosCentrales.STATUS_RESERVADO;

        s.setStatus(st);

        DatosCentrales.SPOTS.add(s);
    }

    System.out.println("Spots cargados desde la base de datos correctamente.");

} catch (Exception e) {
    System.out.println("Error al cargar spots" + e.getMessage());
}

    }


    public static void cargarVehiculos() {
        DatosCentrales.VEHICULOS.clear();

        String sql = "SELECT placa, tipo_vehiculo, tipo_area FROM vehiculos";

        try (Connection cn = Conexion_BD.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Vehiculos v = new Vehiculos();
                v.setPlaca(rs.getString("placa"));
                v.setTipoVehiculo(rs.getString("tipo_vehiculo"));
                v.setTipoArea(rs.getString("tipo_area"));
                DatosCentrales.VEHICULOS.add(v);
            }

            System.out.println("Vehiculos cargados desde la base de datos.");

        } catch (Exception e) {
            System.out.println("Error al cargar vehiculos" + e.getMessage());
        }
    }
    
    
    public static void cargarHistorico() {
    DatosCentrales.HISTORICO.clear();
    String sql = "SELECT ticket_id, placa, area_id, spot_id, fecha_ingreso, fecha_salida, modo_pago, monto FROM historico";

    try (Connection cn = Conexion_BD.conectar();
         PreparedStatement ps = cn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Historico h = new Historico();
            h.IdTicket = rs.getString("ticket_id");
            h.Placa = rs.getString("placa");
            h.IdArea = rs.getString("area_id");
            h.IdSpots = rs.getString("spot_id");
            h.FechaIngreso = rs.getTimestamp("fecha_ingreso").toLocalDateTime();
            h.FechaSalida = rs.getTimestamp("fecha_salida").toLocalDateTime();
            h.MetodoPago = rs.getString("modo_pago");
            h.Monto = rs.getBigDecimal("monto");
            DatosCentrales.HISTORICO.add(h);
        }

        System.out.println("Historico cargado desde la base de datos");

    } catch (Exception e) {
        System.err.println("Error al cargar historico" + e.getMessage());
    }
}

    
    
    
}
