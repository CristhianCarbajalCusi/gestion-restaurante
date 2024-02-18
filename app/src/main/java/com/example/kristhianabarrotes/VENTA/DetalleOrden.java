package com.example.kristhianabarrotes.VENTA;

public class DetalleOrden {

     int detalleID;
     int ordenID;
     int consumoID;
     int cantidad;

    public DetalleOrden(int detalleID, int ordenID, int consumoID, int cantidad) {
        this.detalleID = detalleID;
        this.ordenID = ordenID;
        this.consumoID = consumoID;
        this.cantidad = cantidad;
    }

    public int getDetalleID() {
        return detalleID;
    }

    public void setDetalleID(int detalleID) {
        this.detalleID = detalleID;
    }

    public int getOrdenID() {
        return ordenID;
    }

    public void setOrdenID(int ordenID) {
        this.ordenID = ordenID;
    }

    public int getConsumoID() {
        return consumoID;
    }

    public void setConsumoID(int consumoID) {
        this.consumoID = consumoID;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
