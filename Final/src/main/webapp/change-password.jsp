<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>

<f:view>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Change Password</title>
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
      .form-label {
        font-weight: 600;
      }
      .error {
        color: #e3342f;
        font-size: 0.875rem;
      }
      .btn-primary {
        background-color: #007bff;
        border: none;
      }
      .btn-primary:hover {
        background-color: #0069d9;
      }
    </style>
  </head>

  <body>
    <h:form id="changePwdForm">
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

              <!-- Old Password -->
              <div class="form-group">
                <h:outputLabel
                  for="oldPwd"
                  value="Old Password"
                  styleClass="form-label"
                />
                <h:inputSecret
                  id="oldPwd"
                  value="#{providerController.oldPassword}"
                  required="true"
                  styleClass="form-control"
                />
                <h:message
                  for="oldPwd"
                  styleClass="error mt-1"
                />
              </div>

              <!-- New Password -->
              <div class="form-group">
                <h:outputLabel
                  for="newPwd"
                  value="New Password"
                  styleClass="form-label"
                />
                <h:inputSecret
                  id="newPwd"
                  value="#{providerController.newPassword}"
                  required="true"
                  styleClass="form-control"
                />
                <h:message
                  for="newPwd"
                  styleClass="error mt-1"
                />
              </div>

              <!-- Confirm Password -->
              <div class="form-group">
                <h:outputLabel
                  for="confirmPwd"
                  value="Confirm Password"
                  styleClass="form-label"
                />
                <h:inputSecret
                  id="confirmPwd"
                  value="#{providerController.confirmPassword}"
                  required="true"
                  styleClass="form-control"
                />
                <h:message
                  for="confirmPwd"
                  styleClass="error mt-1"
                />
              </div>

              <!-- Update Button -->
              <div class="form-group">
                <h:commandButton
                  value="Update Password"
                  action="#{providerController.changePassword}"
                  styleClass="btn btn-primary btn-block"
                />
              </div>

              <!-- Back Link -->
              <div class="text-center">
                <h:commandLink
                  value="Back to Dashboard"
                  action="dashboard.jsp?faces-redirect=true"
                  styleClass="btn btn-link"
                />
              </div>

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
