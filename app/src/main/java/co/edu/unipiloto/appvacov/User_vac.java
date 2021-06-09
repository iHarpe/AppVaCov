package co.edu.unipiloto.appvacov;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class User_vac extends AppCompatActivity {
    private EditText edOcupacion;
    private Spinner edMor1;
    private Spinner edMor2;
    private Spinner edMor3;
    private EditText edEps;
    private EditText edCiu1;
    private EditText edCiu2;
    private EditText edDir1;
    private EditText edDir2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_vac);
        edOcupacion = (EditText)findViewById(R.id.teIndetificacion);
        edMor1 = (Spinner)findViewById(R.id.teMor1);
        edMor2 = (Spinner)findViewById(R.id.teMor2);
        edMor3 = (Spinner)findViewById(R.id.teMor3);
        edEps = (EditText) findViewById(R.id.teEPS);
        edCiu1= (EditText) findViewById(R.id.teCiu1);
        edCiu2= (EditText) findViewById(R.id.teCiu2);
        edDir1= (EditText) findViewById(R.id.tedir1);
        edDir2= (EditText) findViewById(R.id.teDir2);
    }
    //metodo para dar de alta al producto
    public void Reservar (View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "reg_vac", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String ocupacion= edOcupacion.getText().toString();
        String mor1= edMor1.getSelectedItem().toString();
        String mor2= edMor2.getSelectedItem().toString();
        String mor3= edMor3.getSelectedItem().toString();
        String eps= edEps.getText().toString();
        String dir1= edDir1.getText().toString()+" "+edCiu1.getText().toString();
        String dir2= edDir2.getText().toString()+" "+edCiu2.getText().toString();

        if (!ocupacion.isEmpty() && !eps.isEmpty() ){
            ContentValues registro = new ContentValues();
            registro.put("cedula", MainActivity.getIdentificador());
            registro.put("ocupacion", ocupacion);
            registro.put("morbidities", mor1 +" "+mor2+" "+mor3);
            registro.put("eps",eps);
            registro.put("direccion1", dir1);
            registro.put("direccion2", dir2);
            Cursor fila = BaseDeDatos.rawQuery
                    ("select ocupacion from reg_vac where cedula =" + MainActivity.getIdentificador(), null);
            if(fila.moveToFirst()){
                Toast.makeText(this, "Usted ya realizo el proceso de reserva", Toast.LENGTH_SHORT).show();
                BaseDeDatos.close();
            }else{


                BaseDeDatos.insert("reg_vac", null, registro);
                BaseDeDatos.close();
                edOcupacion.setText("");
                edEps.setText("");
                Toast.makeText(this,"Reserva exitoso", Toast.LENGTH_SHORT).show();}
        }else{
            Toast.makeText(this, "Debes especificar tu Ocupacion y EPS", Toast.LENGTH_SHORT).show();



        }}

}