package com.taskmanagement.taskmanagement.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileUpdateDTO {

    private String userOfficialEmail;

    private String userName;

    private String department;

    private String designation;

    private String organizationName;

    private boolean active;

}
