package com.taskmanagement.taskmanagement.Security;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TokenBlockListService {

    private final Set<String> kiledTokens= ConcurrentHashMap.newKeySet();

    public void blockedToken(String token){
        kiledTokens.add(token);
    }

    public boolean isBlockedToken(String token){
        return kiledTokens.contains(token);
    }
}
