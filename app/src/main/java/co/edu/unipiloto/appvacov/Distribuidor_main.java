package co.edu.unipiloto.appvacov;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Distribuidor_main extends AppCompatActivity {

    private Button boton1;
    private Button boton2;
    private Button boton3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distribuidor_main);
        boton1 = (Button) findViewById(R.id.button14);
        boton2 = (Button) findViewById(R.id.button15);
        boton3 = (Button) findViewById(R.id.button16);

        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Distribuidor_main.this, distri_inventario.class);
                startActivity(intent1); //lanza la segunda actividad
            }
        });

        boton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Distribuidor_main.this, distri_asignacion.class);
                startActivity(intent1); //lanza la segunda actividad
            }
        });

        boton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Distribuidor_main.this, distri_estado_distribucion.class);
                startActivity(intent1); //lanza la segunda actividad
            }
        });

    }
}