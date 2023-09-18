/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Forms;

import Aplicacion.Application;
import Vista.*;
import Modelo.conx;
import Modelo.crypt;
import com.formdev.flatlaf.FlatClientProperties;
import com.sun.jdi.connect.spi.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import raven.toast.Notifications;

/**
 *
 * @author Fran
 */
public class pnlRecuperacionEmail extends javax.swing.JPanel {

    conx conex = new conx();
    java.sql.Connection acceso;
    String user;
    String mail;
    String codigo;

    /**
     * Creates new form pnlRecuperacionEmail
     */
    public pnlRecuperacionEmail() {
        initComponents();
        init();
        txtCodigo.setEnabled(false);
        btnVerificar.setEnabled(false);
        txtPass.setEnabled(false);
        txtPass2.setEnabled(false);
        btnCambiar.setEnabled(false);
    }

    private void init() {

        txtPass.putClientProperty(FlatClientProperties.STYLE, ""
                + "showRevealButton:true;"
                + "showCapsLock:true");
        txtPass2.putClientProperty(FlatClientProperties.STYLE, ""
                + "showRevealButton:true;"
                + "showCapsLock:true");
    }

    public void ActualizarContra() {
        String cadena = "update tbUsuarios set contra=? "
                + "where usuario=? COLLATE SQL_Latin1_General_CP1_CS_AS;";
        ;

        try {

            acceso = conex.getConexion();
            PreparedStatement prst = acceso.prepareStatement(cadena);
            String contra = txtPass.getText();
            String encryptedPassword = crypt.encryptPassword(contra);
            prst.setString(1, encryptedPassword);
            prst.setString(2, txtUser.getText());

            prst.executeUpdate();
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Contraseña actualizada");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }

    public void BuscarCodigo(String user) {
        String cadena = "select * from tbUsuarios where usuario=? COLLATE SQL_Latin1_General_CP1_CS_AS;";
        ;

        try {
            acceso = conex.getConexion();
            PreparedStatement ptst = acceso.prepareStatement(cadena, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ptst.setString(1, user);
            ResultSet rs = ptst.executeQuery();
            rs.last();
            int found = rs.getRow();
            if (found == 1) {
                String cod = rs.getString("codigo");
                if (cod.equals(txtCodigo.getText())) {
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "El codigo a sido verificado");
                    txtPass.setEnabled(true);
                    txtPass2.setEnabled(true);
                    btnCambiar.setEnabled(true);
                } else {
                    Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "El codigo no coincide");
                    txtPass.setEnabled(false);
                    txtPass2.setEnabled(false);
                    btnCambiar.setEnabled(false);
                }

            } else {
               Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Usuario no encontrado");

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }

    public void EnviarEmail(String txt, String clave, String user) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.ssl.protocols", "TLSv1.2");

            Session ses = Session.getDefaultInstance(props);

            String cRem = "rscarsuport@gmail.com";
            String pRem = "lakoefmrqorstajj";
            String cRec = txt;
            String asunto = "Recuperación de contraseña";
            String mensaje = "Querido " + user + ",\n\n"
                    + "Aquí tiene el código para el cambio de su contraseña:\n"
                    + "Código de verificación: " + clave + "\n\n"
                    + "Por favor, utilice este código para completar el proceso de recuperación de su cuenta.\n"
                    + "Si no solicitó esta recuperación, le recomendamos que tome medidas para proteger su cuenta.\n\n"
                    + "Gracias,\n"
                    + "El equipo de soporte";

            MimeMessage msg = new MimeMessage(ses);
            msg.setFrom(new InternetAddress(cRem));

            msg.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(cRec));
            msg.setSubject(asunto);
            msg.setText(mensaje);

            Transport t = ses.getTransport("smtp");

            t.connect(cRem, pRem);//correo y contraseña
            t.sendMessage(msg, msg.getRecipients(Message.RecipientType.TO));
            t.close();

            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Mensaje enviado");
        } catch (AddressException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            System.out.println(e.toString());
        } catch (MessagingException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            System.out.println(e.toString());
        }
    }

    public void Encontrar() {

        String econtras = "select * from tbUsuarios where usuario=? COLLATE SQL_Latin1_General_CP1_CS_AS;";

        try {
            acceso = conex.getConexion();
            PreparedStatement ptst = acceso.prepareStatement(econtras, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ptst.setString(1, txtUser.getText());
            ResultSet rs = ptst.executeQuery();
            rs.last();
            int found = rs.getRow();
            if (found == 1) {
                user = rs.getString("usuario");
                mail = rs.getString("correo");
                codigo = Codigo(12);
                AgregarCodigo(codigo);
                EnviarEmail(mail, codigo, user);
                txtCodigo.setEnabled(true);
                btnVerificar.setEnabled(true);
            } else {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Usuario no encontrado");
                txtCodigo.setEnabled(false);
                btnVerificar.setEnabled(false);

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }

    public void AgregarCodigo(String codigo) {
        String actu = "update tbUsuarios set codigo=? "
                + "where usuario=? COLLATE SQL_Latin1_General_CP1_CS_AS;";

        PreparedStatement ps;
        try {
            acceso = conex.getConexion();
            ps = acceso.prepareStatement(actu);
            ps.setString(1, codigo);
            ps.setString(2, txtUser.getText());
            ps.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }

    public String Codigo(int largo) {
        String num = "0123456789";
        String lmin = "abcdefghijklmnopqrstuvwxyz";
        String lmay = lmin.toUpperCase();

        String caract = lmay + num;
        Random cod = new Random();
        String result = "";
        for (int i = 0; i < largo; i++) {
            int posic = cod.nextInt(caract.length());
            char caracter = caract.charAt(posic);
            result += caracter;
        }
        return result;

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pBg = new com.k33ptoo.components.KGradientPanel();
        jPanel1 = new javax.swing.JPanel();
        txtCodigo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtPass2 = new javax.swing.JPasswordField();
        txtPass = new javax.swing.JPasswordField();
        btnEnviar = new javax.swing.JButton();
        btnVerificar = new javax.swing.JButton();
        btnCambiar = new javax.swing.JButton();
        btnBack = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();

        pBg.setkBorderRadius(0);
        pBg.setkEndColor(new java.awt.Color(102, 255, 102));
        pBg.setkGradientFocus(90);
        pBg.setkStartColor(new java.awt.Color(0, 189, 97));

        jPanel1.setBackground(new java.awt.Color(49, 62, 74));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Ingrese el codigo de verificacion");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Ingrese su usuario:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Repita la contraseña:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Ingrese su nueva contraseña:");

        txtPass2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPass2ActionPerformed(evt);
            }
        });

        btnEnviar.setText("Enviar codigo");
        btnEnviar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEnviarMouseClicked(evt);
            }
        });

        btnVerificar.setText("Verificar codigo");
        btnVerificar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnVerificarMouseClicked(evt);
            }
        });

        btnCambiar.setText("Cambiar contraseña");
        btnCambiar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCambiarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1)
                                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(89, 89, 89)
                                .addComponent(btnEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(155, 155, 155)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(txtPass2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCambiar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addComponent(btnVerificar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel4))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEnviar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(10, 10, 10)
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnVerificar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPass2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCambiar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        btnBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/back.png"))); // NOI18N
        btnBack.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBackMouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Constantia", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("su contraseña.");
        jLabel3.setToolTipText("");

        jLabel6.setFont(new java.awt.Font("Constantia", 0, 36)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("¿Olvido su contraseña?");
        jLabel6.setToolTipText("");

        jLabel7.setFont(new java.awt.Font("Constantia", 0, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("No se preocupe, aqui tenemos un metodo para recuperar");
        jLabel7.setToolTipText("");

        javax.swing.GroupLayout pBgLayout = new javax.swing.GroupLayout(pBg);
        pBg.setLayout(pBgLayout);
        pBgLayout.setHorizontalGroup(
            pBgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pBgLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pBgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pBgLayout.createSequentialGroup()
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(129, 129, 129)
                        .addComponent(jLabel6))
                    .addGroup(pBgLayout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addGroup(pBgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel3))))
                .addContainerGap(103, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pBgLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );
        pBgLayout.setVerticalGroup(
            pBgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pBgLayout.createSequentialGroup()
                .addGroup(pBgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pBgLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pBgLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(41, 41, 41)
                .addGroup(pBgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pBgLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 82, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(108, 108, 108))
        );

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/appbg.jpg"))); // NOI18N
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 420, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 780, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(0, 420, Short.MAX_VALUE)
                    .addComponent(pBg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pBg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBackMouseClicked
        Application.logout();
    }//GEN-LAST:event_btnBackMouseClicked

    private void txtPass2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPass2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPass2ActionPerformed

    private void btnEnviarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEnviarMouseClicked
        if (txtUser.getText().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Campos vacios");
        } else {
            Encontrar();
        }
    }//GEN-LAST:event_btnEnviarMouseClicked

    private void btnVerificarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVerificarMouseClicked
        if (txtUser.getText().isEmpty() || txtCodigo.getText().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Los campos no pueden estar vacios");
        } else {
            BuscarCodigo(txtUser.getText());
        }
    }//GEN-LAST:event_btnVerificarMouseClicked

    private void btnCambiarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCambiarMouseClicked
        if (txtUser.getText().isEmpty() || txtCodigo.getText().isEmpty() || txtPass.getText().isEmpty() || txtPass2.getText().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Campos vacios");
        } else {
            if (txtPass.getText().equals(txtPass2.getText())) {
                ActualizarContra();
                Application.logout();
            } else {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Las contraseñas no coiciden");
            }
        }
    }//GEN-LAST:event_btnCambiarMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnBack;
    private javax.swing.JButton btnCambiar;
    private javax.swing.JButton btnEnviar;
    private javax.swing.JButton btnVerificar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    public com.k33ptoo.components.KGradientPanel pBg;
    public javax.swing.JTextField txtCodigo;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JPasswordField txtPass2;
    public javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables
}
