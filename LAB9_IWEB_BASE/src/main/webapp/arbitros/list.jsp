<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lista de Partidos</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-4">
    <h1 class="mb-4">Lista de Partidos</h1>

    <!-- Botón para crear un nuevo partido -->
    <div class="text-right mb-3">
        <a href="${pageContext.request.contextPath}/PartidoServlet?action=crear" class="btn btn-primary">Crear Partido</a>
    </div>

    <!-- Tabla para mostrar los partidos -->
    <table class="table table-bordered table-hover">
        <thead class="thead-dark">
        <tr>
            <th>#</th>
            <th>Jornada</th>
            <th>Fecha</th>
            <th>Selección Local</th>
            <th>Selección Visitante</th>
            <th>Estadio</th>
            <th>Árbitro</th>
        </tr>
        </thead>
        <tbody>
        <%
            java.util.List<com.example.lab9_base.Bean.Partido> partidos =
                    (java.util.List<com.example.lab9_base.Bean.Partido>) request.getAttribute("partidos");

            if (partidos != null && !partidos.isEmpty()) {
                for (com.example.lab9_base.Bean.Partido partido : partidos) {
        %>
        <tr>
            <td><%= partido.getIdPartido() %></td>
            <td><%= partido.getNumeroJornada() %></td>
            <td><%= partido.getFecha() %></td>
            <td><%= partido.getSeleccionLocal().getNombre() %></td>
            <td><%= partido.getSeleccionVisitante().getNombre() %></td>
            <td><%= partido.getEstadio().getNombre() %></td>
            <td><%= partido.getArbitro().getNombre() %></td>
        </tr>
        <%
            }
        } else {
        %>
        <tr>
            <td colspan="7" class="text-center">No hay partidos registrados.</td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>
