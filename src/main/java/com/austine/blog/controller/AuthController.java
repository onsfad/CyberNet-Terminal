//package com.austine.blog.controller;
//
//
//import com.austine.blog.Dto.RegisterRequest;
//import com.austine.blog.service.AuthService;
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import static org.springframework.http.HttpStatus.OK;
//
//@RestController
//@RequestMapping("/api/auth")
//@AllArgsConstructor
//public class AuthController {
//
//    private AuthService authService;
//
//
//    @PostMapping("/signup")
//    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest){
//        authService.signup(registerRequest);
//        return new ResponseEntity<>("User Registration Successful", HttpStatus.OK);
//    }
//
////    @GetMapping("accountVerification/{token}")
////    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
////        authService.(token);
////        return new ResponseEntity<>("Account Activated Successfully", OK);
////    }
//
//}
