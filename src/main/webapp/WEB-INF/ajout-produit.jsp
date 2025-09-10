<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Ajouter un produit</title></head>
<body>
  <h2>Formulaire d'ajout de produit</h2>
  <form action="/view/produit-ajout" method="post">
    <label for="intitule">Intitulé :</label>
    <input type="text" name="intitule" required /><br/>

    <label for="prix">Prix :</label>
    <input type="number" name="prix" step="0.01" required /><br/>

    <input type="submit" value="Ajouter" />
  </form>
</body>
</html>
