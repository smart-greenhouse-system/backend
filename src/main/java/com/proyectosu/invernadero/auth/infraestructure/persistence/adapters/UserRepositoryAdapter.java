package com.proyectosu.invernadero.auth.infraestructure.persistence.adapters;

import com.proyectosu.invernadero.auth.domain.model.User;
import com.proyectosu.invernadero.auth.domain.ports.UserRepositoryPort;
import com.proyectosu.invernadero.auth.infraestructure.persistence.entity.UserEntity;
import com.proyectosu.invernadero.auth.infraestructure.persistence.mapper.UserMapper;
import com.proyectosu.invernadero.auth.infraestructure.persistence.repository.UserJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;

    public UserRepositoryAdapter(UserJpaRepository userJpaRepository, UserMapper userMapper) {
        this.userJpaRepository = userJpaRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User save(User user) {
        UserEntity entity = userMapper.toEntity(user);
        UserEntity saved = userJpaRepository.save(entity);
        return userMapper.toDomain(saved);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email)
                .map(userMapper::toDomain);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userJpaRepository.findById(id)
                .map(userMapper::toDomain);

    }

    @Override
    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }
}
