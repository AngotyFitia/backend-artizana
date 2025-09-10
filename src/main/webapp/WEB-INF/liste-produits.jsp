<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.artizana.app.models.Produit" %>
<html>
<head><title>Liste des Produits</title></head>
<body>
  <h2>Produits disponibles</h2>
  <table border="1">
    <tr>
      <th>ID</th>
      <th>Intitulé</th>
    </tr>
<% Produit[] produits = (Produit[]) request.getAttribute("produits");
  if (produits != null) {
    for (Produit p : produits) {
%>
    <tr>
      <td><%= p.getIdProduit() %></td>
      <td><%= p.getIntitule() %></td>
    </tr>
<%
    }
  }
%>
  </table>
</body>
</html>
