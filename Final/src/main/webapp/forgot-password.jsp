<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>

<f:view>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Forgot Password</title>
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
    <h:form id="forgotForm">
      <div class="container py-5">
        <div class="row justify-content-center">
          <div class="col-md-6">
            <div class="card p-4">
              
              <!-- global errors -->
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
    styleClass="form-control" />
<h:message
    for="email"
    styleClass="error mt-1" />
                
<h:message for="email" styleClass="error mt-1" />

<h:message for="email" styleClass="error mt-1" />

<h:message for="email" styleClass="error mt-1" />

<h:message for="email" styleClass="error mt-1" />
               
               
               
<h:message for="email" styleClass="error mt-1" />
                
<h:message for="email" styleClass="error mt-1" />
                
              </div>

              <!-- Send OTP button -->
              <div class="form-group">
                <h:commandButton
                  value="Send OTP"
                  action="#{providerController.sendForgotOtp}"
                  styleClass="btn btn-primary btn-block"
                />
              </div>

              <!-- Back to login -->
              <div class="text-center">
                <h:commandLink
                  value="Back to Login"
                  action="login.jsp"
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
