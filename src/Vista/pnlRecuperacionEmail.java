/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Vista;

import Modelo.conx;
import Modelo.crypt;
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
        txtCodigo.setEnabled(false);
        btnVerificar.setEnabled(false);
        txtPass.setEnabled(false);
        txtPass2.setEnabled(false);
        btnCambiar.setEnabled(false);
    }
    
        public void ActualizarContra(){
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
            JOptionPane.showMessageDialog(null, "Clave actualizada");

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
                    JOptionPane.showMessageDialog(null, "El codigo ha sido verificado");
                    txtPass.setEnabled(true);
                    txtPass2.setEnabled(true);
                    btnCambiar.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(null, "El codigo no coincide");
                    txtPass.setEnabled(false);
                    txtPass2.setEnabled(false);
                    btnCambiar.setEnabled(false);
                }

            } else {
                JOptionPane.showMessageDialog(null, "Usuario no encontrado");

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
                String mensaje = "Querido "+user+",\n\n" +
                                 "Aquí tiene el código para el cambio de su contraseña:\n" +
                                 "Código de verificación: " + clave + "\n\n" +
                                 "Por favor, utilice este código para completar el proceso de recuperación de su cuenta.\n" +
                                 "Si no solicitó esta recuperación, le recomendamos que tome medidas para proteger su cuenta.\n\n" +
                                 "Gracias,\n" +
                                 "El equipo de soporte";

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

            JOptionPane.showMessageDialog(null, "Mensaje enviado");
        } catch (AddressException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            System.out.println(e.toString());
        } catch (MessagingException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            System.out.println(e.toString());
        }
    }
    
    public void Encontrar(){
    
        String econtras = "select * from tbUsuarios where usuario=? COLLATE SQL_Latin1_General_CP1_CS_AS;";
        
        
        
        try{
            acceso = conex.getConexion();
            PreparedStatement ptst = acceso.prepareStatement(econtras, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
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
            } else{
                JOptionPane.showMessageDialog(null, "Usuario no Encontrado");
                txtCodigo.setEnabled(false);
                btnVerificar.setEnabled(false);
                
            }
        } catch (SQLException e){
          JOptionPane.showMessageDialog(null, e.toString());
        }   
    }
    
    public void AgregarCodigo(String codigo){
     String actu = "update tbUsuarios set codigo=? "
                + "where usuario=? COLLATE SQL_Latin1_General_CP1_CS_AS;";
     
     PreparedStatement ps;
     try{
     acceso = conex.getConexion();
     ps = acceso.prepareStatement(actu);
     ps.setString(1, codigo);
     ps.setString(2, txtUser.getText());
     ps.executeUpdate();
     }catch (SQLException e){
     JOptionPane.showMessageDialog(null, e.toString());
     }
    }
    
    public String Codigo(int largo){
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
        txtPass2 = new javax.swing.JTextField();
        txtPass = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnCambiar = new com.k33ptoo.components.KButton();
        btnEnviar = new com.k33ptoo.components.KButton();
        btnVerificar = new com.k33ptoo.components.KButton();
        imgBg = new javax.swing.JLabel();

        pBg.setkBorderRadius(0);
        pBg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Ingrese el codigo de verificacion");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Ingrese su usuario:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Repita la contraseña:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Ingrese su nueva contraseña:");

        btnCambiar.setText("Cambiar la contraseña");
        btnCambiar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCambiarMouseClicked(evt);
            }
        });
        btnCambiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCambiarActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(btnEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(btnVerificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(155, 155, 155)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(txtPass2, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addContainerGap(24, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCambiar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(69, 69, 69))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(10, 10, 10)
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtPass2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(71, 71, 71)
                                .addComponent(btnCambiar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnVerificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
        );

        pBg.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, 750, 350));

        imgBg.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        imgBg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/bg1.png"))); // NOI18N
        imgBg.setText("jLabel1");
        pBg.add(imgBg, new org.netbeans.lib.awtextra.AbsoluteConstraints(-30, 0, 890, 550));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 872, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(pBg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 562, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(pBg, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCambiarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCambiarMouseClicked
        if (txtUser.getText().isEmpty() || txtCodigo.getText().isEmpty() || txtPass.getText().isEmpty() || txtPass2.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Campos vacios");
        } else {
            if (txtPass.getText().equals(txtPass2.getText())) {
                ActualizarContra();
            }
            else{
                JOptionPane.showMessageDialog(null, "La contraseña no es identica");
            }
        }
        //vLogin login = new vLogin();

        //login.setVisible(true);
    }//GEN-LAST:event_btnCambiarMouseClicked

    private void btnCambiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCambiarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCambiarActionPerformed

    private void btnEnviarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEnviarMouseClicked
        if (txtUser.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Campos vacios");
        } else {
            Encontrar();
        }
    }//GEN-LAST:event_btnEnviarMouseClicked

    private void btnVerificarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVerificarMouseClicked
        if (txtUser.getText().isEmpty() || txtCodigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Los campos no pueden estar nulos");
        } else {
            BuscarCodigo(txtUser.getText());
        }
    }//GEN-LAST:event_btnVerificarMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public com.k33ptoo.components.KButton btnCambiar;
    public com.k33ptoo.components.KButton btnEnviar;
    public com.k33ptoo.components.KButton btnVerificar;
    private static javax.swing.JLabel imgBg;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    public com.k33ptoo.components.KGradientPanel pBg;
    public javax.swing.JTextField txtCodigo;
    public javax.swing.JTextField txtPass;
    public javax.swing.JTextField txtPass2;
    public javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables
}
