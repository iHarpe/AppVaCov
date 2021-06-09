package co.edu.unipiloto.appvacov;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class perso_informe extends AppCompatActivity {
    private EditText teIdentificacion;
    private EditText teSintomas;
    private RadioButton ra1,ra2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perso_informe);
        teIdentificacion=(EditText) findViewById(R.id.teIndetificacion);
        teSintomas=(EditText) findViewById(R.id.teSintomas);
        ra1 = (RadioButton) findViewById(R.id.radB1);
        ra2 = (RadioButton) findViewById(R.id.radB2);
    }
    public void CrearInforme (View view){

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administrador", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String id = teIdentificacion.getText().toString();
        String sintomas = teSintomas.getText().toString();
        int proceso;

        if(!id.isEmpty() && !sintomas.isEmpty()){

            Cursor x = BaseDeDatos.rawQuery
                    ("select vacunado from vacunacion where cedula =" +id, null);
            if(x.moveToFirst()){
                proceso = Integer.parseInt(x.getString(0));
                int sel=0;
                ContentValues registro = new ContentValues();
                registro.put("cedula", id);
                registro.put("sintomas", sintomas);
                registro.put("vacunador", MainActivity.getIdentificador());
                BaseDeDatos.insert("informe_perso", null, registro);

                ContentValues reg = new ContentValues();
                if(ra1.isChecked()){ sel=1;}

                if(ra2.isChecked()){ sel=2;}

                if(proceso<sel){
                    reg.put("vacunado", sel);
                    int z= BaseDeDatos.update("vacunacion", reg, "cedula =" +id, null);
                    BaseDeDatos.close();
                    Toast.makeText(this, "Informe creado satisfactoriamente", Toast.LENGTH_SHORT).show();
                    teIdentificacion.setText("");
                    teSintomas.setText("");
                }else{
                    Toast.makeText(this, "Ya se ha aplicado esta dosis.", Toast.LENGTH_SHORT).show();
                }


            }else{
                Toast.makeText(this, "No encontrado", Toast.LENGTH_SHORT).show();

            }
            }else{Toast.makeText(this, "Deber llenar todos los campos.", Toast.LENGTH_SHORT).show();
             BaseDeDatos.close();}



            }
}