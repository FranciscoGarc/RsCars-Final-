/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.MostrarDatosTabla;
import Modelo.Valida;
import Modelo.conx;
import Modelo.crypt;
import Modelo.mContadores;
import Modelo.mUsuario;
import Vista.ControlContadores;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
public class cContadores implements ActionListener, MouseListener {

    private mContadores modeloContadores;
    private mUsuario modeloUsuario;
    private ControlContadores vistaContadores;
    private MostrarDatosTabla mostrarDatosTabla;

    public cContadores(ControlContadores vistaContadores, mContadores modeloContadores) {
        this.modeloContadores = modeloContadores;
        this.vistaContadores = vistaContadores;
        this.vistaContadores.btnRegistrar.addActionListener(this);
        this.vistaContadores.btnAgregarUsuario.addActionListener(this);
        this.vistaContadores.btnActualizar.addActionListener(this);
        this.vistaContadores.btnEliminar.addActionListener(this);
        this.mostrarDatosTabla = new MostrarDatosTabla();
        vistaContadores.txtName.setDocument(new Valida(30, "[a-zA-Z áÁéÉíÍóÓúÚüÜ]*"));
        vistaContadores.txtApe.setDocument(new Valida(30, "[a-zA-Z áÁéÉíÍóÓúÚüÜ]*"));
        vistaContadores.txtUser.setDocument(new Valida(30, "[a-zA-Z0-9]*"));
        vistaContadores.txtContra.setDocument(new Valida(12, "[a-zA-Z0-9]*"));
        vistaContadores.txtDirec.setDocument(new Valida(30, "[a-zA-Z0-9 áÁéÉíÍóÓúÚüÜ]*"));
        vistaContadores.txtTel.setDocument(new Valida(8, "[0-9]*"));
        vistaContadores.txtDui.setDocument(new Valida(9, "[0-9]*"));
        this.vistaContadores.tbDatosCl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ValidarCeldas();
                mostrarDatosEnCamposTexto();
            }
        });
        vistaContadores.txtSearch.getDocument().addDocumentListener(new DocumentListener() {
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
        ValidarCeldas();
        cargarDatosTabla();
    }

    private void cargarDatosTabla() {
        mostrarDatosTabla = new MostrarDatosTabla();
        mostrarDatosTabla.mostrarDatosEnTablaContadores(vistaContadores.tbDatosCl);

    }

    private void buscarDatos() {
        String textoBusqueda = vistaContadores.txtSearch.getText();
        mostrarDatosTabla.buscarDatosEnTablaContadores(vistaContadores.tbDatosCl, textoBusqueda);
    }

    // Método para validar que un texto contiene solo números
    private boolean esNumero(String texto) {
        return texto.matches("\\d+");
    }

    // Método para validar que un texto no excede el tamaño máximo permitido
    private boolean validarLongitud(String texto, int longitudMaxima) {
        return texto.length() <= longitudMaxima;
    }

    // Método para validar los campos de DUI y teléfono
    private boolean validarCampos(String telefono, String dui) {
        if (!esNumero(telefono) || !validarLongitud(telefono, 8)) {
            JOptionPane.showMessageDialog(null, "El campo de teléfono debe contener solo números y no exceder los 8 caracteres.");
            return false;
        }

        // Validar que el campo de DUI contenga solo números y no exceda el tamaño máximo de 10 caracteres
        if (!esNumero(dui) || !validarLongitud(dui, 10)) {
            JOptionPane.showMessageDialog(null, "El campo de DUI debe contener solo números y no exceder los 10 caracteres.");
            return false;
        }

        return true;
    }

    private void mostrarDatosEnCamposTexto() {
        int filaSeleccionada = vistaContadores.tbDatosCl.getSelectedRow();
        if (filaSeleccionada >= 0 && filaSeleccionada < vistaContadores.tbDatosCl.getRowCount()) {
            int idUsuario = Integer.parseInt(vistaContadores.tbDatosCl.getValueAt(filaSeleccionada, 0).toString());
            String usuario = getCellValueOrDefault(vistaContadores.tbDatosCl, filaSeleccionada, 1, "");
            String contraEncrypted = getCellValueOrDefault(vistaContadores.tbDatosCl, filaSeleccionada, 2, "");
            String correo = getCellValueOrDefault(vistaContadores.tbDatosCl, filaSeleccionada, 3, "");
            int idCliente = Integer.parseInt(getCellValueOrDefault(vistaContadores.tbDatosCl, filaSeleccionada, 4, "0"));
            String nombre = getCellValueOrDefault(vistaContadores.tbDatosCl, filaSeleccionada, 5, "");
            String apellido = getCellValueOrDefault(vistaContadores.tbDatosCl, filaSeleccionada, 6, "");
            String telefono = getCellValueOrDefault(vistaContadores.tbDatosCl, filaSeleccionada, 7, "");
            String direccion = getCellValueOrDefault(vistaContadores.tbDatosCl, filaSeleccionada, 8, "");
            String dui = getCellValueOrDefault(vistaContadores.tbDatosCl, filaSeleccionada, 9, "");

            vistaContadores.txtUser.setText(usuario);
            vistaContadores.txtName.setText(nombre);
            vistaContadores.txtApe.setText(apellido);
            vistaContadores.txtTel.setText(telefono);
            vistaContadores.txtDirec.setText(direccion);
            vistaContadores.txtDui.setText(dui);
            vistaContadores.txtCorreo.setText(correo);
            // Desencriptar y mostrar la contraseña en el campo de texto
            String contraDesencriptada = crypt.decryptPassword(contraEncrypted);
            vistaContadores.txtContra.setText(contraDesencriptada);
        } else {
            // Si no hay filas seleccionadas o la selección es inválida, limpiamos los campos de texto.
            vistaContadores.txtUser.setText("");
            vistaContadores.txtName.setText("");
            vistaContadores.txtApe.setText("");
            vistaContadores.txtTel.setText("");
            vistaContadores.txtDirec.setText("");
            vistaContadores.txtDui.setText("");
            vistaContadores.txtCorreo.setText("");
            vistaContadores.txtContra.setText("");
        }
    }

    private String getCellValueOrDefault(JTable table, int row, int col, String defaultValue) {
        Object value = table.getValueAt(row, col);
        return value != null ? value.toString() : defaultValue;
    }

    private void limpiarCamposTexto() {
        vistaContadores.txtUser.setText("");
        vistaContadores.txtName.setText("");
        vistaContadores.txtApe.setText("");
        vistaContadores.txtTel.setText("");
        vistaContadores.txtDui.setText("");
        vistaContadores.txtDirec.setText("");
        vistaContadores.txtCorreo.setText("");
        vistaContadores.txtContra.setText("");
    }

    private int obtenerIdUserPorTabla(int codigo) throws SQLException {
        // Realiza la consulta SQL para obtener el idProveedor basado en el código
        String query = "SELECT idUsuario FROM tbContadores WHERE idUsuario = ?";
        PreparedStatement preparedStatement = conx.getConexion().prepareStatement(query);
        preparedStatement.setInt(1, codigo);

        int idCita = -1;
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            idCita = resultSet.getInt("idUsuario");
        }

        resultSet.close();
        preparedStatement.close();

        return idCita;
    }

    private boolean validarIdUsuario(int idUser) {
        return idUser != -1;
    }

    private void ValidarCeldas() {
        int filaSeleccionada = vistaContadores.tbDatosCl.getSelectedRow();
        if (filaSeleccionada >= 0) {
            vistaContadores.txtName.setEnabled(true);
            vistaContadores.txtApe.setEnabled(true);
            vistaContadores.txtDirec.setEnabled(true);
            vistaContadores.txtDui.setEnabled(true);
            vistaContadores.txtTel.setEnabled(true);
            vistaContadores.btnRegistrar.setEnabled(true);
            vistaContadores.btnAgregarUsuario.setEnabled(false);
            vistaContadores.txtUser.setEnabled(false);
            vistaContadores.btnActualizar.setEnabled(true);
            vistaContadores.btnEliminar.setEnabled(true);
        } else {
            vistaContadores.txtName.setEnabled(false);
            vistaContadores.txtApe.setEnabled(false);
            vistaContadores.txtDirec.setEnabled(false);
            vistaContadores.txtDui.setEnabled(false);
            vistaContadores.txtTel.setEnabled(false);
            vistaContadores.btnRegistrar.setEnabled(false);
            vistaContadores.btnAgregarUsuario.setEnabled(true);
            vistaContadores.btnActualizar.setEnabled(false);
            vistaContadores.btnEliminar.setEnabled(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaContadores.btnRegistrar) {
            int filaSeleccionada = vistaContadores.tbDatosCl.getSelectedRow();

            if (filaSeleccionada >= 0) {
                int idUsuario = Integer.parseInt(vistaContadores.tbDatosCl.getValueAt(filaSeleccionada, 0).toString());
                String nombre = vistaContadores.txtName.getText();
                String apellido = vistaContadores.txtApe.getText();
                String telefono = vistaContadores.txtTel.getText();
                String direccion = vistaContadores.txtDirec.getText();
                String dui = vistaContadores.txtDui.getText();

                try {
                    int idUser = obtenerIdUserPorTabla(idUsuario);
                    if (validarIdUsuario(idUser)) {
                        JOptionPane.showMessageDialog(vistaContadores, "Ya existe un usuario registrado");
                        return;  // Terminar la función si la validación falla

                    }

                    if (nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty() || direccion.isEmpty() || dui.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Llene todos los campos");
                    } else {
                        // Llamar al método de validación de campos
                        if (!validarCampos(telefono, dui)) {
                            return; // Detener la ejecución si los campos no son válidos
                        }
                        modeloContadores.setIdUsuario(idUsuario);
                        modeloContadores.setNombre(nombre);
                        modeloContadores.setApellido(apellido);
                        modeloContadores.setTelefono(telefono);
                        modeloContadores.setDireccion(direccion);
                        modeloContadores.setDui(dui);

                        if (modeloContadores.AgregarCliente(modeloContadores)) {
                            JOptionPane.showMessageDialog(vistaContadores, "Contador registrado exitosamente.");
                            cargarDatosTabla(); // Actualizar la tabla
                            limpiarCamposTexto();
                        } else {
                            JOptionPane.showMessageDialog(vistaContadores, "Error al registrar el contador.");
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(cContadores.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Por favor, seleccione un usuario de la tabla");
            }

        }

        if (e.getSource() == vistaContadores.btnEliminar) {
            int filaSeleccionada = vistaContadores.tbDatosCl.getSelectedRow();
            if (filaSeleccionada >= 0) {
                int idUsuario = Integer.parseInt(vistaContadores.tbDatosCl.getValueAt(filaSeleccionada, 4).toString());
                modeloContadores.setIdUsuario(idUsuario);

                if (modeloContadores.EliminarCliente(modeloContadores)) {
                    JOptionPane.showMessageDialog(vistaContadores, "Contador eliminado exitosamente.");
                    limpiarCamposTexto();
                } else {
                    JOptionPane.showMessageDialog(vistaContadores, "Error al eliminar el cliente.");
                }
            } else {
                JOptionPane.showMessageDialog(vistaContadores, "Debe seleccionar un cliente de la tabla.");
            }
        }
        if (e.getSource() == vistaContadores.btnActualizar) {
            int filaSeleccionada = vistaContadores.tbDatosCl.getSelectedRow();
            if (filaSeleccionada >= 0) {
                int idCliente = Integer.parseInt(vistaContadores.tbDatosCl.getValueAt(filaSeleccionada, 4).toString());
                String nom = vistaContadores.txtName.getText();
                String apel = vistaContadores.txtApe.getText();
                String tel = vistaContadores.txtTel.getText();
                String direccion = vistaContadores.txtDirec.getText();
                String dui = vistaContadores.txtDui.getText();
                if (nom.isEmpty() || apel.isEmpty() || tel.isEmpty() || direccion.isEmpty() || dui.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Llene todos los campos");
                } else {
                    // Llamar al método de validación de campos
                    if (!validarCampos(tel, dui)) {
                        return; // Detener la ejecución si los campos no son válidos
                    }

                    modeloContadores.setIdContador(idCliente);
                    modeloContadores.setNombre(nom);
                    modeloContadores.setApellido(apel);
                    modeloContadores.setTelefono(tel);
                    modeloContadores.setDireccion(direccion);
                    modeloContadores.setDui(dui);

                    if (modeloContadores.ActualizarCliente(modeloContadores)) {
                        JOptionPane.showMessageDialog(vistaContadores, "Contador actualizado exitosamente.");
                    } else {
                        JOptionPane.showMessageDialog(vistaContadores, "Error al actualizar el cliente.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(vistaContadores, "Debe seleccionar un cliente de la tabla.");
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
