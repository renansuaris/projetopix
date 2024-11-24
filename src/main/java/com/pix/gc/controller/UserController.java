package com.pix.gc.controller;
import com.pix.gc.dto.UserDto;
import com.pix.gc.entities.User;
import com.pix.gc.mapper.UserMapper;
import com.pix.gc.repo.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "User Controler")
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    // ----------- End Points ----------- //

    @Operation(summary = "Create a User")
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid User user) {
        User createdUser = userRepository.save(user);
        UserDto userDto = userMapper.userToUserDto(createdUser);
        return ResponseEntity.ok(userDto);
    }

    @Operation(summary = "Get a User by specified ID")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        User foundUser = userRepository.findById(id).orElse(null);
        if (foundUser == null) {
            return ResponseEntity.notFound().build();  // Retorna 404 se não encontrar o usuário
        }
        UserDto userDto = userMapper.userToUserDto(foundUser);
        return ResponseEntity.ok(userDto);
    }

    @Operation(summary = "Get all Users")
    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = users.stream()
                .map(userMapper::userToUserDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDtos);
    }

    @Operation(summary = "Delete a User by specified ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) throws Exception {
        if(userRepository.existsById(id)) {
            userRepository.deleteById(id);
        }
        else{
            throw new RuntimeException(); // MELHORAR ESSA LÓGICA, CRIAR EXCEÇÕES PERSONALIZADAS
                                          // OU CLASSES DE SERVIÇO
        }
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Update a User by specified ID")
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody @Valid UserDto userDto) {
        // Verifica se o usuário existe
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser == null) {
            return ResponseEntity.notFound().build();
        }

        existingUser.setName(userDto.name());
        existingUser.setEmail(userDto.email());
        existingUser.setPhoneNumber(userDto.phoneNumber());

        User updatedUser = userRepository.save(existingUser);
        UserDto updatedUserDto = userMapper.userToUserDto(updatedUser);

        return ResponseEntity.ok(updatedUserDto);
    }


}
