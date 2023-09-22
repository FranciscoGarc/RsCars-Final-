/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Forms.MainForm;
import Modelo.MostrarDatosTabla;
import Modelo.Valida;
import Modelo.crypt;
import Modelo.mCliente;
import Modelo.mMecanicos;
import Modelo.mUsuario;
import Vista.pnlContolMecanicos;
import Vista.pnlControlClientes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import raven.toast.Notifications;

/**
 *
 * @author Fran
 */
public class cMecanicos implements ActionListener, MouseListener {

    private mMecanicos modeloMecanicos;
    private mUsuario modeloUsuario;
    private pnlContolMecanicos vistaMecanicos;
    private MostrarDatosTabla mostrarDatosTabla;
    private int idUser;

    public cMecanicos(pnlContolMecanicos vistaMecanicos, mMecanicos modeloMecanicos) {
        this.modeloMecanicos = modeloMecanicos;
        this.vistaMecanicos = vistaMecanicos;

        this.vistaMecanicos.btnRegistrar.addActionListener(this);
        this.vistaMecanicos.btnAgregarUsuario.addActionListener(this);
        this.vistaMecanicos.btnActualizar.addActionListener(this);
        this.vistaMecanicos.btnEliminar.addActionListener(this);
        this.mostrarDatosTabla = new MostrarDatosTabla();
        this.idUser = vistaMecanicos.getIdUsuario();
        setIdUsuario(idUser);

        vistaMecanicos.txtName.setDocument(new Valida(30, "[a-zA-Z áÁéÉíÍóÓúÚüÜ ]*"));
        vistaMecanicos.txtApe.setDocument(new Valida(30, "[a-zA-Z áÁéÉíÍóÓúÚüÜ ]*"));
        vistaMecanicos.txtUser.setDocument(new Valida(30, "[a-zA-Z0-9]*"));
        vistaMecanicos.txtContra.setDocument(new Valida(12, "[a-zA-Z0-9]*"));
        vistaMecanicos.txtDirec.setDocument(new Valida(30, "[a-zA-Z0-9 áÁéÉíÍóÓúÚüÜ]*"));
        vistaMecanicos.txtTel.setDocument(new Valida(8, "[0-9]*"));
        vistaMecanicos.txtDui.setDocument(new Valida(9, "[0-9]*"));
        this.vistaMecanicos.tbDatosCl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mostrarDatosEnCamposTexto();
            }
        });
        vistaMecanicos.txtSearch.getDocument().addDocumentListener(new DocumentListener() {
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

    private void actualizarLabelIDUser(int idUser) {
        vistaMecanicos.labelIDUser.setText("ID del Usuario: " + idUser);
        vistaMecanicos.labelIDUser.setVisible(false);
    }

    public void setIdUsuario(int idUser) {
        this.idUser = idUser;
    }

    public int getIdUsuario() {
        return idUser;
    }

    private boolean esNumero(String texto) {
        return texto.matches("\\d+");
    }

    private boolean validarLongitud(String texto, int longitudMaxima) {
        return texto.length() <= longitudMaxima;
    }

    private boolean validarCampos(String telefono, String dui) {
        if (!esNumero(telefono) || !validarLongitud(telefono, 8)) {
            JOptionPane.showMessageDialog(null, "El campo de teléfono debe contener solo números y no exceder los 8 caracteres.");
            return false;
        }

        if (!esNumero(dui) || !validarLongitud(dui, 10)) {
            JOptionPane.showMessageDialog(null, "El campo de DUI debe contener solo números y no exceder los 10 caracteres.");
            return false;
        }

        return true;
    }

    private void cargarDatosTabla() {
        mostrarDatosTabla = new MostrarDatosTabla();
        mostrarDatosTabla.mostrarDatosEnTablaMecanicos(vistaMecanicos.tbDatosCl);

    }

    private void buscarDatos() {
        String textoBusqueda = vistaMecanicos.txtSearch.getText();
        mostrarDatosTabla.buscarDatosEnTablaMecanicos(vistaMecanicos.tbDatosCl, textoBusqueda);
    }
    
    

    private void mostrarDatosEnCamposTexto() {
        int filaSeleccionada = vistaMecanicos.tbDatosCl.getSelectedRow();
        if (filaSeleccionada >= 0 && filaSeleccionada < vistaMecanicos.tbDatosCl.getRowCount()) {
            int idUsuario = Integer.parseInt(vistaMecanicos.tbDatosCl.getValueAt(filaSeleccionada, 0).toString());
            String usuario = getCellValueOrDefault(vistaMecanicos.tbDatosCl, filaSeleccionada, 1, "");
            String contraEncrypted = getCellValueOrDefault(vistaMecanicos.tbDatosCl, filaSeleccionada, 2, "");
            String correo = getCellValueOrDefault(vistaMecanicos.tbDatosCl, filaSeleccionada, 3, "");
            int idCliente = Integer.parseInt(getCellValueOrDefault(vistaMecanicos.tbDatosCl, filaSeleccionada, 4, "0"));
            String nombre = getCellValueOrDefault(vistaMecanicos.tbDatosCl, filaSeleccionada, 5, "");
            String apellido = getCellValueOrDefault(vistaMecanicos.tbDatosCl, filaSeleccionada, 6, "");
            String telefono = getCellValueOrDefault(vistaMecanicos.tbDatosCl, filaSeleccionada, 7, "");
            String direccion = getCellValueOrDefault(vistaMecanicos.tbDatosCl, filaSeleccionada, 8, "");
            String dui = getCellValueOrDefault(vistaMecanicos.tbDatosCl, filaSeleccionada, 9, "");

            vistaMecanicos.txtUser.setText(usuario);
            vistaMecanicos.txtName.setText(nombre);
            vistaMecanicos.txtApe.setText(apellido);
            vistaMecanicos.txtTel.setText(telefono);
            vistaMecanicos.txtDirec.setText(direccion);
            vistaMecanicos.txtDui.setText(dui);
            vistaMecanicos.txtCorreo.setText(correo);
            // Desencriptar y mostrar la contraseña en el campo de texto
            String contraDesencriptada = crypt.decryptPassword(contraEncrypted);
            vistaMecanicos.txtContra.setText(contraDesencriptada);
        } else {
            // Si no hay filas seleccionadas o la selección es inválida, limpiamos los campos de texto.
            vistaMecanicos.txtUser.setText("");
            vistaMecanicos.txtName.setText("");
            vistaMecanicos.txtApe.setText("");
            vistaMecanicos.txtTel.setText("");
            vistaMecanicos.txtDirec.setText("");
            vistaMecanicos.txtDui.setText("");
            vistaMecanicos.txtCorreo.setText("");
            vistaMecanicos.txtContra.setText("");
        }
    }

    private String getCellValueOrDefault(JTable table, int row, int col, String defaultValue) {
        Object value = table.getValueAt(row, col);
        return value != null ? value.toString() : defaultValue;
    }

    private void limpiarCamposTexto() {
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
        if (e.getSource() == vistaMecanicos.btnRegistrar) {
            int filaSeleccionada = vistaMecanicos.tbDatosCl.getSelectedRow();
            int idUser = getIdUsuario();
            
            actualizarLabelIDUser(idUser);

            if (filaSeleccionada >= 0) {
                int idUsuario = Integer.parseInt(vistaMecanicos.tbDatosCl.getValueAt(filaSeleccionada, 0).toString());
                int idUsere;
                String nombre = vistaMecanicos.txtName.getText();
                String apellido = vistaMecanicos.txtApe.getText();
                String telefono = vistaMecanicos.txtTel.getText();
                String direccion = vistaMecanicos.txtDirec.getText();
                String dui = vistaMecanicos.txtDui.getText();

                if (nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty() || direccion.isEmpty() || dui.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Llene todos los campos");
                } else {
                    // Llamar al método de validación de campos
                    if (!validarCampos(telefono, dui)) {
                        return; // Detener la ejecución si los campos no son válidos
                    }
                    modeloMecanicos.setIdUsuario(idUsuario);
                    modeloMecanicos.setNombre(nombre);
                    modeloMecanicos.setApellido(apellido);
                    modeloMecanicos.setTelefono(telefono);
                    modeloMecanicos.setDireccion(direccion);
                    modeloMecanicos.setDui(dui);

                    if (modeloMecanicos.AgregarMecanico(modeloMecanicos)) {
                        JOptionPane.showMessageDialog(vistaMecanicos, "Mecanico registrado exitosamente.");
                        cargarDatosTabla(); // Actualizar la tabla
                        limpiarCamposTexto();
                    } else {
                        JOptionPane.showMessageDialog(vistaMecanicos, "Error al registrar el cliente.");
                    }
                }
            } else {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Por favor, seleccione un usuario de la tabla");
            }

        }

        if (e.getSource() == vistaMecanicos.btnEliminar) {
            int filaSeleccionada = vistaMecanicos.tbDatosCl.getSelectedRow();
            if (filaSeleccionada >= 0) {
                int idUsuario = Integer.parseInt(vistaMecanicos.tbDatosCl.getValueAt(filaSeleccionada, 4).toString());
                modeloMecanicos.setIdUsuario(idUsuario);

                if (modeloMecanicos.EliminarMecanico(modeloMecanicos)) {
                    JOptionPane.showMessageDialog(vistaMecanicos, "Mecanico eliminado exitosamente.");
                    limpiarCamposTexto();
                } else {
                    JOptionPane.showMessageDialog(vistaMecanicos, "Error al eliminar el cliente.");
                }
            } else {
                JOptionPane.showMessageDialog(vistaMecanicos, "Debe seleccionar un cliente de la tabla.");
            }
        }
        if (e.getSource() == vistaMecanicos.btnActualizar) {
            int filaSeleccionada = vistaMecanicos.tbDatosCl.getSelectedRow();
            if (filaSeleccionada >= 0) {
                int idCliente = Integer.parseInt(vistaMecanicos.tbDatosCl.getValueAt(filaSeleccionada, 4).toString());
                String nom = vistaMecanicos.txtName.getText();
                String apel = vistaMecanicos.txtApe.getText();
                String tel = vistaMecanicos.txtTel.getText();
                String direccion = vistaMecanicos.txtDirec.getText();
                String dui = vistaMecanicos.txtDui.getText();
                if (nom.isEmpty() || apel.isEmpty() || tel.isEmpty() || direccion.isEmpty() || dui.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Llene todos los campos");
                } else {
                    // Llamar al método de validación de campos
                    if (!validarCampos(tel, dui)) {
                        return; // Detener la ejecución si los campos no son válidos
                    }

                    modeloMecanicos.setIdMecanico(idCliente);
                    modeloMecanicos.setNombre(nom);
                    modeloMecanicos.setApellido(apel);
                    modeloMecanicos.setTelefono(tel);
                    modeloMecanicos.setDireccion(direccion);
                    modeloMecanicos.setDui(dui);

                    if (modeloMecanicos.ActualizarMecanico(modeloMecanicos)) {
                        JOptionPane.showMessageDialog(vistaMecanicos, "Mecanico actualizado exitosamente.");
                    } else {
                        JOptionPane.showMessageDialog(vistaMecanicos, "Error al actualizar el cliente.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(vistaMecanicos, "Debe seleccionar un cliente de la tabla.");
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
