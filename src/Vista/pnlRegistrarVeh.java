/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Vista;

import Controlador.cVehiculos;
import Modelo.mVehiculos;
import com.formdev.flatlaf.FlatClientProperties;

/**
 *
 * @author Fran
 */
public class pnlRegistrarVeh extends javax.swing.JPanel {

    /**
     * Creates new form pnlRegistrarVeh
     */
    
    private mVehiculos modeloVehiculos;
    private cVehiculos controladorVehiculos;
    
    public pnlRegistrarVeh() {
        initComponents();
        modeloVehiculos = new mVehiculos();
        controladorVehiculos = new cVehiculos(modeloVehiculos, this);
        init();

    }

        private void init() {


        txtModelo.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Modelo");
        txtMarca.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Marca");
        txtAno.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Año");
        txtPlaca.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Placa");
        txtCliente.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Usuario");
        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Buscar un vehiculo");
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jProgressBar1 = new javax.swing.JProgressBar();
        pLogin = new com.k33ptoo.components.KGradientPanel();
        txtMarca = new javax.swing.JTextField();
        txtAno = new javax.swing.JTextField();
        txtPlaca = new javax.swing.JTextField();
        txtCliente = new javax.swing.JTextField();
        txtModelo = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbVehi = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnRegistrar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pLogin.setkEndColor(new java.awt.Color(0, 0, 0));
        pLogin.setkStartColor(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout pLoginLayout = new javax.swing.GroupLayout(pLogin);
        pLogin.setLayout(pLoginLayout);
        pLoginLayout.setHorizontalGroup(
            pLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pLoginLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(pLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtCliente)
                    .addGroup(pLoginLayout.createSequentialGroup()
                        .addGroup(pLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtAno, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtModelo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(pLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        pLoginLayout.setVerticalGroup(
            pLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pLoginLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(pLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAno, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(57, Short.MAX_VALUE))
        );

        add(pLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 650, 260));

        tbVehi.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tbVehi);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, 780, 150));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Buscar:");
        add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));
        add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 440, 39));

        btnRegistrar.setText("Agregar vehiculo");
        add(btnRegistrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 130, 132, 41));

        btnEliminar.setText("Eliminar vehiculo");
        add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 230, 132, 41));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnEliminar;
    public javax.swing.JButton btnRegistrar;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private com.k33ptoo.components.KGradientPanel pLogin;
    public javax.swing.JTable tbVehi;
    public javax.swing.JTextField txtAno;
    public javax.swing.JTextField txtCliente;
    public javax.swing.JTextField txtMarca;
    public javax.swing.JTextField txtModelo;
    public javax.swing.JTextField txtPlaca;
    public javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
