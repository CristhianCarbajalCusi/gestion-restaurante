package com.example.kristhianabarrotes.VENTA;

public class BoletaItem {
    private String clienteNombre;
    private String clienteApellidos;
    private String clienteDNI;
    private String fechaOrden;
    private String nombreConsumo;
    private String descripcionConsumo;
    private double precioUnitario;
    private int cantidad;
    private double subtotal;
     // Nuevo campo para el monto total

    // Constructor
    public BoletaItem(String clienteNombre, String clienteApellidos, String clienteDNI, String fechaOrden, String nombreConsumo, String descripcionConsumo, double precioUnitario, int cantidad, double subtotal) {
        this.clienteNombre = clienteNombre;
        this.clienteApellidos = clienteApellidos;
        this.clienteDNI = clienteDNI;
        this.fechaOrden = fechaOrden;
        this.nombreConsumo = nombreConsumo;
        this.descripcionConsumo = descripcionConsumo;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
         // El monto total se establecerá más tarde
    }

    // Agregar otros métodos getter y setter según sea necesario para los demás campos

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public String getClienteApellidos() {
        return clienteApellidos;
    }

    public void setClienteApellidos(String clienteApellidos) {
        this.clienteApellidos = clienteApellidos;
    }

    public String getClienteDNI() {
        return clienteDNI;
    }

    public void setClienteDNI(String clienteDNI) {
        this.clienteDNI = clienteDNI;
    }

    public String getFechaOrden() {
        return fechaOrden;
    }

    public void setFechaOrden(String fechaOrden) {
        this.fechaOrden = fechaOrden;
    }

    public String getNombreConsumo() {
        return nombreConsumo;
    }

    public void setNombreConsumo(String nombreConsumo) {
        this.nombreConsumo = nombreConsumo;
    }

    public String getDescripcionConsumo() {
        return descripcionConsumo;
    }

    public void setDescripcionConsumo(String descripcionConsumo) {
        this.descripcionConsumo = descripcionConsumo;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}
