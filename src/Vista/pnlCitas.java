/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Vista;

import Controlador.cCitas;
import Modelo.CbCitas;
import Modelo.mCitas;
import com.formdev.flatlaf.FlatClientProperties;

/**
 *
 * @author Fran
 */
public class pnlCitas extends javax.swing.JPanel {

    private CbCitas cbCitas;
    private mCitas mCitas;
    private cCitas cCitas;

    private int idUser;
    private int idTipoUser;

    /**
     * Creates new form pnlCitas
     */
    public pnlCitas(int idUser, int idTipoUser) {
        initComponents();
        cbCitas = new CbCitas();
        cbCitas.llenarComboBoxEs(this);
        cbCitas.llenarComboBoxRe(this);
        cbCitas.llenarComboBoxSer(this);
        setIdUsuario(idUser);
        setIdTipoUser(idTipoUser);
        mCitas = new mCitas();
        cCitas = new cCitas(mCitas, this, cbCitas);
        init();
    }

    private void init() {

        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Buscar cita");
        txtPlaca.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Placa del vehiculo");
        txtMecanico.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Usuario del mecanico");

        if (idTipoUser == 2) {
            ValidarCeldasMec();
        } else {
            ValidarCeldas();
        }
    }

    public void setIdUsuario(int idUser) {
        this.idUser = idUser;
    }

    public int getIdUsuario() {
        return idUser;
    }

    public void setIdTipoUser(int idTipoUser) {
        this.idTipoUser = idTipoUser;
    }

    public int getIdTipoUser() {
        return idTipoUser;
    }

    private void ValidarCeldas() {
        int filaSeleccionada = tbCitas.getSelectedRow();
        if (filaSeleccionada >= 0) {
            date.setEnabled(false);
            txtMecanico.setEnabled(false);
            txtPlaca.setEnabled(false);
            cbServicio.setEnabled(false);
            Estado.setEnabled(true);
            Repuesto.setEnabled(false);
            btnRegistrar.setEnabled(false);
        } else {
            date.setEnabled(true);
            txtMecanico.setEnabled(true);
            txtPlaca.setEnabled(true);
            cbServicio.setEnabled(true);
            Estado.setEnabled(true);
            Repuesto.setEnabled(true);
        }
    }

    private void ValidarCeldasMec() {
        int filaSeleccionada = tbCitas.getSelectedRow();
        if (filaSeleccionada >= 0) {
            date.setEnabled(false);
            txtMecanico.setEnabled(false);
            txtPlaca.setEnabled(false);
            cbServicio.setEnabled(false);
            Estado.setEnabled(true);
            Repuesto.setEnabled(false);
            btnRegistrar.setVisible(false);
        } else {
            date.setEnabled(false);
            txtMecanico.setEnabled(false);
            txtPlaca.setEnabled(false);
            cbServicio.setEnabled(false);
            Estado.setEnabled(true);
            Repuesto.setEnabled(false);
            btnRegistrar.setVisible(false);
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

        pLogin = new com.k33ptoo.components.KGradientPanel();
        txtPlaca = new javax.swing.JTextField();
        txtMecanico = new javax.swing.JTextField();
        date = new rojeru_san.componentes.RSDateChooser();
        cbServicio = new javax.swing.JComboBox<>();
        Repuesto = new javax.swing.JComboBox<>();
        Estado = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbCitas = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnRegistrar = new javax.swing.JButton();
        btnActua = new javax.swing.JButton();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pLogin.setkEndColor(new java.awt.Color(0, 0, 0));
        pLogin.setkStartColor(new java.awt.Color(0, 0, 0));

        date.setColorForeground(new java.awt.Color(255, 255, 255));
        date.setColorSelForeground(new java.awt.Color(0, 0, 0));
        date.setColorTextDiaActual(new java.awt.Color(0, 0, 0));
        date.setName(""); // NOI18N

        cbServicio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        Repuesto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        Estado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout pLoginLayout = new javax.swing.GroupLayout(pLogin);
        pLogin.setLayout(pLoginLayout);
        pLoginLayout.setHorizontalGroup(
            pLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pLoginLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtMecanico, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(167, 167, 167))
            .addGroup(pLoginLayout.createSequentialGroup()
                .addGroup(pLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pLoginLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(cbServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Repuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54)
                        .addComponent(Estado, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pLoginLayout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        pLoginLayout.setVerticalGroup(
            pLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pLoginLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(pLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(txtMecanico, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addGroup(pLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Repuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Estado, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32))
        );

        add(pLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, -1, -1));

        tbCitas.setModel(new javax.swing.table.DefaultTableModel(
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
        tbCitas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbCitasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbCitas);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 900, 190));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Buscar:");
        add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));
        add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 440, 39));

        btnRegistrar.setText("Agregar cita");
        add(btnRegistrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 153, 132, 41));

        btnActua.setText("Actualizar cita");
        add(btnActua, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 253, 132, 41));
    }// </editor-fold>//GEN-END:initComponents

    private void tbCitasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbCitasMouseClicked
        if (idTipoUser == 1) {
            ValidarCeldas();
        } else if (idTipoUser == 2) {
            ValidarCeldasMec();
        }
    }//GEN-LAST:event_tbCitasMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JComboBox<String> Estado;
    public javax.swing.JComboBox<String> Repuesto;
    public javax.swing.JButton btnActua;
    public javax.swing.JButton btnRegistrar;
    public javax.swing.JComboBox<String> cbServicio;
    public rojeru_san.componentes.RSDateChooser date;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private com.k33ptoo.components.KGradientPanel pLogin;
    public javax.swing.JTable tbCitas;
    public javax.swing.JTextField txtMecanico;
    public javax.swing.JTextField txtPlaca;
    public javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
