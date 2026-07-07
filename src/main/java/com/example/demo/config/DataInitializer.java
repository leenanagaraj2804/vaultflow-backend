package com.example.demo.config;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
  
    @Override
    public void run(String... args) {
        if (userRepository.findByEmail("admin@vaultflow.io").isEmpty()) {
            User admin = new User();
            admin.setEmail("admin@vaultflow.io");
            admin.setPassword(passwordEncoder.encode("Admin@123"));
            admin.setRole("ADMIN");
            admin.setUserId("USR-FIN-001");
            admin.setOrganizationId("CORP-VAULT-01");
            userRepository.save(admin);
        }

        if (userRepository.findByEmail("auditor@vaultflow.io").isEmpty()) {
            User auditor = new User();
            auditor.setEmail("auditor@vaultflow.io");
            auditor.setPassword(passwordEncoder.encode("Auditor@123"));
            auditor.setRole("AUDITOR");
            auditor.setUserId("USR-FIN-002");
            auditor.setOrganizationId("CORP-VAULT-01");
            userRepository.save(auditor);
        }

        if (userRepository.findByEmail("treasurer@vaultflow.io").isEmpty()) {
            User treasurer = new User();
            treasurer.setEmail("treasurer@vaultflow.io");
            treasurer.setPassword(passwordEncoder.encode("Treasurer@123"));
            treasurer.setRole("TREASURER");
            treasurer.setUserId("USR-FIN-003");
            treasurer.setOrganizationId("CORP-VAULT-01");
            userRepository.save(treasurer);
        }
    }
}
