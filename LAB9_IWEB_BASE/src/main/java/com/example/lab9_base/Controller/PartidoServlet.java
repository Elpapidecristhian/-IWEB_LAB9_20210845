package com.example.lab9_base.Controller;

import com.example.lab9_base.Bean.Partido;
import com.example.lab9_base.Bean.Seleccion;
import com.example.lab9_base.Bean.Arbitro;
import com.example.lab9_base.Dao.DaoPartidos;
import com.example.lab9_base.Dao.DaoSelecciones;
import com.example.lab9_base.Dao.DaoArbitros;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "PartidoServlet", urlPatterns = {"/PartidoServlet"})
public class PartidoServlet extends HttpServlet {

    DaoPartidos daoPartidos = new DaoPartidos();
    DaoSelecciones daoSelecciones = new DaoSelecciones();
    DaoArbitros daoArbitros = new DaoArbitros();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");

        switch (action) {
            case "lista":
                ArrayList<Partido> partidos = daoPartidos.listarPartidos();
                request.setAttribute("partidos", partidos);
                RequestDispatcher view = request.getRequestDispatcher("/partidos/list.jsp");
                view.forward(request, response);
                break;

            case "crear":
                ArrayList<Seleccion> selecciones = daoSelecciones.listarSelecciones();
                ArrayList<Arbitro> arbitros = daoArbitros.listarArbitros();
                request.setAttribute("selecciones", selecciones);
                request.setAttribute("arbitros", arbitros);
                RequestDispatcher form = request.getRequestDispatcher("/partidos/form.jsp");
                form.forward(request, response);
                break;

            case "buscar":
                String criterio = request.getParameter("criterio");
                ArrayList<Partido> resultados = daoPartidos.buscarPartidos(criterio);
                request.setAttribute("partidos", resultados);
                RequestDispatcher buscador = request.getRequestDispatcher("/partidos/list.jsp");
                buscador.forward(request, response);
                break;

            default:
                response.sendRedirect(request.getContextPath() + "/PartidoServlet?action=lista");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("guardar".equals(action)) {
            String fecha = request.getParameter("fecha");
            int numeroJornada = Integer.parseInt(request.getParameter("jornada"));
            int idSeleccionLocal = Integer.parseInt(request.getParameter("local"));
            int idSeleccionVisitante = Integer.parseInt(request.getParameter("visitante"));
            int idArbitro = Integer.parseInt(request.getParameter("arbitro"));

            if (idSeleccionLocal == idSeleccionVisitante) {
                request.setAttribute("error", "La selección local no puede ser igual a la visitante.");
                reenviarFormulario(request, response);
                return;
            }

            Partido partido = new Partido();
            partido.setFecha(fecha);
            partido.setNumeroJornada(numeroJornada);

            Seleccion seleccionLocal = new Seleccion();
            seleccionLocal.setIdSeleccion(idSeleccionLocal);
            partido.setSeleccionLocal(seleccionLocal);

            Seleccion seleccionVisitante = new Seleccion();
            seleccionVisitante.setIdSeleccion(idSeleccionVisitante);
            partido.setSeleccionVisitante(seleccionVisitante);

            Arbitro arbitro = new Arbitro();
            arbitro.setIdArbitro(idArbitro);
            partido.setArbitro(arbitro);

            boolean exito = daoPartidos.crearPartido(partido);
            if (exito) {
                response.sendRedirect(request.getContextPath() + "/PartidoServlet?action=lista");
            } else {
                request.setAttribute("error", "Error al guardar el partido. Inténtelo nuevamente.");
                reenviarFormulario(request, response);
            }
        }
    }

    private void reenviarFormulario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Seleccion> selecciones = daoSelecciones.listarSelecciones();
        ArrayList<Arbitro> arbitros = daoArbitros.listarArbitros();
        request.setAttribute("selecciones", selecciones);
        request.setAttribute("arbitros", arbitros);
        RequestDispatcher form = request.getRequestDispatcher("/partidos/form.jsp");
        form.forward(request, response);
    }
}
