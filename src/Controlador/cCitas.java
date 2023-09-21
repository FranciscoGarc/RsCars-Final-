/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.CbCitas;
import Modelo.crypt;
import Modelo.mCitas;
import Vista.pnlCitas;
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

public class cCitas implements ActionListener, MouseListener {

    private mCitas modeloCitas;
    private pnlCitas vistaCitas;
    private CbCitas modeloCbCitas;

    public cCitas(mCitas modelCitas, pnlCitas vistCitas, CbCitas modeCbCitas) {
        this.modeloCitas = modelCitas;
        this.vistaCitas = vistCitas;
        this.modeloCbCitas = modeCbCitas;
        this.vistaCitas.btnRegistrar.addActionListener(this);
        this.vistaCitas.btnActua.addActionListener(this);
        vistaCitas.txtSearch.getDocument().addDocumentListener(new DocumentListener() {
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
        modeloCitas.mostrarDatosEnTabla(vistaCitas.tbCitas);

    }

    private void buscarDatos() {
        String textoBusqueda = vistaCitas.txtSearch.getText();
        modeloCitas.buscarEnTabla(textoBusqueda, vistaCitas.tbCitas);
    }

    private int obtenerIdMecanicoPorUsuario(String usuario) throws SQLException {
        // Realiza la consulta SQL para obtener el idCliente basado en el usuario
        String query = "SELECT M.idMecanico FROM tbMecanicos M "
                + "INNER JOIN tbUsuarios U ON M.idUsuario = U.idUsuario "
                + "WHERE U.usuario = ?";
        PreparedStatement preparedStatement = conx.getConexion().prepareStatement(query);
        preparedStatement.setString(1, usuario);

        int idCliente = -1;
        if (preparedStatement.execute()) {
            if (preparedStatement.getResultSet().next()) {
                idCliente = preparedStatement.getResultSet().getInt("idMecanico");
            }
        }
        return idCliente;
    }

    private void limpiarCamposTexto() {
        vistaCitas.txtMecanico.setText("");
        vistaCitas.txtPlaca.setText("");
        vistaCitas.date.setDatoFecha(null);
    }

    private int obtenerIdVehiculoPorPlaca(String placa) throws SQLException {
        // Realiza la consulta SQL para obtener el idCliente basado en el usuario
        String query = "SELECT V.idVehiculo FROM tbVehiculos V "
                + "WHERE V.placa = ?";
        PreparedStatement preparedStatement = conx.getConexion().prepareStatement(query);
        preparedStatement.setString(1, placa);

        int idVehiculo = -1;
        if (preparedStatement.execute()) {
            if (preparedStatement.getResultSet().next()) {
                idVehiculo = preparedStatement.getResultSet().getInt("idVehiculo");
            }
        }
        return idVehiculo;
    }

    private boolean validarFecha(Date fechaSeleccionada) {
        Date fechaActual = new Date(); // Obtener la fecha actual
        return !fechaSeleccionada.before(fechaActual); // Si la fecha seleccionada no es anterior a la actual
    }

    private boolean validarIdVehiculo(int idVehiculo) {
        return idVehiculo != -1; // Si idVehiculo es diferente a -1, se encontró un idVehiculo
    }

    private boolean validarIdMecanico(int idMecanico) {
        return idMecanico != -1; // Si idMecanico es diferente a -1, se encontró un idMecanico
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaCitas.btnRegistrar) {
            String idVehi = vistaCitas.txtPlaca.getText();
            String idSer = vistaCitas.cbServicio.getSelectedItem().toString();
            String idRepuesto = vistaCitas.Repuesto.getSelectedItem().toString();
            String idEstado = vistaCitas.Estado.getSelectedItem().toString();
            String idMeca = vistaCitas.txtMecanico.getText();
            Date fechaSeleccionada = vistaCitas.date.getDatoFecha();
            // Verificar si la fecha seleccionada es nula
            if (fechaSeleccionada == null) {
                JOptionPane.showMessageDialog(vistaCitas, "La fecha seleccionada no puede ser nula.");
                return; // Detener la operación si la fecha es nula
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String fechaFormateada = dateFormat.format(fechaSeleccionada);

            try {

                // Validar idVehiculo
                int idVehiculo = obtenerIdVehiculoPorPlaca(idVehi);
                if (!validarIdVehiculo(idVehiculo)) {
                    JOptionPane.showMessageDialog(vistaCitas, "El idVehiculo no es válido.");
                    return;  // Terminar la función si la validación falla
                }

                // Validar idMecanico
                int idMecanico = obtenerIdMecanicoPorUsuario(idMeca);
                if (!validarIdMecanico(idMecanico)) {
                    JOptionPane.showMessageDialog(vistaCitas, "El idMecanico no es válido.");
                    return;  // Terminar la función si la validación falla
                }

                // Validar fecha
                if (!validarFecha(fechaSeleccionada)) {
                    JOptionPane.showMessageDialog(vistaCitas, "La fecha seleccionada no es válida.");
                    return;  // Terminar la función si la validación falla
                }

                modeloCbCitas.setEstado(idEstado);
                modeloCbCitas.setRepuesto(idRepuesto);
                modeloCbCitas.setServicio(idSer);
                modeloCitas.setFecha(fechaFormateada);
                modeloCitas.setIdMecanico(idMecanico);
                modeloCitas.setIdVehiculo(idVehiculo);

                modeloCbCitas.traerIdDeTbServicio(modeloCitas, modeloCbCitas);
                modeloCbCitas.traerIdDeTbRepuesto(modeloCitas, modeloCbCitas);
                modeloCbCitas.traerIdDeTbEstado(modeloCitas, modeloCbCitas);

                modeloCitas.AgCi(modeloCitas, modeloCbCitas);

                JOptionPane.showMessageDialog(vistaCitas, "Cita registrada");
                limpiarCamposTexto();
                cargarDatosTabla();

            } catch (SQLException ex) {
                Logger.getLogger(cCitas.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        if (e.getSource() == vistaCitas.btnActua) {
            int filaSeleccionada = vistaCitas.tbCitas.getSelectedRow();

            if (filaSeleccionada >= 0) {
                int idCita = Integer.parseInt(vistaCitas.tbCitas.getValueAt(filaSeleccionada, 0).toString());
                String idEstado = vistaCitas.Estado.getSelectedItem().toString();

                modeloCbCitas.setEstado(idEstado);
                modeloCitas.setIdCita(idCita);

                modeloCbCitas.traerIdDeTbEstado(modeloCitas, modeloCbCitas);
                modeloCitas.ActCi(modeloCitas, modeloCbCitas);

                JOptionPane.showMessageDialog(vistaCitas, "Cita actualizada");
                limpiarCamposTexto();
                cargarDatosTabla();

            } else {
                JOptionPane.showMessageDialog(vistaCitas, "Debe seleccionar una cita en la tabla.");
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e
    ) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mousePressed(MouseEvent e
    ) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseReleased(MouseEvent e
    ) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseEntered(MouseEvent e
    ) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseExited(MouseEvent e
    ) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
