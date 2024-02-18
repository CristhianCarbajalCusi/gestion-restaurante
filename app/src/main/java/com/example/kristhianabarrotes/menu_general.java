package com.example.kristhianabarrotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class menu_general extends AppCompatActivity {
    Button pedido;
    TextView POS;
    TextView Platos_del_dia;
    TextView Consumo_del_dia;
    TextView Cantidad_Del_dia;
    ControladorDatabase control_menu_db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_general);
        POS = findViewById(R.id.txtPOS);
        Platos_del_dia = findViewById(R.id.txtNumPV);
        Consumo_del_dia = findViewById(R.id.txtConsumo);
        Cantidad_Del_dia = findViewById(R.id.txtCantidad);
        pedido = findViewById(R.id.btnPedido);

        control_menu_db = new ControladorDatabase(this);
        control_menu_db.open();

        POS.setText("Monto POS: "+control_menu_db.mostrar_pos_del_dia());
        Platos_del_dia.setText("Numero de platos Vendidos: "+control_menu_db.mostrar_cantidad_de_consumos_vendidos()+"");
        if(control_menu_db.mostrar_consumo_mas_vendido() == null){
            Consumo_del_dia.setText("Consumo: "+"Aun no se han generado ventas.");
            Cantidad_Del_dia.setText("Cantidad: "+"Aun no se han generado ventas.");
        }else{
            Consumo_del_dia.setText("Consumo: "+control_menu_db.mostrar_consumo_mas_vendido().get(0)+"");
            Cantidad_Del_dia.setText("Cantidad: "+control_menu_db.mostrar_consumo_mas_vendido().get(1)+"");

        }

        pedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(menu_general.this,pedido.class);
                startActivity(i);
            }
        });


    }

}