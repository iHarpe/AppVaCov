package co.edu.unipiloto.appvacov;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class distri_estado_distribucion extends AppCompatActivity {
    private TextView tv_info;
    private EditText idx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distri_estado_distribucion);
        tv_info = (TextView) findViewById(R.id.txInformacion);
        idx = (EditText) findViewById(R.id.edIdsitio);
    }

    public void Consulta (View view){


        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administrador", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        String sitio = idx.getText().toString();
        Cursor res = BaseDeDatos.rawQuery
                ("select  *  from distri where cedula =" + sitio, null);
        if (res.moveToFirst()){

            for(res.moveToFirst(); !res.isAfterLast(); res.moveToNext()){
                tv_info.append("Guia :"+res.getString(0)+" EPS ID: "+res.getString(1)+" Ubicacion :"+res.getString(2)+" Vacunas :"+res.getString(3)+'\n');
            }


        }else {
            tv_info.setText("No existen guias");
        }


        BaseDeDatos.close();

    }
}