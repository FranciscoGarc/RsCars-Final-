/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.crypt;
import Modelo.mVehiculos;
import Vista.pnlRegistrarVeh;
import Modelo.Valida;
import Modelo.conx;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.PreparedStatement;
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

public class cVehiculos implements ActionListener, MouseListener {

    private mVehiculos modeloVehiculos;
    private pnlRegistrarVeh vistaVeh;

    public cVehiculos(mVehiculos modelVehiculos, pnlRegistrarVeh visVeh) {
        this.modeloVehiculos = modelVehiculos;
        this.vistaVeh = visVeh;
        this.vistaVeh.btnRegistrar.addActionListener(this);
        this.vistaVeh.btnEliminar.addActionListener(this);
        vistaVeh.txtModelo.setDocument(new Valida(50, "[a-zA-Z ]*"));
        vistaVeh.txtMarca.setDocument(new Valida(50, "[a-zA-Z ]*"));
        vistaVeh.txtAno.setDocument(new Valida(4, "[0-9]*"));
        vistaVeh.txtPlaca.setDocument(new Valida(6, "[0-9ABCDEF]*"));
        vistaVeh.txtCliente.setDocument(new Valida(30, "[a-zA-Z0-9]*"));
        this.vistaVeh.tbVehi.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
            }
        });
        vistaVeh.txtSearch.getDocument().addDocumentListener(new DocumentListener() {
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
        modeloVehiculos.mostrarDatosEnTabla(vistaVeh.tbVehi);

    }

    private void buscarDatos() {
        String textoBusqueda = vistaVeh.txtSearch.getText();
        modeloVehiculos.buscarVehiculo(textoBusqueda,vistaVeh.tbVehi);
    }

    private void limpiarCamposTexto() {
        vistaVeh.txtModelo.setText("");
        vistaVeh.txtMarca.setText("");
        vistaVeh.txtAno.setText("");
        vistaVeh.txtPlaca.setText("");
        vistaVeh.txtCliente.setText("");
    }

    private int obtenerIdClientePorUsuario(String usuario) throws SQLException {
        // Realiza la consulta SQL para obtener el idCliente basado en el usuario
        String query = "SELECT C.idCliente FROM tbClientes C "
                + "INNER JOIN tbUsuarios U ON C.idUsuario = U.idUsuario "
                + "WHERE U.usuario = ?";
        PreparedStatement preparedStatement = conx.getConexion().prepareStatement(query);
        preparedStatement.setString(1, usuario);

        int idCliente = -1;
        if (preparedStatement.execute()) {
            if (preparedStatement.getResultSet().next()) {
                idCliente = preparedStatement.getResultSet().getInt("idCliente");
            }
        }
        return idCliente;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaVeh.btnRegistrar) {

            int añoMinimo = 1903;
            int añoMaximo = 2024;

            String modelo = vistaVeh.txtModelo.getText();
            String marca = vistaVeh.txtMarca.getText();
            int año = Integer.parseInt(vistaVeh.txtAno.getText());
            String placa = vistaVeh.txtPlaca.getText();
            String usuarioIngresado = vistaVeh.txtCliente.getText();

            if (año < añoMinimo || año > añoMaximo) {
                JOptionPane.showMessageDialog(null, "El año debe estar entre 1903 y 2024.");
                return;  // No continúa con la operación si el año está fuera del rango
            }

            if (modelo.isEmpty() || marca.isEmpty() || placa.isEmpty() || usuarioIngresado.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Llene todos los campos");
            } else {
                try {
                    int idCliente = obtenerIdClientePorUsuario(usuarioIngresado);

                    // Verificar si se encontró un cliente con ese usuario
                    if (idCliente != -1) {
                        modeloVehiculos.setModelo(modelo);
                        modeloVehiculos.setMarca(marca);
                        modeloVehiculos.setAño(año);
                        modeloVehiculos.setPlaca(placa);
                        modeloVehiculos.setIdCliente(idCliente);

                        if (modeloVehiculos.AgVe(modeloVehiculos)) {
                            JOptionPane.showMessageDialog(vistaVeh, "Vehiculo agregado exitosamente.");
                            cargarDatosTabla(); // Actualizar la tabla
                            limpiarCamposTexto();
                        } else {
                            JOptionPane.showMessageDialog(vistaVeh, "Error al agregar el vehiculo.");
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontró un cliente con ese usuario.");
                    }
                } catch (SQLException ex) {
                    // Maneja cualquier excepción de SQL aquí
                    JOptionPane.showMessageDialog(vistaVeh, ex.toString());
                }
            }

        }
        if (e.getSource() == vistaVeh.btnEliminar) {
            int filaSeleccionada = vistaVeh.tbVehi.getSelectedRow();
            if (filaSeleccionada >= 0) {
                int idVehiculo = Integer.parseInt(vistaVeh.tbVehi.getValueAt(filaSeleccionada, 4).toString());
                modeloVehiculos.setIdVehiculo(idVehiculo);

                if (modeloVehiculos.EliminarVeh(modeloVehiculos)) {
                    JOptionPane.showMessageDialog(vistaVeh, "Cliente eliminado exitosamente.");
                    cargarDatosTabla();
                    limpiarCamposTexto();
                } else {
                    JOptionPane.showMessageDialog(vistaVeh, "Error al eliminar el cliente.");
                }
            } else {
                JOptionPane.showMessageDialog(vistaVeh, "Debe seleccionar un cliente de la tabla.");
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
