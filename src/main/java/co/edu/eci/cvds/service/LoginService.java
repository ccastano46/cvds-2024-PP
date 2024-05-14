package co.edu.eci.cvds.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.eci.cvds.repository.LoginRepository;

@Service
public class LoginService {

    private final LoginRepository loginRepository;

    @Autowired
    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public boolean authenticate(String username, String password) {
        return loginRepository.authenticate(username, password);
    }

    public boolean createAdminAccount(String username, String password) {
        return loginRepository.createAdminAccount(username, password);
    }
}