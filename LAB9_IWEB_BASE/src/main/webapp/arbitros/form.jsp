<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Crear Partido</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-4">
    <h1>Crear Partido</h1>
    <form action="${pageContext.request.contextPath}/PartidoServlet?action=guardar" method="post">
        <div class="form-group">
            <label for="fecha">Fecha</label>
            <input type="date" class="form-control" id="fecha" name="fecha" required>
        </div>
        <div class="form-group">
            <label for="jornada">Número de Jornada</label>
            <input type="number" class="form-control" id="jornada" name="jornada" required>
        </div>
        <div class="form-group">
            <label for="local">Selección Local</label>
            <select class="form-control" id="local" name="local" required>
                <c:forEach var="seleccion" items="${selecciones}">
                    <option value="${seleccion.idSeleccion}">${seleccion.nombre}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label for="visitante">Selección Visitante</label>
            <select class="form-control" id="visitante" name="visitante" required>
                <c:forEach var="seleccion" items="${selecciones}">
                    <option value="${seleccion.idSeleccion}">${seleccion.nombre}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label for="arbitro">Árbitro</label>
            <select class="form-control" id="arbitro" name="arbitro" required>
                <c:forEach var="arbitro" items="${arbitros}">
                    <option value="${arbitro.idArbitro}">${arbitro.nombre}</option>
                </c:forEach>
            </select>
        </div>
        <button type="submit" class="btn btn-primary">Guardar</button>
    </form>
</div>
</body>
</html>
