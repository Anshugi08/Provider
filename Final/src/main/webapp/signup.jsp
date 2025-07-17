<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>

<f:view>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8"/>
    <title>Sign Up</title>
    <link 
      rel="stylesheet" 
      href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" />
    <style>
      body { background: #f8f9fa; }
      .card { border-radius: .5rem; box-shadow: 0 .125rem .25rem rgba(0,0,0,.075); }
      .form-label { font-weight:600; }
      .error { color:#e3342f; font-size:.875rem; }
      .form-text { font-size:.85rem; color: #6c757d; margin-top: .25rem; }
      .btn-primary { background:#007bff; border:none; }
      .btn-primary:hover { background:#0069d9; }
    </style>
  </head>

  <body>
    <h:form id="signupForm">
      <div class="container py-5">
        <div class="row justify-content-center">
          <div class="col-md-6">
            <div class="card p-4">

              <!-- will show DAO errors like “Password must be …” -->
              <h:messages globalOnly="true" layout="list" styleClass="error mb-3"/>

              <!-- Email… -->
              <div class="form-group">
                <h:outputLabel for="Email"
                               value="email"
                               styleClass="form-label"/>
                <h:inputText id="email"
                               value="#{providerController.email}"
                               required="true"
                               styleClass="form-control"/>
                <small class="form-text">
                  Enter the correct Email
                </small>
                <h:message for="Email" styleClass="error mt-1"/>
              </div>

              <!-- Password with static hint -->
              <div class="form-group">
                <h:outputLabel for="newPassword"
                               value="Password"
                               styleClass="form-label"/>
                <h:inputSecret id="newPassword"
                               value="#{providerController.newPassword}"
                               required="true"
                               styleClass="form-control"/>
                <small class="form-text">
                  Password must be 8–12 characters, contain letters, digits and a special character.
                </small>
                <h:message for="newPassword" styleClass="error mt-1"/>
              </div>

              <!-- Confirm Password… -->
              <div class="form-group">
                <h:outputLabel for="confirmPassword"
                               value="Confirm Password"
                               styleClass="form-label"/>
                <h:inputSecret id="confirmPassword"
                               value="#{providerController.confirmPassword}"
                               required="true"
                               styleClass="form-control"/>
                <h:message for="confirmPassword" styleClass="error mt-1"/>
              </div>
<h:commandButton value="Sign Up" action="#{providerController.signup}"/>
        <h:commandLink value="Login" action="login.jsp"/>

              <!-- Submit button… -->

            </div>
          </div>
        </div>
      </div>
    </h:form>
  </body>
</html>
</f:view>
