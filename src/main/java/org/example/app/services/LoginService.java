package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.app.repository.UserRepository;
import org.example.web.dto.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private Logger logger = Logger.getLogger(LoginService.class);
    private final UserRepository<LoginForm> userRepo;

    @Autowired
    public LoginService(UserRepository<LoginForm> userRepo) {
        this.userRepo = userRepo;
    }

    public boolean authenticate(LoginForm loginForm) {
        logger.info("try auth with user-form: " + loginForm);
        return userRepo.authUser(loginForm);
        //return loginForm.getUsername().equals("root") && loginForm.getPassword().equals("123");
    }

    public void register(LoginForm loginForm) {
        logger.info("try to add user with user-form: " + loginForm);
        userRepo.save(loginForm);
    }
}
