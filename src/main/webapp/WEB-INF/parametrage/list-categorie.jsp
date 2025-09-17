<%@include file="../utilitaire/header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/dataTables.bootstrap4.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">

<div class="col-12">
    <h2 class="mb-2 page-title">Liste des Catégories</h2>


    <div class="mb-3">
        <button class="btn btn-success">
            <i class="bi bi-plus-circle"></i> Ajouter
        </button>
    </div>

    <div class="row my-4">
        <div class="col-md-12">
            <div class="card shadow">
                <div class="card-body">
                    <!-- table -->
                    <table class="table datatables" id="dataTable-1">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Intitulé</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <liste:forEach items="${categories}" var="cat">
                                <tr>
                                    <td>${cat.idCategorie}</td>
                                    <td>${cat.intitule}</td>
                                    <td>

                                        <button class="btn btn-sm btn-primary">
                                            <i class="bi bi-pencil-square"></i> Modifier
                                        </button>

                                        <button class="btn btn-sm btn-danger" 
                                                onclick="return confirm('Voulez-vous vraiment supprimer cette catégorie ?');">
                                            <i class="bi bi-trash"></i> Supprimer
                                        </button>
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
