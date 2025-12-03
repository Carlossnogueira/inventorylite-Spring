package com.github.carlossnogueira.inventorylite.api.application.service.User;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.carlossnogueira.inventorylite.api.exception.BussinesValidationException;
import com.github.carlossnogueira.inventorylite.api.exception.UserException.UserAlreadyExistsException;
import com.github.carlossnogueira.inventorylite.domain.dto.request.CreateUserJson;
import com.github.carlossnogueira.inventorylite.domain.dto.request.UpdateUserJson;
import com.github.carlossnogueira.inventorylite.domain.dto.response.CreateUserSuccessJson;
import com.github.carlossnogueira.inventorylite.domain.entities.User;
import com.github.carlossnogueira.inventorylite.domain.repositories.IUserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService {

    private final IUserRepository userRepository;

    public void create(CreateUserJson request) {
        boolean userExists = userRepository.existsByEmail(request.getEmail());

        if (userExists) {
            throw new UserAlreadyExistsException();
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .userIdentifier("")
                .build();

        userRepository.saveAndFlush(user);
    }
    
    public CreateUserSuccessJson searchById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BussinesValidationException(
                        List.of("User doesn't exist")));

        return CreateUserSuccessJson.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public CreateUserSuccessJson searchByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BussinesValidationException(
                        List.of("User doesn't exist")));

        return CreateUserSuccessJson.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public void deleteById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BussinesValidationException(
                        List.of("User doesn't exist")));

        userRepository.delete(user);
    }

    public CreateUserSuccessJson update(Long id, UpdateUserJson updatedData) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BussinesValidationException(List.of("User not found")));

        if (updatedData.getName() != null) {
            user.setName(updatedData.getName());
        }

        if (updatedData.getEmail() != null) {

            boolean emailExists = userRepository.existsByEmail(updatedData.getEmail());
            if (emailExists && !user.getEmail().equals(updatedData.getEmail())) {
                throw new UserAlreadyExistsException();
            }
            user.setEmail(updatedData.getEmail());
        }

        if (updatedData.getPassword() != null) {
            user.setPassword(updatedData.getPassword());
        }

        userRepository.saveAndFlush(user);

        return CreateUserSuccessJson.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
