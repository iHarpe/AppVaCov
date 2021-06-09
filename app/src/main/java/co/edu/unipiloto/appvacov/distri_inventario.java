package co.edu.unipiloto.appvacov;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class distri_inventario extends AppCompatActivity {
    private EditText vacunas;
    private TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distri_inventario);
        vacunas=(EditText) findViewById(R.id.edNuevasvac);
        info=(TextView) findViewById(R.id.txInvInfo);

    }

    public void Añadir (View view){

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administrador", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String vac = vacunas.getText().toString();
        ContentValues reg = new ContentValues();
        if(!vac.isEmpty()){
            Cursor fila = BaseDeDatos.rawQuery
                    ("select vacunas from inventario where cedula =" +MainActivity.getIdentificador(), null);
            if(fila.moveToFirst()){
                int actual=0;
                actual=Integer.parseInt(fila.getString(0));
                reg.put("vacunas",actual+ Integer.parseInt(vac));
                int z= BaseDeDatos.update("inventario", reg, "cedula =" +MainActivity.getIdentificador(), null);
                if (z ==1){
                    Toast.makeText(this, "Inventario actualizado", Toast.LENGTH_SHORT).show();
                }

            }else{
                reg.put("cedula",MainActivity.getIdentificador());
                reg.put("vacunas",vac);
                BaseDeDatos.insert("inventario", null, reg);
                Toast.makeText(this, "Inventario creado", Toast.LENGTH_SHORT).show();


            }
            Cursor x = BaseDeDatos.rawQuery
                    ("select vacunas from inventario where cedula =" +MainActivity.getIdentificador(), null);
            if(x.moveToFirst()){
                info.setText("Inventario: " +x.getString(0));
            }

            BaseDeDatos.close();
        }
    }
}