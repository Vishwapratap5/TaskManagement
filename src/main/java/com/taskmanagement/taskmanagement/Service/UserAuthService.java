package com.taskmanagement.taskmanagement.Service;

import com.taskmanagement.taskmanagement.DTO.AuthResponseDTO;
import com.taskmanagement.taskmanagement.DTO.LoginRequestDTO;
import com.taskmanagement.taskmanagement.DTO.RegisterRequestDTO;
import com.taskmanagement.taskmanagement.Entity.UserAuth;
import com.taskmanagement.taskmanagement.Repository.UserAuthRepo;
import com.taskmanagement.taskmanagement.Security.JWTToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserAuthService {

    @Autowired
    private UserAuthRepo userAuthRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTToken jwtToken;

    String token;
    public String registerUser(RegisterRequestDTO register) {
       UserAuth user = new UserAuth();
       user.setUserName(register.userName);
       user.setUserOfficialEmail(register.userOfficialEmail);
       user.setPassword(register.password);
       user.setRole(register.role);

       UserAuth isUserPresent=userAuthRepo.findByUserOfficialEmail(register.userOfficialEmail).orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST,"User Already Exist"));
       userAuthRepo.save(user);
        token= jwtToken.generateToken(user);
        return "registration Successfull";
    }

    public AuthResponseDTO loginUser(LoginRequestDTO login) {
        UserAuth user=userAuthRepo.findByUserOfficialEmail(login.userOfficialEmail).orElseThrow(()->new RuntimeException("user not found..!") );
                if(passwordEncoder.matches(login.password,user.getPassword())){
                    throw new RuntimeException("Wrong password/ Invalid credentials");
                }

                return jwtToken.generateToken(token,"loged in Successfull..!");
    }
}
