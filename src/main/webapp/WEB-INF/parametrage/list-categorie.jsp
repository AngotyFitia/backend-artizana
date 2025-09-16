<%@include file="../utilitaire/header.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dataTables.bootstrap4.css">
            <div class="col-12">
              <h2 class="mb-2 page-title">Liste des Catégories</h2>
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
                          </tr>
                        </thead>
                        <tbody>
                          <liste:forEach items="${categories}" var="cat">
                            <tr>
                            <td>
                              ${cat.idCategorie}
                            </td>
                            <td>
                              ${cat.intitule}
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