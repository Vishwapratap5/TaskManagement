package com.taskmanagement.taskmanagement.Repository;

import com.taskmanagement.taskmanagement.Entity.UserProfileUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileUpdateRepo extends JpaRepository<UserProfileUpdate,Long> {

    Optional<UserProfileUpdate> findByUserOfficialEmail(String email);
}
