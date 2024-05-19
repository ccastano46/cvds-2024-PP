package co.edu.eci.cvds;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import co.edu.eci.cvds.service.ClienteService;
import co.edu.eci.cvds.service.CotizacionSerrvice;
import co.edu.eci.cvds.service.LoginService;
import co.edu.eci.cvds.service.ProductoService;
import co.edu.eci.cvds.service.VehiculoService;
import lombok.extern.slf4j.Slf4j;



@SpringBootApplication
@Slf4j
public class SpringApplicationCvds {

    private final ProductoService productoService;
    private final VehiculoService vehiculoService;
    private final CotizacionSerrvice cotizacionSerrvice;
    private final ClienteService clienteService;
    private final LoginService loginService;
    @Autowired
    public SpringApplicationCvds(
            ProductoService productoService,
            VehiculoService vehiculoService,
            CotizacionSerrvice cotizacionService,
            ClienteService clienteService,
            LoginService loginService

    ) {
        this.productoService = productoService;
        this.vehiculoService = vehiculoService;
        this.cotizacionSerrvice = cotizacionService;
        this.clienteService = clienteService;
        this.loginService = loginService;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringApplicationCvds.class, args);
    }

    @Bean
    public CommandLineRunner run() {
        return args -> {

        };
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }



}