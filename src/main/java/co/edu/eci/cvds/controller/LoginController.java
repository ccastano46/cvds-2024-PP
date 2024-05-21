package co.edu.eci.cvds.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import co.edu.eci.cvds.service.LoginService;

@Controller
@RequestMapping(value = "/LincolnLines/privado")
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

    @PostMapping("/autentificar")
    public String authenticate(@RequestParam("username") String username, @RequestParam("password") String password) {
        if (loginService.authenticate(username, password)) {
            return "redirect:/dashboard"; // Redireccionar al dashboard si la autenticación es exitosa
        } else {
            return "redirect:/login?error"; // Redireccionar de nuevo a la página de inicio de sesión con un mensaje de error
        }
    }

    @GetMapping("/formularioRegistro")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/respuestaRegistro")
    public String registerAdminAccount(@RequestParam("username") String username, @RequestParam("password") String password) {
        if (loginService.createAdminAccount(username, password)) {
            return "redirect:/login?success"; // Redireccionar a la página de inicio de sesión con un mensaje de éxito
        } else {
            return "redirect:/login?error"; // Redireccionar a la página de inicio de sesión con un mensaje de error
        }
    }

}
