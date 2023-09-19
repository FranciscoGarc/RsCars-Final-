package Modelo;

import Vista.pnlCitas;
import java.sql.*;

public class CbCitas {

    private int idRepuesto;
    private int idEstado;
    private int idServicio;
    private String Repuesto;
    private String Estado;
    private String Servicio;

    public int getIdRepuesto() {
        return idRepuesto;
    }

    public void setIdRepuesto(int idRepuesto) {
        this.idRepuesto = idRepuesto;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public String getRepuesto() {
        return Repuesto;
    }

    public void setRepuesto(String Repuesto) {
        this.Repuesto = Repuesto;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

    public String getServicio() {
        return Servicio;
    }

    public void setServicio(String Servicio) {
        this.Servicio = Servicio;
    }

    public void traerIdDeTbTipo(mRegistro modeloUsuario, NivelesUsuario modeloNivelesUsuario) {
        try {
            String query = "SELECT idtipo from tbTipoUsuarios  where tipo = ?";
            PreparedStatement addSeccion = conx.getConexion().prepareStatement(query);
            addSeccion.setString(1, modeloNivelesUsuario.getTipo());
            ResultSet resultado = addSeccion.executeQuery();

            while (resultado.next()) {
                modeloUsuario.setIdTipo(resultado.getInt("idTipo"));

            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    //Mostrar datos en Combobox
    public void llenarComboBoxEs(pnlCitas vistaCitas) {
        vistaCitas.Estado.removeAllItems();
        try {
            String query = "select estado from tbEstadoCita;";

            Statement statement = conx.getConexion().createStatement();
            ResultSet resultado = statement.executeQuery(query);

            while (resultado.next()) {
                vistaCitas.Estado.addItem(resultado.getString("estado"));
            }

        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    //Mostrar datos en Combobox
    public void llenarComboBoxSer(pnlCitas vistaCitas) {
        vistaCitas.cbServicio.removeAllItems();
        try {
            String query = "select descripcion from tbServicios;";

            Statement statement = conx.getConexion().createStatement();
            ResultSet resultado = statement.executeQuery(query);

            while (resultado.next()) {
                vistaCitas.cbServicio.addItem(resultado.getString("descripcion"));
            }

        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }
    //Mostrar datos en Combobox

    public void llenarComboBoxRe(pnlCitas vistaCitas) {
        vistaCitas.Repuesto.removeAllItems();
        try {
            String query = "select descripción from tbRepuestos;";

            Statement statement = conx.getConexion().createStatement();
            ResultSet resultado = statement.executeQuery(query);

            while (resultado.next()) {
                vistaCitas.Repuesto.addItem(resultado.getString("descripción"));
            }

        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }

}
