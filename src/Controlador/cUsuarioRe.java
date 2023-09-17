/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.MostrarDatosTabla;
import Modelo.crypt;
import Modelo.mUsuario;
import Vista.ControlRecepcionista;
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
public class cUsuarioRe implements ActionListener{
    private mUsuario modeloUsuario;
    private ControlRecepcionista vistaContadores;
    private MostrarDatosTabla mostrarDatosTabla;
    
    public cUsuarioRe(ControlRecepcionista vistaContadores, mUsuario modeloUsuario) {
        this.modeloUsuario = modeloUsuario;
        this.vistaContadores = vistaContadores;
        this.vistaContadores.btnRegistrar.addActionListener(this);
        this.vistaContadores.btnAgregarUsuario.addActionListener(this);
        this.vistaContadores.btnActualizar.addActionListener(this);
        this.vistaContadores.btnEliminar.addActionListener(this);

    }
    
        private boolean esCorreoValido(String correo) {
            // Expresión regular para validar el formato de un correo electrónico
            String patronCorreo = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
            return correo.matches(patronCorreo);
        }

        private void cargarDatosTabla() {            
            mostrarDatosTabla = new MostrarDatosTabla();
            mostrarDatosTabla.mostrarDatosEnTablaRecep(vistaContadores.tbDatosCl);           

}

        private void limpiarCamposTextoUsuario() {
           vistaContadores.txtUser.setText("");
            vistaContadores.txtName.setText("");
            vistaContadores.txtApe.setText("");
            vistaContadores.txtTel.setText("");
            vistaContadores.txtDui.setText("");
            vistaContadores.txtDirec.setText("");
            vistaContadores.txtCorreo.setText("");
            vistaContadores.txtContra.setText("");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaContadores.btnAgregarUsuario) {
            int idTipoUsuario = 4; 
            String usuario = vistaContadores.txtUser.getText();
            String contra = vistaContadores.txtContra.getText();
            String correo = vistaContadores.txtCorreo.getText();


            if (usuario.isEmpty() || contra.isEmpty() || correo.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Llene todos los campos.");
            } else {
                  if (!esCorreoValido(correo)) {
                    return; // Detener la ejecución si los campos no son válidos
                }
                String encryptedPassword = crypt.encryptPassword(contra);                
                modeloUsuario.setIdTipo(idTipoUsuario);
                modeloUsuario.setUsuario(usuario);
                modeloUsuario.setContra(encryptedPassword);
                modeloUsuario.setCorreo(correo);

                if (modeloUsuario.AgregarUsuario(modeloUsuario)) {
                    JOptionPane.showMessageDialog(vistaContadores, "Usuario registrado exitosamente.");
                    cargarDatosTabla();
                    limpiarCamposTextoUsuario();
                } else {
                    JOptionPane.showMessageDialog(vistaContadores, "Error al registrar el usuario.");
                }
            }
        }

        if (e.getSource() == vistaContadores.btnEliminar) {
            int filaSeleccionada = vistaContadores.tbDatosCl.getSelectedRow();
            if (filaSeleccionada >= 0) {
                int idUsuario = Integer.parseInt(vistaContadores.tbDatosCl.getValueAt(filaSeleccionada, 0).toString());
                modeloUsuario.setIdUsuario(idUsuario);

                if (modeloUsuario.EliminarUsuario(modeloUsuario)) {
                    JOptionPane.showMessageDialog(vistaContadores, "Usuario eliminado exitosamente.");
                    cargarDatosTabla();
                    limpiarCamposTextoUsuario();
                } else {
                    JOptionPane.showMessageDialog(vistaContadores, "Error al eliminar el usuario.");
                }
            } else {
                JOptionPane.showMessageDialog(vistaContadores, "Debe seleccionar un usuario de la tabla.");
            }
        }

        if (e.getSource() == vistaContadores.btnActualizar) {
            int filaSeleccionada = vistaContadores.tbDatosCl.getSelectedRow();
            if (filaSeleccionada >= 0) {
                int idUsuario = Integer.parseInt(vistaContadores.tbDatosCl.getValueAt(filaSeleccionada, 0).toString());
                int idTipoUsuario = 4; // Nivel de usuario fijo: 4 para Recepcionistas
                String usuario = vistaContadores.txtUser.getText();
                String contra = vistaContadores.txtContra.getText();
                String correo = vistaContadores.txtCorreo.getText();
                
                String encryptedPassword = crypt.encryptPassword(contra);
                modeloUsuario.setIdUsuario(idUsuario);
                modeloUsuario.setIdTipo(idTipoUsuario);
                modeloUsuario.setUsuario(usuario);
                modeloUsuario.setContra(encryptedPassword);
                modeloUsuario.setCorreo(correo);

                if (modeloUsuario.ActualizarUsuario(modeloUsuario)) {
                    JOptionPane.showMessageDialog(vistaContadores, "Usuario actualizado exitosamente.");
                    cargarDatosTabla();
                    limpiarCamposTextoUsuario();
                } else {
                    JOptionPane.showMessageDialog(vistaContadores, "Error al actualizar el usuario.");
                }
            } else {
                JOptionPane.showMessageDialog(vistaContadores, "Debe seleccionar un usuario de la tabla.");
            }
        }
    }
    
}
