<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.artizana.app.models.Societe" %>
<%@ include file="utilitaire/header.jsp" %> 
  
<main role="main" class="main-content">
  <div class="container-fluid">
    <div class="row justify-content-center">
      <div class="col-12">
        <h2 class="page-title">Liste des sociétés</h2>
        <div class="row">
          <div class="col-md-12 my-4">
            <div class="card shadow">
              <div class="card-body">
                <h5 class="card-title">Simple Table</h5>
                <p class="card-text">Liste des sociétés enregistrées. </p>
                <div class="d-flex justify-content-end mb-3">
                  <div class="mr-3"><button type="button" class="btn btn-primary">Importer les données</button></div>
                  <div><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#varyModal" data-whatever="@mdo">Ajouter</button></div>
                </div>                               
                <table class="table table-hover">
                  <thead>
                    <tr>
                      <th>ID</th>
                      <th>Nom</th>
                      <th>Titre</th>
                      <th>Histoire</th>
                      <th>Photo</th>
                      <th>Action</th>
                    </tr>
                  </thead>
                  <tbody>
                    <%
                      Societe[] societes = (Societe[]) request.getAttribute("societes");
                      if (societes != null) {
                        for (Societe p : societes) {
                    %>
                    <tr>
                      <td><%= p.getIdSociete() %></td>
                      <td><%= p.getNom() %></td>
                      <td><%= p.getTitre() %></td>
                      <td><%= p.getHistoire() %></td> 
                      <td><img src="<%= request.getContextPath() + "/api/mobile/societe/image/" + p.getIdSociete() %>" width="100" height="100" /></td>
                      <td>
                        <button class="btn btn-sm dropdown-toggle more-horizontal" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                          <span class="text-muted sr-only">Action</span>
                        </button>
                        <div class="dropdown-menu dropdown-menu-right">
                          <a class="dropdown-item" href="/api/web/vue-societe/<%= p.getIdSociete()%>">Editer/Voir</a>
                          <a class="dropdown-item" href="/api/web/supprimer-societe/<%= p.getIdSociete()%>/2">Supprimer</a>
                        </div>
                      </td>
                    </tr>
                    <%  } %> 
                    <%} else { %>
                      <tr><td colspan="6">Aucune société trouvée.</td></tr>
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
              <form action="/api/web/ajout-societe" method="post" enctype="multipart/form-data">
              <div class="modal-header">
                <h5 class="modal-title" id="varyModalLabel">Nouvelle société</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span>
                </button>
              </div>
                <div class="modal-body">
                  <div class="form-group">
                    <label for="recipient-name" class="col-form-label" >Utilisateur:</label>
                    <input type="text" class="form-control" id="recipient-name" name="idUtilisateur">
                  </div>
                  <div class="form-group">
                    <label for="recipient-name" class="col-form-label" >Nom de la société:</label>
                    <input type="text" class="form-control" id="recipient-name" name="nom">
                  </div>
                  <div class="form-group">
                    <label for="message-text" class="col-form-label">Titre:</label>
                    <textarea class="form-control" id="message-text" name="titre"></textarea>
                  </div>
                  <div class="form-group">
                    <label for="message-text" class="col-form-label">Petit histoire:</label>
                    <textarea class="form-control" id="message-text" name="histoire"></textarea>
                  </div>
                  <div class="form-group">
                    <label for="message-text" class="col-form-label">Image de la société:</label>
                    <input type="file" class="form-control" id="recipient-name" name="photo">
                  </div>
                  <div class="form-group">
                    <label for="message-text" class="col-form-label">Vidéo de la société:</label>
                    <input type="file" class="form-control" id="recipient-name" name="video">
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
        <div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="varyModalLabel" aria-hidden="true">
          <div class="modal-dialog" role="document">
            <div class="modal-content">
              <div class="modal-header">
                <h5 class="modal-title" id="varyModalLabel">Edit société</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span>
                </button>
              </div>
              <% Societe propreSociete = (Societe) request.getAttribute("societe");
              if (propreSociete != null) { %>
                <form action="/api/web/editer-societe" method="get" enctype="multipart/form-data">
                <div class="modal-body">
                  <div class="form-group">
                    <label for="recipient-name" class="col-form-label" >Nom de la société:</label>
                    <input type="text" class="form-control" name="idSociete" value="<%= propreSociete.getIdSociete() %>">
                    <input type="text" class="form-control" name="nom" placeholder="<%= propreSociete != null ? propreSociete.getNom() : "" %>">
                  </div>
                  <div class="form-group">
                    <label for="message-text" class="col-form-label">Titre:</label>
                    <textarea class="form-control" name="titre"
                    placeholder="<%= propreSociete != null ? propreSociete.getTitre() : "" %>"></textarea>                  
                  </div>
                  <div class="form-group">
                    <label for="message-text" class="col-form-label">Petit histoire:</label>
                    <textarea class="form-control" name="histoire" placeholder="<%= propreSociete != null ? propreSociete.getHistoire() : "" %>"></textarea>
                  </div>
                </div>
                <% } %>
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
