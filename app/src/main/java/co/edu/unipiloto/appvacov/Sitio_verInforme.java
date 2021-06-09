package co.edu.unipiloto.appvacov;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Sitio_verInforme extends AppCompatActivity {
    private TextView txVer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitio_ver_informe);
        txVer = (TextView)findViewById(R.id.txVerInfor);
        refreshing();
    }

    public void refreshing(){
        txVer.setText("");
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administrador", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        Cursor res = BaseDeDatos.rawQuery
                ("SELECT * FROM informe_perso",null);
        if (res.moveToFirst()) {
            for(res.moveToFirst(); !res.isAfterLast(); res.moveToNext()){
                txVer.append("Reporte creado por: "+res.getString(3)+ '\n'+"Contenido: "+res.getString(2)+ '\n'+"Paciente: "+res.getString(1)+ '\n');
                txVer.append("- - - - - - - - - - - - - - - - - - - - - - -"+'\n');
            }

        }else txVer.setText("No hay informes para mostrar");
    }


}