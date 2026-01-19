package com.taskmanagement.taskmanagement.DTO;

import com.taskmanagement.taskmanagement.Enums.Role;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RegisterRequestDTO {
    public Long id;
    public String userName;
    public String userOfficialEmail;
    public String password;
    public Role role;
}
