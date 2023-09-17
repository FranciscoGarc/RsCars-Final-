/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.MostrarDatosTabla;
import Modelo.NivelesUsuario;
import Modelo.mCliente;
import Modelo.conx;
import Modelo.mUsuario;
import Modelo.Valida;
import Forms.RegistroForm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import Modelo.crypt;
import Modelo.mRegistro;
import java.sql.SQLException;
import raven.toast.Notifications;

/**
 *
 * @author Fran
 */
public class cRegistro implements ActionListener {

    private mRegistro modeloUsuario;
    private RegistroForm vistaRegistro;
    private NivelesUsuario modelNivelesUsuario;

    public cRegistro(RegistroForm vistaRegistro, mRegistro modeloUsuario, NivelesUsuario modeloNivelesUsuario) {
        this.modeloUsuario = modeloUsuario;
        this.vistaRegistro = vistaRegistro;
        this.modelNivelesUsuario = modeloNivelesUsuario;
        this.vistaRegistro.btnUser.addActionListener(this);
        vistaRegistro.txtUs.setDocument(new Valida(30, "[a-zA-Z0-9]*"));
        vistaRegistro.txtContras.setDocument(new Valida(12, "[a-zA-Z0-9]*"));

    }

    private void limpiarCamposTextoUsuario() {

        vistaRegistro.txtCorre.setText("");
        vistaRegistro.txtContras.setText("");
        vistaRegistro.txtUs.setText("");
    }

    private boolean isValidEmail(String email) {
        String allowedPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(allowedPattern);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaRegistro.btnUser) {

            try {
                String idTipoUsuario = vistaRegistro.cbLista.getSelectedItem().toString();
                String usuario = vistaRegistro.txtUs.getText();
                String contra = vistaRegistro.txtContras.getText();
                String correo = vistaRegistro.txtCorre.getText();

                if (vistaRegistro.txtUs.getText().length() < 4 || vistaRegistro.txtContras.getText().length() < 8) {
                    Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "La contraseña debe tener como minimo 8 caracteres");
                } else if (!isValidEmail(correo)) {
                    Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Ingrese un correo valido");
                } else {
                    if (usuario.isEmpty() || contra.isEmpty() || correo.isEmpty()) {
                        Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Llene todos los campos");
                    } else {
                        String encryptedPassword = crypt.encryptPassword(contra);
                        modelNivelesUsuario.setTipo(idTipoUsuario);
                        modeloUsuario.setUsuario(usuario);
                        modeloUsuario.setContra(encryptedPassword);
                        modeloUsuario.setCorreo(correo);
                        // Aquí puedes llamar a la función que genera el error
                        modelNivelesUsuario.traerIdDeTbTipo(modeloUsuario, modelNivelesUsuario);

                        modeloUsuario.AgregarUsuarioR(modeloUsuario, modelNivelesUsuario);

                        Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Ha sido registrado exitosamente");
                        limpiarCamposTextoUsuario();

                    }

                }
            } catch (Exception ex) {
                // Captura cualquier excepción que ocurra durante la ejecución del bloque try
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error: " + ex.getMessage());
            }
        }

    }
}
