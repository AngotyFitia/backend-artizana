<%@include file="../utilitaire/header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/dataTables.bootstrap4.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">

<div class="col-12">
    <h2 class="mb-2 page-title">Liste des produits à valider </h2>

        <c:if test="${not empty success}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                ${success}
<button type="button" class="close" data-dismiss="alert" aria-label="Close">
  <span aria-hidden="true">&times;</span>
</button>
            </div>
        </c:if>

        <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                ${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>


    <div class="row my-4">
        <div class="col-md-12">
            <div class="card shadow">
                <div class="card-body">
                    <!-- table -->
                    <table class="table datatables" id="dataTable-1">
                        <thead>
                            <tr>
                                <th>Intitulé</th>
                                <th>Catégorie</th>
                                <th>Société</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <liste:forEach items="${produits}" var="cat">
                                <tr>
                                    <td>${cat.intitule}</td>
                                    <td>${cat.categorie.intitule}</td>
                                    <td>${cat.societe.nom}</td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/api/web/produit/valider/${cat.idProduit}" 
                                        class="btn btn-sm btn-success">
                                        <i class="bi bi-check-circle"></i> Valider
                                        </a>
                                    </td>

                                </tr>
                            </liste:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div> <!-- simple table -->
    </div> <!-- end section -->
</div> <!-- .col-12 -->

<%@include file="../utilitaire/footer.jsp" %>