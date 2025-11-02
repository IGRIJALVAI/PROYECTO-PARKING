
package com.mycompany.proyecto_estacionamiento;
import javax.swing.*;
import java.awt.*;


   public class RecursosVisualesxd extends JPanel {
       
    private final Image imagen;

    public RecursosVisualesxd(String rutaClasspath) {
        ImageIcon ii = new ImageIcon(getClass().getResource(rutaClasspath));
        this.imagen = ii.getImage();
        setOpaque(false); // deja ver el fondo
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagen != null) {
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
        }
    }
}