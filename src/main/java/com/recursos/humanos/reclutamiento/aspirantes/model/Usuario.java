package main.java.com.recursos.humanos.reclutamiento.aspirantes.model;

public class Usuario {
    //atributos
    public String id_usuario;
    public String correo;
    public String cotrasena;
    
    //constructor
    public Usuario(String id_usuario, String correo, String cotrasena) {
        this.id_usuario = id_usuario;
        this.correo = correo;
        this.cotrasena = cotrasena;
    }
    
    //metodos
    //getters
    public String getId_usuario() {
        return id_usuario;
    }

    public String getCorreo() {
        return correo;
    }

    public String getCotrasena() {
        return cotrasena;
    }
    
    //setters
    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setCotrasena(String cotrasena) {
        this.cotrasena = cotrasena;
    }
    
}
