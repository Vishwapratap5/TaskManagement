package com.taskmanagement.taskmanagement.Entity;

import com.taskmanagement.taskmanagement.Enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name="userAuth")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserAuth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String userName;

    @Column(unique=true,nullable=false)
    private String password;

    @Column(unique=true,nullable=false)
    private String userOfficialEmail;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String resetToken;
    private LocalDateTime resetTokenExpire;


}
