
package com.simran.job_management.dto;

import com.simran.job_management.entity.UserRole;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class UserRequestDTO {

    private String name;
    private String email;
    private String password;
    private UserRole role;
}
