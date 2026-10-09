package main.java.com.recursos.humanos.reclutamiento.aspirantes.model;

public class Pais {
    //atributos
    public String id_pais;
    public String nombre;
    public String ciudad;
    public String direccion;
    
    //constructor
    public Pais(String id_pais, String nombre, String ciudad, String direccion) {
        this.id_pais = id_pais;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.direccion = direccion;
    }
    
    //metodos
    //getters 
    public String getId_pais() {
        return id_pais;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getDireccion() {
        return direccion;
    }
    
    //setters
    public void setId_pais(String id_pais) {
        this.id_pais = id_pais;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
}
