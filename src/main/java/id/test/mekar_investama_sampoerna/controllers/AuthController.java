package id.test.mekar_investama_sampoerna.controllers;

import id.test.mekar_investama_sampoerna.entities.ERole;
import id.test.mekar_investama_sampoerna.entities.RefreshToken;
import id.test.mekar_investama_sampoerna.entities.Role;
import id.test.mekar_investama_sampoerna.entities.User;
import id.test.mekar_investama_sampoerna.exceptions.TokenRefreshException;
import id.test.mekar_investama_sampoerna.payloads.requests.*;
import id.test.mekar_investama_sampoerna.payloads.response.*;
import id.test.mekar_investama_sampoerna.repositories.RoleRepository;
import id.test.mekar_investama_sampoerna.repositories.UserRepository;
import id.test.mekar_investama_sampoerna.securities.jwt.JwtUtils;
import id.test.mekar_investama_sampoerna.securities.service.RefreshTokenService;
import id.test.mekar_investama_sampoerna.securities.service.UserDetailsImpl;
import id.test.mekar_investama_sampoerna.services.UserBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserBalanceService userBalanceService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest)
    {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
                userDetails.getUsername(), userDetails.getEmail(), userDetails.getPhoneNo(), roles));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest, Errors errors) {
        ResponseData<UserResponse> response = new ResponseData<UserResponse>();

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            response.setStatus(false);
            response.setPayload(null);
            response.getMessages().add("Error: Username is already taken!");
            return ResponseEntity.badRequest().body(response);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            response.setStatus(false);
            response.setPayload(null);
            response.getMessages().add("Error: Email is already in use!");
            return ResponseEntity.badRequest().body(response);
        }

        if(userRepository.existsByPhoneNo(signUpRequest.getPhoneNo())) {
            response.setStatus(false);
            response.setPayload(null);
            response.getMessages().add("Error: Phone Number is already in use!");
            return ResponseEntity.badRequest().body(response);
        }

        if(errors.hasErrors()) {
            for(ObjectError error : errors.getAllErrors()) {
                response.getMessages().add(error.getDefaultMessage());
            }
            response.setStatus(false);
            response.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
                signUpRequest.getPhoneNo(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);
        Logger logger = Logger.getLogger(AuthController.class.getName());

        logger.log(Level.INFO, user.getId().toString());

        // save user account balance
        AddUserBalanceRequest addUserBalanceRequest = new AddUserBalanceRequest(user.getPhoneNo(), user);
        userBalanceService.addNew(addUserBalanceRequest);
        logger.log(Level.INFO, addUserBalanceRequest.getBalanceNo().toString());
        UserResponse userResponse = new UserResponse(user);
        response.setStatus(true);
        response.setPayload(userResponse);
        response.getMessages().add("User registered successfully!");
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@Valid @RequestBody LogOutRequest logOutRequest) {
        refreshTokenService.deleteByUserId(logOutRequest.getUserId());
        return ResponseEntity.ok(new MessageResponse("Log out successful!"));
    }
}
