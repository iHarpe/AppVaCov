package co.edu.unipiloto.appvacov;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class Adapter extends AppCompatActivity {
    private ListView lvItems;
    private Adaptador adaptador;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_adapter);
            lvItems = (ListView) findViewById(R.id.lvItems);
            adaptador = new Adaptador(this, GetArrayItems());
            lvItems.setAdapter(adaptador);
            lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                /**
                 * Callback method to be invoked when an item in this AdapterView has
                 * been clicked.
                 * <p>
                 * Implementers can call getItemAtPosition(position) if they need
                 * to access the data associated with the selected item.
                 *
                 * @param parent   The AdapterView where the click happened.
                 * @param view     The view within the AdapterView that was clicked (this
                 *                 will be a view provided by the adapter)
                 * @param position The position of the view in the adapter.
                 * @param id       The row id of the item that was clicked.
                 */
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switch (position){
                        case 0:
                            Intent intent0 = new Intent(Adapter.this,SecondActivity.class);
                            startActivity(intent0);
                            break;
                        case 1:
                            Intent intent1 = new Intent(Adapter.this,MainActivity.class);
                            startActivity(intent1);
                            break;
                        case 3:
                            Intent intent3 = new Intent(Adapter.this,perso_ruta.class);
                            startActivity(intent3);
                            break;
                    }

                }

            });

        }

        private ArrayList<Entidad> GetArrayItems(){
            ArrayList<Entidad> listItems= new ArrayList<> ();
            listItems.add(new Entidad(R.drawable.registro, "REGISTRO", "A contimuación te registraras en la aplicación"));
            listItems.add(new Entidad(R.drawable.login, "LOGIN", "Entrar a la aplicación"));
            listItems.add(new Entidad(R.drawable.etapa, "ETAPA", "Conoce en que etapa se encuentra la vacunación"));
            listItems.add(new Entidad(R.drawable.ruta, "RUTA", "Conoce tu ruta "));
            listItems.add(new Entidad(R.drawable.info, "INFORMACIÓN", "Informate mas sobre este virus" ));

            return listItems;
        }
    }

