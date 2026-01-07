package com.taskmanagement.taskmanagement.DTO;

import com.taskmanagement.taskmanagement.Enums.Role;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class RegisterRequestDTO {
    private Long id;
    private String userName;
    private String userOfficialEmail;
    private Role role;
}
