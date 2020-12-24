package by.questionary.service;

import by.questionary.domain.User;
import by.questionary.security.payload.request.SignupRequest;

public interface UserService {

    void saveUser(User user);

    boolean findUserByName(String name);

    boolean findUserByEmail(String email);

    User createUser(SignupRequest signupRequest);

    boolean comparePasswords(String password, String repeatedPassword);

}
