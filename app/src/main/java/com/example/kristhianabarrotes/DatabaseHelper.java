package com.example.kristhianabarrotes;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.kristhianabarrotes.VENTA.BoletaItem;
import com.example.kristhianabarrotes.VENTA.Cliente;
import com.example.kristhianabarrotes.VENTA.Consumo;
import com.example.kristhianabarrotes.VENTA.DetalleOrden;
import com.example.kristhianabarrotes.VENTA.Login;
import com.example.kristhianabarrotes.VENTA.Orden;


import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "restaurante010.db";
    private static final int DATABASE_VERSION = 1;
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String TABLA_LOGIN = "CREATE TABLE Login_Principal(" +
                "loginID INTEGER PRIMARY KEY," +
                "nombre TEXT UNIQUE," +
                "contraseña TEXT" +
                ");";

        String TABLA_CLIENTES = "CREATE TABLE Clientes (" +
                "ClienteID INTEGER PRIMARY KEY," +
                "nombre TEXT," +
                "apellidos TEXT," +
                "DNI TEXT UNIQUE" +
                ");";

        String TABLA_ORDENES = "CREATE TABLE Ordenes (" +
                "OrdenID INTEGER PRIMARY KEY," +
                "NumeroDeOrden TEXT,"+
                "FechaHora DATETIME," +

                "ClienteID INTEGER," +
                "FOREIGN KEY (ClienteID) REFERENCES Clientes(ClienteID)" +
                ");";

        String TABLA_CONSUMO = "CREATE TABLE Consumo (" +
                "ConsumoID INTEGER PRIMARY KEY," +
                "nombre TEXT UNIQUE," +
                "tipo TEXT," +
                "descripcion TEXT," +
                "precio REAL" +
                ");";

        String TABLA_DETALLES_ORDEN = "CREATE TABLE Detalles_de_la_Orden (" +
                "DetalleID INTEGER PRIMARY KEY," +
                "OrdenID INTEGER," +
                "ConsumoID INTEGER," +
                "Cantidad INTEGER," +
                "FOREIGN KEY (OrdenID) REFERENCES Ordenes(OrdenID)," +
                "FOREIGN KEY (ConsumoID) REFERENCES Consumo(ConsumoID)" +
                ");";

        // db.execSQL(TABLE_CREATE);
        db.execSQL(TABLA_LOGIN);
        db.execSQL(TABLA_CLIENTES);
        db.execSQL(TABLA_ORDENES);
        db.execSQL(TABLA_CONSUMO);
        db.execSQL(TABLA_DETALLES_ORDEN);


    }
    // Metodos Insertar


    public long insertarLogin(String nombre, String contraseña) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("contraseña", contraseña);
        long id = db.insert("Login_Principal", null, values);
        db.close();
        return id;
    }

    public long insertarCliente(String nombre, String apellidos, String dni) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("apellidos", apellidos);
        values.put("DNI", dni);
        long id = db.insert("Clientes", null, values);
        db.close();
        return id;
    }

    public long insertarOrden(String numerodeorden,String fechaHora,long clienteID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NumeroDeOrden", numerodeorden);
        values.put("FechaHora", fechaHora);
        values.put("ClienteID", clienteID);
        long id = db.insert("Ordenes", null, values);
        db.close();
        return id;
    }

    public long insertarConsumo(String nombre, String tipo, String descripcion, double precio) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("tipo", tipo);
        values.put("descripcion", descripcion);
        values.put("precio", precio);
        long id = db.insert("Consumo", null, values);
        db.close();
        return id;
    }

    public long insertarDetalleOrden(long ordenID, long consumoID, int cantidad) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("OrdenID", ordenID);
        values.put("ConsumoID", consumoID);
        values.put("Cantidad", cantidad);
        long id = db.insert("Detalles_de_la_Orden", null, values);
        db.close();
        return id;
    }

    // comprobaciones y busquedas

    // TODO SOBRE LA BOLETA - no hay tablas solo query resultado almacenado en objetos.
    public List<BoletaItem> obtenerBoletaPorNumeroDeOrden(String numeroDeOrden) {
        List<BoletaItem> items = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        String query =  "SELECT C.nombre AS 'ClienteNombre',C.apellidos AS 'ClienteApellidos', C.DNI AS 'ClienteDNI',O.FechaHora AS 'FechaOrden', CON.nombre AS 'NombreConsumo', CON.descripcion AS 'DescripcionConsumo', CON.precio AS 'PrecioUnitario', DO.Cantidad AS 'Cantidad' " +
                "FROM Clientes AS C " +
                "INNER JOIN Ordenes AS O ON C.ClienteID = O.ClienteID " +
                "LEFT JOIN Detalles_de_la_Orden AS DO ON O.OrdenID = DO.OrdenID " +
                "LEFT JOIN Consumo AS CON ON DO.ConsumoID = CON.ConsumoID " +
                "WHERE O.NumeroDeOrden = "+"'"+numeroDeOrden+"'";


        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            String clienteNombre = cursor.getString(0);
            String clienteApellidos = cursor.getString(1);
            String clienteDNI = cursor.getString(2);
            String fechaOrden = cursor.getString(3);
            String nombreConsumo = cursor.getString(4);
            String descripcionConsumo = cursor.getString(5);
            double precioUnitario = cursor.getDouble(6);
            int cantidad = cursor.getInt(7);
            double subtotal = precioUnitario * cantidad; // Calcular el subtotal

            BoletaItem item = new BoletaItem(clienteNombre, clienteApellidos, clienteDNI, fechaOrden, nombreConsumo, descripcionConsumo, precioUnitario, cantidad, subtotal);
            items.add(item);
        }

        cursor.close();
        db.close();


        return items;
    }

    public double calcularMontoTotal(String numeroDeOrden) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columnas = {"SUM(CON.precio * DO.Cantidad) AS 'Total'"};
        String tabla = "Clientes C " +
                "INNER JOIN Ordenes O ON C.ClienteID = O.ClienteID " +
                "LEFT JOIN Detalles_de_la_Orden DO ON O.OrdenID = DO.OrdenID " +
                "LEFT JOIN Consumo CON ON DO.ConsumoID = CON.ConsumoID";
        String where = "O.NumeroDeOrden = ?";
        String[] parametros = {String.valueOf(numeroDeOrden)};

        Cursor cursor = db.query(tabla, columnas, where, parametros, null, null, null);
        double montoTotal = 0.0;

        if (cursor.moveToFirst()) {
            montoTotal = cursor.getDouble(0);
        }

        cursor.close();
        db.close();
        return montoTotal;
    }





    // COMPROBAR NUMERO DE ORDEN UNICO LA MOMENTO DE GENERAR
    public boolean comprobar_numero_de_orden(String numerodeorden){
        String query = "SELECT * FROM Ordenes";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        boolean condicion = false;
        if (cursor.moveToFirst()) {
            do {

                String NumeroDeOrden = cursor.getString(1);
                if(NumeroDeOrden.equals(numerodeorden)){
                    condicion = true;
                    break;
                }

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return condicion;
    }
    // RETORNA IDCLIENTE POR DNI UNIQUE
    public int buscar_id_cliente_por_dni(String dni){
        String query = "SELECT ClienteID FROM Clientes WHERE DNI = '"+dni+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int IDcliente = 0;
        if (cursor.moveToFirst()) {
            IDcliente = cursor.getInt(0);
        }
        return IDcliente;
    }
    // COMPROBANDO DNI EXISTENTE
    public boolean comprobar_dni_cliente(String dni){
        String query = "SELECT * FROM Clientes";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        boolean condicion = false ;
        if (cursor.moveToFirst()) {
            do {

                String DNI = cursor.getString(3);

                if(DNI.equals(dni)){
                    condicion = true;
                }else{
                    condicion = false;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return condicion;
    }

    // COMPROBANDO INGRESO DE LOGIN
    public boolean comprobar_login(String nombre,String contraseña){
        String query = "SELECT * FROM Login_Principal";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        boolean condicion = false ;
        if (cursor.moveToFirst()) {
            do {

                String nombre_login = cursor.getString(1);
                String contraseña_login = cursor.getString(2);
                if(nombre.equals(nombre_login) && contraseña.equals(contraseña_login)){
                    condicion = true;
                }else{
                    condicion = false;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return condicion;
    }


    // MENU GENERAL - INFORMACIONES
    public double mostrar_pos_del_dia(){
        String query = "SELECT SUM(C.precio * DO.Cantidad) AS 'Monto total del día' " +
                "FROM Detalles_de_la_Orden DO " +
                "INNER JOIN Ordenes O ON DO.OrdenID = O.OrdenID " +
                "INNER JOIN Consumo C ON DO.ConsumoID = C.ConsumoID " +
                "WHERE DATE(O.FechaHora) = DATE('now', 'localtime');";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Double pos = 0.0;
        boolean condicion = false ;
        if (cursor.moveToFirst()) {
            pos = cursor.getDouble(0);
        }
        cursor.close();
        db.close();
        return pos;
    }

    public int mostrar_cantidad_de_consumos_vendidos() {

        String query = "SELECT COUNT(*) AS 'Número de platos del día' " +
                "FROM Detalles_de_la_Orden DO " +
                "INNER JOIN Ordenes O ON DO.OrdenID = O.OrdenID " +
                "WHERE DATE(O.FechaHora) = DATE('now', 'localtime');";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int nroConsumos = 0;
        if (cursor.moveToFirst()) {
           nroConsumos = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return nroConsumos;
    }
    public List<String> mostrar_consumo_mas_vendido() {
        String query = "SELECT C.nombre AS 'Nombre del consumo', COUNT(*) AS 'Cantidad vendida' " +
                "FROM Detalles_de_la_Orden DO " +
                "INNER JOIN Consumo C ON DO.ConsumoID = C.ConsumoID " +
                "INNER JOIN Ordenes O ON DO.OrdenID = O.OrdenID " +
                "WHERE DATE(O.FechaHora) = DATE('now', 'localtime') " +
                "GROUP BY C.nombre " +
                "ORDER BY COUNT(*) DESC " +
                "LIMIT 1;";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<String> CMV = null;
        if (cursor.moveToFirst()) {
            CMV = new ArrayList<String>();
            CMV.add(cursor.getString(0));
            CMV.add(cursor.getInt(1)+"");
        }
        cursor.close();
        db.close();
        return CMV;
    }

    // Metodos SELECT  - ArrayList <>
    public List<Login> obtenerTodasLasCredenciales() {
        List<Login> listaCredenciales = new ArrayList<>();
        String query = "SELECT * FROM Login";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int loginID = cursor.getInt(0);
                String nombre = cursor.getString(1);
                String contraseña = cursor.getString(2);
                Login credencial = new Login(loginID, nombre, contraseña);
                listaCredenciales.add(credencial);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listaCredenciales;
    }

    public List<Cliente> obtenerTodosLosClientes() {
        List<Cliente> listaClientes = new ArrayList<>();
        String query = "SELECT * FROM Clientes";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int clienteID = cursor.getInt(0);
                String nombre = cursor.getString(1);
                String apellidos = cursor.getString(2);
                String dni = cursor.getString(3);
                Cliente cliente = new Cliente(clienteID, nombre, apellidos, dni);
                listaClientes.add(cliente);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listaClientes;
    }


    public List<Orden> obtenerTodasLasOrdenes() {
        List<Orden> listaOrdenes = new ArrayList<>();
        String query = "SELECT * FROM Ordenes";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int ordenID = cursor.getInt(0);
                String NumeroDeOrden = cursor.getString(1);
                String fechaHora = cursor.getString(2);
                int clienteID = cursor.getInt(3);
                Orden orden = new Orden(ordenID,NumeroDeOrden,fechaHora,clienteID);
                listaOrdenes.add(orden);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listaOrdenes;
    }

    public List<Consumo> obtenerTodosLosConsumos() {
        List<Consumo> listaConsumos = new ArrayList<>();
        String query = "SELECT * FROM Consumo";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int consumoID = cursor.getInt(0);
                String nombre = cursor.getString(1);
                String tipo = cursor.getString(2);
                String descripcion = cursor.getString(3);
                float precio = cursor.getFloat(4);
                Consumo consumo = new Consumo(consumoID, nombre, tipo, descripcion, precio);
                listaConsumos.add(consumo);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listaConsumos;
    }
    // Obtener Consumo por ID
    public Consumo obtenerTodosLosConsumosPorID(int idMapConsumo) {
        Consumo registroConsumos = null;
        String query = "SELECT * FROM Consumo WHERE ConsumoID = '"+idMapConsumo+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int consumoID = cursor.getInt(0);
                String nombre = cursor.getString(1);
                String tipo = cursor.getString(2);
                String descripcion = cursor.getString(3);
                float precio = cursor.getFloat(4);
                registroConsumos = new Consumo(consumoID, nombre, tipo, descripcion, precio);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return registroConsumos;
    }

    public List<DetalleOrden> obtenerTodosLosDetallesDeOrden() {
        List<DetalleOrden> listaDetallesDeOrden = new ArrayList<>();
        String query = "SELECT * FROM Detalles_de_la_Orden";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int detalleID = cursor.getInt(0);
                int ordenID = cursor.getInt(1);
                int consumoID = cursor.getInt(2);
                int cantidad = cursor.getInt(3);
                DetalleOrden detalleOrden = new DetalleOrden(detalleID, ordenID, consumoID, cantidad);
                listaDetallesDeOrden.add(detalleOrden);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listaDetallesDeOrden;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + "Login_Principal");
        db.execSQL("DROP TABLE IF EXISTS " + "Clientes");
        db.execSQL("DROP TABLE IF EXISTS " + "Ordenes");
        db.execSQL("DROP TABLE IF EXISTS " + "Consumo");
        db.execSQL("DROP TABLE IF EXISTS " + "Detalles_de_la_Orden");

        onCreate(db);
    }
}
