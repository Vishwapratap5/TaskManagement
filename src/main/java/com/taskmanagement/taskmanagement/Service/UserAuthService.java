package com.taskmanagement.taskmanagement.Service;

import com.taskmanagement.taskmanagement.DTO.*;
import com.taskmanagement.taskmanagement.Email.EmailService;
import com.taskmanagement.taskmanagement.Entity.UserAuth;
import com.taskmanagement.taskmanagement.Repository.UserAuthRepo;
import com.taskmanagement.taskmanagement.Security.JWTToken;
import com.taskmanagement.taskmanagement.Security.TokenBlockListService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserAuthService {

    @Autowired
    private UserAuthRepo userAuthRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTToken jwtToken;

    @Autowired
    private EmailService emailService;

    @Autowired
    TokenBlockListService tokenBlockListService;

    String token;
    public String registerUser(RegisterRequestDTO register) {

        Optional<UserAuth> existingEmail=userAuthRepo.findByUserOfficialEmail(register.userOfficialEmail);
        if(existingEmail.isPresent()){
            throw new RuntimeException("User already exists");
        }
       UserAuth user = new UserAuth();
       user.setUserName(register.userName);
       user.setUserOfficialEmail(register.userOfficialEmail);
       user.setPassword(passwordEncoder.encode(register.password));
       user.setRole(register.role);

       userAuthRepo.save(user);
        token=jwtToken.generateToken(user);
       return "registration Successful";
    }

    public AuthResponseDTO loginUser(LoginRequestDTO login) {

        UserAuth user = userAuthRepo
                .findByUserOfficialEmail(login.userOfficialEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(login.password, user.getPassword())) {
            throw new RuntimeException("Wrong password / Invalid credentials");
        }

        String token = jwtToken.generateToken(user);

        return new AuthResponseDTO(token, "Logged in successfully");
    }

    public void forgotPassword(ForgotPasswrdDTO forgotPasswrd) {

        UserAuth user=userAuthRepo.findByUserOfficialEmail(forgotPasswrd.userOfficialEmail).orElseThrow(() -> new RuntimeException("User not found"));

        String token= UUID.randomUUID().toString();
        user.setResetToken(token);

        user.getResetTokenExpire();
        new Date(System.currentTimeMillis()+10*60*1000);
        userAuthRepo.save(user);

        String resetLink="http://localhost:8080/auth/reset-password/token?"+token;
        emailService.passwordMail(user.getUserOfficialEmail(),resetLink);
        System.out.println("Reset token: "+token);
    }


    public void resetPassword(ResetPasswordRequestDTO resetPassword) {

        UserAuth user = userAuthRepo
                .findByResetToken(resetPassword.token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (user.getResetTokenExpire() == null) {
            throw new RuntimeException("Token expiry missing. Request again.");
        }

        if (user.getResetTokenExpire().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired");
        }

        user.setPassword(passwordEncoder.encode(resetPassword.newPassword));
        user.setResetToken(null);
        user.setResetTokenExpire(null);

        userAuthRepo.save(user);
    }

    public String loggedOut(HttpServletRequest request) {
        String header=request.getHeader("Authorization");
        String token= jwtToken.extractToken(header);

        if(token==null){
            tokenBlockListService.blockedToken(token);
        }

        return "Logged out";
    }

}
