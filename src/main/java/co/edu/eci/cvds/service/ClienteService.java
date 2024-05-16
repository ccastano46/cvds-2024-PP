package co.edu.eci.cvds.service;

import co.edu.eci.cvds.model.Cliente;
import co.edu.eci.cvds.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Clase Service de Cliente
 * @author Equipo Pixel Pulse
 * 10/05/2024
 */

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }

    /**
     * Devuelve un cliente acorde a su nombre y apellido
     * @param nombre
     * @param apellido
     * @return Cliente registrado en la base de datos con el nombre y apellido especificados.
     */
    public Cliente getCLiente(String nombre, String apellido){
        return clienteRepository.findByNombreAndApellido(nombre.toUpperCase(),apellido.toUpperCase()).get(0);
    }

    /**
     * Devuelve una lista de todos los clientes registrados en la base de datos.
     * @return lista de todos los clientes en la base de datos
     */
    public List<Cliente> listarClientes(){
        return clienteRepository.findAll();
    }

    public void limpiarTabla(){
        clienteRepository.deleteAllInBatch();
    }

    /**
     * Registra un cliente en la base de datos
     * @param cliente, cliente a registrar
     */
    public void agregarCliente(Cliente cliente){
        clienteRepository.save(cliente);
    }

}
