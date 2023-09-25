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
import Modelo.conx;
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
public class cCliente implements ActionListener, MouseListener {

    private mCliente modeloCliente;
    private mUsuario modeloUsuario;
    private pnlControlClientes vistaClientes;
    private MostrarDatosTabla mostrarDatosTabla;

    private int idUser;
    private int idTipoUser;

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
        this.idTipoUser = vistasClientes.getIdTipoUser();
        setIdTipoUser(idTipoUser);
        if (idTipoUser == 2) {
            this.vistaClientes.tbDatosCl.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    mostrarDatosEnCamposTexto();
                }
            });
        } else if (idTipoUser == 4) {
            this.vistaClientes.tbDatosCl.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    mostrarDatosEnCamposTexto();
                    ValidarCeldasRecep();
                }
            });
        } else {
            this.vistaClientes.tbDatosCl.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    mostrarDatosEnCamposTexto();
                    ValidarCeldas();
                }
            });
        }

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

        if (idTipoUser == 4) {
            ValidarCeldasRecep();
            cargarDatosTabla();
        } else {
            ValidarCeldas();
            cargarDatosTabla();
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
            //String contraEncrypted = getCellValueOrDefault(vistaClientes.tbDatosCl, filaSeleccionada, 2, "");
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
            //String contraDesencriptada = crypt.decryptPassword(contraEncrypted);
            //vistaClientes.txtContra.setText(contraDesencriptada);
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

    private void ValidarCeldas() {
        int filaSeleccionada = vistaClientes.tbDatosCl.getSelectedRow();
        if (filaSeleccionada >= 0) {
            vistaClientes.txtName.setEnabled(true);
            vistaClientes.txtApe.setEnabled(true);
            vistaClientes.txtDirec.setEnabled(true);
            vistaClientes.txtDui.setEnabled(true);
            vistaClientes.txtTel.setEnabled(true);
            vistaClientes.btnRegistrar.setEnabled(true);
            vistaClientes.btnAgregarUsuario.setEnabled(false);
            vistaClientes.txtUser.setEnabled(false);
            vistaClientes.btnActualizar.setEnabled(true);
            vistaClientes.btnEliminar.setEnabled(true);
        } else {
            vistaClientes.txtName.setEnabled(false);
            vistaClientes.txtApe.setEnabled(false);
            vistaClientes.txtDirec.setEnabled(false);
            vistaClientes.txtDui.setEnabled(false);
            vistaClientes.txtTel.setEnabled(false);
            vistaClientes.btnRegistrar.setEnabled(false);
            vistaClientes.btnAgregarUsuario.setEnabled(false);
            vistaClientes.btnActualizar.setEnabled(false);
            vistaClientes.btnEliminar.setEnabled(false);
        }
    }

    private void ValidarCeldasRecep() {
        int filaSeleccionada = vistaClientes.tbDatosCl.getSelectedRow();
        if (filaSeleccionada >= 0) {
            vistaClientes.txtName.setEnabled(true);
            vistaClientes.txtApe.setEnabled(true);
            vistaClientes.txtDirec.setEnabled(true);
            vistaClientes.txtDui.setEnabled(true);
            vistaClientes.txtTel.setEnabled(true);
            vistaClientes.btnRegistrar.setEnabled(true);
            vistaClientes.btnAgregarUsuario.setVisible(false);
            vistaClientes.txtUser.setEnabled(false);
            vistaClientes.btnActualizar.setVisible(false);
            vistaClientes.btnEliminar.setVisible(false);
        } else {
            vistaClientes.txtName.setEnabled(false);
            vistaClientes.txtApe.setEnabled(false);
            vistaClientes.txtDirec.setEnabled(false);
            vistaClientes.txtDui.setEnabled(false);
            vistaClientes.txtTel.setEnabled(false);
            vistaClientes.btnRegistrar.setEnabled(true);
            vistaClientes.btnAgregarUsuario.setVisible(false);
            vistaClientes.btnActualizar.setVisible(false);
            vistaClientes.btnEliminar.setVisible(false);
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

    private int obtenerIdUserPorTabla(int codigo) throws SQLException {
        // Realiza la consulta SQL para obtener el idProveedor basado en el código
        String query = "SELECT idUsuario FROM tbClientes WHERE idUsuario = ?";
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

                try {
                    int idUser = obtenerIdUserPorTabla(idUsuario);
                    if (validarIdUsuario(idUser)) {
                        JOptionPane.showMessageDialog(vistaClientes, "Ya existe un usuario registrado");
                        return;  // Terminar la función si la validación falla

                    }

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
                } catch (SQLException ex) {
                    Logger.getLogger(cCliente.class.getName()).log(Level.SEVERE, null, ex);
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
