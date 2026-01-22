package com.taskmanagement.taskmanagement.Controller;

import com.taskmanagement.taskmanagement.DTO.*;
import com.taskmanagement.taskmanagement.Service.UserAuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class UserAuthController {

    @Autowired
    private UserAuthService userAuthService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequestDTO registerRequestDTO) {
        String registerMessage=userAuthService.registerUser(registerRequestDTO);
        return new ResponseEntity<>(registerMessage, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> loginUser(@RequestBody LoginRequestDTO loginRequestDTO) {
        return  ResponseEntity.ok(userAuthService.loginUser(loginRequestDTO));
    }

    @PostMapping("/logOut")
    public ResponseEntity<String> logoutUser(HttpServletRequest request) {
        return ResponseEntity.ok(userAuthService.loggedOut(request));
    }

    @PostMapping("/fogot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswrdDTO forgotPasswrdDTO) {
        userAuthService.forgotPassword(forgotPasswrdDTO);
        return ResponseEntity.ok("Reset password link sent on Email");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequestDTO resetPasswrdDTO) {
        userAuthService.resetPassword(resetPasswrdDTO);
        return ResponseEntity.ok("Password reset Successfully");
    }

}
