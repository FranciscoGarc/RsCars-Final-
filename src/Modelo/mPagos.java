/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Fran
 */
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class mPagos {

    private int idMetodo;
    private int idPago;
    private int idCita;
    private BigDecimal monto;
    private String fecha;
    private String observacion;

    public int getIdMetodo() {
        return idMetodo;
    }

    public void setIdMetodo(int idMetodo) {
        this.idMetodo = idMetodo;
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public int getIdCita() {
        return idCita;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public boolean AgPa(mPagos pagosModelo, cbPagos modeloCbPagos) {

        try {

            PreparedStatement addCliente = conx.getConexion().prepareStatement("INSERT INTO tbPagos (idCita, montoPagado, fechaPago, observaciones, idFormaPago) VALUES  (?, ?, ?, ?, ?);");

            addCliente.setInt(1, pagosModelo.getIdCita());
            addCliente.setBigDecimal(2, pagosModelo.getMonto());
            addCliente.setString(3, pagosModelo.getFecha());
            addCliente.setString(4, pagosModelo.getObservacion());
            addCliente.setInt(5, pagosModelo.getIdMetodo());

            addCliente.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public void mostrarDatosEnTabla(JTable tbDatosCl) {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacemos que todas las celdas no sean editables
            }
        };
        modelo.setColumnIdentifiers(new Object[]{"ID Cita", "Fecha y Hora", "Repuesto", "Servicio", "Costo", "Estado", "Placa Vehículo", "Monto Pagado", "Cambio", "Forma de Pago"});
        tbDatosCl.setModel(modelo);

        try {
            Connection conexion = conx.getConexion();
            String sql = "SELECT C.idCita, C.fechaHora, R.descripción AS Repuesto, S.descripcion AS Servicio, S.costo, E.estado, V.placa, P.montoPagado AS monto, (P.montoPagado - S.costo) AS cambio, FP.descripcion AS FormaPago "
                    + "FROM tbCitas C "
                    + "JOIN tbMecanicos M ON C.idMecanico = M.idMecanico "
                    + "LEFT JOIN tbRepuestos R ON C.idRepuesto = R.idRepuesto "
                    + "LEFT JOIN tbServicios S ON C.idServicio = S.idServicio "
                    + "JOIN tbVehiculos V ON C.idVehiculo = V.idVehiculo "
                    + "JOIN tbClientes VC ON V.idCliente = VC.idCliente "
                    + "JOIN tbEstadoCita E ON C.idEstado = E.idEstado "
                    + "JOIN tbPagos P ON C.idCita = P.idCita "
                    + "JOIN tbFormasPago FP ON P.idFormaPago = FP.idFormaPago;";

            PreparedStatement statement = conexion.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                modelo.addRow(new Object[]{rs.getInt("idCita"), rs.getString("fechaHora"), rs.getString("Repuesto"), rs.getString("Servicio"), rs.getDouble("costo"), rs.getString("estado"), rs.getString("placa"), rs.getDouble("monto"), rs.getDouble("cambio"), rs.getString("FormaPago")});
            }
            conexion.close();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    public void buscarEnTabla(String criterio, JTable tbDatosCl) {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacemos que todas las celdas no sean editables
            }
        };
        modelo.setColumnIdentifiers(new Object[]{"ID Cita", "Fecha y Hora", "Repuesto", "Servicio", "Costo", "Estado", "Placa Vehículo", "Monto Pagado", "Cambio", "Forma de Pago"});
        tbDatosCl.setModel(modelo);

        try {
            Connection conexion = conx.getConexion();
            String sql = "SELECT C.idCita, C.fechaHora, R.descripción AS Repuesto, S.descripcion AS Servicio, S.costo, E.estado, V.placa, P.montoPagado AS monto, (P.montoPagado - S.costo) AS cambio, FP.descripcion AS FormaPago "
                    + "FROM tbCitas C "
                    + "JOIN tbMecanicos M ON C.idMecanico = M.idMecanico "
                    + "LEFT JOIN tbRepuestos R ON C.idRepuesto = R.idRepuesto "
                    + "LEFT JOIN tbServicios S ON C.idServicio = S.idServicio "
                    + "JOIN tbVehiculos V ON C.idVehiculo = V.idVehiculo "
                    + "JOIN tbClientes VC ON V.idCliente = VC.idCliente "
                    + "JOIN tbEstadoCita E ON C.idEstado = E.idEstado "
                    + "JOIN tbPagos P ON C.idCita = P.idCita "
                    + "JOIN tbFormasPago FP ON P.idFormaPago = FP.idFormaPago "
                    + "WHERE V.placa LIKE ? OR E.estado LIKE ? OR R.descripcion LIKE ? OR S.descripcion LIKE ? OR M.nombre LIKE ?";

            PreparedStatement statement = conexion.prepareStatement(sql);
            for (int i = 1; i <= 5; i++) {
                statement.setString(i, "%" + criterio + "%");
            }

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                modelo.addRow(new Object[]{rs.getInt("idCita"), rs.getString("fechaHora"), rs.getString("Repuesto"), rs.getString("Servicio"), rs.getDouble("costo"), rs.getString("estado"), rs.getString("placa"), rs.getDouble("monto"), rs.getDouble("cambio"), rs.getString("FormaPago")});
            }
            conexion.close();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }

}
