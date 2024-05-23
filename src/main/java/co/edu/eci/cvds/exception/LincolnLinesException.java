package co.edu.eci.cvds.exception;

public class LincolnLinesException extends Exception{
    public static final String DATOS_FALTANTES = "No se proporcionaron los datos necesario";
    public static final  String VALOR_NEGATIVO = "No se aceptan valores negativos";
    public static final String PRODUCTO_NO_COMPATIBLE = "El Producto no es apto para el vehiculo";
    public static final String FECHA_NO_DISPONIBLE = "No se puede separar cita para esa fecha u hora";
    public static final String NO_ACCIONES = "No se pude realizar cualquier otra accion";
    public static final String PRODUCTO_SIN_CATEGORIA = "Asocie el producto a una categoria antes de realizar cualquier accion";
    public static final String COTIZACION_AGENDADA = "Esta cotizacion ya fue agendada";
    public static final String CARRITO_VACIO = "No se puede agendar una cita para un carrito vacio";
    public static final String FUERA_RANGO = "Un porcentaje no puede ser mayor al 100%";
    public LincolnLinesException(String message){
        super(message);
    }
}