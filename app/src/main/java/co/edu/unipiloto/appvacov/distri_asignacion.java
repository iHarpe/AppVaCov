package co.edu.unipiloto.appvacov;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class distri_asignacion extends AppCompatActivity {
    private EditText idRepresentante;
    private EditText region;
    private EditText ciudad;
    private EditText localidad;
    private EditText vacunas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distri_asignacion);

        idRepresentante=(EditText) findViewById(R.id.edIdRep);
        region=(EditText) findViewById(R.id.edRegion);
        ciudad=(EditText) findViewById(R.id.edCiudad);
        localidad=(EditText) findViewById(R.id.edLocalidad);
        vacunas=(EditText) findViewById(R.id.edVacunas);

    }
    public void AsignarASitio (View view){

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administrador", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String representante = idRepresentante.getText().toString();
        String regi = region.getText().toString();
        String ciu = ciudad.getText().toString();
        String loca = localidad.getText().toString();
        String vac = vacunas.getText().toString();
        String compl = regi+", "+ciu+", "+loca;

        if(!representante.isEmpty() && !ciu.isEmpty() && !regi.isEmpty() && !loca.isEmpty() && !vac.isEmpty()){
            int cantidad = Integer.parseInt(vac);
            int inv =0;
            Cursor fila = BaseDeDatos.rawQuery
                    ("select vacunas from inventario where cedula =" +MainActivity.getIdentificador(), null);
            if(fila.moveToFirst()){
                inv = Integer.parseInt(fila.getString(0));
                if(inv-cantidad>0){
                    ContentValues registro = new ContentValues();
                    registro.put("cedula", representante);
                    registro.put("ubicacion", compl);
                    registro.put("vacunas", vac);
                    registro.put("accepted",false);
                    BaseDeDatos.insert("distri", null, registro);
                    ContentValues reg = new ContentValues();
                    reg.put("vacunas",inv-cantidad);
                    int z= BaseDeDatos.update("inventario", reg, "cedula =" +MainActivity.getIdentificador(), null);
                    if (z ==1){
                        Toast.makeText(this, "Inventario actualizado", Toast.LENGTH_SHORT).show();
                    }
                    BaseDeDatos.close();
                    region.setText("");
                    ciudad.setText("");
                    localidad.setText("");
                    vacunas.setText("");


                }else{
                    Toast.makeText(this, "Inventario insuficiente", Toast.LENGTH_SHORT).show();
                    BaseDeDatos.close();
                }
            }else{
                Toast.makeText(this, "No hay vacunas en el inventario", Toast.LENGTH_SHORT).show();
                BaseDeDatos.close();
            }
    }BaseDeDatos.close();
}
}