package co.edu.eci.cvds.repository;

import java.util.HashMap;
import java.util.Map;

public class LoginRepository {
    private final Map<String, String> users = new HashMap<>();

    public boolean authenticate(String username, String password) {
        // Lógica de autenticación
        return users.containsKey(username) && users.get(username).equals(password);
    }

    public boolean createAdminAccount(String username, String password) {
        // Lógica para crear una cuenta de administrador
        if (!users.containsKey(username)) {
            users.put(username, password);
            return true; 
        }
        return false;
    }
}
