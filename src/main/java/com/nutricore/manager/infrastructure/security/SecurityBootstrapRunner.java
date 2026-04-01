package com.nutricore.manager.infrastructure.security;

import com.nutricore.manager.application.security.UserAccountProvisioningService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityBootstrapRunner implements ApplicationRunner {

    private final BootstrapUserProperties bootstrapUserProperties;
    private final UserAccountProvisioningService userAccountProvisioningService;

    @Override
    public void run(ApplicationArguments args) {
        if (!bootstrapUserProperties.isEnabled()) {
            return;
        }

        if (isBlank(bootstrapUserProperties.getFullName())
                || isBlank(bootstrapUserProperties.getEmail())
                || isBlank(bootstrapUserProperties.getPassword())) {
            throw new IllegalStateException("Bootstrap de segurança habilitado sem credenciais completas.");
        }

        userAccountProvisioningService.provisionBootstrapUser(
                bootstrapUserProperties.getFullName(),
                bootstrapUserProperties.getEmail(),
                bootstrapUserProperties.getPassword(),
                bootstrapUserProperties.getRole()
        );
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
