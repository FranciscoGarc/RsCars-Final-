/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.MostrarDatosTabla;
import Modelo.crypt;
import Modelo.mCliente;
import Modelo.mUsuario;
import Vista.pnlControlClientes;
import Modelo.Valida;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import raven.toast.Notifications;

/**
 *
 * @author Fran
 */
public class cCliente implements ActionListener, MouseListener {

    private mCliente modeloCliente;
    private mUsuario modeloUsuario;
    private pnlControlClientes vistaClientes;
    private MostrarDatosTabla mostrarDatosTabla;

    public cCliente(pnlControlClientes vistasClientes, mCliente modeloCliente) {
        this.modeloCliente = modeloCliente;
        this.vistaClientes = vistasClientes;
        this.vistaClientes.btnRegistrar.addActionListener(this);
        this.vistaClientes.btnAgregarUsuario.addActionListener(this);
        this.vistaClientes.btnActualizar.addActionListener(this);
        this.vistaClientes.btnEliminar.addActionListener(this);
        this.mostrarDatosTabla = new MostrarDatosTabla();
        vistaClientes.txtName.setDocument(new Valida(30, "[a-zA-Z ]*"));
        vistaClientes.txtApe.setDocument(new Valida(30, "[a-zA-Z ]*"));
        vistaClientes.txtUser.setDocument(new Valida(30, "[a-zA-Z0-9]*"));
        vistaClientes.txtContra.setDocument(new Valida(12, "[a-zA-Z0-9]*"));
        vistaClientes.txtDirec.setDocument(new Valida(30, "[a-zA-Z0-9 áÁéÉíÍóÓúÚüÜ]*"));
        vistaClientes.txtTel.setDocument(new Valida(8, "[0-9]*"));
        vistaClientes.txtDui.setDocument(new Valida(9, "[0-9]*"));
        this.vistaClientes.tbDatosCl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mostrarDatosEnCamposTexto();
            }
        });
        vistaClientes.txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                buscarDatos();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                buscarDatos();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                buscarDatos();
            }
        });

        cargarDatosTabla();
    }

    private void cargarDatosTabla() {
        mostrarDatosTabla = new MostrarDatosTabla();
        mostrarDatosTabla.mostrarDatosEnTabla(vistaClientes.tbDatosCl);

    }
    //Funcion para buscar

    private void buscarDatos() {
        String textoBusqueda = vistaClientes.txtSearch.getText();
        mostrarDatosTabla.buscarDatosEnTabla(vistaClientes.tbDatosCl, textoBusqueda);
    }
    //Funcion para mosstrar los datos en los textfields

    private void mostrarDatosEnCamposTexto() {
        int filaSeleccionada = vistaClientes.tbDatosCl.getSelectedRow();
        if (filaSeleccionada >= 0 && filaSeleccionada < vistaClientes.tbDatosCl.getRowCount()) {
            int idUsuario = Integer.parseInt(vistaClientes.tbDatosCl.getValueAt(filaSeleccionada, 0).toString());
            String usuario = getCellValueOrDefault(vistaClientes.tbDatosCl, filaSeleccionada, 1, "");
            String contraEncrypted = getCellValueOrDefault(vistaClientes.tbDatosCl, filaSeleccionada, 2, "");
            String correo = getCellValueOrDefault(vistaClientes.tbDatosCl, filaSeleccionada, 3, "");
            int idCliente = Integer.parseInt(getCellValueOrDefault(vistaClientes.tbDatosCl, filaSeleccionada, 4, "0"));
            String nombre = getCellValueOrDefault(vistaClientes.tbDatosCl, filaSeleccionada, 5, "");
            String apellido = getCellValueOrDefault(vistaClientes.tbDatosCl, filaSeleccionada, 6, "");
            String telefono = getCellValueOrDefault(vistaClientes.tbDatosCl, filaSeleccionada, 7, "");
            String direccion = getCellValueOrDefault(vistaClientes.tbDatosCl, filaSeleccionada, 8, "");
            String dui = getCellValueOrDefault(vistaClientes.tbDatosCl, filaSeleccionada, 9, "");

            vistaClientes.txtUser.setText(usuario);
            vistaClientes.txtName.setText(nombre);
            vistaClientes.txtApe.setText(apellido);
            vistaClientes.txtTel.setText(telefono);
            vistaClientes.txtDirec.setText(direccion);
            vistaClientes.txtDui.setText(dui);
            vistaClientes.txtCorreo.setText(correo);
            // Desencriptar y mostrar la contraseña en el campo de texto
            String contraDesencriptada = crypt.decryptPassword(contraEncrypted);
            vistaClientes.txtContra.setText(contraDesencriptada);
        } else {
            // Si no hay filas seleccionadas o la selección es inválida, limpiamos los campos de texto.
            vistaClientes.txtUser.setText("");
            vistaClientes.txtName.setText("");
            vistaClientes.txtApe.setText("");
            vistaClientes.txtTel.setText("");
            vistaClientes.txtDirec.setText("");
            vistaClientes.txtDui.setText("");
            vistaClientes.txtCorreo.setText("");
            vistaClientes.txtContra.setText("");
        }
    }

    private String getCellValueOrDefault(JTable table, int row, int col, String defaultValue) {
        Object value = table.getValueAt(row, col);
        return value != null ? value.toString() : defaultValue;
    }

    private void limpiarCamposTexto() {
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
        if (e.getSource() == vistaClientes.btnRegistrar) {
            int filaSeleccionada = vistaClientes.tbDatosCl.getSelectedRow();

            if (filaSeleccionada >= 0) {
                int idUsuario = Integer.parseInt(vistaClientes.tbDatosCl.getValueAt(filaSeleccionada, 0).toString());
                String nombre = vistaClientes.txtName.getText();
                String apellido = vistaClientes.txtApe.getText();
                String telefono = vistaClientes.txtTel.getText();
                String direccion = vistaClientes.txtDirec.getText();
                String dui = vistaClientes.txtDui.getText();

                if (nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty() || direccion.isEmpty() || dui.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Llene todos los campos");
                } else {
                    modeloCliente.setIdUsuario(idUsuario);
                    modeloCliente.setNombre(nombre);
                    modeloCliente.setApellido(apellido);
                    modeloCliente.setTelefono(telefono);
                    modeloCliente.setDireccion(direccion);
                    modeloCliente.setDui(dui);

                    if (modeloCliente.AgregarCliente(modeloCliente)) {
                        JOptionPane.showMessageDialog(vistaClientes, "Cliente registrado exitosamente.");
                        cargarDatosTabla(); // Actualizar la tabla
                        limpiarCamposTexto();
                    } else {
                        JOptionPane.showMessageDialog(vistaClientes, "Error al registrar el cliente.");
                    }
                }
            } else {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Por favor, seleccione un usuario de la tabla");
            }

        }

        if (e.getSource() == vistaClientes.btnEliminar) {
            int filaSeleccionada = vistaClientes.tbDatosCl.getSelectedRow();
            if (filaSeleccionada >= 0) {
                int idUsuario = Integer.parseInt(vistaClientes.tbDatosCl.getValueAt(filaSeleccionada, 4).toString());
                modeloCliente.setIdUsuario(idUsuario);

                if (modeloCliente.EliminarCliente(modeloCliente)) {
                    JOptionPane.showMessageDialog(vistaClientes, "Cliente eliminado exitosamente.");
                    limpiarCamposTexto();
                } else {
                    JOptionPane.showMessageDialog(vistaClientes, "Error al eliminar el cliente.");
                }
            } else {
                JOptionPane.showMessageDialog(vistaClientes, "Debe seleccionar un cliente de la tabla.");
            }
        }
        if (e.getSource() == vistaClientes.btnActualizar) {
            int filaSeleccionada = vistaClientes.tbDatosCl.getSelectedRow();
            if (filaSeleccionada >= 0) {
                int idCliente = Integer.parseInt(vistaClientes.tbDatosCl.getValueAt(filaSeleccionada, 4).toString());
                String nom = vistaClientes.txtName.getText();
                String apel = vistaClientes.txtApe.getText();
                String tel = vistaClientes.txtTel.getText();
                String direccion = vistaClientes.txtDirec.getText();
                String dui = vistaClientes.txtDui.getText();

                modeloCliente.setIdCliente(idCliente);
                modeloCliente.setNombre(nom);
                modeloCliente.setApellido(apel);
                modeloCliente.setTelefono(tel);
                modeloCliente.setDireccion(direccion);
                modeloCliente.setDui(dui);

                if (modeloCliente.ActualizarCliente(modeloCliente)) {
                    JOptionPane.showMessageDialog(vistaClientes, "Cliente actualizado exitosamente.");
                } else {
                    JOptionPane.showMessageDialog(vistaClientes, "Error al actualizar el cliente.");
                }
            } else {
                JOptionPane.showMessageDialog(vistaClientes, "Debe seleccionar un cliente de la tabla.");
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
