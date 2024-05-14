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

import co.edu.eci.cvds.service.LoginService;

import java.time.Instant;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/authenticate")
    public String authenticate(@RequestParam("username") String username, @RequestParam("password") String password) {
        if (loginService.authenticate(username, password)) {
            return "redirect:/dashboard"; // Redireccionar al dashboard si la autenticación es exitosa
        } else {
            return "redirect:/login?error"; // Redireccionar de nuevo a la página de inicio de sesión con un mensaje de error
        }
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerAdminAccount(@RequestParam("username") String username, @RequestParam("password") String password) {
        if (loginService.createAdminAccount(username, password)) {
            return "redirect:/login?success"; // Redireccionar a la página de inicio de sesión con un mensaje de éxito
        } else {
            return "redirect:/login?error"; // Redireccionar a la página de inicio de sesión con un mensaje de error
        }
    }

}
