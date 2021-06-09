package co.edu.unipiloto.appvacov;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Sitio_vac extends AppCompatActivity {
    private EditText ed_cedula;
    private EditText ed_nombre;
    private EditText ed_telefono;
    private EditText ed_direccion;
    private EditText ed_edad;
    private EditText ed_nombredeusuario;
    private TextView tv_info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitio_vac);
        ed_cedula=(EditText)findViewById(R.id.edCedula);
        ed_nombre=(EditText)findViewById(R.id.edNombre);
        ed_telefono=(EditText)findViewById(R.id.edTelefono);
        ed_direccion=(EditText)findViewById(R.id.edDireccion);
        ed_edad=(EditText)findViewById(R.id.edEdad);
        ed_nombredeusuario=(EditText)findViewById(R.id.edNombredeusuario);
        tv_info=(TextView)findViewById(R.id.txInfo);
        tv_info.setMovementMethod(new ScrollingMovementMethod());

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void Buscar(View view){
        tv_info.setText("");
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatabase = admin.getWritableDatabase();
        String codigo = ed_cedula.getText().toString();

        if(!codigo.isEmpty()){
            Cursor fila = BaseDeDatabase.rawQuery
                    ("select nombre, edad, telefono, direccion, correo from usuario where cedula =" + codigo, null);
            if (fila.moveToFirst()){
                ed_nombre.setText(fila.getString(0));
                ed_edad.setText(fila.getString(1));
                ed_telefono.setText(fila.getString(2));
                ed_direccion.setText(fila.getString(3));
                ed_nombredeusuario.setText(fila.getString(4));

                Cursor res = BaseDeDatabase.rawQuery
                        ("select  ocupacion, morbidities, eps  from reg_vac where cedula =" + codigo, null);
                if (res.moveToFirst()){
                    tv_info.append("Ocupacion: "+res.getString(0)+'\n'+"Comorbilidades: "+res.getString(1)+'\n'+"EPS: "+res.getString(2)+'\n');


                    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate fechaNac = LocalDate.parse(fila.getString(1), fmt);
                    LocalDate ahora = LocalDate.now();
                    Period periodo = Period.between(fechaNac, ahora);
                    String etapa="";
                    if(periodo.getYears() >80 ){
                        etapa="Etapa 1";
                    }else if(periodo.getYears() <80 && periodo.getYears()>59){
                        etapa="Etapa 2";
                    }else if(periodo.getYears() <60 && periodo.getYears()>15 && res.getString(1).compareToIgnoreCase("Ninguna Ninguna Ninguna")!=0){
                        etapa="Etapa 3";
                    }else etapa="Etapas 4-5";
                    tv_info.append(etapa+'\n');


                    Cursor z = BaseDeDatabase.rawQuery
                            ("select  vacunado, vacunador, fech_dosis1, fech_dosis2  from vacunacion where cedula =" + codigo, null);
                    if (z.moveToFirst()){
                        tv_info.append('\n'+"Dosis aplicadas: "+z.getString(0)+'\n'+"Id del vacunador asignado: "+z.getString(1)+'\n'+"Fecha dosis 1: "+z.getString(2)+'\n'+"Fecha dosis 2: "+z.getString(3)+'\n');
                    }else {
                        tv_info.append("No se ha asignado al personal"+'\n');
                    }
                }else {
                    tv_info.append("El usuario no ha realizado el proceso de reserva"+'\n');
                }


                BaseDeDatabase.close();
            }else {
                Toast.makeText(this, "No existe el usuario", Toast.LENGTH_SHORT).show();
                BaseDeDatabase.close();
            }


        }else {
            Toast.makeText(this, "No hay cedula", Toast.LENGTH_SHORT).show();

        }

    }
}