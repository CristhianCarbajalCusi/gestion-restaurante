package com.example.kristhianabarrotes;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.kristhianabarrotes.VENTA.BoletaItem;
import com.example.kristhianabarrotes.VENTA.Consumo;


import java.util.List;

import kotlin.contracts.Returns;

public class ControladorDatabase {

    private DatabaseHelper dbHelper;
    private Context context;
    static SQLiteDatabase database;

    public ControladorDatabase(Context c) {
        context = c;
    }

    public ControladorDatabase open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();

        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public long insertar_login_usuario(String nombre, String contraseña) {
        return dbHelper.insertarLogin(nombre, contraseña);
    }

    public boolean comprobando_login(String nombre, String contraseña) {
        return dbHelper.comprobar_login(nombre, contraseña);
    }

    public long insertar_consumo(String nombre, String tipo, String descripcion, double precio) {
        return dbHelper.insertarConsumo(nombre, tipo, descripcion, precio);
    }

    public List<Consumo> mostrar_consumo() {
        return dbHelper.obtenerTodosLosConsumos();
    }

    public Consumo obtenerTodosLosConsumosPorID(int idMapConsumo) {
        return dbHelper.obtenerTodosLosConsumosPorID(idMapConsumo);
    }

    public long insertarCliente(String nombre, String apellidos, String dni) {
        return dbHelper.insertarCliente(nombre, apellidos, dni);
    }

    public long insertarOrden(String numerodeorden,String fechaHora, long clienteID) {
        return dbHelper.insertarOrden(numerodeorden,fechaHora, clienteID);
    }

    public boolean comprobar_dni_cliente(String dni) {
        return dbHelper.comprobar_dni_cliente(dni);
    }

    public int buscar_id_cliente_por_dni(String dni) {
        return dbHelper.buscar_id_cliente_por_dni(dni);
    }

    public long insertarDetalleOrden(long ordenID, long consumoID, int cantidad) {
        return dbHelper.insertarDetalleOrden(ordenID, consumoID, cantidad);
    }

    public boolean comprobar_numero_de_orden(String numerodeorden){
        return dbHelper.comprobar_numero_de_orden(numerodeorden);
    }
    public List<BoletaItem> obtenerBoletaPorNumeroDeOrden(String numeroDeOrden) {
        return dbHelper.obtenerBoletaPorNumeroDeOrden(numeroDeOrden);
    }
    public double calcularMontoTotal(String numeroDeOrden) {
        return dbHelper.calcularMontoTotal(numeroDeOrden);
    }
    public double mostrar_pos_del_dia(){
        return dbHelper.mostrar_pos_del_dia();
    }
    public int mostrar_cantidad_de_consumos_vendidos(){
        return dbHelper.mostrar_cantidad_de_consumos_vendidos();
    }
    public List<String> mostrar_consumo_mas_vendido(){
        return dbHelper.mostrar_consumo_mas_vendido();
    }
}