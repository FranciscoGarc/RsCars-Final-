/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.crypt;
import Modelo.mProveedor;
import Vista.pnlProveedores;
import Modelo.Valida;
import Modelo.conx;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.PreparedStatement;
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

public class cProveedores implements ActionListener, MouseListener {

    private mProveedor modeloProveedores;
    private pnlProveedores vistaProveedores;

    public cProveedores(mProveedor modeloProveedores, pnlProveedores vistaProveedores) {
        this.modeloProveedores = modeloProveedores;
        this.vistaProveedores = vistaProveedores;
        this.vistaProveedores.btnRegistrar.addActionListener(this);
        this.vistaProveedores.btnEliminar.addActionListener(this);
        this.vistaProveedores.btnActualizar.addActionListener(this);
        vistaProveedores.txtSearch.getDocument().addDocumentListener(new DocumentListener() {
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
        modeloProveedores.mostrarProveedoresEnTabla(vistaProveedores.tbDatosPb);

    }

    private void buscarDatos() {
        String textoBusqueda = vistaProveedores.txtSearch.getText();
        modeloProveedores.buscarProveedoresPorNombre(textoBusqueda, vistaProveedores.tbDatosPb);
    }

    private void limpiarCamposTexto() {
        vistaProveedores.txtName.setText("");
        vistaProveedores.txtCorreo.setText("");
        vistaProveedores.txtCode.setText("");
        vistaProveedores.txtTelefono.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaProveedores.btnRegistrar) {
            String Nombre = vistaProveedores.txtName.getText();
            String Telefono = vistaProveedores.txtTelefono.getText();
            String Coreeo = vistaProveedores.txtTelefono.getText();
            int code = Integer.parseInt(vistaProveedores.txtCode.getText());
            modeloProveedores.setNombre(Nombre);
            modeloProveedores.setTelefono(Telefono);
            modeloProveedores.setCorreo(Coreeo);
            modeloProveedores.setCodigo(code);
            modeloProveedores.AgPro(modeloProveedores);
            JOptionPane.showMessageDialog(vistaProveedores, "Proveedor registrado");
            limpiarCamposTexto();
            cargarDatosTabla();

        }
        if (e.getSource() == vistaProveedores.btnEliminar) {
            int filaSeleccionada = vistaProveedores.tbDatosPb.getSelectedRow();
            if (filaSeleccionada >= 0) {
                int idRepuesto = Integer.parseInt(vistaProveedores.tbDatosPb.getValueAt(filaSeleccionada, 0).toString());
                modeloProveedores.setIdProveedor(idRepuesto);

                if (modeloProveedores.EliminarRep(modeloProveedores)) {
                    JOptionPane.showMessageDialog(vistaProveedores, "Proveedor eliminado exitosamente.");
                    cargarDatosTabla();
                    limpiarCamposTexto();
                } else {
                    JOptionPane.showMessageDialog(vistaProveedores, "Error al eliminar el proveedor.");
                }
            } else {
                JOptionPane.showMessageDialog(vistaProveedores, "Debe seleccionar un proveedor de la tabla.");
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
