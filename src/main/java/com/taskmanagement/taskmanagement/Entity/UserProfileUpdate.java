package com.taskmanagement.taskmanagement.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name="user_profile_update")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileUpdate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    private String userOfficialEmail;

    @Column(unique = true,nullable = false)
    private String userName;

    @Column(nullable = false)
    private String department;

    private String designation;

    @Column(nullable = false)
    private String organizationName;

    private boolean active;

    @CreationTimestamp
    private LocalDateTime createdDate=LocalDateTime.now();
}
