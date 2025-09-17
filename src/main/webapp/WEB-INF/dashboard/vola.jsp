<%@include file="../utilitaire/header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/dataTables.bootstrap4.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">

<div class="col-12">
    <h2 class="mb-2 page-title">Chiffre d'affaire</h2>

    <!-- Formulaire filtre -->
    <div class="mb-3">
        <form method="get" action="${pageContext.request.contextPath}/dashboard/vola" class="form-inline">
            <label for="date" class="mr-2">Date :</label>
            <input type="date" id="date" name="date" class="form-control mr-2" 
                   value="${selectedDate}">
            <label for="month" class="mr-2">Mois :</label>
            <input type="number" id="month" name="month" class="form-control mr-2" 
                   value="${selectedMonth}" min="1" max="12">
            <label for="year" class="mr-2">Année :</label>
            <input type="number" id="year" name="year" class="form-control mr-2" 
                   value="${selectedYear}">
            <button type="submit" class="btn btn-primary">
                <i class="bi bi-funnel"></i> Filtrer
            </button>
        </form>
    </div>

    <!-- Par jour -->
    <h4>Par Jour</h4>
    <table class="table datatables">
        <thead>
            <tr><th>Date</th><th>Vola</th></tr>
        </thead>
        <tbody>
            <c:forEach items="${volaByDay}" var="v">
                <tr>
                    <td>${v.date}</td>
                    <td>${v.vola}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <!-- Par mois -->
    <h4>Par Mois</h4>
    <table class="table datatables">
        <thead>
            <tr><th>Année</th><th>Mois</th><th>Vola</th></tr>
        </thead>
        <tbody>
            <c:forEach items="${volaByMonth}" var="v">
                <tr>
                    <td>${v.annee}</td>
                    <td>${v.mois}</td>
                    <td>${v.vola}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <!-- Par année -->
    <h4>Par Année</h4>
    <table class="table datatables">
        <thead>
            <tr><th>Année</th><th>Vola</th></tr>
        </thead>
        <tbody>
            <c:forEach items="${volaByYear}" var="v">
                <tr>
                    <td>${v.annee}</td>
                    <td>${v.vola}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

<%@include file="../utilitaire/footer.jsp" %>
