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


    @Autowired
    public SpringApplicationCvds(
            ProductoService productoService,
            VehiculoService vehiculoService,
            CotizacionService cotizacionService,
            ClienteService clienteService

    ) {


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