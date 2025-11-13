
package com.mycompany.proyecto_estacionamiento;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 *
 * @author grija
 */
public class Mapa {
    
      

    private static Color colorPorEstado(String st) {   //se cmabia de color segunn estado
        if (st == null) return Color.GRAY;
        st = st.trim().toUpperCase();
        if (st.equals(DatosCentrales.STATUS_LIBRE))     return new Color(46, 204, 113); // verde
        if (st.equals(DatosCentrales.STATUS_OCUPADO))   return new Color(231, 76, 60);  // rojo
        if (st.equals(DatosCentrales.STATUS_RESERVADO)) return new Color(243, 156, 18); // anaranjado
        return Color.GRAY;
    }

    
    private static JLabel crearLabelSpot(Spots s, Runnable refresh) { //  Crea un  jlabel pra los spots y con clic cmania y lo guiarda en bd  
        JLabel lbl = new JLabel(s.getIdSpots(), SwingConstants.CENTER);
        lbl.setOpaque(true);
        lbl.setBackground(colorPorEstado(s.getStatus()));
        lbl.setForeground(Color.WHITE);
        lbl.setPreferredSize(new Dimension(70, 50));
        lbl.setMinimumSize(new Dimension(70, 50));
        lbl.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        lbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lbl.setToolTipText("<html><b>Área:</b> " + s.getIdArea()
                + "<br><b>Spot:</b> " + s.getIdSpots()
                + "<br><b>Tipo:</b> " + s.getTipoVehiculo()
                + "<br><b>Status:</b> " + s.getStatus() + "</html>");

       
        lbl.addMouseListener(new MouseAdapter() {   // cambia de  estado en memoria y lo sube a la bd
            @Override public void mouseClicked(MouseEvent e) {
              
                String nuevo = DatosCentrales.STATUS_LIBRE.equalsIgnoreCase(s.getStatus())    // aalterna en memoria
                        ? DatosCentrales.STATUS_OCUPADO
                        : DatosCentrales.STATUS_LIBRE;
                s.setStatus(nuevo);

                
                String sql = "UPDATE spots SET status=? WHERE area_id=? AND spot_id=?";  // actualiza la bd
                try (var cn = Conexion_BD.conectar();
                     var ps = cn.prepareStatement(sql)) {
                    ps.setString(1, nuevo);
                    ps.setString(2, s.getIdArea());
                    ps.setString(3, s.getIdSpots());
                    ps.executeUpdate();
                } catch (Exception ex) {
                    System.out.println("Error al actualizar BD: " + ex.getMessage());
                }

              
                refresh.run(); // refresca el panel
            }
        });
        return lbl;
    }

   
    public static void refrescar(JPanel panelMapa) {  // pinta todos los epots
        SwingUtilities.invokeLater(() -> {
            panelMapa.removeAll();
            panelMapa.setBackground(new Color(245, 245, 245));

            int total = DatosCentrales.SPOTS.size();
            if (total <= 0) {
                panelMapa.setLayout(new BorderLayout());
                JLabel empty = new JLabel("No hay spots para mostrar", SwingConstants.CENTER);
                empty.setFont(empty.getFont().deriveFont(Font.BOLD, 16f));
                empty.setForeground(Color.GRAY);
                panelMapa.add(empty, BorderLayout.CENTER);
                panelMapa.revalidate();
                panelMapa.repaint();
                return;
            }

            int cols = Math.max(5, (int)Math.ceil(Math.sqrt(total)));
            panelMapa.setLayout(new GridLayout(0, cols, 8, 8));

            Runnable refresh = () -> refrescar(panelMapa);
            for (Spots s : DatosCentrales.SPOTS) {
                panelMapa.add(crearLabelSpot(s, refresh));
            }

            panelMapa.revalidate();
            panelMapa.repaint();
        });
    }
    
}
