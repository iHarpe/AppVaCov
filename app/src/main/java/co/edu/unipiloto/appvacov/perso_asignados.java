package co.edu.unipiloto.appvacov;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class perso_asignados extends AppCompatActivity {
    private TextView tvxInfo;
    private EditText txFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perso_asignados2);
        tvxInfo = (TextView)findViewById(R.id.txVerInfor);
        txFecha = (EditText)findViewById(R.id.edtFesha);
    }

    public void Consultar (View view){
        String fecha= txFecha.getText().toString();
        tvxInfo.setText("");
        if (!fecha.isEmpty()){
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administrador", null, 1);
            SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
            Cursor res = BaseDeDatos.rawQuery
                    ("SELECT u.nombre, v.fech_dosis1, v.fech_dosis2,v.vacunado,r.direccion1,r.direccion2 FROM usuario u, vacunacion v, reg_vac r WHERE (v.fech_dosis1 = '"+fecha+ "' OR v.fech_dosis2 = '"+fecha+ "') AND u.cedula = v.cedula AND r.cedula = v.cedula",null);
            if (res.moveToFirst()) {
                for(res.moveToFirst(); !res.isAfterLast(); res.moveToNext()){
                if (res.getString(1).compareToIgnoreCase(fecha) == 0) {
                    tvxInfo.append("Nombre: " + res.getString(0) + '\n'+ "Fecha: " + res.getString(1) + '\n'+" Dosis aplicadas :" + res.getString(3) + '\n'+ "Direccion :" + res.getString(4) + '\n');
                }else{
                    tvxInfo.append("Nombre: " + res.getString(0) + '\n'+ "Fecha: " + res.getString(2) + '\n'+ "Dosis aplicadas :" + res.getString(3) + '\n'+ "Direccion :" + res.getString(5) + '\n');
                }
                    tvxInfo.append("- - - - - - - - - - - - - - - - - - - - "+'\n');
            }
            }else tvxInfo.setText("No se encontro nada para esta fecha");
        }else{
            Toast.makeText(this, "Debes ingresar la fecha.", Toast.LENGTH_SHORT).show();
        }
    }
}