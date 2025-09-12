<%@ page import="com.artizana.app.models.Societe" %>
<table border="1">
  <tr>
    <th>ID</th>
    <th>Nom</th>
    <th>Image</th>
    <th>Vidéo</th>
    <th>Action</th>
  </tr>

<%
  Societe[] societes = (Societe[]) request.getAttribute("societes");
  if (societes != null) {
    for (Societe p : societes) {
%>
  <tr>
    <td><%= p.getIdSociete() %></td>
    <td><%= p.getNom() %></td>
    <td>
      <img src="<%= request.getContextPath() + "/api/mobile/societe/image/" + p.getIdSociete() %>" width="100" height="100"/>
    </td>
    <td>
      <video width="320" height="240" controls>
        <source src="<%= request.getContextPath() + "/api/mobile/societe/video/" + p.getIdSociete() %>" type="video/mp4">
        Votre navigateur ne supporte pas la vidéo.
      </video>
    </td>
    <td>
      <form action="<%= request.getContextPath() + "/api/web/vue-societe/" + p.getIdSociete() %>" method="get">
        <input type="submit" value="Editer" />
      </form>
    </td>
    <td>
      <form action="<%= request.getContextPath() + "/api/web/supprimer-societe/" + p.getIdSociete() +"/2" %>" method="get ">
        <input type="submit" value="Supprimer" />
      </form>
    </td>
  </tr>
<%
    }
  } else {
%>
  <tr><td colspan="5">Aucune société trouvée.</td></tr>
<%
  }
%>
</table>
