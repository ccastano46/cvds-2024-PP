package co.edu.eci.cvds.model;

import co.edu.eci.cvds.id.ClienteID;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

/**
 * Clase - Entidad Cliente
 * @author Equipo Pixel Pulse
 * 10/05/2024
 */

@Entity
@Table(name = "Clientes")
@IdClass(ClienteID.class)
public class Cliente {
    @Id
    @Column(name = "nombre", length = 50,nullable = false)
    @Getter @Setter private String nombre;
    @Id
    @Column(name = "apellido", length = 100, nullable = false)
    @Getter @Setter private String apellido;
    @Column(name = "correo", length = 50)
    @Getter @Setter private String correo;
    @Column(name = "celular", length = 10, nullable = false)
    @Getter @Setter private String celular;


    public Cliente() {

    }

    /**
     * Constructos de la clase Cliente
     * @param correo, correlo electronico del cliente.
     * @param nombre, nombre del cliente.
     * @param apellido, los dos apellidos del cliente
     * @param celular, celular del cliente
     */
    public Cliente(String nombre, String apellido, String celular,String correo){
        this.correo = correo;
        this.nombre = nombre.toUpperCase();
        this.apellido = apellido.toUpperCase();
        this.celular = celular;
    }



    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        result = prime * result + ((correo == null) ? 0 : correo.hashCode());
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        result = prime * result + ((apellido == null) ? 0 : apellido.hashCode());
        result = prime * result + ((celular == null) ? 0 : celular.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null || obj.getClass() != this.getClass()) return false;
        Cliente client = (Cliente) obj;
        return (this.nombre == null ? client.getNombre() == null : this.nombre.equals(client.getNombre()))
                && (this.apellido == null ? client.getApellido() == null :this.apellido.equals(client.getApellido()))
                && (this.celular == null ? client.getCelular() == null :this.celular.equals(client.getCelular()))
                && (correo == null ? client.getCorreo() == null : correo.equals(client.getCorreo()));


    }

}