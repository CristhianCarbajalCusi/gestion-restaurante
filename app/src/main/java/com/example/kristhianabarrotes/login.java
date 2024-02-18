package com.example.kristhianabarrotes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kristhianabarrotes.VENTA.Cliente;
import com.example.kristhianabarrotes.VENTA.Consumo;

import java.util.ArrayList;
import java.util.List;


public class login extends AppCompatActivity {
    Button ingresar;
    EditText usuario;
    EditText contraseña;

    private ControladorDatabase control_database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ingresar = findViewById(R.id.btnIngresar);
        usuario = findViewById(R.id.txtUsuario);
        contraseña = findViewById(R.id.txtContraseña);
        control_database = new ControladorDatabase(this);

        // Abre la base de datos
        try {
            control_database.open();
            long result = control_database.insertar_login_usuario("Kristhian","TI");
            if (result != -1) {
                System.out.println("Ingresó un registró");
            } else {
                System.out.println("No se registró algo...");
            }
            System.out.println("Se conectó correctamente");
        }catch (SQLException e) {

            Toast.makeText(this, "Error al abrir la base de datos", Toast.LENGTH_SHORT).show();

        }
        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Recuperar de la interfaz-----------------------
                String user= usuario.getText().toString();
                String pass = contraseña.getText().toString();
                // Recuperar datos de la base de datos
                int condicionFinal = 0;
                boolean Cond = control_database.comprobando_login(user,pass);
               if (Cond){
                   System.out.println("Entró a la comparacion de user y pass");
                  Intent i = new Intent(login.this,menu_general.class);
                  startActivity(i);
                  // El inicio de sesión fue exitoso
                  Toast.makeText(login.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                  condicionFinal = 1;

               }
               if(condicionFinal == 0){
                  Toast.makeText(login.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
               }

            }

        });

    }
}