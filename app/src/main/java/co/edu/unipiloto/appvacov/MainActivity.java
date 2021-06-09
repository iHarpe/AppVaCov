package co.edu.unipiloto.appvacov;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText user;
    private EditText password;
    private Button login;
    private Button logup;
    private TextView info;
    private static String identificador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user =(EditText) findViewById(R.id.edUser);
        password = (EditText) findViewById(R.id.edPassword);
        login = (Button) findViewById(R.id.btnLogin);
        logup = (Button) findViewById(R.id.btnLogup);
        info = (TextView) findViewById(R.id.txvInfo);

        logup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent); //lanza la segunda actividad
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.onEditorAction(EditorInfo.IME_ACTION_DONE);    //Esconder teclado
                password.onEditorAction(EditorInfo.IME_ACTION_DONE);
                validate(user.getText().toString(),password.getText().toString()); //Valida los datos
            }
        });
    }
    public void launch(View view){
        Intent intent = new Intent(MainActivity.this, Adapter.class);
        startActivity(intent);
    }
    private void validate (String usr, String passwrd){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "admin", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        Cursor fila = BaseDeDatos.rawQuery
                ("select password, tipo from usuario where cedula =" + usr, null);
        if(fila.moveToFirst()){
            if(fila.getString(0).compareTo(passwrd)==0){
                Toast.makeText(this, "Iniciando", Toast.LENGTH_SHORT).show();
                int rol = fila.getInt(1);
                setIdentificador(usr);
                switch (rol){
                    case 1:
                        Intent intent1 = new Intent(MainActivity.this, User_vac.class);
                        startActivity(intent1); //lanza la segunda actividad
                        break;

                    case 2:
                        Intent intent2 = new Intent(MainActivity.this, Perso_main.class);
                        startActivity(intent2); //lanza la segunda actividad
                        break;

                    case 3:
                        Intent intent3 = new Intent(MainActivity.this, Sitio_main.class);
                        startActivity(intent3); //lanza la segunda actividad
                        break;

                    case 4:
                        Intent intent4 = new Intent(MainActivity.this, Distribuidor_main.class);
                        startActivity(intent4); //lanza la segunda actividad
                        break;

                    default:
                        Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                        break;
                }


                //launch
            }else{
                info.setText("Datos incorrectos."); //Actualiza los datos de info
            }
        }else {
            info.setText("Datos incorrectos."); //Actualiza los datos de info
        }

    }
    public static String getIdentificador(){
        return identificador;
    }
    public static void setIdentificador(String identific){
        identificador = identific;
    }


}
