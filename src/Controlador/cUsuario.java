/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.MostrarDatosTabla;
import Modelo.crypt;
import Modelo.mUsuario;
import Vista.pnlControlClientes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import java.sql.SQLException;
/**
 *
 * @author Fran
 */
public class cUsuario implements ActionListener{
    private mUsuario modeloUsuario;
    private pnlControlClientes vistaClientes;
    private MostrarDatosTabla mostrarDatosTabla;
    
    public cUsuario(pnlControlClientes vistaClientes, mUsuario modeloUsuario) {
        this.modeloUsuario = modeloUsuario;
        this.vistaClientes = vistaClientes;
        this.vistaClientes.btnRegistrar.addActionListener(this);
        this.vistaClientes.btnAgregarUsuario.addActionListener(this);
        this.vistaClientes.btnActualizar.addActionListener(this);
        this.vistaClientes.btnEliminar.addActionListener(this);

    }
    


        private void cargarDatosTabla() {            
            mostrarDatosTabla = new MostrarDatosTabla();
            mostrarDatosTabla.mostrarDatosEnTabla(vistaClientes.tbDatosCl);           

}

        private void limpiarCamposTextoUsuario() {
           vistaClientes.txtUser.setText("");
            vistaClientes.txtName.setText("");
            vistaClientes.txtApe.setText("");
            vistaClientes.txtTel.setText("");
            vistaClientes.txtDui.setText("");
            vistaClientes.txtDirec.setText("");
            vistaClientes.txtCorreo.setText("");
            vistaClientes.txtContra.setText("");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaClientes.btnAgregarUsuario) {
            int idTipoUsuario = 5; // Nivel de usuario fijo: 5 para Cliente
            String usuario = vistaClientes.txtUser.getText();
            String contra = vistaClientes.txtContra.getText();
            String correo = vistaClientes.txtCorreo.getText();


            if (usuario.isEmpty() || contra.isEmpty() || correo.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Llene todos los campos.");
            } else {
                String encryptedPassword = crypt.encryptPassword(contra);                
                modeloUsuario.setIdTipo(idTipoUsuario);
                modeloUsuario.setUsuario(usuario);
                modeloUsuario.setContra(encryptedPassword);
                modeloUsuario.setCorreo(correo);

                if (modeloUsuario.AgregarUsuario(modeloUsuario)) {
                    JOptionPane.showMessageDialog(vistaClientes, "Usuario registrado exitosamente.");
                    cargarDatosTabla();
                    limpiarCamposTextoUsuario();
                } else {
                    JOptionPane.showMessageDialog(vistaClientes, "Error al registrar el usuario.");
                }
            }
        }

        if (e.getSource() == vistaClientes.btnEliminar) {
            int filaSeleccionada = vistaClientes.tbDatosCl.getSelectedRow();
            if (filaSeleccionada >= 0) {
                int idUsuario = Integer.parseInt(vistaClientes.tbDatosCl.getValueAt(filaSeleccionada, 0).toString());
                modeloUsuario.setIdUsuario(idUsuario);

                if (modeloUsuario.EliminarUsuario(modeloUsuario)) {
                    JOptionPane.showMessageDialog(vistaClientes, "Usuario eliminado exitosamente.");
                    cargarDatosTabla();
                    limpiarCamposTextoUsuario();
                } else {
                    JOptionPane.showMessageDialog(vistaClientes, "Error al eliminar el usuario.");
                }
            } else {
                JOptionPane.showMessageDialog(vistaClientes, "Debe seleccionar un usuario de la tabla.");
            }
        }

        if (e.getSource() == vistaClientes.btnActualizar) {
            int filaSeleccionada = vistaClientes.tbDatosCl.getSelectedRow();
            if (filaSeleccionada >= 0) {
                int idUsuario = Integer.parseInt(vistaClientes.tbDatosCl.getValueAt(filaSeleccionada, 0).toString());
                int idTipoUsuario = 5; // Nivel de usuario fijo: 5 para Cliente
                String usuario = vistaClientes.txtUser.getText();
                String contra = vistaClientes.txtContra.getText();
                String correo = vistaClientes.txtCorreo.getText();
                
                String encryptedPassword = crypt.encryptPassword(contra);
                modeloUsuario.setIdUsuario(idUsuario);
                modeloUsuario.setIdTipo(idTipoUsuario);
                modeloUsuario.setUsuario(usuario);
                modeloUsuario.setContra(encryptedPassword);
                modeloUsuario.setCorreo(correo);

                if (modeloUsuario.ActualizarUsuario(modeloUsuario)) {
                    JOptionPane.showMessageDialog(vistaClientes, "Usuario actualizado exitosamente.");
                    cargarDatosTabla();
                    limpiarCamposTextoUsuario();
                } else {
                    JOptionPane.showMessageDialog(vistaClientes, "Error al actualizar el usuario.");
                }
            } else {
                JOptionPane.showMessageDialog(vistaClientes, "Debe seleccionar un usuario de la tabla.");
            }
        }
    }
    
}
