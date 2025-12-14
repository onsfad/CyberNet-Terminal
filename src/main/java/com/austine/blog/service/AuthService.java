//package com.austine.blog.service;
//
//import com.austine.blog.Dto.RegisterRequest;
//import com.austine.blog.model.NotificationEmail;
//import com.austine.blog.model.User;
//import com.austine.blog.service.MailService;
//import com.austine.blog.model.VerificationToken;
//import com.austine.blog.repository.UserRepository;
//import com.austine.blog.repository.VerificationTokenRepository;
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//import java.util.Date;
//import java.util.UUID;
//
//@Service
//@Transactional
//@AllArgsConstructor
//public class AuthService {
//
//
//    private final MailService mailService;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private VerificationTokenRepository verificationTokenRepository;
//
//    public AuthService() {
//        mailService = new MailService();
//    }
//
//
//    public void signup(RegisterRequest registerRequest) {
//        User user = new User();
//        user.setUsername(registerRequest.getUsername());
//        user.setEmail(registerRequest.getEmail());
//        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
//        user.setDate(new Date());
//        user.setEnabled(false);
//
//        userRepository.save(user);
//
//        String token = generateVerificationToken(user);
//        mailService.sendMail(new NotificationEmail("Please Activate your CyberLink Account",
//                user.getEmail(), "Thank you for signing up to CyberLink, " +
//                "please click on the below url to activate your account : " +
//                "http://localhost:5050/cyberLink/api/auth/accountVerification/" + token));
//    }
//
//    private String generateVerificationToken(User user) {
//        String token = UUID.randomUUID().toString();
//        VerificationToken verificationToken = new VerificationToken();
//        verificationToken.setToken(token);
//        verificationToken.setUser(user);
//
//        verificationTokenRepository.save(verificationToken);
//        return token;
//
//    }
//
//
//}
