package com.austine.blog.controller;

import com.austine.blog.Dto.LoginRequest;
import com.austine.blog.Dto.UserDto;
import com.austine.blog.MessageUtil.ApiResponse;
import com.austine.blog.MessageUtil.CustomMessages;
import com.austine.blog.exceptions.RecordAlreadyPresentException;
import com.austine.blog.model.auth.OauthClientDetails;
import com.austine.blog.model.auth.Role;
import com.austine.blog.model.auth.User;
import com.austine.blog.repository.UserDetailRepository;
import com.austine.blog.service.auth.OauthService;

import io.swagger.annotations.ApiOperation;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/v1/user")
@Validated
public class UserController {


    @Autowired
    OauthService oauthService;

    @Autowired
    UserDetailRepository userDetailRepository;

    @Autowired
    private UserDetailRepository userService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private AuthenticationManager authenticationManager;


//    private MailSenderService mailSenderService;

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    ModelMapper modelMapper = new ModelMapper();

    @PostMapping("/authenticateAndGetUserRoles")
    public ResponseEntity authenticateUser(@RequestBody LoginRequest loginRequest) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);


        User principal = (User) authentication.getPrincipal();

        UserDto userDTO = modelMapper.map(principal, UserDto.class);

        //get userID from database

        Optional<User> userIdToGet = oauthService.getUserByUsername(principal.getUsername());


        userDTO.setId(userIdToGet.get().getId());
        return ResponseEntity.ok().body(new ApiResponse<>(CustomMessages.Success, userDTO));
    }


    @ApiOperation(value = "Get All Active Users")
    @GetMapping("/user")
    public Object getAllUser() {
        List<User> userInfos = userService.findAll();
        if (userInfos == null || userInfos.isEmpty()) {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
        return userInfos;
    }

    @ApiOperation(value = "Create new User")
    @PostMapping("/createUser")
    public ResponseEntity addUser(@RequestBody User userRecord) {

        Optional<User> findUserByUsername = oauthService.getUserByUsername(userRecord.getUsername());
        try {
            if (!findUserByUsername.isPresent()) {
                log.info("Sending Person {} from Client with Values " + "Surname: " + userRecord.getSurname() + " LastName: " + userRecord.getLastName() + " Password: " + userRecord.getPassword() + "Email: " + userRecord.getEmail());

                if (userRecord == null) {
                    return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Null Object was submitted"));
                }

                String encodedPassword = passwordEncoder.encode(userRecord.getPassword());
//        String encodedClientSecret = passwordEncoder.encode(userRecord.getClientSecret());

                userRecord.setPassword(encodedPassword);
                userRecord.setAccountNonExpired(true);
                userRecord.setEnabled(true);
                userRecord.setDateCreated(new Date());
                userRecord.setCredentialsNonExpired(true);
                userRecord.setAccountNonLocked(true);
//        userRecord.setRoles(Arrays.asList(new Role("USER")));

                OauthClientDetails clientDetails = new OauthClientDetails();
                clientDetails.setAccessTokenValidity(3600);
                clientDetails.setAutoapprove("");
                clientDetails.setAdditionalInformation("{}");
                clientDetails.setAuthorizedGrantTypes("authorization_code,password,refresh_token,implicit");
//        clientDetails.setClientId(userRecord.getClientId());
                clientDetails.setClientId(userRecord.getUsername());
//        clientDetails.setClientSecret(encodedClientSecret);
                clientDetails.setClientSecret(encodedPassword);
                clientDetails.setRefreshTokenValidity(10000);
                clientDetails.setResourceIds("cyberlink-rest-api");
                clientDetails.setAuthorities("ROLE_user");
                clientDetails.setScope("READ,WRITE");
                clientDetails.setWebServerRedirectUri("http://localhost:5050/");

                System.out.println(userRecord);

                oauthService.saveUser(userRecord);


                oauthService.saveClientDetails(clientDetails);


                return ResponseEntity.ok(new ApiResponse<>(CustomMessages.Success, userRecord));


            } else throw new RecordAlreadyPresentException(
                    "User with username: " + userRecord.getUsername() + " already exists!!");
        } catch (RecordAlreadyPresentException e) {
            return ResponseEntity.ok(new ApiResponse<>(CustomMessages.AlreadyExist, "User with username: " + userRecord.getUsername() + " already exists!!"));
        }
    }

    @ApiOperation("To add role to user")
    @PostMapping(value = "/add/userRole", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addSupplierAccount(@RequestBody User user) {

        List<Role> roleList = user.getRoles().stream().distinct().collect(Collectors.toList());
        user.setRoles(roleList);
        User savedSupplierAccount = oauthService.saveUser(user);
        return ResponseEntity.ok(new ApiResponse<>(CustomMessages.Success, savedSupplierAccount));
    }

    @ApiOperation("Save Roles")
    @PostMapping(value = "/add/role", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addRoles(@RequestBody Role user) {
        Role roles = oauthService.saveRole(user);
        return ResponseEntity.ok(new ApiResponse<>(CustomMessages.Success, roles));
    }


    @ApiOperation(value = "Get User by ID")
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> User = userService.findUsersById(id);
        if (User == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(User.get(), HttpStatus.OK);
    }


    @ApiOperation("To delete a User details by ID")
    @DeleteMapping("/user/{id}")
    public ResponseEntity deleteProductById(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
//        userService.deleteUserById(id);
        return ResponseEntity.ok(new ApiResponse<>(CustomMessages.Deleted, CustomMessages.DeletedMessage));
    }

    @ApiOperation("To return all Roles")
    @GetMapping("/list/roles")
    public List<Role> getAllRoles() {
        return oauthService.findAllRoles();
    }

    @ApiOperation("To delete Role by ID")
    @DeleteMapping("/deleteAssignedRole/{userId}/{roleId}")
    public ResponseEntity deleteAssignedRole(@PathVariable("userId") Long userId, @PathVariable("roleId") Long roleId) {
        Optional<User> user = oauthService.findUserById(userId);
        if (user.isPresent()) {
            List<Role> roleList = user.get().getRoles().stream().collect(Collectors.toList());
            List<Role> filteredRoleList = roleList.stream().filter(x -> x.getId() != roleId).collect(Collectors.toList());
            user.get().setRoles(filteredRoleList);
            User savedUser = oauthService.saveUser(user.get());
        }

        return ResponseEntity.ok().body(new ApiResponse<>(CustomMessages.Success, CustomMessages.DeletedMessage));
    }

}
