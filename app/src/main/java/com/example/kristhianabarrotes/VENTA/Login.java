package com.example.kristhianabarrotes.VENTA;

public class Login {

    int loginID;
    String nombre;
    String contraseña;

    public Login(int loginID, String nombre, String contraseña) {
        this.loginID = loginID;
        this.nombre = nombre;
        this.contraseña = contraseña;
    }

    public int getLoginID() {
        return loginID;
    }

    public void setLoginID(int loginID) {
        this.loginID = loginID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}
