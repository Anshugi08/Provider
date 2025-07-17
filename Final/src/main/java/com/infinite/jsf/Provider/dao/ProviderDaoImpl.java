package com.infinite.jsf.Provider.dao;

import java.util.Date;
import java.util.List;


import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.infinite.jsf.Provider.model.OtpToken;
import com.infinite.jsf.Provider.model.PasswordHistory;
import com.infinite.jsf.Provider.model.Provider;
import com.infinite.jsf.Provider.util.MailSend;
import com.infinite.jsf.Provider.util.OTPGenerator;
import com.infinite.jsf.Provider.util.PasswordEncryptor;
import com.infinite.jsf.Provider.util.SessionHelper;

public class ProviderDaoImpl implements ProviderDao {

    /**
     *  - length 8–12
     *  - at least one letter
     *  - at least one digit
     *  - at least one special character
     */
    private static final String PASS_REGEX =
        "^(?=.{8,12}$)(?=.*[A-Za-z])(?=.*\\d)(?=.*[^A-Za-z\\d]).*$";

    private SessionFactory sf = SessionHelper.getConnection();

    @Override
    public void signup(Provider provider) throws Exception {
        String raw = provider.getPasswordHash();
        validatePassword(raw);

        // encrypt before storing
        String encrypted = PasswordEncryptor.encrypt(raw);
        provider.setPasswordHash(encrypted);

        Session session = null;
        Transaction tx = null;
        try {
            session = sf.openSession();
            tx = session.beginTransaction();

            session.save(provider);
            session.save(new PasswordHistory(provider, encrypted, new Date()));

            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public Provider login(String email, String password) throws Exception {
        Session session = null;
        try {
            session = sf.openSession();
            Query q = session.createQuery("FROM Provider WHERE email = :e");
            q.setParameter("e", email);

            Provider p = (Provider) q.uniqueResult();
            String encryptedInput = PasswordEncryptor.encrypt(password);

            if (p == null || !p.getPasswordHash().equals(encryptedInput)) {
                throw new Exception("Invalid credentials.");
            }
            return p;
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public void changePassword(int providerId, String oldPassword, String newPassword) throws Exception {
        validatePassword(newPassword);

        Session session = null;
        Transaction tx = null;
        try {
            session = sf.openSession();
            tx = session.beginTransaction();

            Provider p = (Provider) session.get(Provider.class, (long) providerId);
            if (p == null) {
                throw new Exception("Provider not found.");
            }

            // verify old password
            String oldEnc = PasswordEncryptor.encrypt(oldPassword);
            if (!p.getPasswordHash().equals(oldEnc)) {
                throw new Exception("Old password incorrect.");
            }

            // no reuse of last 3
            if (isAmongLastPasswords(session, p, newPassword)) {
                throw new Exception("Cannot reuse any of the last 3 passwords.");
            }

            // encrypt & update
            String newEnc = PasswordEncryptor.encrypt(newPassword);
            p.setPasswordHash(newEnc);
            session.update(p);
            session.save(new PasswordHistory(p, newEnc, new Date()));

            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public void sendForgotPasswordOtp(String email) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = sf.openSession();
            Query query = s.createQuery("FROM Provider p WHERE p.email = :e");
            query.setParameter("e", email);
            Provider p = (Provider) query.uniqueResult();
            if (p == null) {
                throw new Exception("Email not found");
            }

            String otp = OTPGenerator.generateOTP(6);
            Date expires = new Date(System.currentTimeMillis() + 10 * 60 * 1000);

            tx = s.beginTransaction();
            s.save(new OtpToken(p, otp, expires));
            tx.commit();

            // inner try/catch for email sending
            try {
                MailSend.sendInfo(email, "OTP Code", "Your OTP is: " + otp);
            } catch (Exception mex) {
                // now catches any exception from sendInfo()
                throw new Exception("Failed to send OTP email", mex);
            }

        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            if (s != null) {
                s.close();
            }
        }
    }
    // uploading for Github please do that
    @Override
    public boolean validateForgotPasswordOtp(String email, String otpCode) throws Exception {
        Session session = null;
        try {
            session = sf.openSession();

            Provider provider = (Provider) session.createQuery("FROM Provider WHERE email = :e")
                                                  .setParameter("e", email)
                                                  .uniqueResult();
            if (provider == null) return false;

            Query q = session.createQuery(
                "FROM OtpToken WHERE provider = :p ORDER BY expiresAt DESC"
            );
            q.setParameter("p", provider);
            q.setMaxResults(1);

            OtpToken latest = (OtpToken) q.uniqueResult();
            return latest != null
                && latest.getToken().equals(otpCode)
                && latest.getExpiresAt().after(new Date());
        } finally {
            if (session != null) session.close();
        }
    }

    /* latest use
    @Override
    public boolean validateForgotPasswordOtp(String email, String otpCode) throws Exception {
        Session session = null;
        try {
            session = sf.openSession();

            // find the provider by email
            Provider p = (Provider) session
                         .createQuery("FROM Provider WHERE email = :e")
                         .setParameter("e", email)
                         .uniqueResult();
            if (p == null) return false;

            // look up the latest matching OTP token
            Query q = session.createQuery(
                "FROM OtpToken WHERE provider = :p AND token = :o ORDER BY expiresAt DESC"
            );
            q.setParameter("p", p);
            q.setParameter("o", otpCode);
            q.setMaxResults(1);

            @SuppressWarnings("unchecked")
            List<OtpToken> tokens = q.list();
            return !tokens.isEmpty() 
                   && tokens.get(0).getExpiresAt().after(new Date());
        } finally {
            if (session != null) session.close();
        }
    }

  g*/  

/*
    @Override
    public boolean validateForgotPasswordOtp(int providerId, String otpCode) throws Exception {
        Session session = null;
        try {
            session = sf.openSession();
            Provider p = (Provider) session.get(Provider.class, (long) providerId);
            if (p == null) return false;

            Query q = session.createQuery(
                "FROM OtpToken WHERE provider = :p AND token = :o ORDER BY expiresAt DESC"
            );
            q.setParameter("p", p);
            q.setParameter("o", otpCode);
            q.setMaxResults(1);

            @SuppressWarnings("unchecked")
            List<OtpToken> tokens = q.list();
            return !tokens.isEmpty() && tokens.get(0).getExpiresAt().after(new Date());
        } finally {
            if (session != null) session.close();
        }
    }
*/
    @Override
    public void completeForgotPassword(int providerId, String newPassword) throws Exception {
        validatePassword(newPassword);

        Session session = null;
        Transaction tx = null;
        try {
            session = sf.openSession();
            tx = session.beginTransaction();

            Provider p = (Provider) session.get(Provider.class, (long) providerId);
            if (p == null) {
                throw new Exception("Provider not found.");
            }

            if (isAmongLastPasswords(session, p, newPassword)) {
                throw new Exception("Cannot reuse any of the last 3 passwords.");
            }

            // encrypt & update
            String newEnc = PasswordEncryptor.encrypt(newPassword);
            p.setPasswordHash(newEnc);
            session.update(p);
            session.save(new PasswordHistory(p, newEnc, new Date()));

            // clear used OTPs
            Query deleteQ = session.createQuery("DELETE FROM OtpToken WHERE provider = :p");
            deleteQ.setParameter("p", p);
            deleteQ.executeUpdate();

            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }

    /**
     * Checks the last 3 encrypted passwords against the encrypted form of newPassword.
     */
    private boolean isAmongLastPasswords(Session session, Provider p, String newPassword) {
        // encrypt once
        String newEnc = PasswordEncryptor.encrypt(newPassword);

        Query q = session.createQuery(
            "FROM PasswordHistory WHERE provider = :p ORDER BY createdAt DESC"
        );
        q.setParameter("p", p);
        q.setMaxResults(3);

        @SuppressWarnings("unchecked")
        List<PasswordHistory> last = q.list();
        return last.stream()
                   .anyMatch(ph -> ph.getPasswordHash().equals(newEnc));
    }

    /** Throws if rule broken */
    private void validatePassword(String pwd) throws Exception {
        if (pwd == null || !pwd.matches(PASS_REGEX)) {
            throw new Exception(
                "Password must be 8–12 characters long, include letters, digits and a special character."
            );
        }
    }
    
    public Provider findByEmail(String email) throws Exception {
        Session session = null;
        try {
            session = sf.openSession();
            Query q = session.createQuery("FROM Provider WHERE email = :e");
            q.setParameter("e", email);
            return (Provider) q.uniqueResult();
        } finally {
            if (session != null) session.close();
        }
    }

}
