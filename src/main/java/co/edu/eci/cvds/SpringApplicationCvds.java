package co.edu.eci.cvds;


import co.edu.eci.cvds.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;



@SpringBootApplication
@Slf4j
public class SpringApplicationCvds {

    private final ProductoService productoService;
    private final VehiculoService vehiculoService;
    private final CotizacionSerrvice cotizacionSerrvice;
    private final ClienteService clienteService;

    @Autowired
    public SpringApplicationCvds(
            ProductoService productoService,
            VehiculoService vehiculoService,
            CotizacionSerrvice cotizacionService,
            ClienteService clienteService

    ) {
        this.productoService = productoService;
        this.vehiculoService = vehiculoService;
        this.cotizacionSerrvice = cotizacionService;
        this.clienteService = clienteService;

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