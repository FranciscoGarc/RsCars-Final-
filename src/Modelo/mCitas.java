/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Fran
 */
public class mCitas {

    public void mostrarDatosEnTabla(JTable tbDatosCl) {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacemos que todas las celdas no sean editables
            }
        };
        modelo.setColumnIdentifiers(new Object[]{"ID Cita", "Fecha y Hora", "Nombre Mecánico", "Apellido Mecánico", "DUI", "Descripción Repuesto", "Descripción Servicio", "Estado", "Marca Vehículo", "Modelo Vehículo", "Placa Vehículo"});
        tbDatosCl.setModel(modelo);

        try {
            Connection conexion = conx.getConexion();
            String sql = "SELECT C.idcita, C.fechaHora, M.nombre, M.apellido, M.dui, R.descripción AS descripcion_repuesto, S.descripcion AS descripcion_servicio, E.estado, V.marca, V.modelo, V.placa "
                    + "FROM tbCitas C "
                    + "JOIN tbMecanicos M ON C.idMecanico = M.idMecanico "
                    + "LEFT JOIN tbRepuestos R ON C.idRepuesto = R.idRepuesto "
                    + "LEFT JOIN tbServicios S ON C.idServicio = S.idServicio "
                    + "JOIN tbVehiculos V ON C.idVehiculo = V.idVehiculo "
                    + "JOIN tbEstadoCita E ON C.idEstado = E.idEstado;";

            PreparedStatement statement = conexion.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                modelo.addRow(new Object[]{rs.getInt("idcita"), rs.getString("fechaHora"), rs.getString("nombre"), rs.getString("apellido"), rs.getString("dui"), rs.getString("descripcion_repuesto"), rs.getString("descripcion_servicio"), rs.getString("estado"), rs.getString("marca"), rs.getString("modelo"), rs.getString("placa")});
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
    modelo.setColumnIdentifiers(new Object[]{"ID Cita", "Fecha y Hora", "Nombre Mecánico", "Apellido Mecánico", "DUI", "Descripción Repuesto", "Descripción Servicio", "Estado", "Marca Vehículo", "Modelo Vehículo", "Placa Vehículo"});
    tbDatosCl.setModel(modelo);

    try {
        Connection conexion = conx.getConexion();
        String sql = "SELECT C.idcita, C.fechaHora, M.nombre, M.apellido, M.dui, R.descripción AS descripcion_repuesto, S.descripcion AS descripcion_servicio, E.estado, V.marca, V.modelo, V.placa "
                + "FROM tbCitas C "
                + "JOIN tbMecanicos M ON C.idMecanico = M.idMecanico "
                + "LEFT JOIN tbRepuestos R ON C.idRepuesto = R.idRepuesto "
                + "LEFT JOIN tbServicios S ON C.idServicio = S.idServicio "
                + "JOIN tbVehiculos V ON C.idVehiculo = V.idVehiculo "
                + "JOIN tbEstadoCita E ON C.idEstado = E.idEstado "
                + "WHERE V.placa LIKE ? OR E.estado LIKE ? OR S.descripcion LIKE ? OR R.descripción LIKE ? OR M.nombre LIKE ?";

        PreparedStatement statement = conexion.prepareStatement(sql);
        for (int i = 1; i <= 5; i++) {
            statement.setString(i, "%" + criterio + "%");
        }

        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            modelo.addRow(new Object[]{rs.getInt("idcita"), rs.getString("fechaHora"), rs.getString("nombre"), rs.getString("apellido"), rs.getString("dui"), rs.getString("descripcion_repuesto"), rs.getString("descripcion_servicio"), rs.getString("estado"), rs.getString("marca"), rs.getString("modelo"), rs.getString("placa")});
        }
        conexion.close();
    } catch (SQLException ex) {
        System.out.println(ex.toString());
    }
}
}
