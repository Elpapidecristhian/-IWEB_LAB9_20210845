package com.example.lab9_base.Dao;

import com.example.lab9_base.Bean.Arbitro;
import com.example.lab9_base.Bean.Estadio;
import com.example.lab9_base.Bean.Partido;
import com.example.lab9_base.Bean.Seleccion;

import java.sql.*;
import java.util.ArrayList;

public class DaoPartidos extends DaoBase {

    public ArrayList<Partido> listarPartidos() {
        ArrayList<Partido> partidos = new ArrayList<>();
        String sql = "SELECT p.idPartido, p.fecha, p.numeroJornada, " +
                "sLocal.nombre AS seleccionLocal, sVisitante.nombre AS seleccionVisitante, " +
                "e.nombre AS estadio, a.nombre AS arbitro " +
                "FROM partido p " +
                "JOIN seleccion sLocal ON p.seleccionLocal = sLocal.idSeleccion " +
                "JOIN seleccion sVisitante ON p.seleccionVisitante = sVisitante.idSeleccion " +
                "JOIN arbitro a ON p.arbitro = a.idArbitro " +
                "JOIN estadio e ON sLocal.estadio_idEstadio = e.idEstadio";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Partido partido = new Partido();
                partido.setIdPartido(rs.getInt("idPartido"));
                partido.setFecha(rs.getString("fecha"));
                partido.setNumeroJornada(rs.getInt("numeroJornada"));

                Seleccion seleccionLocal = new Seleccion();
                seleccionLocal.setNombre(rs.getString("seleccionLocal"));
                partido.setSeleccionLocal(seleccionLocal);

                Seleccion seleccionVisitante = new Seleccion();
                seleccionVisitante.setNombre(rs.getString("seleccionVisitante"));
                partido.setSeleccionVisitante(seleccionVisitante);

                Estadio estadio = new Estadio();
                estadio.setNombre(rs.getString("estadio"));
                partido.setEstadio(estadio);

                Arbitro arbitro = new Arbitro();
                arbitro.setNombre(rs.getString("arbitro"));
                partido.setArbitro(arbitro);

                partidos.add(partido);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return partidos;
    }

    public boolean crearPartido(Partido partido) {
        String sql = "INSERT INTO partido (fecha, numeroJornada, seleccionLocal, seleccionVisitante, arbitro) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, partido.getFecha());
            pstmt.setInt(2, partido.getNumeroJornada());
            pstmt.setInt(3, partido.getSeleccionLocal().getIdSeleccion());
            pstmt.setInt(4, partido.getSeleccionVisitante().getIdSeleccion());
            pstmt.setInt(5, partido.getArbitro().getIdArbitro());

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Partido> buscarPartidos(String criterio) {
        ArrayList<Partido> partidos = new ArrayList<>();
        String sql = "SELECT p.idPartido, p.fecha, p.numeroJornada, " +
                "sLocal.nombre AS seleccionLocal, sVisitante.nombre AS seleccionVisitante, " +
                "e.nombre AS estadio, a.nombre AS arbitro " +
                "FROM partido p " +
                "JOIN seleccion sLocal ON p.seleccionLocal = sLocal.idSeleccion " +
                "JOIN seleccion sVisitante ON p.seleccionVisitante = sVisitante.idSeleccion " +
                "JOIN arbitro a ON p.arbitro = a.idArbitro " +
                "JOIN estadio e ON sLocal.estadio_idEstadio = e.idEstadio " +
                "WHERE sLocal.nombre LIKE ? OR sVisitante.nombre LIKE ? OR " +
                "a.nombre LIKE ? OR p.fecha LIKE ?";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String wildcard = "%" + criterio + "%";
            pstmt.setString(1, wildcard);
            pstmt.setString(2, wildcard);
            pstmt.setString(3, wildcard);
            pstmt.setString(4, wildcard);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Partido partido = new Partido();
                    partido.setIdPartido(rs.getInt("idPartido"));
                    partido.setFecha(rs.getString("fecha"));
                    partido.setNumeroJornada(rs.getInt("numeroJornada"));

                    Seleccion seleccionLocal = new Seleccion();
                    seleccionLocal.setNombre(rs.getString("seleccionLocal"));
                    partido.setSeleccionLocal(seleccionLocal);

                    Seleccion seleccionVisitante = new Seleccion();
                    seleccionVisitante.setNombre(rs.getString("seleccionVisitante"));
                    partido.setSeleccionVisitante(seleccionVisitante);

                    Estadio estadio = new Estadio();
                    estadio.setNombre(rs.getString("estadio"));
                    partido.setEstadio(estadio);

                    Arbitro arbitro = new Arbitro();
                    arbitro.setNombre(rs.getString("arbitro"));
                    partido.setArbitro(arbitro);

                    partidos.add(partido);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return partidos;
    }
}
