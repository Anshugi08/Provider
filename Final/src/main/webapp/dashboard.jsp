<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>

<f:view>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Dashboard</title>
    <!-- Bootstrap CSS -->
    <link
      rel="stylesheet"
      href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
    />
    <style>
      body {
        background-color: #f8f9fa;
      }
      .card {
        border-radius: 0.5rem;
        box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075);
      }
      .welcome-text {
        font-size: 1.25rem;
        margin-bottom: 1.5rem;
      }
      .btn-secondary {
        background-color: #6c757d;
        border: none;
      }
      .btn-secondary:hover {
        background-color: #5a6268;
      }
      .btn-danger {
        background-color: #dc3545;
        border: none;
      }
      .btn-danger:hover {
        background-color: #c82333;
      }
      .text-warning {
        color: #ffc107;
      }
    </style>
  </head>

  <body>
    <h:form id="dashboardForm">
      <div class="container py-5">
        <div class="row justify-content-center">
          <div class="col-md-8">
            <div class="card p-4">

              <!-- When logged in -->
              <h:panelGroup rendered="#{providerController.loggedIn}">
                <h:outputText
                  value="Welcome, #{providerController.currentProvider.email}!"
                  styleClass="welcome-text"
                />

                <div class="d-flex mb-3">
                  <h:commandLink
                    value="Change Password"
                    action="change-password.jsp"
                    styleClass="btn btn-secondary flex-fill mr-2"
                  />
                  <h:commandButton
                    value="Logout"
                    action="#{providerController.logout}"
                    styleClass="btn btn-danger flex-fill"
                  />
                </div>
              </h:panelGroup>

              <!-- When not logged in -->
              <h:panelGroup rendered="#{not providerController.loggedIn}">
                <h:outputText
                  value="You are not logged in."
                  styleClass="text-warning mb-3"
                />
                <h:commandLink
                  value="Go to Login"
                  action="login.jsp"
                  styleClass="btn btn-primary"
                />
              </h:panelGroup>

            </div>
          </div>
        </div>
      </div>
    </h:form>

    <!-- jQuery + Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"
    ></script>
  </body>
</html>
</f:view>
