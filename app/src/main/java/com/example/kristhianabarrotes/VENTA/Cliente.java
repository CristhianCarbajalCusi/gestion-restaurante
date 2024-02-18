package com.example.kristhianabarrotes.VENTA;

public class Cliente {

    int clienteID;
    String nombre;
    String apellidos;
    String DNI;


    public Cliente(int clienteID, String nombre, String apellidos, String DNI) {
        this.clienteID = clienteID;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.DNI = DNI;
    }

    public int getClienteID() {
        return clienteID;
    }

    public void setClienteID(int clienteID) {
        this.clienteID = clienteID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

}
