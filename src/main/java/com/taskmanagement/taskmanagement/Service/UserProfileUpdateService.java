package com.taskmanagement.taskmanagement.Service;

import com.taskmanagement.taskmanagement.DTO.UserProfileUpdateDTO;
import com.taskmanagement.taskmanagement.Entity.UserProfileUpdate;
import com.taskmanagement.taskmanagement.Repository.UserProfileUpdateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileUpdateService {

    @Autowired
    private UserProfileUpdateRepo userProfileUpdateRepo;

    public UserProfileUpdate updateUserProfile(UserProfileUpdateDTO dto) {

        UserProfileUpdate profile = userProfileUpdateRepo
                .findByUserOfficialEmail(dto.getUserOfficialEmail())
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        profile.setUserName(dto.getUserName());
        profile.setDepartment(dto.getDepartment());
        profile.setDesignation(dto.getDesignation());
        profile.setOrganizationName(dto.getOrganizationName());
        profile.setActive(dto.isActive());

        return userProfileUpdateRepo.save(profile);
    }

    public List<UserProfileUpdateDTO> getAllProfiles(){
        return userProfileUpdateRepo.findAll().stream().map(this::toDTO).toList();
    }

    public UserProfileUpdateDTO getProfileByUserOfficialEmail(String userOfficialEmail){
        UserProfileUpdate userProfileUpdate=userProfileUpdateRepo.findByUserOfficialEmail(userOfficialEmail).orElseThrow(()->new RuntimeException("Not present..!"));
        return toDTO(userProfileUpdate);
    }
    private UserProfileUpdateDTO toDTO(UserProfileUpdate userProfileUpdate) {
        UserProfileUpdateDTO userProfileUpdateDTO = new UserProfileUpdateDTO();

        userProfileUpdateDTO.setUserOfficialEmail(userProfileUpdate.getUserOfficialEmail());
        userProfileUpdateDTO.setActive(userProfileUpdate.isActive());
        userProfileUpdateDTO.setDepartment(userProfileUpdate.getDepartment());
        userProfileUpdateDTO.setOrganizationName(userProfileUpdate.getOrganizationName());
        userProfileUpdateDTO.setUserName(userProfileUpdate.getUserName());

        return userProfileUpdateDTO;
    }


}
