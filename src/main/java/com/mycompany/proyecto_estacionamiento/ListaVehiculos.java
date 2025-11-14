/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.proyecto_estacionamiento;

import javax.swing.JOptionPane;


/**
 *
 * @author grija
 */
public class ListaVehiculos extends javax.swing.JPanel {

    /**
     * Creates new form ListaVehiculos
     */
    public ListaVehiculos() {
           initComponents();  
        
       Combox.setModel(new javax.swing.DefaultComboBoxModel<>( new String[]{ "Usuarios", "Vehículos", "Áreas", "Spots", "Usuarios y Vehiculos", "Histórico y Totales"}));
         
         
        Combox.setSelectedIndex(0);// combo  no editable 
        Tablitaa.setAutoCreateRowSorter(true);
        Llenartabla();
    }
        
    
    
    public void Llenartabla() {
    int idx = Combox.getSelectedIndex();
    switch (idx) {
        case 0 -> mostrarUsuarios();
        case 1 -> mostrarVehiculos();
        case 2 -> mostrarAreas();
        case 3 -> mostrarSpots();
        case 4 -> mostrarUsuariosConVehiculo();
        case 5 -> mostrarHistoricoYTOTALES();
        default -> {}
    }
}




    static class editartabla extends javax.swing.table.DefaultTableModel {
        
        editartabla(String[] cols) { super(cols, 0); }
        @Override
        public boolean isCellEditable(int row, int col) {
            return true; //  editar las tabala
        }
    }
    
    private String s(Object o){ 
        return o==null? "" : 
                o.toString();
        }
    
       
           
            private String normPlaca(String s){   // elimina guionesy espacios y pasa a mayusuclas para empatar
                if (s == null) return "";
                return s.replace("-", "").replace(" ", "").toUpperCase();
            }

    
    public void mostrarUsuarios() {
        
    editartabla md = new editartabla(new String[]{"Carne","Nombre","Placa","Carrera"});
    
    for (Usuarios u : DatosCentrales.USUARIOS) {
        md.addRow(new Object[]{ 
            s(u.getCarne()), 
            s(u.getNombre()),
            s(u.getPlaca()),
            s(u.getCarrera()) });
    }
        Tablitaa.setModel(md);
     }

    
    
    public void mostrarVehiculos() {
        
    editartabla md = new editartabla(new String[]{"Placa","Tipo Vehiculo","Tipo area"});
    for (Vehiculos v : DatosCentrales.VEHICULOS) {
        
        md.addRow(new Object[]{ s(v.getPlaca()), s(v.getTipoVehiculo()), s(v.getTipoArea()) });
    }
              Tablitaa.setModel(md);
         }

    public void mostrarAreas() {
        
    editartabla md = new editartabla(new String[]{"ID Area","Nombre area","Capacidad","Tipo Vehiculo"});
    for (Areas a : DatosCentrales.AREAS) {
        md.addRow(new Object[]{ s(a.getIdArea()), s(a.getNombreA()), s(a.getCapacidad()), s(a.getTipoVehiculo()) });
    }
         Tablitaa.setModel(md);
     }

    public void mostrarSpots() {
        
    editartabla md = new editartabla(new String[]{"ID Spots","ID Area","Tipo Vehiculo","Estado"});
    for (Spots sp : DatosCentrales.SPOTS) {
        
        md.addRow(new Object[]{ s(sp.getIdSpots()), s(sp.getIdArea()), s(sp.getTipoVehiculo()), s(sp.getStatus()) });
        
    }
               Tablitaa.setModel(md);
    }
    
       private void mostrarUsuariosConVehiculo() {
   
                if (Tablitaa.isEditing()) {  // cierra para leer mejor los datos
                    Tablitaa.getCellEditor().stopCellEditing();
                }

                
                java.util.Map<String, Vehiculos> idxVehPorPlaca = new java.util.HashMap<>(); // placa normalizada y vvehiculos
                
                        for (Vehiculos v : DatosCentrales.VEHICULOS) {
                            String p = normPlaca(v == null ? null : v.getPlaca());
                            if (!p.isEmpty()) idxVehPorPlaca.put(p, v);
                        }

             
                javax.swing.table.DefaultTableModel md = new javax.swing.table.DefaultTableModel(
                    new String[]{"Carné","Nombre","Placa","Carrera","Tipo Vehículo","Tipo Área"}, 0
                ){
                    @Override public boolean isCellEditable(int r, int c){ return false; }
                };

                
                for (Usuarios u : DatosCentrales.USUARIOS) {  // aqui se une el usuario y el vehiculo por la placa normalixadda 
                    String carne   = nz(u.getCarne());
                    String nombre  = nz(u.getNombre());
                    String placa   = nz(u.getPlaca());
                    String carrera = nz(u.getCarrera());

                    Vehiculos v = idxVehPorPlaca.get(normPlaca(placa));
                    String tipoVeh = (v == null) ? "" : nz(v.getTipoVehiculo());
                    String tipoArea = (v == null) ? "" : nz(v.getTipoArea());

                    md.addRow(new Object[]{ carne, nombre, placa, carrera, tipoVeh, tipoArea });
                }

                Tablitaa.setModel(md);
    
            }
       
     
      public void mostrarHistoricoYTOTALES() {
          
        javax.swing.table.DefaultTableModel md = new javax.swing.table.DefaultTableModel(
            new String[]{"Ticket", "Placa", "Área", "Spot", "Ingreso", "Salida", "Pago", "Monto"}, 0
        );

                java.math.BigDecimal totalEfe = java.math.BigDecimal.ZERO;
                java.math.BigDecimal totalTar = java.math.BigDecimal.ZERO;
                java.math.BigDecimal totalTra = java.math.BigDecimal.ZERO;
                java.math.BigDecimal totalGen = java.math.BigDecimal.ZERO;

            for (Historico h : DatosCentrales.HISTORICO) {
                
                String pago = (h.MetodoPago == null ? "" : h.MetodoPago.toUpperCase());
                md.addRow(new Object[]{
                    h.IdTicket,
                    h.Placa,
                    h.IdArea,
                    h.IdSpots,
                    (h.FechaIngreso == null ? "" : h.FechaIngreso.toString()),
                    (h.FechaSalida == null ? "" : h.FechaSalida.toString()),
                    pago,
                    "Q " + h.Monto
                });

        if (pago.contains("EFE")) totalEfe = totalEfe.add(h.Monto);  //se suman los totalers
        else if (pago.contains("TAR")) totalTar = totalTar.add(h.Monto);
        else if (pago.contains("TRA")) totalTra = totalTra.add(h.Monto);

        totalGen = totalGen.add(h.Monto);
    }

    
    md.addRow(new Object[]{"", "", "", "", "", "", "EFECTIVO", "Q " + totalEfe}); // se agregarn liernas de totales al final
    md.addRow(new Object[]{"", "", "", "", "", "", "TARJETA", "Q " + totalTar});
    md.addRow(new Object[]{"", "", "", "", "", "", "TRANSFERENCIA", "Q " + totalTra});
    md.addRow(new Object[]{"", "", "", "", "", "", "TOTAL", "Q " + totalGen});

    Tablitaa.setModel(md);
}
 
       
       

  
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tablitaa = new javax.swing.JTable();
        Combox = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        BtnGuardar = new javax.swing.JButton();
        BtnEliminar = new javax.swing.JButton();
        BtnModificar = new javax.swing.JButton();
        BtnSubir = new javax.swing.JButton();

        jLabel1.setText("Datos Generales");

        Tablitaa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(Tablitaa);

        Combox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        Combox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboxActionPerformed(evt);
            }
        });

        jLabel2.setText("Seleccione el dato que desa obeservar");

        BtnGuardar.setText("Guardar");
        BtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGuardarActionPerformed(evt);
            }
        });

        BtnEliminar.setText("Eliminar");
        BtnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEliminarActionPerformed(evt);
            }
        });

        BtnModificar.setText("Agregar");
        BtnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnModificarActionPerformed(evt);
            }
        });

        BtnSubir.setText("Subir");
        BtnSubir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSubirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(BtnGuardar)
                        .addGap(18, 18, 18)
                        .addComponent(BtnModificar)
                        .addGap(18, 18, 18)
                        .addComponent(BtnEliminar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtnSubir)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(Combox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(137, 137, 137))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(Combox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnGuardar)
                    .addComponent(BtnEliminar)
                    .addComponent(BtnModificar)
                    .addComponent(BtnSubir))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void ComboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboxActionPerformed
        // TODO add your handling code here:
                                             
    Llenartabla();


    }//GEN-LAST:event_ComboxActionPerformed

    private void BtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGuardarActionPerformed
        // TODO add your handling code here:                                          
      if (Tablitaa.isEditing()) {
        Tablitaa.getCellEditor().stopCellEditing();
    }

    
    javax.swing.table.TableModel model = Tablitaa.getModel();
    int sel = Combox.getSelectedIndex(); // selecciona que archuivo quire 

    switch (sel) {
        case 0 -> {
            DatosCentrales.USUARIOS.clear();
            
            for (int i = 0; i < model.getRowCount(); i++) {
                
                Usuarios u = new Usuarios();
              
                u.setCarne(nz(model.getValueAt(i, 0)));
                u.setNombre(nz(model.getValueAt(i, 1)));
                u.setPlaca(nz(model.getValueAt(i, 2)));
                u.setCarrera(nz(model.getValueAt(i, 3)));
                
                if (!u.getCarne().isEmpty() || !u.getNombre().isEmpty()) {
                    DatosCentrales.USUARIOS.add(u);
                }
            }
            
        }

        case 1 -> { 
            DatosCentrales.VEHICULOS.clear();
           
            for (int i = 0; i < model.getRowCount(); i++) {
               
                Vehiculos v = new Vehiculos();
                v.setPlaca(nz(model.getValueAt(i, 0)));
                v.setTipoVehiculo(nz(model.getValueAt(i, 1)));
                v.setTipoArea(nz(model.getValueAt(i, 2)));
                if (!v.getPlaca().isEmpty()) {
                    DatosCentrales.VEHICULOS.add(v);
                }
            }
            
        }

        case 2 -> { 
            DatosCentrales.AREAS.clear();
           
            for (int i = 0; i < model.getRowCount(); i++) {
             
                Areas a = new Areas();
                a.setIdArea(nz(model.getValueAt(i, 0)));
                a.setNombreA(nz(model.getValueAt(i, 1)));
                a.setCapacidad(nz(model.getValueAt(i, 2)));
                a.setTipoVehiculo(nz(model.getValueAt(i, 3)));
                if (!a.getIdArea().isEmpty()) {
                    DatosCentrales.AREAS.add(a);
                }
            }
            
        }

        case 3 -> { 
            DatosCentrales.SPOTS.clear();
            
            for (int i = 0; i < model.getRowCount(); i++) {
              
                Spots s = new Spots();
                s.setIdSpots(nz(model.getValueAt(i, 0)));
                s.setIdArea(nz(model.getValueAt(i, 1)));
                s.setTipoVehiculo(nz(model.getValueAt(i, 2)));
                s.setStatus(nz(model.getValueAt(i, 3)));
                if (!s.getIdSpots().isEmpty()) {
                    DatosCentrales.SPOTS.add(s);
                }
            }
            
        }
    }

    JOptionPane.showMessageDialog(this, "Cambios guardados en memoria");
}

        
        private String nz(Object o) {  //  evita que quede en nulls
            return (o == null) ? "" : o.toString().trim();


    }//GEN-LAST:event_BtnGuardarActionPerformed

    private void BtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEliminarActionPerformed
        // TODO add your handling code here:
         int fila = Tablitaa.getSelectedRow();
         
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una fila para eliminar");
            return;
        }

       
        int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas eliminar esta fila?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION)
            return;

        
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) Tablitaa.getModel(); // Eliminar de la tabla
        model.removeRow(fila);

       
        BtnGuardarActionPerformed(evt); // reutiliza el botón Guardar 

        JOptionPane.showMessageDialog(this, "Fila eliminada correctamente.");
        
    }//GEN-LAST:event_BtnEliminarActionPerformed

    private void BtnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnModificarActionPerformed
        // TODO add your handling code here:
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) Tablitaa.getModel();
        
            model.addRow(new Object[]{"", "", "", ""}); // agrega fila sin nada
    }//GEN-LAST:event_BtnModificarActionPerformed

    private void BtnSubirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSubirActionPerformed
        // TODO add your handling code here:
        Subir_BD.subirTodoDesdeDatosCentrales();
    }//GEN-LAST:event_BtnSubirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnEliminar;
    private javax.swing.JButton BtnGuardar;
    private javax.swing.JButton BtnModificar;
    private javax.swing.JButton BtnSubir;
    private javax.swing.JComboBox<String> Combox;
    private javax.swing.JTable Tablitaa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
