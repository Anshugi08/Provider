package com.infinite.jsf.Provider.dao;

import com.infinite.jsf.Provider.model.Provider;



public interface ProviderDao {
    void signup(Provider provider) throws Exception;
    Provider login(String usernameOrEmail, String password) throws Exception;
    void changePassword(int providerId, String oldPassword, String newPassword) throws Exception;
    void sendForgotPasswordOtp(String email) throws Exception;
 // instead of providerId, accept email
    boolean validateForgotPasswordOtp(String email, String otpCode) throws Exception;
   // boolean validateForgotPasswordOtp(int providerId, String otpCode) throws Exception;
    void completeForgotPassword(int providerId, String newPassword) throws Exception;
    //
    Provider findByEmail(String email) throws Exception;
    //
    


}



