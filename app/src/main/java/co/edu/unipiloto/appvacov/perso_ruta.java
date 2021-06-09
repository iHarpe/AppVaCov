package co.edu.unipiloto.appvacov;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class perso_ruta extends AppCompatActivity {
    private Geocoder geo;
    private TextView txVi1;
    private EditText fecha;
    private TextView txVi2;
    private RadioButton apie,enauto,enbici;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perso_ruta);
        geo = new Geocoder(this, Locale.getDefault());
        apie = (RadioButton) findViewById(R.id.isCaminando);
        enauto = (RadioButton) findViewById(R.id.isAuto);
        enbici = (RadioButton) findViewById(R.id.isBici);
        fecha = (EditText) findViewById(R.id.txFechaBuscar);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }
    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
    public void ObtenerRuta(View view) {

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administrador", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        String direccion = "";
        List<Address> dir = new LinkedList<>();
        String fex = fecha.getText().toString();
        Cursor res = BaseDeDatos.rawQuery
                ("Select cedula, fech_dosis1, fech_dosis2 FROM vacunacion WHERE fech_dosis1 = '"+fex+"'OR fech_dosis2= "+fex, null);
        if (res.moveToFirst()) {
            Cursor x = BaseDeDatos.rawQuery
                    ("Select direccion1, direccion2 FROM reg_vac WHERE cedula = "+res.getString(0), null);
            if (x.moveToFirst()) {
                if(res.getString(1).compareTo(fex)==0){
                    try {
                        dir.addAll( geo.getFromLocationName(x.getString(0).toString(),1));
                    } catch (IOException e) {
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(this, "Vacuna 1", Toast.LENGTH_SHORT).show();
                }else if(res.getString(2).compareTo(fex)==0){
                    try {
                        dir.addAll( geo.getFromLocationName(x.getString(1).toString(),1));
                    } catch (IOException e) {
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(this, "Vacuna 2", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "Se ha producido un error", Toast.LENGTH_SHORT).show();
                }

            }else{
                Toast.makeText(this, "No se ha encontrado", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "No existen ubicaciones", Toast.LENGTH_SHORT).show();
        }


        BaseDeDatos.close();
        String medio ="";
        if(enauto.isChecked()) medio="d";
        if(enbici.isChecked()) medio="b";
        if(apie.isChecked()) medio="w";
        Uri gmmIntentUri = Uri.parse("google.navigation:q="+dir.get(0).getLatitude()+","+dir.get(0).getLongitude()+"&mode="+medio);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);

        //Donde mode =
        //d = auto
        //w = a pie
        //b = bicicleta
    }
}