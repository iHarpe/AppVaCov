package co.edu.unipiloto.appvacov;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class sitio_ingresarVac extends AppCompatActivity {
    private EditText edNumGuia;
    private TextView tvxGuias,tvxInfoInv;
    private String guia="";
    private Button btnIngresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitio_ingresar_vac);
        edNumGuia = (EditText)findViewById(R.id.edNumGuia);
        tvxGuias = (TextView)findViewById(R.id.tvxGuias);
        btnIngresar = (Button)findViewById(R.id.btnIngresar13);
        tvxInfoInv = (TextView)findViewById(R.id.tvxInfoInv);
        refreshing();
    }

    public void refreshing (){
        tvxGuias.setText("");
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administrador", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        String sitio = MainActivity.getIdentificador();
        Cursor res = BaseDeDatos.rawQuery
                ("select  *  from distri where cedula =" + sitio, null);
        if (res.moveToFirst()){

            for(res.moveToFirst(); !res.isAfterLast(); res.moveToNext()){
                tvxGuias.append("Guia :"+res.getString(0)+" EPS ID: "+res.getString(1)+" Ubicacion :"+res.getString(2)+" Vacunas :"+res.getString(3)+" Ingresadas: "+res.getString(4).toString() +'\n');
                guia=res.getString(0);
            }
            edNumGuia.setText(guia);

        }else {
            tvxGuias.setText("No hay guias disponibles");
            btnIngresar.setEnabled(false);
        }
        Cursor x = BaseDeDatos.rawQuery
                ("select vacunas from inventario where cedula =" +MainActivity.getIdentificador(), null);
        if(x.moveToFirst()){
            tvxInfoInv.setText("Inventario: " +x.getString(0));
        }



        BaseDeDatos.close();
    }
    public void Ingresar (View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administrador", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        String nGuia= edNumGuia.getText().toString();
        ContentValues reg = new ContentValues();
        ContentValues acc = new ContentValues();
        Cursor res = BaseDeDatos.rawQuery
                ("select  *  from distri where guia =" + nGuia, null);
        if (res.moveToFirst()){
            if(res.getString(4).compareToIgnoreCase("0")==0){

                Cursor fila = BaseDeDatos.rawQuery
                        ("select vacunas from inventario where cedula =" +MainActivity.getIdentificador(), null);
                if(fila.moveToFirst()){
                    int actual=0;
                    actual=Integer.parseInt(fila.getString(0));
                    reg.put("vacunas",actual+ Integer.parseInt(res.getString(3)));
                    int z= BaseDeDatos.update("inventario", reg, "cedula =" +MainActivity.getIdentificador(), null);
                    acc.put("accepted",true);
                    int x= BaseDeDatos.update("distri", acc, "guia =" +nGuia, null);
                    if (z ==1 && x==1){
                        Toast.makeText(this, "Inventario actualizado", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    reg.put("cedula",MainActivity.getIdentificador());
                    reg.put("vacunas",res.getString(3));
                    BaseDeDatos.insert("inventario", null, reg);
                    acc.put("accepted",true);
                    int x= BaseDeDatos.update("distri", acc, "guia =" +nGuia, null);

                    Toast.makeText(this, "Inventario creado", Toast.LENGTH_SHORT).show();


                }
            }else{
                Toast.makeText(this, "Guia ya ingresada", Toast.LENGTH_SHORT).show();
            }
        }BaseDeDatos.close();
        refreshing();
    }
}