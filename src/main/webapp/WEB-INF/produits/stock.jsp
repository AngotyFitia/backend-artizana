<%@include file="../utilitaire/header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../utilitaire/header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<div class="col-12">
    <h2 class="mb-2 page-title">Ajouter au stock</h2>

    <c:if test="${not empty success}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            ${success}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <c:if test="${not empty error}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            ${error}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <div class="card shadow">
        <div class="card-body">
            <form action="<%= request.getContextPath() %>/api/web/stock-ajout" method="post">
                <input type="hidden" name="idProduit" value="${idProduit}" />

                <div class="mb-3">
                    <label for="quantite" class="form-label">Quantité à ajouter</label>
                    <input type="number" class="form-control" id="quantite" name="quantite" min="1" required>
                </div>

                <button type="submit" class="btn btn-primary">
                    <i class="bi bi-plus-circle"></i> Ajouter
                </button>
            </form>
        </div>
    </div>
</div>

<%@ include file="../utilitaire/footer.jsp" %>

<%@include file="../utilitaire/footer.jsp" %>