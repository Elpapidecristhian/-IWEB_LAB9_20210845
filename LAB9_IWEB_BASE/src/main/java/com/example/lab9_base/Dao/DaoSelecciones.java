package com.example.lab9_base.Dao;

import com.example.lab9_base.Bean.Seleccion;

import java.sql.*;
import java.util.ArrayList;

public class DaoSelecciones extends DaoBase {

    public ArrayList<Seleccion> listarSelecciones() {
        ArrayList<Seleccion> selecciones = new ArrayList<>();
        String sql = "SELECT idSeleccion, nombre, tecnico, estadio_idEstadio FROM seleccion";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Seleccion seleccion = new Seleccion();
                seleccion.setIdSeleccion(rs.getInt("idSeleccion"));
                seleccion.setNombre(rs.getString("nombre"));
                seleccion.setTecnico(rs.getString("tecnico"));
                // No incluimos información del estadio en este ejemplo, pero puedes extenderlo si lo necesitas
                selecciones.add(seleccion);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return selecciones;
    }
}
