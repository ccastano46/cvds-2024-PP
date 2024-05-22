package co.edu.eci.cvds.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.eci.cvds.model.Login;
import co.edu.eci.cvds.repository.LoginRepository;

@Service
public class LoginService {

    private final LoginRepository loginRepository;

    @Autowired
    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public boolean authenticate(String username, String password) {
        return loginRepository.existsByUsernameAndPassword(username,password);
    }

    public boolean createAdminAccount(String username, String password) {
        // Verifica si ya existe un usuario con el nombre de usuario dado
        if (!loginRepository.existsByUsername(username)) {
            loginRepository.save(new Login(username, password));
            return true;
        }
        return false;
    }

    public Login obtenerLogin(String username){
        return loginRepository.findByUsername(username).get(0);
    }

}
