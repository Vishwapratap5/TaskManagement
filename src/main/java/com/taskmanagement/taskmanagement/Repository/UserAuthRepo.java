package com.taskmanagement.taskmanagement.Repository;

import com.taskmanagement.taskmanagement.Entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAuthRepo extends JpaRepository<UserAuth, Long> {
    Optional<UserAuth> findById(Long id);
    Optional<UserAuth> findByUsername(String username);
    Optional<UserAuth> findByUserOfficialEmail(String userOfficialEmail);

}
