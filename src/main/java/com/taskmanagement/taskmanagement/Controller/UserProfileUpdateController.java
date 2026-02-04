package com.taskmanagement.taskmanagement.Controller;

import com.taskmanagement.taskmanagement.DTO.UserProfileUpdateDTO;
import com.taskmanagement.taskmanagement.Service.UserProfileUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/update-profile")
@RequiredArgsConstructor
public class UserProfileUpdateController {
    @Autowired
    private UserProfileUpdateService userProfileUpdateService;

    @PutMapping("/update-profile")
    public ResponseEntity<UserProfileUpdateDTO> updateProfile(@RequestBody UserProfileUpdateDTO userProfileUpdateDTO){
      return ResponseEntity.ok(userProfileUpdateService.updateUserProfile(userProfileUpdateDTO));
    }

    @GetMapping("/allprofiles")
    public ResponseEntity<List<UserProfileUpdateDTO>> getAllUserProfiles(){
        return ResponseEntity.ok(userProfileUpdateService.getAllProfiles());
    }

    @GetMapping("/get-by-email/{email}")
    public ResponseEntity<UserProfileUpdateDTO> getUserProfileByEmail(@PathVariable("email") String email){
        return ResponseEntity.ok(userProfileUpdateService.getProfileByUserOfficialEmail(email));
    }
}
