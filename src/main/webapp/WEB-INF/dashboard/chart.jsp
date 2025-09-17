<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../utilitaire/header.jsp" %>

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<div class="container mt-4">
    <h2>Produits Vendus</h2>

    <!-- Formulaire de filtre -->
    <form method="get" action="${pageContext.request.contextPath}/dashboard/produits-vendus" class="row g-3 mb-4">
        <div class="col-md-2">
            <label for="year" class="form-label">Année</label>
            <input type="number" name="year" id="year" class="form-control" value="${selectedYear}">
        </div>
        <div class="col-md-2">
            <label for="week" class="form-label">Semaine</label>
            <input type="number" name="week" id="week" class="form-control" value="${selectedWeek}">
        </div>
        <div class="col-md-2">
            <label for="month" class="form-label">Mois</label>
            <input type="number" name="month" id="month" class="form-control" value="${selectedMonth}">
        </div>
        <div class="col-md-2 align-self-end">
            <button type="submit" class="btn btn-primary">Filtrer</button>
        </div>
    </form>

    <!-- Chart pour la semaine -->
    <h4>Produits Vendus - Semaine ${selectedWeek}</h4>
    <canvas id="weekChart"></canvas>

    <!-- Chart pour le mois -->
    <h4 class="mt-5">Produits Vendus - Mois ${selectedMonth}</h4>
    <canvas id="monthChart"></canvas>
</div>

<script>
    // Données pour la semaine
    const weekLabels = [
        <c:forEach var="p" items="${produitsWeek}">
            '${p.intitule}',
        </c:forEach>
    ];

    const weekData = [
        <c:forEach var="p" items="${produitsWeek}">
            ${p.total_vendu},
        </c:forEach>
    ];

    const ctxWeek = document.getElementById('weekChart').getContext('2d');
    new Chart(ctxWeek, {
        type: 'bar',
        data: {
            labels: weekLabels,
            datasets: [{
                label: 'Quantité Vendue',
                data: weekData,
                backgroundColor: 'rgba(54, 162, 235, 0.6)',
                borderColor: 'rgba(54, 162, 235, 1)',
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: { display: false },
                title: { display: true, text: 'Produits Vendus par Semaine' }
            },
            scales: {
                y: { beginAtZero: true }
            }
        }
    });

    // Données pour le mois
    const monthLabels = [
        <c:forEach var="p" items="${produitsMonth}">
            '${p.intitule}',
        </c:forEach>
    ];

    const monthData = [
        <c:forEach var="p" items="${produitsMonth}">
            ${p.total_vendu},
        </c:forEach>
    ];

    const ctxMonth = document.getElementById('monthChart').getContext('2d');
    new Chart(ctxMonth, {
        type: 'bar',
        data: {
            labels: monthLabels,
            datasets: [{
                label: 'Quantité Vendue',
                data: monthData,
                backgroundColor: 'rgba(255, 99, 132, 0.6)',
                borderColor: 'rgba(255, 99, 132, 1)',
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: { display: false },
                title: { display: true, text: 'Produits Vendus par Mois' }
            },
            scales: {
                y: { beginAtZero: true }
            }
        }
    });
</script>

<%@ include file="../utilitaire/footer.jsp" %>
