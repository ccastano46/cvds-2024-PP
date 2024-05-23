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
import org.springframework.web.servlet.ModelAndView;

import co.edu.eci.cvds.service.LoginService;

import java.time.Instant;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

    private static final String LOGIN_PAGE = "login/login";
    private static final String ERROR_LOGIN_PAGE = "login/errorLogin"; // Nueva constante para la página de error
    private static final String SUCCESS_LOGIN_PAGE = "login/successLogin"; // Nueva constante para la página de éxito
    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
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

    // Login
    @PostMapping("") 
    public String authenticate(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        if (loginService.authenticate(username, password)) {
            return "login/successLogin"; // Redireccionar al dashboard si la autenticación es exitosa
        } else {
            model.addAttribute("errorMessage", "¡Error al loggear el administrador!"); // Agrega el mensaje de error al modelo            
            return LOGIN_PAGE; // Retorna la página de inicio de sesión
        }
    }

     // Método para registrar administrador
     @PostMapping("/register")
     public String registerAdminAccount(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
         if (loginService.createAdminAccount(username, password)) {
            model.addAttribute("successMessage", "¡Registro exitoso!"); // Agrega el mensaje de éxito al modelo 
            return "login/register"; // Redireccionar a la página de éxito de registro
         } else {
             return "login/errorLogin"; // Redireccionar a la página de error
         }
    }    
    
    // Manejador para la solicitud GET en /login/error
    @GetMapping("error")
    public String errorLogin() {
        return ERROR_LOGIN_PAGE; // Retorna la página de error
    }

    @GetMapping("success")
    public String successLogin() {
        return SUCCESS_LOGIN_PAGE; // Retorna la página de éxito
    }

}
