package co.edu.eci.cvds.service;

import co.edu.eci.cvds.model.Login;
import co.edu.eci.cvds.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final LoginRepository loginRepository;

    @Autowired
    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public boolean authenticate(String username, String password) {
        return loginRepository.existsByUsername(username, password);
    }

    public boolean createAdminAccount(String username, String password) {
        // Verifica si ya existe un usuario con el nombre de usuario dado
        if (!loginRepository.existsByUsername(username, password)) {
            loginRepository.save(new Login(username, password));
            return true; 
        }
        return false; 
    }


}

