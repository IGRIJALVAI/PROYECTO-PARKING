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
public class DatosCentrales {

    public static final List<Usuarios> USUARIOS = new ArrayList<>();
    public static final List<Vehiculos> VEHICULOS = new ArrayList<>();
    public static final List<Areas> AREAS = new ArrayList<>();
    public static final List<Spots> SPOTS = new ArrayList<>();
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    public static void limpiarTodo() {
        USUARIOS.clear();
        VEHICULOS.clear();
        AREAS.clear();
        SPOTS.clear();
    }
}
