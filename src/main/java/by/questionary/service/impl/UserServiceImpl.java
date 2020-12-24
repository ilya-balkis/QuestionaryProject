package by.questionary.service.impl;

import by.questionary.domain.Role;
import by.questionary.domain.User;
import by.questionary.repository.UserRepository;
import by.questionary.security.payload.request.SignupRequest;
import by.questionary.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private static final boolean NOT_ACTIVATED = false;

    private final UserRepository userRepository;
    private final UUIDServiceImpl uuidServiceImpl;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    UserServiceImpl(UserRepository userRepository,
                    UUIDServiceImpl uuidServiceImpl,
                    PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.uuidServiceImpl = uuidServiceImpl;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean findUserByName(String name) {

        return userRepository.existsByName(name);
    }

    @Override
    public boolean findUserByEmail(String email) {

        return userRepository.existsByEmail(email);
    }

    @Override
    public void saveUser(User user) {

        save(user);

        log.info("User {} has been added", user);
    }

    @Override
    public User createUser(SignupRequest signupRequest) {

        User user = new User();
        fillUser(user, signupRequest);

        log.info("User {} has been created", user);

        return user;
    }

    @Override
    public boolean comparePasswords(String password, String repeatedPassword) {
        return password.equals(repeatedPassword);
    }

    private void fillUser(User user, SignupRequest signUpRequest) {

        String activationCode = uuidServiceImpl.createUUID();
        String password = signUpRequest.getPassword();
        String encodedPassword = passwordEncoder.encode(password);

        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(encodedPassword);
        user.setActivated(NOT_ACTIVATED);
        user.setRegistrationDate(new Date());
        user.setRoles(Collections.singleton(Role.ROLE_USER));
        user.setActivationCode(activationCode);
    }

    private void save(User user) {

        userRepository.save(user);
    }
}
