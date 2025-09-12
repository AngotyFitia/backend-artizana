<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <meta
      name="viewport"
      content="width=device-width, initial-scale=1, shrink-to-fit=no"
    />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <link rel="icon" href="favicon.ico" />
    <title>Artizana</title>
    <!-- Simple bar CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/simplebar.css" />
    <!-- Fonts CSS -->
    <link
      href="https://fonts.googleapis.com/css2?family=Overpass:ital,wght@0,100;0,200;0,300;0,400;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,600;1,700;1,800;1,900&display=swap"
      rel="stylesheet"
    />
    <!-- Icons CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/feather.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/select2.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dropzone.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/uppy.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery.steps.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery.timepicker.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/quill.snow.css" />
    <!-- Date Range Picker CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/daterangepicker.css" />
    <!-- App CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app-light.css" id="lightTheme"/>
    <link rel="stylesheet" href="css/app-dark.css" id="darkTheme" disabled />
  </head>
  <body class="horizontal light">
    <div class="wrapper">
      <nav
        class="navbar navbar-expand-lg navbar-light bg-white flex-row border-bottom shadow"
      >
        <div class="container-fluid">
          <a class="navbar-brand mx-lg-1 mr-0" href="./index.html">
            <svg
              version="1.1"
              id="logo"
              class="navbar-brand-img brand-sm"
              xmlns="http://www.w3.org/2000/svg"
              xmlns:xlink="http://www.w3.org/1999/xlink"
              x="0px"
              y="0px"
              viewBox="0 0 120 120"
              xml:space="preserve"
            >
              <g>
                <polygon class="st0" points="78,105 15,105 24,87 87,87 	" />
                <polygon class="st0" points="96,69 33,69 42,51 105,51 	" />
                <polygon class="st0" points="78,33 15,33 24,15 87,15 	" />
              </g>
            </svg>
          </a>
          <button class="navbar-toggler mt-2 mr-auto toggle-sidebar text-muted">
            <i class="fe fe-menu navbar-toggler-icon"></i>
          </button>
          <div class="collapse navbar-collapse navbar-slide bg-white ml-lg-4" id="navbarSupportedContent">

            <a
              href="#"
              class="btn toggle-sidebar d-lg-none text-muted ml-2 mt-3"
              data-toggle="toggle"
            >
              <i class="fe fe-x"><span class="sr-only"></span></i>
            </a>
            <ul class="navbar-nav mr-auto">
              <li class="nav-item">
                <a class="nav-link" href="widgets.html">
                  <span class="ml-lg-2">Accueil</span>
                </a>
              </li>
              <li class="nav-item dropdown">
                <a
                  href="#"
                  id="dashboardDropdown"
                  class="dropdown-toggle nav-link"
                  role="button"
                  data-toggle="dropdown"
                  aria-haspopup="true"
                  aria-expanded="false"
                >
                  <span class="ml-lg-2">Commande</span
                  ><span class="sr-only">(current)</span>
                </a>
                <div class="dropdown-menu" aria-labelledby="dashboardDropdown">
                  <a class="nav-link pl-lg-2" href="./index.html"
                    ><span class="ml-1">Default</span></a
                  >
                  <a class="nav-link pl-lg-2" href="./dashboard-analytics.html"
                    ><span class="ml-1">Analytics</span></a
                  >
                  <a class="nav-link pl-lg-2" href="./dashboard-sales.html"
                    ><span class="ml-1">E-commerce</span></a
                  >
                  <a class="nav-link pl-lg-2" href="./dashboard-saas.html"
                    ><span class="ml-1">Saas Dashboard</span></a
                  >
                  <a class="nav-link pl-lg-2" href="./dashboard-system.html"
                    ><span class="ml-1">Systems</span></a
                  >
                </div>
              </li>
              <li class="nav-item dropdown">
                <a
                  href="#"
                  id="ui-elementsDropdown"
                  class="dropdown-toggle nav-link"
                  role="button"
                  data-toggle="dropdown"
                  aria-haspopup="true"
                  aria-expanded="false"
                >
                  <span class="ml-lg-2">Paramétrage</span>
                </a>
                <div
                  class="dropdown-menu"
                  aria-labelledby="ui-elementsDropdown"
                >
                  <a class="nav-link pl-lg-2" href="./ui-color.html"
                    ><span class="ml-1">Catégories</span></a
                  >
                  <a class="nav-link pl-lg-2" href="/api/web/formulaire-societe">
                    <span class="ml-1">Sociétés</span>
                  </a>
                  <a class="nav-link pl-lg-2" href="./ui-icons.html"
                    ><span class="ml-1">Produits</span></a
                  >
                </div>
              </li>
            </ul>
          </div>
          <form
            class="form-inline ml-md-auto d-none d-lg-flex searchform text-muted"
          >
            <input
              class="form-control mr-sm-2 bg-transparent border-0 pl-4 text-muted"
              type="search"
              placeholder="Recherche"
              aria-label="Search"
            />
          </form>
          <ul class="navbar-nav d-flex flex-row">
            <li class="nav-item dropdown ml-lg-0">
              <a
                class="nav-link dropdown-toggle text-muted"
                href="#"
                id="navbarDropdownMenuLink"
                role="button"
                data-toggle="dropdown"
                aria-haspopup="true"
                aria-expanded="false"
              >
                <span class="avatar avatar-sm mt-2">
                  <img
                    src="./assets/avatars/face-1.jpg"
                    alt="..."
                    class="avatar-img rounded-circle"
                  />
                </span>
              </a>
              <ul
                class="dropdown-menu dropdown-menu-right"
                aria-labelledby="navbarDropdownMenuLink"
              >
                <li class="nav-item">
                  <a class="nav-link pl-3" href="#">Settings</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link pl-3" href="#">Profile</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link pl-3" href="#">Activities</a>
                </li>
              </ul>
            </li>
          </ul>
        </div>
      </nav>
    </div>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.min.js"></script>
<script>
  $(function () {
    $('.dropdown-toggle').dropdown(); // initialise manuellement
  });
</script>

  </body>
</html>
