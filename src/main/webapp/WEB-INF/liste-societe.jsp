<%@ page import="com.artizana.app.models.Societe" %>
<%
  Societe[] societes = (Societe[]) request.getAttribute("societes");
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
  </tr>
<%
  }
%>
