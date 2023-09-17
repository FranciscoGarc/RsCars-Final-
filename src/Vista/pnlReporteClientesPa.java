/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Vista;

import Modelo.conx;
import java.util.HashMap;
import java.util.Map;
import javax.swing.WindowConstants;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Fran
 */
public class pnlReporteClientesPa extends javax.swing.JPanel {

    /**
     * Creates new form pnlReporteClientes
     */
    public pnlReporteClientesPa() {
        initComponents();
    }

    private void mostrarReporte() {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("Nombre", txtUsuario.getText());

            JasperReport report = (JasperReport) JRLoader.loadObjectFromFile("src/Reportes/ClientesAPa.jasper");
            JasperPrint jprint = JasperFillManager.fillReport(report, parametros, conx.getConexion());

            JasperViewer view = new JasperViewer(jprint, false);
            view.setTitle("Reporte clientes");
            view.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            view.setVisible(true);

        } catch (JRException ex) {
            System.out.println(ex.toString());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PPrincipal = new com.k33ptoo.components.KGradientPanel();
        btnAgregarUsuario = new com.k33ptoo.components.KButton();
        txtUsuario = new javax.swing.JTextField();
        imgBgP = new javax.swing.JLabel();

        PPrincipal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnAgregarUsuario.setText("Imprimir reporte");
        btnAgregarUsuario.setkBorderRadius(20);
        btnAgregarUsuario.setkEndColor(new java.awt.Color(153, 153, 153));
        btnAgregarUsuario.setkHoverForeGround(new java.awt.Color(51, 153, 0));
        btnAgregarUsuario.setkHoverStartColor(new java.awt.Color(51, 255, 51));
        btnAgregarUsuario.setkStartColor(new java.awt.Color(153, 153, 153));
        btnAgregarUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAgregarUsuarioMouseClicked(evt);
            }
        });
        btnAgregarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarUsuarioActionPerformed(evt);
            }
        });
        PPrincipal.add(btnAgregarUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 590, 170, 50));
        PPrincipal.add(txtUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 220, 320, 50));

        imgBgP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/bg1.png"))); // NOI18N
        PPrincipal.add(imgBgP, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 980, 890));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 980, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(PPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 890, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(PPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarUsuarioMouseClicked
        //mostrarReporte();
    }//GEN-LAST:event_btnAgregarUsuarioMouseClicked

    private void btnAgregarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarUsuarioActionPerformed
        mostrarReporte();
    }//GEN-LAST:event_btnAgregarUsuarioActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.k33ptoo.components.KGradientPanel PPrincipal;
    public com.k33ptoo.components.KButton btnAgregarUsuario;
    private javax.swing.JLabel imgBgP;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
