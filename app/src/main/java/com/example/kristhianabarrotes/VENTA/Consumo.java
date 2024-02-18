package com.example.kristhianabarrotes.VENTA;

public class Consumo {


    String nombre;
    String tipo;
    String descripcion;
    float precio;
    int consumoID;

    public Consumo(int consumoID,String nombre, String tipo, String descripcion, float precio) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.consumoID = consumoID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getConsumoID() {
        return consumoID;
    }

    public void setConsumoID(int consumoID) {
        this.consumoID = consumoID;
    }
}