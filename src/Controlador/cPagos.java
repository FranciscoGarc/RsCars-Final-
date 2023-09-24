/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

/**
 *
 * @author Fran
 */
import Modelo.cbPagos;
import Modelo.crypt;
import Modelo.mPagos;
import Vista.pnlPagos;
import Modelo.Valida;
import Modelo.conx;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
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

public class cPagos implements ActionListener, MouseListener {

    private mPagos modeloPagos;
    private pnlPagos vistaPagos;
    private cbPagos modeloCbPagos;

    public cPagos(mPagos modelPagos, pnlPagos vistPagos, cbPagos modeCbPagos) {
        this.modeloPagos = modelPagos;
        this.vistaPagos = vistPagos;
        this.modeloCbPagos = modeCbPagos;
        this.vistaPagos.btnRegistrar.addActionListener(this);
        vistaPagos.txtIdCita.setDocument(new Valida(4, "[0-9]*"));
        vistaPagos.txtMonto.setDocument(new Valida(8, "[0-9].*"));
        vistaPagos.txtDescripcion.setDocument(new Valida(20, "[a-zA-Z]*"));
        vistaPagos.txtSearch.getDocument().addDocumentListener(new DocumentListener() {
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
        modeloPagos.mostrarDatosEnTabla(vistaPagos.tbPagos);

    }

    private void buscarDatos() {
        String textoBusqueda = vistaPagos.txtSearch.getText();
        modeloPagos.buscarEnTabla(textoBusqueda, vistaPagos.tbPagos);
    }

    private boolean validarFecha(Date fechaSeleccionada) {
        Date fechaActual = new Date(); // Obtener la fecha actual
        return !fechaSeleccionada.before(fechaActual); // Si la fecha seleccionada no es anterior a la actual
    }

    private boolean validarIdCita(int idCita) {
        return idCita != -1; // Si idVehiculo es diferente a -1, se encontró un idVehiculo
    }

    private int obtenerIdProvPorCode(String codigo) throws SQLException {
        // Realiza la consulta SQL para obtener el idProveedor basado en el código
        String query = "SELECT idCita FROM tbCitas WHERE idCita = ?";
        PreparedStatement preparedStatement = conx.getConexion().prepareStatement(query);
        preparedStatement.setString(1, codigo);

        int idCita = -1;
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            idCita = resultSet.getInt("idCita");
        }

        resultSet.close();
        preparedStatement.close();

        return idCita;
    }

    private void limpiarCamposTexto() {
        vistaPagos.txtIdCita.setText("");
        vistaPagos.txtDescripcion.setText("");
        vistaPagos.txtMonto.setText("");
        vistaPagos.txtDescripcion.setText("");
        vistaPagos.date.setDatoFecha(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaPagos.btnRegistrar) {
            try {
                String idCita = vistaPagos.txtIdCita.getText();
                String montoTexto = vistaPagos.txtMonto.getText();
                BigDecimal montoPagado;
                String Metodo = vistaPagos.cbIdMetodo.getSelectedItem().toString();
                String descripcion = vistaPagos.txtDescripcion.getText();

                Date fechaSeleccionada = vistaPagos.date.getDatoFecha();

                if (idCita.isEmpty() || montoTexto.isEmpty() || Metodo.isEmpty() || descripcion.isEmpty() || fechaSeleccionada == null) {
                    JOptionPane.showMessageDialog(vistaPagos, "Todos los campos deben estar llenos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Terminar la función si hay campos vacíos
                }

                try {
                    montoPagado = new BigDecimal(montoTexto);
                } catch (NumberFormatException ec) {
                    JOptionPane.showMessageDialog(vistaPagos, "El monto ingresado no es válido.", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Terminar la función si el monto no es válido
                }

                // Verificar si la fecha seleccionada es nula
                if (fechaSeleccionada == null) {
                    JOptionPane.showMessageDialog(vistaPagos, "La fecha seleccionada no puede ser nula.");
                    return; // Detener la operación si la fecha es nula
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String fechaFormateada = dateFormat.format(fechaSeleccionada);
                montoPagado = new BigDecimal(montoTexto);

                int idCitaa = obtenerIdProvPorCode(idCita);
                if (!validarIdCita(idCitaa)) {
                    JOptionPane.showMessageDialog(vistaPagos, "No se encontro ninguna cita");
                    return;  // Terminar la función si la validación falla
                }

                // Validar fecha
                if (!validarFecha(fechaSeleccionada)) {
                    JOptionPane.showMessageDialog(vistaPagos, "La fecha seleccionada no es válida.");
                    return;  // Terminar la función si la validación falla
                }

                modeloCbPagos.setMetodo(Metodo);
                modeloPagos.setIdCita(idCitaa);
                modeloPagos.setMonto(montoPagado);
                modeloPagos.setObservacion(descripcion);
                modeloPagos.setFecha(fechaFormateada);

                modeloCbPagos.traerIdDeTbMetodos(modeloPagos, modeloCbPagos);

                if (modeloPagos.AgPa(modeloPagos, modeloCbPagos)) {
                    JOptionPane.showMessageDialog(vistaPagos, "Pago registrado");
                    limpiarCamposTexto();
                    cargarDatosTabla();
                } else {
                    JOptionPane.showMessageDialog(vistaPagos, "Error al registrar el pago");
                }

            } catch (SQLException ex) {
                Logger.getLogger(cPagos.class.getName()).log(Level.SEVERE, null, ex);
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
