package com.nutricore.manager.infrastructure.security;

import com.nutricore.manager.domain.enums.security.UserRole;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.security.bootstrap")
public class BootstrapUserProperties {

    private boolean enabled;
    private String fullName;
    private String email;
    private String password;
    private UserRole role = UserRole.ADMIN;
}
