package org.example.app.repository;

import org.apache.log4j.Logger;
import org.example.web.dto.LoginForm;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class LoginRepository implements UserRepository<LoginForm> {
    private final Logger logger = Logger.getLogger(LoginRepository.class);
    private final List<LoginForm> userList = new ArrayList<>();

    @Override
    public List<LoginForm> retreiveAll() {
        return new ArrayList<>(userList);
    }

    @Override
    public void save(LoginForm loginForm) {
        int isExists = 0;
        for (LoginForm user : retreiveAll()) {
            if (user.getUsername().equals(loginForm.getUsername()) && user.getPassword().equals(loginForm.getPassword())) {
                logger.info("Exists user: " + user);
                isExists = 1;
            }
        }
        if (isExists != 1) {
            loginForm.setId(loginForm.hashCode());
            logger.info("store new book: " + loginForm);
            userList.add(loginForm);
        }
    }

    @Override
    public boolean authUser(LoginForm loginForm) {
        int isExists = 0;
        for (LoginForm user : retreiveAll()) {
            if (user.getUsername().equals(loginForm.getUsername()) && user.getPassword().equals(loginForm.getPassword())) {
                logger.info("Exists user: " + user);
                isExists = 1;
            }
        }
        return (isExists == 1);
    }
}
