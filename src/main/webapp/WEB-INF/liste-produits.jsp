<%@ page import="com.artizana.app.models.Produit" %>
<%
  Produit[] produits = (Produit[]) request.getAttribute("produits");
  for (Produit p : produits) {
%>
  <tr>
    <td><%= p.getIdProduit() %></td>
    <td><%= p.getIntitule() %></td>
  </tr>
<%
  }
%>
