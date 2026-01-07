package com.taskmanagement.taskmanagement.DTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Data
@Builder
public class AuthResponseDTO {
    public String token;
    public String message;
}
