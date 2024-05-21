package co.edu.eci.cvds.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

    private static final String LOGIN_PAGE = "login/login";

    @Autowired
    // Constructor vacío (la anotación @Autowired no es necesaria aquí)
    public LoginController() {
    }

    // Manejador para la solicitud GET en /login
    @GetMapping("")
    public String login() {
        return LOGIN_PAGE; // Retorna la página de inicio de sesión
    }

    // Manejador para la solicitud GET en /login/register
    @GetMapping("register")
    public String register() {
        return "login/register"; // Retorna la página de registro
    }

}
