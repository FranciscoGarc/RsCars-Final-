/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.crypt;
import Modelo.mRepuestos;
import Vista.pnlRepuestos;
import Modelo.Valida;
import Modelo.conx;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

public class cRepuestos implements ActionListener, MouseListener {

    private mRepuestos modeloRepuestos;
    private pnlRepuestos vistaRepuestos;

    public cRepuestos(mRepuestos modeloRepuestos, pnlRepuestos vistaRepuestos) {
        this.modeloRepuestos = modeloRepuestos;
        this.vistaRepuestos = vistaRepuestos;
        this.vistaRepuestos.btnRegistrar.addActionListener(this);
        this.vistaRepuestos.btnEliminar.addActionListener(this);
        vistaRepuestos.txtCodeProv.setDocument(new Valida(4, "[0-9]*"));
        vistaRepuestos.txtStock.setDocument(new Valida(6, "[0-9]*"));
        vistaRepuestos.txtPrecio.setDocument(new Valida(7, "[0-9].*"));
        vistaRepuestos.txtDesc.setDocument(new Valida(20, "[a-zA-Z]*"));
        vistaRepuestos.txtSearch.getDocument().addDocumentListener(new DocumentListener() {
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
        modeloRepuestos.mostrarRepuestosEnTabla(vistaRepuestos.tbProducts);

    }

    private void buscarDatos() {
        String textoBusqueda = vistaRepuestos.txtSearch.getText();
        modeloRepuestos.buscarRepuestos(textoBusqueda, vistaRepuestos.tbProducts);
    }

    private void limpiarCamposTexto() {
        vistaRepuestos.txtCodeProv.setText("");
        vistaRepuestos.txtPrecio.setText("");
        vistaRepuestos.txtDesc.setText("");
        vistaRepuestos.txtStock.setText("");
    }

    private int obtenerIdProvPorCode(String codigo) throws SQLException {
        // Realiza la consulta SQL para obtener el idProveedor basado en el código
        String query = "SELECT idProveedor FROM tbProveedores WHERE codigoProv = ?";
        PreparedStatement preparedStatement = conx.getConexion().prepareStatement(query);
        preparedStatement.setString(1, codigo);

        int idProveedor = -1;
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            idProveedor = resultSet.getInt("idProveedor");
        }

        resultSet.close();
        preparedStatement.close();

        return idProveedor;
    }

    private boolean validarIdProv(int idProv) {
        return idProv != -1; // Si idMecanico es diferente a -1, se encontró un idMecanico
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaRepuestos.btnRegistrar) {
            try {
                if (vistaRepuestos.txtStock.getText().length() < 1 || vistaRepuestos.txtPrecio.getText().length() < 2) {
                    JOptionPane.showMessageDialog(vistaRepuestos, "Campos vacios", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Terminar la función si hay campos vacíos
                }

                String desc = vistaRepuestos.txtDesc.getText();
                String montoTexto = vistaRepuestos.txtPrecio.getText();
                BigDecimal montoPagado;
                String idProv = vistaRepuestos.txtCodeProv.getText();
                int stock = Integer.parseInt(vistaRepuestos.txtStock.getText());
                montoPagado = new BigDecimal(montoTexto);
                try {
                    montoPagado = new BigDecimal(montoTexto);
                } catch (NumberFormatException ec) {
                    JOptionPane.showMessageDialog(vistaRepuestos, "El monto ingresado no es válido.", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Terminar la función si el monto no es válido
                }
                int idProveedor = obtenerIdProvPorCode(idProv);

                if (desc.isEmpty() || montoTexto.isEmpty() || idProv.isEmpty()) {
                    JOptionPane.showMessageDialog(vistaRepuestos, "Todos los campos deben estar llenos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Terminar la función si hay campos vacíos
                }

                if (!validarIdProv(idProveedor)) {
                    JOptionPane.showMessageDialog(vistaRepuestos, "No se ha a encontrado ningun proveedor");
                    return;  // Terminar la función si la validación falla
                }

                modeloRepuestos.setDescripcion(desc);
                modeloRepuestos.setIdProveedor(idProveedor);
                modeloRepuestos.setPrecio(montoPagado);
                modeloRepuestos.setStock(stock);

                if (modeloRepuestos.AgRe(modeloRepuestos)) {
                    JOptionPane.showMessageDialog(vistaRepuestos, "Repuesto registrado");
                    limpiarCamposTexto();
                    cargarDatosTabla();
                } else {
                    JOptionPane.showMessageDialog(vistaRepuestos, "Error al registrar");
                }

            } catch (SQLException ex) {
                Logger.getLogger(cRepuestos.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        if (e.getSource() == vistaRepuestos.btnEliminar) {
            int filaSeleccionada = vistaRepuestos.tbProducts.getSelectedRow();
            if (filaSeleccionada >= 0) {
                int idRepuesto = Integer.parseInt(vistaRepuestos.tbProducts.getValueAt(filaSeleccionada, 0).toString());
                modeloRepuestos.setIdRepuesto(idRepuesto);

                if (modeloRepuestos.EliminarRep(modeloRepuestos)) {
                    JOptionPane.showMessageDialog(vistaRepuestos, "Repuesto eliminado exitosamente.");
                    cargarDatosTabla();
                    limpiarCamposTexto();
                } else {
                    JOptionPane.showMessageDialog(vistaRepuestos, "Error al eliminar el repuesto.");
                }
            } else {
                JOptionPane.showMessageDialog(vistaRepuestos, "Debe seleccionar un repuesto de la tabla.");
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
