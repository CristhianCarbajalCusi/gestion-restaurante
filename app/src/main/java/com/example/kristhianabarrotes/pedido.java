package com.example.kristhianabarrotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.kristhianabarrotes.VENTA.Cliente;
import com.example.kristhianabarrotes.VENTA.Consumo;
import com.example.kristhianabarrotes.VENTA.DetalleOrden;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;
import java.util.Timer;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class pedido extends AppCompatActivity {

    public static final String NUMERO_DE_ORDEN = "#Orden";
    EditText txtNom;
    EditText txtApe;
    EditText txtDNI;
    Button btnCarrito;
    ListView listViewPersonas ;
    Spinner spinTIPO ;
    ControladorDatabase control_pedido_db;
    List<Consumo> consumo;
    // elecciones-de-consumo : Guarda todos los registros segun tipo
    List<Consumo> elecciones_de_consumo;
    // carrito : Guarda consumo x consumo
    List<Consumo> carrito;
    Cliente cliente ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);
        txtNom = findViewById(R.id.txtNombre);
        txtApe = findViewById(R.id.txtApellido);
        txtDNI = findViewById(R.id.txtDni);
        btnCarrito = findViewById(R.id.btnCarrito);
        btnCarrito.setEnabled(false);


        // Elementos de la interfaz
        spinTIPO = findViewById(R.id.spinTipo);
        listViewPersonas = findViewById(R.id.listViewPersonas);

        // 1.- Inicio a base de datos
        control_pedido_db = new ControladorDatabase(this);
        control_pedido_db.open();

        // bebidas
        control_pedido_db.insertar_consumo("Coca-Cola", "Bebida", "Refresco de cola", 2.50);
        control_pedido_db.insertar_consumo("Jugo de naranja", "Bebida", "Jugo natural de naranja", 3.00);
        control_pedido_db.insertar_consumo("Cerveza IPA", "Bebida", "Cerveza India Pale Ale", 4.50);
        control_pedido_db.insertar_consumo("Agua mineral con gas", "Bebida", "Agua con gas natural", 1.50);
        control_pedido_db.insertar_consumo("Mojito", "Bebida", "Cóctel refrescante con menta y ron", 6.00);
        control_pedido_db.insertar_consumo("Vino tinto Cabernet Sauvignon", "Bebida", "Vino tinto de uva Cabernet Sauvignon", 5.00);
        control_pedido_db.insertar_consumo("Sprite", "Bebida", "Refresco de lima-limón", 2.50);
        // entradas
        control_pedido_db.insertar_consumo("Ensalada César", "Entrada", "Lechuga romana, crutones y aderezo César", 7.50);
        control_pedido_db.insertar_consumo("Calamares a la romana", "Entrada", "Calamares fritos con salsa de tomate", 8.00);
        control_pedido_db.insertar_consumo("Bruschetta", "Entrada", "Pan tostado con tomate, ajo y albahaca", 6.50);
        control_pedido_db.insertar_consumo("Tacos de pescado", "Entrada", "Tortillas de maíz con pescado a la parrilla", 9.00);
        control_pedido_db.insertar_consumo("Sopa de tomate", "Entrada", "Sopa a base de tomates, ajo y hierbas", 5.50);
        control_pedido_db.insertar_consumo("Queso y uvas", "Entrada", "Tabla de quesos con uvas frescas", 10.00);
        control_pedido_db.insertar_consumo("Nachos con guacamole", "Entrada", "Nachos con salsa de guacamole", 8.50);
        // segundo
        control_pedido_db.insertar_consumo("Filete de res a la parrilla", "Segundo", "Filete jugoso de carne de res con acompañamientos", 15.00);
        control_pedido_db.insertar_consumo("Pollo a la parrilla", "Segundo", "Pechuga de pollo a la parrilla con verduras asadas", 12.00);
        control_pedido_db.insertar_consumo("Salmón a la plancha", "Segundo", "Salmón fresco cocinado a la plancha con salsa de limón", 14.50);
        control_pedido_db.insertar_consumo("Pasta Alfredo con pollo", "Segundo", "Fettuccine en una cremosa salsa Alfredo con pollo", 11.00);
        control_pedido_db.insertar_consumo("Lomo de cerdo asado", "Segundo", "Lomo de cerdo asado con puré de papas", 16.00);
        control_pedido_db.insertar_consumo("Ternera a la Stroganoff", "Segundo", "Ternera en salsa Stroganoff con arroz", 13.50);
        control_pedido_db.insertar_consumo("Tofu salteado con verduras", "Segundo", "Tofu salteado con verduras en salsa de soja", 10.50);
        // sopas
        control_pedido_db.insertar_consumo("Sopa de lentejas", "Sopa", "Sopa nutritiva de lentejas, zanahorias y cebollas", 6.00);
        control_pedido_db.insertar_consumo("Gazpacho", "Sopa", "Sopa fría de tomate, pepino, pimiento y cebolla", 5.50);
        control_pedido_db.insertar_consumo("Sopa de pollo con fideos", "Sopa", "Caldo de pollo con fideos y verduras", 7.00);
        control_pedido_db.insertar_consumo("Crema de champiñones", "Sopa", "Crema de champiñones con crutones", 6.50);
        control_pedido_db.insertar_consumo("Sopa tailandesa de coco", "Sopa", "Sopa tailandesa de coco con pollo y hierbas", 8.00);
        control_pedido_db.insertar_consumo("Sopa de tomate y albahaca", "Sopa", "Sopa de tomate con hojas frescas de albahaca", 6.50);
        control_pedido_db.insertar_consumo("Sopa de miso", "Sopa", "Sopa japonesa de miso con tofu y algas", 5.00);



        // 1.1.- Consumo : Una List<> del objeto Consumo
        consumo = control_pedido_db.mostrar_consumo();



        // Interfaz parte II - Colocar Sppinner con tipos Consumos
        ArrayList<String> ELECCION_TIPO = colocar_spinner_tipo_consumo();
        // Interfaz parte III - Colocar ListView Consumos por TIPO
        // System.out.println(ELECCION_TIPO.get(0));

        spinTIPO.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                               @Override
                                               public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                   elecciones_de_consumo = colocar_consumos(consumo, ELECCION_TIPO.get(i));
                                               }

                                               @Override
                                               public void onNothingSelected(AdapterView<?> adapterView) {

                                               }
        });

        btnCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // GENERANDO UN NUMERO DE ORDEN
                Random random = new Random();

                // Generar un número aleatorio de 3 dígitos
                int numeroAleatorio = random.nextInt(900) + 100;
                String numerodeorden = "ORD-"+numeroAleatorio;
                while(control_pedido_db.comprobar_numero_de_orden(numerodeorden)){
                    numeroAleatorio = random.nextInt(900) + 100;
                    numerodeorden = "ORD-"+numeroAleatorio;
                }
                generar_venta(txtDNI.getText().toString(),numerodeorden);
            }
        });
        carrito = new ArrayList<>();
        listViewPersonas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(txtNom.getText().toString().equals("") || txtApe.getText().toString().equals("") || txtDNI.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Debe rellenaar los tres campos",Toast.LENGTH_SHORT).show();

                }else{
                    carrito.add(elecciones_de_consumo.get(i));
                     txtNom.setEnabled(false);
                    txtApe.setEnabled(false);
                    txtDNI.setEnabled(false);
                    btnCarrito.setEnabled(true);
                    // Obtener la fecha y hora actual

                    String CARRITO = "";

                    for(int x = 0; x < carrito.size(); x++){

                        CARRITO += "Tipo: "+ carrito.get(x).getTipo()+"\n"+
                                "Consumo: "+ carrito.get(x).getNombre()+"\n\n";

                    }

                    Toast.makeText(getApplicationContext(), "Gracias por su compra. Esta Llevando:\n"+CARRITO,Toast.LENGTH_LONG).show();
                    // Para probar lo coloqué aqui : generar_venta();
                }


            }
        });

        // Agregar el codigo del Spinner tipo


    }

    // CONTADOR <MAP>
    public void generar_venta(String DNI,String NroOrden){
        Map<Integer, Integer> contadorConsumos = new HashMap<>();
        List<Integer> IDES = new ArrayList<>();

        // Obtiene todos los IDES de consumos elegidos con selectListView
        for(int j = 0; j< carrito.size(); j++){
            IDES.add(carrito.get(j).getConsumoID());
        }
        /*
        for (Integer idCon : IDES) {
            // Verifica si el consumo ya está en el mapa y actualiza su contador
            contadorConsumos.put(idCon, contadorConsumos.getOrDefault(idCon, 0) + 1);
        }*/

        // Iterar sobre la lista y contar las repeticiones de cada elemento
        for (Integer elemento : IDES) {
            if (contadorConsumos.containsKey(elemento)) {
                contadorConsumos.put(elemento, contadorConsumos.get(elemento) + 1);
            } else {
                contadorConsumos.put(elemento, 1);
            }
        }
        List<DetalleOrden> boleta = new ArrayList<DetalleOrden>();
        if(!control_pedido_db.comprobar_dni_cliente(DNI)){
            control_pedido_db.insertarCliente(txtNom.getText().toString(),txtApe.getText().toString(),txtDNI.getText().toString());

        }

        for (Map.Entry<Integer, Integer> entry : contadorConsumos.entrySet()) {
        //    System.out.println("Elemento: " + entry.getKey() + ", Cantidad: " + entry.getValue());
            Toast.makeText(getApplicationContext(),"Elemento: " + entry.getKey() + ", Cantidad: " + entry.getValue(),Toast.LENGTH_LONG).show();

            TimeZone timeZonePeru = TimeZone.getTimeZone("America/Lima");
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            dateFormat.setTimeZone(timeZonePeru);
            String fechaHoraPeru = dateFormat.format(new Date());


            // sE AGREGA FECHA Y id  DE CLIENTE
            long OrdenID = control_pedido_db.insertarOrden(NroOrden,fechaHoraPeru,control_pedido_db.buscar_id_cliente_por_dni(DNI));
            int OrdenConsumo = entry.getKey().intValue();
            int Cantidad = entry.getValue().intValue();

            System.out.println(OrdenID + OrdenConsumo + Cantidad);
            // Se va al registro
            long DetalleOrdenID = control_pedido_db.insertarDetalleOrden(Integer.parseInt(OrdenID+""),OrdenConsumo,Cantidad);
            // Se va a la boleta_a
            DetalleOrden pedido_por_cliente = new DetalleOrden(
                    Integer.parseInt(DetalleOrdenID+""),
                    Integer.parseInt(OrdenID+""),
                    OrdenConsumo,
                    Cantidad
            );
            boleta.add(pedido_por_cliente);

        }
        // Testing: System.out.println(boleta_a.get(0).getConsumoID()+"un ejemplo del objeto boletx_a - Final ");
        Intent toBoleta= new Intent(pedido.this, ResultadoBoleta.class);
        toBoleta.putExtra(NUMERO_DE_ORDEN,NroOrden);
        startActivity(toBoleta);

    }


    public ArrayList<String> colocar_spinner_tipo_consumo(){
        ArrayList<String> lista_tipo_consumo = new ArrayList<String>();
        lista_tipo_consumo.add("Seleccione el tipo de consumo");
        lista_tipo_consumo.add("Bebida");
        lista_tipo_consumo.add("Entrada");
        lista_tipo_consumo.add("Segundo");
        lista_tipo_consumo.add("Sopa");
        ArrayAdapter spinTipo = new ArrayAdapter(
                this, android.R.layout.simple_spinner_dropdown_item,lista_tipo_consumo);
        spinTIPO.setAdapter(spinTipo);

        return lista_tipo_consumo;

    }

    public List<Consumo> colocar_consumos(List<Consumo> consumo,String tipo){
        ArrayList<String> obtenerConsumo = new ArrayList<String>();
        List<Consumo> grupo_por_tipos = new ArrayList<Consumo>();
        if (!tipo.equals("Seleccione el tipo de consumo")) {
            for (int i = 0; i < consumo.size(); i++) {
                if (consumo.get(i) .getTipo().equals(tipo)) {

                    obtenerConsumo.add(
                            //consumo.get(i).getConsumoID() + " == " +
                            "=> "+consumo.get(i).getNombre() + "\n" +
                                    "TIPO: " + consumo.get(i).getTipo() + "\n" +
                                    "detalle: " + consumo.get(i).getDescripcion() + "\n" +
                                    "COSTO:  s/" + consumo.get(i).getPrecio() + "\n"
                    );
                    int idCon = consumo.get(i).getConsumoID();
                    String nombreCon = consumo.get(i).getNombre();
                    String tipoCon = consumo.get(i).getTipo();
                    String detalleCon = consumo.get(i).getDescripcion();
                    float costoCon = consumo.get(i).getPrecio();
                    Consumo eleccionCon = new Consumo(idCon,nombreCon,tipoCon,detalleCon,costoCon);
                    grupo_por_tipos.add(eleccionCon);



                    ArrayAdapter consumoAdaptador = new ArrayAdapter(
                            this,
                            android.R.layout.simple_list_item_1,
                            obtenerConsumo
                    );
                    listViewPersonas.setAdapter(consumoAdaptador);

                }
            }

            return grupo_por_tipos;
        } else {
            for (int j = 0; j < consumo.size(); j++) {
                obtenerConsumo.add(
                        //consumo.get(j).getConsumoID() + " == " +
                                "=> "+consumo.get(j).getNombre() + "\n" +
                                "TIPO: " + consumo.get(j).getTipo() + "\n" +
                                "detalle: " + consumo.get(j).getDescripcion() + "\n" +
                                "COSTO:  s/" + consumo.get(j).getPrecio() + "\n"
                );
                int idCon = consumo.get(j).getConsumoID();
                String nombreCon = consumo.get(j).getNombre();
                String tipoCon = consumo.get(j).getTipo();
                String detalleCon = consumo.get(j).getDescripcion();
                float costoCon = consumo.get(j).getPrecio();
                Consumo eleccionCon = new Consumo(idCon,nombreCon,tipoCon,detalleCon,costoCon);
                grupo_por_tipos.add(eleccionCon);


            }

            ArrayAdapter consumoAdaptador = new ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    obtenerConsumo
            );
            listViewPersonas.setAdapter(consumoAdaptador);
            return grupo_por_tipos;

        }

    }
}