package com.infinite.jsf.Provider.controller;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.infinite.jsf.Provider.dao.ProviderDao;
import com.infinite.jsf.Provider.dao.ProviderDaoImpl;
import com.infinite.jsf.Provider.model.Provider;
import com.infinite.jsf.Provider.util.SessionHelper;

@ManagedBean(name = "providerController")
@SessionScoped
public class ProviderController implements Serializable {

    private static final long serialVersionUID = 1L;

    private ProviderDao dao = new ProviderDaoImpl();
    private Provider currentProvider;

    private String email;
    private String password;
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
    private String otp;
//---For resend otp
 // new field to hold expiry millis
    private long otpExpiryMillis;

    // getter for your JSP
    public long getOtpExpiryMillis() {
        return otpExpiryMillis;
    }
    
    // ─── SIGNUP ─────────────────────────────────────────────────────────────
    public String signup() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        try {
            // ensure newPassword and confirmPassword match
            if (newPassword == null || !newPassword.equals(confirmPassword)) {
                throw new Exception("Passwords must match.");
            }

            // build provider with raw password; DAO will encrypt & persist
            Provider p = new Provider();
            p.setEmail(email);
            p.setPasswordHash(newPassword);
            dao.signup(p);

            ctx.addMessage(null,
                new FacesMessage("Signup successful! Please log in."));
            return "login.jsp?faces-redirect=true";

        } catch (Exception e) {
            ctx.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            return null;
        }
    }

    // ─── LOGIN ──────────────────────────────────────────────────────────────
    public String login() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        try {
            // DAO.login will encrypt the submitted password and compare
            currentProvider = dao.login(email, password);

            // ensure Hibernate SessionFactory is initialized
            SessionHelper.getConnection();

            // store in HTTP session
            ctx.getExternalContext()
               .getSessionMap()
               .put("currentProvider", currentProvider);

            return "dashboard.jsp?faces-redirect=true";

        } catch (Exception e) {
            ctx.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            return null;
        }
    }

    // ─── LOGOUT ─────────────────────────────────────────────────────────────
    public String logout() {
        FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .invalidateSession();
        currentProvider = null;
        return "login.jsp?faces-redirect=true";
    }

    // ─── CHANGE PASSWORD ────────────────────────────────────────────────────
    public String changePassword() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        try {
            if (newPassword == null || !newPassword.equals(confirmPassword)) {
                throw new Exception("New passwords must match.");
            }

            dao.changePassword(
                currentProvider.getId().intValue(),
                oldPassword,
                newPassword
            );

            ctx.addMessage(null,
                new FacesMessage("Password updated successfully."));
            return "dashboard.jsp?faces-redirect=true";

        } catch (Exception e) {
            ctx.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            return null;
        }
    }

    // ─── SEND FORGOT PASSWORD OTP ───────────────────────────────────────────
    public String sendForgotOtp() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        try {
            // Check if email ends with "@gmail.com"
            if (email == null || !email.matches("^\\S+@gmail\\.com$")) {
                throw new Exception("Email must be a valid Gmail address ending with @gmail.com.");
            }

            dao.sendForgotPasswordOtp(email);
            ctx.addMessage(null, new FacesMessage("OTP sent. Check your email."));
            return "verify-otp.jsp?faces-redirect=true";

        } catch (Exception e) {
            ctx.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            return null;
        }
    }
// resend Oto function
    public String resendOtp()
    {
    	return sendForgotOtp();
    }
    
    //-______verify and reset password______
    
    public String verifyOtpAndReset() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        try {
            if (!newPassword.equals(confirmPassword)) {
                throw new Exception("Passwords must match.");
            }

            // use the email they typed in the “send OTP” step
            if (!dao.validateForgotPasswordOtp(email, otp)) {
                throw new Exception("Invalid or expired OTP.");
            }

            // now complete reset
            Provider p = dao.findByEmail(email);
            dao.completeForgotPassword(p.getId().intValue(), newPassword);

            ctx.addMessage(null,
                new FacesMessage("Password reset successful. Please log in."));
            return "login.jsp?faces-redirect=true";
        }
        catch (Exception e) {
            ctx.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            return null;
        }
    }

    
    
/*
    // ─── VERIFY OTP & RESET PASSWORD ───────────────────────────────────────
    public String verifyOtpAndReset() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        try {
            if (newPassword == null || !newPassword.equals(confirmPassword)) {
                throw new Exception("Passwords must match.");
            }

            int providerId = currentProvider != null
                ? currentProvider.getId().intValue()
                : -1;

            if (!dao.validateForgotPasswordOtp(providerId, otp)) {
                throw new Exception("Invalid or expired OTP.");
            }

            dao.completeForgotPassword(providerId, newPassword);
            ctx.addMessage(null,
                new FacesMessage("Password reset successful. Please log in."));
            return "login.jsp?faces-redirect=true";

        } catch (Exception e) {
            ctx.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            return null;
        }
    }
*/
    // ─── HELPER ─────────────────────────────────────────────────────────────
    public boolean isLoggedIn() {
        return currentProvider != null;
    }

    // ─── GETTERS & SETTERS ─────────────────────────────────────────────────
    public Provider getCurrentProvider() {
        return currentProvider;
    }

    public void setCurrentProvider(Provider currentProvider) {
        this.currentProvider = currentProvider;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email != null ? email.trim() : null;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
