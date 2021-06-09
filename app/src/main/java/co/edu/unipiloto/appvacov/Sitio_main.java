package co.edu.unipiloto.appvacov;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Sitio_main extends AppCompatActivity {
    private Button boton1;
    private Button boton2;
    private Button boton3;
    private Button boton4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitio_main);
        boton1 = (Button) findViewById(R.id.button6);
        boton2 = (Button) findViewById(R.id.button7);
        boton3 = (Button) findViewById(R.id.button13);
        boton4 = (Button) findViewById(R.id.buttonRepor);

        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Sitio_main.this, Sitio_vac.class);
                startActivity(intent1); //lanza la segunda actividad
            }
        });

        boton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Sitio_main.this, Sitio_asignar.class);
                startActivity(intent1); //lanza la segunda actividad
            }
        });
        boton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Sitio_main.this, sitio_ingresarVac.class);
                startActivity(intent1); //lanza la segunda actividad
            }
        });
        boton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Sitio_main.this, Sitio_verInforme.class);
                startActivity(intent1); //lanza la segunda actividad
            }
        });
    }
}