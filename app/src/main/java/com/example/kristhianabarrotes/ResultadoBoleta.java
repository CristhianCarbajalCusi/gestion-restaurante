package com.example.kristhianabarrotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kristhianabarrotes.VENTA.BoletaItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ResultadoBoleta extends AppCompatActivity {

    TextView resultado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_boleta);
        resultado = findViewById(R.id.txtBoletas);
        Intent toBoleta = getIntent();
        String NroOrden = toBoleta.getStringExtra(pedido.NUMERO_DE_ORDEN);
        ControladorDatabase control_boleta_db;
        control_boleta_db = new ControladorDatabase(this);
        control_boleta_db.open();
        List<BoletaItem> boletaFinal = control_boleta_db.obtenerBoletaPorNumeroDeOrden(NroOrden);
        double montoFinalDeLaBoleta=  control_boleta_db.calcularMontoTotal(NroOrden);
        Toast.makeText(getApplicationContext(),"Monto Total: "+montoFinalDeLaBoleta+" "+boletaFinal.get(0).getClienteNombre(),Toast.LENGTH_LONG).show();


        String boletas = "";




        String fechaOrden = boletaFinal.get(0).getFechaOrden()+"";
        String clienteNombre = boletaFinal.get(0).getClienteNombre()+"";
        String clienteApellidos = boletaFinal.get(0).getClienteApellidos()+"";
        String clienteDNI = boletaFinal.get(0).getClienteDNI()+"";

        double total = montoFinalDeLaBoleta;
        // Generar la boleta como una cadena de texto
        String boleta = "*********************************************\n" +
                "               BOLETA DE COMPRA\n | " +NroOrden+
                "*********************************************\n" +
                "\n" +
                "Fecha: " + fechaOrden + "\n" +
                "Cliente: " + clienteNombre + " " + clienteApellidos + "\n" +
                "DNI: " + clienteDNI + "\n" +
                "\n" +
                "- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -\n";
                //"Descripción               Cantidad  Precio\n" +
                boleta += String.format("%-30s %8s %10s\n", "Descripción" , "Cantidad", "Precio");

        boleta += "- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -\n";
        for (BoletaItem bol : boletaFinal){
            String nombreConsumo = bol.getNombreConsumo() ;
            int cantidad = bol.getCantidad();
            double precioUnitario = bol.getPrecioUnitario();
            boleta += String.format("%-30s %8d %10.2f\n", nombreConsumo, cantidad, precioUnitario);

        }
         boleta += "- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -\n"+

                "\n" +
                //"Subtotal: $" + subtotal + "\n" +
                //"Impuesto (18%): $" + impuesto + "\n" +
                "Total: s/" + total + "\n" +
                "\n" +
                "¡Gracias por su compra!";
        resultado.setText(boleta);

        /* ****************************************************************


        // Crear el documento PDF
        PdfDocument pdfDocument = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842, 1).create(); // Tamaño A4 (595x842 puntos)
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

// Configurar fuente y tamaño de texto
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        paint.setTextSize(12);
        float x = 40;
        float y = 40;
        float lineHeight = 20;

        // Agregar contenido al documento
        canvas.drawText("BOLETA DE COMPRA"+" | nro Orden: "+NroOrden, x, y, paint);
        y += lineHeight;

        // Formatear y agregar las variables
        canvas.drawText("Fecha de Orden: " + fechaOrden, x, y, paint);
        y += lineHeight;
        canvas.drawText("Cliente: " + clienteNombre + " " + clienteApellidos, x, y, paint);
        y += lineHeight;
        canvas.drawText("DNI del Cliente: " + clienteDNI, x, y, paint);
        y += lineHeight;

// Continuar con el formato y agregación de más variables
        for (BoletaItem bol : boletaFinal){
            String nombreConsumo = bol.getNombreConsumo() ;
            int cantidad = bol.getCantidad();
            double precioUnitario = bol.getPrecioUnitario();
            canvas.drawText("Nombre del Consumo: " + nombreConsumo, x, y, paint);
            y += lineHeight;
            canvas.drawText("Cantidad: " + cantidad, x, y, paint);
            y += lineHeight;
            canvas.drawText("Precio Unitario: $" + String.format("%.2f", precioUnitario), x, y, paint);
            y += lineHeight;



        }

        canvas.drawText("Total: $" + String.format("%.2f", total), x, y, paint);


        // Puedes continuar agregando más contenido aquí...

        pdfDocument.finishPage(page);

        // Guardar el documento PDF en el almacenamiento externo

        File pdfFile = new File(Environment.getExternalStorageDirectory(), "boleta.pdf");
        try {
            pdfDocument.writeTo(new FileOutputStream(pdfFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

        pdfDocument.close();


        ************************************************************* */
    }




}
