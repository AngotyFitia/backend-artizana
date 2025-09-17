<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../utilitaire/header.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/dataTables.bootstrap4.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2 class="mb-2 page-title">Liste des commandes à valider </h2>

<table class="table table-bordered">
    <thead>
        <tr>
            <th>ID</th>
            <th>Utilisateur</th>
            <th>Société</th>
            <th>Date</th>
            <th>Total</th>
            <th>Action</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="facture" items="${factures}">
            <tr>
                <td>${facture.idFacture}</td>
                <td>${facture.utilisateur.nom} </td>
                <td>${facture.societe.nom}</td>
                <td>${facture.date}</td>
                <td>${facture.total}</td>
                <td>
                    <a href="/api/web/factures/valider?id=${facture.idFacture}" class="btn btn-success btn-sm">
                        Valider
                    </a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<!-- Pagination -->
<nav aria-label="Page navigation example">
    <ul class="pagination justify-content-center">
        <c:forEach begin="1" end="${totalPages}" var="i">
            <li class="page-item ${i == currentPage ? 'active' : ''}">
                <a class="page-link" href="?page=${i}&size=${pageSize}">${i}</a>
            </li>
        </c:forEach>
    </ul>
</nav>

<!-- Messages de succès / erreur -->
<c:if test="${not empty success}">
    <div class="alert alert-success">${success}</div>
</c:if>
<c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
</c:if>


<%@ include file="../utilitaire/footer.jsp" %>
