<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.artizana.app.models.Produit" %>
<%@ page import="com.artizana.app.models.Societe" %>
<%@ page import="com.artizana.app.models.Categorie" %>
<%@ include file="utilitaire/header.jsp" %> 
  
<main role="main" class="main-content">
  <div class="container-fluid">
    <div class="row justify-content-center">
      <div class="col-12">
        <%
                    if (user != null && user.getEtat() == 0) {
        %>
        <h2 class="page-title">Liste des produits</h2>
        <% } else { %>
          <h2 class="page-title">Mes produits</h2>
        <% } %>
        <div class="row">
          <div class="col-md-12 my-4">
            <div class="card shadow">
              <div class="card-body">
                <h5 class="card-title">Liste des produits enregistrés</h5>
                <%
                    if (user != null && user.getEtat() == 10) {
                %>
                <div class="d-flex justify-content-end mb-3">
                  <div class="mr-3"><button type="button" class="btn btn-primary">Importer les données</button></div>
                  <div><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#varyModal" data-whatever="@mdo">Ajouter</button></div>
                </div>    
                <%}%>  
                <%
                    if (user != null && user.getEtat() == 0) {
                %>
                <div class="d-flex justify-content-end mb-3">
                    <a href="/api/web/validation" class="btn btn-lg btn-primary shadow-sm">
                        <i class="bi bi-check2-square"></i> Les produits à valider
                    </a>
                </div>
                  
                <%}%>                           
                <table class="table table-hover">
                  <thead>
                    <tr>
                      <th>ID</th>
                      <th>Intitule</th>
                      <th>Catégorie</th>
                      <th>Société</th>
                      <th>Action</th>
                    </tr>
                  </thead>
                  <tbody>
                    <%
                      Produit[] produits = (Produit[]) request.getAttribute("produits");
                      if (produits != null) {
                        for (Produit p : produits) {
                    %>
                    <tr>
                      <td><%= p.getIdProduit() %></td>
                      <td><%= p.getIntitule() %></td>
                      <td><%= p.getCategorie().getIntitule() %></td>
                      <td><%= p.getSociete().getNom() %></td> 
                      <td>
                        <%
                            if (user != null && user.getEtat() == 10) {
                        %>
                        <button class="btn btn-sm dropdown-toggle more-horizontal" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                          <span class="text-muted sr-only">Action</span>
                        </button>
                        <div class="dropdown-menu dropdown-menu-right">
                          <a class="dropdown-item" href="/api/web/vue-societe/<%= p.getIdProduit() %>">Editer/Voir</a>
                          <a class="dropdown-item" href="/api/web/supprimer-societe/<%= p.getIdProduit() %>/2">Supprimer</a>
                          <a class="dropdown-item" href="<%= request.getContextPath() %>/api/web/ajout-stock?id=<%= p.getIdProduit() %>">Ajout en stock</a>
                        </div>
                        <%}%>
                      </td>
                    </tr>
                    <%  } %> 
                    <%} else { %>
                      <tr><td colspan="6">Aucun produit.</td></tr>
                    <% } %>
                  </tbody>
                </table>    
              </div> 
            </div> 
          </div> 
        </div>
        <div class="modal fade" id="varyModal" tabindex="-1" role="dialog" aria-labelledby="varyModalLabel" aria-hidden="true">
          <div class="modal-dialog" role="document">
            <div class="modal-content">
              <form action="/api/web/ajout-produit" method="post" enctype="multipart/form-data">
              <div class="modal-header">
                <h5 class="modal-title" id="varyModalLabel">Nouveau produit</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span>
                </button>
              </div>
              <div class="modal-body">
                <div class="form-group">
                  <label for="intitule" class="col-form-label">Nom du produit:</label>
                  <input type="text" class="form-control" id="intitule" name="intitule">
                </div>
                <div class="form-group">
                  <label for="idSociete" class="col-form-label">Catégorie:</label>
                  <select class="form-control" id="idSociete" name="idCategorie">
                    <% Categorie[] categories = (Categorie[]) request.getAttribute("categories");
                      if (categories != null && categories.length > 0) {
                    %>
                      <option value="">-- Choisir un catégorie --</option>
                    <% for (Categorie c : categories) {  %>
                      <option value="<%= c.getIdCategorie() %>"><%= c.getIntitule() %></option>
                    <%} %>
                    <% } else { %>
                        <option disabled>Aucun catégorie.</option>
                    <% } %>
                  </select>
                </div>
                </div>
                <div class="modal-footer">
                  <button type="button" class="btn mb-2 btn-secondary" data-dismiss="modal">Fermer</button>
                  <button type="submit" class="btn mb-2 btn-primary">Ajouter</button>
                </div>
              </form>
            </div>
          </div>
        </div>  
      </div> 
    </div> 
  </div> 
</main>
<%
  Boolean openModal = (Boolean) request.getAttribute("openModal");
  if (openModal != null && openModal) {
%>
  <script>
    $(document).ready(function() {
      $('#editModal').modal('show');
    });
  </script>
<%
  }
%>

<%@ include file="utilitaire/footer.jsp" %>
