package com.taskmanagement.taskmanagement.Client;

import com.taskmanagement.taskmanagement.Enums.Role;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

public interface UserClient {

    @GetMapping("/api/users/{email}/roles")
    Set<Role> getRoles(@PathVariable("email") String email);
}
