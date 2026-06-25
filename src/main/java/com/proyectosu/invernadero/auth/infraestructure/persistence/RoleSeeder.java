package com.proyectosu.invernadero.auth.infraestructure.persistence;

import com.proyectosu.invernadero.auth.infraestructure.persistence.entity.RoleEntity;
import com.proyectosu.invernadero.auth.infraestructure.persistence.repository.RoleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleSeeder implements CommandLineRunner {
    private final RoleJpaRepository roleJpaRepository;

    @Override
    public void run(String... args) {

        crearRolSiNoExiste("ROLE_USER");
        crearRolSiNoExiste("ROLE_ADMIN");

    }

    private void crearRolSiNoExiste(String nombre) {

        boolean existe = roleJpaRepository.existsByName(nombre);

        if (!existe) {

            RoleEntity role = new RoleEntity();

            role.setName(nombre);

            roleJpaRepository.save(role);

            System.out.println("Rol creado: " + nombre);
        }

    }
}
