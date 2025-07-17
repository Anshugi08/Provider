<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>

<f:view>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Login</title>
    <!-- Bootstrap CSS -->
    <link
      rel="stylesheet"
      href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
    />
    <style>
      body { background: #f8f9fa; }
      .card { border-radius: .5rem; box-shadow: 0 .125rem .25rem rgba(0,0,0,.075); }
      .form-label { font-weight: 600; }
      .error { color: #e3342f; font-size: .875rem; }
      .btn-primary { background: #007bff; border: none; }
      .btn-primary:hover { background: #0069d9; }
    </style>
  </head>
  <body>
    <h:form id="loginForm">
      <div class="container py-5">
        <div class="row justify-content-center">
          <div class="col-md-6">
            <div class="card p-4">

              <!-- Global error messages -->
              <h:messages
                globalOnly="true"
                layout="list"
                styleClass="error mb-3"
              />

              <!-- Email field -->
              <div class="form-group">
                <h:outputLabel
                  for="email"
                  value="Email"
                  styleClass="form-label"
                />
                <h:inputText
                  id="email"
                  value="#{providerController.email}"
                  required="true"
                  styleClass="form-control"
                />
                <h:message
                  for="email"
                  styleClass="error mt-1"
                />
              </div>

              <!-- Password field -->
              <div class="form-group">
                <h:outputLabel
                  for="password"
                  value="Password"
                  styleClass="form-label"
                />
                <h:inputSecret
                  id="password"
                  value="#{providerController.password}"
                  required="true"
                  styleClass="form-control"
                />
                <h:message
                  for="password"
                  styleClass="error mt-1"
                />
              </div>

              <!-- Login button -->
              <div class="form-group">
                <h:commandButton
                  value="Login"
                  action="#{providerController.login}"
                  styleClass="btn btn-primary btn-block"
                />
              </div>

              <!-- Links -->
              <div class="d-flex justify-content-between">
                <h:commandLink
                  value="Forgot Password?"
                  action="forgot-password.jsp"
                  styleClass="btn btn-link"
                />
                <h:commandLink
                  value="Sign Up"
                  action="signup.jsp"
                  styleClass="btn btn-link"
                />
              </div>

            </div>
          </div>
        </div>
      </div>
    </h:form>

    <!-- Bootstrap JS + dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"
    ></script>
  </body>
</html>
</f:view>