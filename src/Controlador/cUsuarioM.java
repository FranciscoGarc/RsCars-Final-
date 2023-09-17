/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.MostrarDatosTabla;
import Modelo.crypt;
import Modelo.mUsuario;
import Vista.pnlContolMecanicos;
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
public class cUsuarioM implements ActionListener{
    private mUsuario modeloUsuario;
    private pnlContolMecanicos vistaMecanicos;
    private MostrarDatosTabla mostrarDatosTabla;
    
    public cUsuarioM(pnlContolMecanicos vistaMecanicos, mUsuario modeloUsuario) {
        this.modeloUsuario = modeloUsuario;
        this.vistaMecanicos = vistaMecanicos;
        this.vistaMecanicos.btnRegistrar.addActionListener(this);
        this.vistaMecanicos.btnAgregarUsuario.addActionListener(this);
        this.vistaMecanicos.btnActualizar.addActionListener(this);
        this.vistaMecanicos.btnEliminar.addActionListener(this);

    }
    
        private boolean esCorreoValido(String correo) {
            // Expresión regular para validar el formato de un correo electrónico
            String patronCorreo = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
            return correo.matches(patronCorreo);
        }

        private void cargarDatosTabla() {            
            mostrarDatosTabla = new MostrarDatosTabla();
            mostrarDatosTabla.mostrarDatosEnTablaMecanicos(vistaMecanicos.tbDatosCl);           

}

        private void limpiarCamposTextoUsuario() {
           vistaMecanicos.txtUser.setText("");
            vistaMecanicos.txtName.setText("");
            vistaMecanicos.txtApe.setText("");
            vistaMecanicos.txtTel.setText("");
            vistaMecanicos.txtDui.setText("");
            vistaMecanicos.txtDirec.setText("");
            vistaMecanicos.txtCorreo.setText("");
            vistaMecanicos.txtContra.setText("");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaMecanicos.btnAgregarUsuario) {
            int idTipoUsuario = 2; 
            String usuario = vistaMecanicos.txtUser.getText();
            String contra = vistaMecanicos.txtContra.getText();
            String correo = vistaMecanicos.txtCorreo.getText();


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
                    JOptionPane.showMessageDialog(vistaMecanicos, "Usuario registrado exitosamente.");
                    cargarDatosTabla();
                    limpiarCamposTextoUsuario();
                } else {
                    JOptionPane.showMessageDialog(vistaMecanicos, "Error al registrar el usuario.");
                }
            }
        }

        if (e.getSource() == vistaMecanicos.btnEliminar) {
            int filaSeleccionada = vistaMecanicos.tbDatosCl.getSelectedRow();
            if (filaSeleccionada >= 0) {
                int idUsuario = Integer.parseInt(vistaMecanicos.tbDatosCl.getValueAt(filaSeleccionada, 0).toString());
                modeloUsuario.setIdUsuario(idUsuario);

                if (modeloUsuario.EliminarUsuario(modeloUsuario)) {
                    JOptionPane.showMessageDialog(vistaMecanicos, "Usuario eliminado exitosamente.");
                    cargarDatosTabla();
                    limpiarCamposTextoUsuario();
                } else {
                    JOptionPane.showMessageDialog(vistaMecanicos, "Error al eliminar el usuario.");
                }
            } else {
                JOptionPane.showMessageDialog(vistaMecanicos, "Debe seleccionar un usuario de la tabla.");
            }
        }

        if (e.getSource() == vistaMecanicos.btnActualizar) {
            int filaSeleccionada = vistaMecanicos.tbDatosCl.getSelectedRow();
            if (filaSeleccionada >= 0) {
                int idUsuario = Integer.parseInt(vistaMecanicos.tbDatosCl.getValueAt(filaSeleccionada, 0).toString());
                int idTipoUsuario = 2; // Nivel de usuario fijo: 2 para Mecanicos
                String usuario = vistaMecanicos.txtUser.getText();
                String contra = vistaMecanicos.txtContra.getText();
                String correo = vistaMecanicos.txtCorreo.getText();
                
                String encryptedPassword = crypt.encryptPassword(contra);
                modeloUsuario.setIdUsuario(idUsuario);
                modeloUsuario.setIdTipo(idTipoUsuario);
                modeloUsuario.setUsuario(usuario);
                modeloUsuario.setContra(encryptedPassword);
                modeloUsuario.setCorreo(correo);

                if (modeloUsuario.ActualizarUsuario(modeloUsuario)) {
                    JOptionPane.showMessageDialog(vistaMecanicos, "Usuario actualizado exitosamente.");
                    cargarDatosTabla();
                    limpiarCamposTextoUsuario();
                } else {
                    JOptionPane.showMessageDialog(vistaMecanicos, "Error al actualizar el usuario.");
                }
            } else {
                JOptionPane.showMessageDialog(vistaMecanicos, "Debe seleccionar un usuario de la tabla.");
            }
        }
    }
    
}
