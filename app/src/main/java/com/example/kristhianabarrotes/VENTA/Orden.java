package com.example.kristhianabarrotes.VENTA;


public class Orden {
    int ordenID;
    String NumeroDeOrden;
    String fechaHora;
    int clienteID;

    public Orden(int ordenID, String numeroDeOrden, String fechaHora, int clienteID) {
        this.ordenID = ordenID;
        NumeroDeOrden = numeroDeOrden;
        this.fechaHora = fechaHora;
        this.clienteID = clienteID;
    }

    public int getOrdenID() {
        return ordenID;
    }

    public void setOrdenID(int ordenID) {
        this.ordenID = ordenID;
    }

    public String getNumeroDeOrden() {
        return NumeroDeOrden;
    }

    public void setNumeroDeOrden(String numeroDeOrden) {
        NumeroDeOrden = numeroDeOrden;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public int getClienteID() {
        return clienteID;
    }

    public void setClienteID(int clienteID) {
        this.clienteID = clienteID;
    }
}
