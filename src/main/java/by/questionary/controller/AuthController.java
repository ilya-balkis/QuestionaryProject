package by.questionary.controller;

import by.questionary.domain.User;
import by.questionary.security.entity.UserDetailsImpl;
import by.questionary.security.jwt.JwtUtils;
import by.questionary.security.payload.request.LoginRequest;
import by.questionary.security.payload.request.SignupRequest;
import by.questionary.security.payload.response.JwtResponse;
import by.questionary.security.payload.response.MessageResponse;
import by.questionary.service.MailSenderService;
import by.questionary.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/authentication")
public class AuthController {

    private final MailSenderService mailSenderService;
    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userServiceImpl;
    private final JwtUtils jwtUtils;

    @PostMapping("/signIn")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        String name = loginRequest.getName();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        name,
                        password
                ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication);

        log.info("Generate JWT: {}", jwt);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        JwtResponse jwtResponse = new JwtResponse(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.isEnabled(),
                roles);

        log.info("Create jwtResponse: {}", jwtResponse);

        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/signUp")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        String name = signUpRequest.getName();
        String email = signUpRequest.getEmail();
        String password = signUpRequest.getPassword();
        String repeatedPassword = signUpRequest.getRepeatedPassword();

        if (userServiceImpl.findUserByName(name)) {

            log.warn("Username {} is already taken!", name);
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userServiceImpl.findUserByEmail(email)) {

            log.warn("Email {} is already in use!", email);
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        if (userServiceImpl.comparePasswords(password, repeatedPassword)) {

            log.warn("Password and repeated password must be the same");
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Password and repeated password must be the same!"));
        }

        User user = userServiceImpl.createUser(signUpRequest);
        userServiceImpl.saveUser(user);
        mailSenderService.sendActivationMessage(user);

        log.info("User {} registered successfully!", name);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
