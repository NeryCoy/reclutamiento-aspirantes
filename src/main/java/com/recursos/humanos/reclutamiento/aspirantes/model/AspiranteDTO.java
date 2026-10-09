package main.java.com.recursos.humanos.reclutamiento.aspirantes.model;

public class AspiranteDTO {
    
    //atriubtos
    private String idAspirantes;
    private String idPais;
    private String idUsuario;
    private String nombre;
    private String apellido;
    private String telefono;
    private int edad;

    // Constructor
    public AspiranteDTO(String idAspirantes, String idPais, String idUsuario, String nombre, String apellido, String telefono, int edad) {
        this.idAspirantes = idAspirantes;
        this.idPais = idPais;
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.edad = edad;
    }
    
    //metodos
    //getters
    public String getIdAspirantes() {
        return idAspirantes;
    }

    public String getIdPais() {
        return idPais;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public int getEdad() {
        return edad;
    }
    
    //setters
    public void setIdAspirantes(String idAspirantes) {
        this.idAspirantes = idAspirantes;
    }

    public void setIdPais(String idPais) {
        this.idPais = idPais;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
    
}