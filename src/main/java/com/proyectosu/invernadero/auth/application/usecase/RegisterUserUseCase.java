package com.proyectosu.invernadero.auth.application.usecase;

import com.proyectosu.invernadero.auth.domain.model.Role;
import com.proyectosu.invernadero.auth.domain.model.User;
import com.proyectosu.invernadero.auth.domain.model.enums.UserStatus;
import com.proyectosu.invernadero.auth.domain.ports.PasswordEncoderPort;
import com.proyectosu.invernadero.auth.domain.ports.RoleRepositoryPort;
import com.proyectosu.invernadero.auth.domain.ports.UserRepositoryPort;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public class RegisterUserUseCase {

    private final UserRepositoryPort userRepository;
    private final PasswordEncoderPort passwordEncoder;
    private final RoleRepositoryPort roleRepository;

    public RegisterUserUseCase(UserRepositoryPort userRepository,
                               PasswordEncoderPort passwordEncoder,
                               RoleRepositoryPort roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public void execute(String email, String password) {

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("ese email ya se encuentra en uso");
        }
        if (!User.esPasswordValida(password)) {
            throw new IllegalArgumentException("La contraseña no es válida. Debe tener al menos 8 caracteres, incluir una mayúscula, una minúscula, un número y un carácter especial.");
        }

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() ->
                        new RuntimeException("Error por defincion de rol del sistema"));

        String encodedPassword = passwordEncoder.encode(password);

        User user = new User(
                null,
                email,
                encodedPassword,
                UserStatus.ACTIVE,
                LocalDateTime.now(),
                Set.of(userRole)
        );

        userRepository.save(user);
    }

}
