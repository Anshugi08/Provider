<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>

<f:view>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8"/>
    <title>Verify OTP  Reset Password</title>

    <!-- Bootstrap CSS -->
    <link
      rel="stylesheet"
      href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
    />

    <style>
      body {
        background: #f8f9fa;
        font-family: Arial, sans-serif;
      }
      .card {
        border-radius: .5rem;
        box-shadow: 0 .125rem .25rem rgba(0, 0, 0, .075);
      }
      .card-title {
        font-size: 1.5rem;
        font-weight: 600;
      }
      .form-label {
        font-weight: 500;
      }
      .form-text {
        font-size: .85rem;
        color: #6c757d;
        margin-top: .25rem;
      }
      .error {
        color: #e3342f;
        font-size: .875rem;
      }
      .btn-primary {
        background: #007bff;
        border: none;
      }
      .btn-primary:hover {
        background: #0069d9;
      }
    </style>
  </head>

  <body>
    <h:form id="otpForm">
      <div class="container py-5">
        <div class="row justify-content-center">
          <div class="col-lg-5 col-md-7">
            <div class="card p-4">

              <h2 class="card-title text-center mb-4">
                Reset Your Password
              </h2>

              <!-- global errors (e.g. Invalid or expired OTP) -->
              <h:messages
                globalOnly="true"
                layout="list"
                styleClass="error mb-3"
              />

              <!-- Email (read‐only) -->
              <div class="form-group">
                
                <h:inputText
                  id="email"
                  value="#{ProviderController.email}"
                  readonly="true"
                  styleClass="form-control-plaintext"
                />
              </div>

              <!-- OTP code -->
              <div class="form-group">
                <h:outputLabel
                  for="otp"
                  value="OTP Code"
                  styleClass="form-label"
                />
                <h:inputText
                  id="otp"
                  value="#{providerController.otp}"
                  required="true"
                  styleClass="form-control"
                />
                <h:message
                  for="otp"
                  styleClass="error mt-1"
                />
                <small class="form-text">
                  Enter the 6-digit code we emailed you (valid for 10 minutes).
                </small>
              </div>

              <!-- New password -->
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
                <small class="form-text">
                  8–12 characters, include letters, digits & a special character.
                </small>
              </div>

              <!-- Confirm new password -->
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

              <!-- Reset button -->
              <div class="form-group">
                <h:commandButton
                  value="Reset Password"
                  action="#{providerController.verifyOtpAndReset}"
                  styleClass="btn btn-primary btn-block"
                />
              </div>

              <!-- Back to login link -->
              <div class="text-center">
                <h:commandLink
                  value="Back to Login"
                  action="login.jsp?faces-redirect=true"
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
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>

    
  </body>
</html>
</f:view>
