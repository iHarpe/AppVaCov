package co.edu.unipiloto.appvacov;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SecondActivity extends AppCompatActivity{
    private EditText ed_cedula;
    private EditText ed_nombre;
    private EditText ed_telefono;
    private EditText ed_direccion;
    private EditText ed_edad;
    private EditText ed_nombredeusuario;
    private EditText passwordx;
    private Spinner tipox;
    javax.mail.Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ed_cedula=(EditText)findViewById(R.id.edCedula);
        ed_nombre=(EditText)findViewById(R.id.edNombre);
        ed_telefono=(EditText)findViewById(R.id.edTelefono);
        ed_direccion=(EditText)findViewById(R.id.edDireccion);
        ed_edad=(EditText)findViewById(R.id.edEdad);
        ed_nombredeusuario=(EditText)findViewById(R.id.edNombredeusuario);
        passwordx=(EditText)findViewById(R.id.edContraseña);
        tipox=(Spinner)findViewById(R.id.tip_usr);

    }
    //metodo para dar de alta al producto
    public void Registrar (View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "usuario", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String cedula= ed_cedula.getText().toString();
        String nombre= ed_nombre.getText().toString();
        String telefono= ed_telefono.getText().toString();
        String direccion= ed_direccion.getText().toString();
        String edad= ed_edad.getText().toString();
        String correo= ed_nombredeusuario.getText().toString();
        String password= passwordx.getText().toString();
        String tipo = tipox.getSelectedItem().toString();
        final String correoperfil = "secocomail@gmail.com";
        final String contraperfil ="JavaEE1379";


        if (!cedula.isEmpty() && !nombre.isEmpty() && !telefono.isEmpty() && !direccion.isEmpty() && !edad.isEmpty() && !correo.isEmpty() && !password.isEmpty() ){
            ContentValues registro = new ContentValues();
            registro.put("cedula", cedula);
            registro.put("nombre", nombre);
            registro.put("edad", edad);
            registro.put("telefono", telefono);
            registro.put("direccion", direccion);
            registro.put("correo", correo);
            registro.put("password", password);
            switch(tipo) {
                case "Usuario de vacunación":
                    registro.put("tipo", 1);
                    break;
                case "Personal de vacunación":
                    registro.put("tipo", 2);
                    break;
                case "Representante de sitio de vacunación":
                    registro.put("tipo", 3);
                    break;
                case "Receptor y distribuidor de vacunas":
                    registro.put("tipo", 4);
                    break;
            }
            Cursor fila = BaseDeDatos.rawQuery
                    ("select nombre from usuario where cedula =" +cedula, null);
            if(fila.moveToFirst()){
                Toast.makeText(this, "Numero de cedula ya registrado", Toast.LENGTH_SHORT).show();
                BaseDeDatos.close();
            }else{


                BaseDeDatos.insert("usuario", null, registro);
                BaseDeDatos.close();
                ed_cedula.setText("");
                ed_nombre.setText("");
                ed_telefono.setText("");
                ed_direccion.setText("");
                ed_edad.setText("");
                ed_nombredeusuario.setText("");
                passwordx.setText("");


                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                Properties properties = new Properties();
                properties.put("mail.smtp.host","smtp.googlemail.com");
                properties.put("mail.smtp.socketFactory.port","465");
                properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
                properties.put("mail.smtp.auth","true");
                properties.put("mail.smtp.port","465");
                try {
                    session= Session.getDefaultInstance(properties, new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(correoperfil,contraperfil);
                        }
                    });

                    if(session!=null){
                        MimeMessage message = new MimeMessage(session);
                        message.setFrom(new InternetAddress(correoperfil));
                        message.setSubject("Registro exitoso AppVaCov");
                        message.setRecipients(MimeMessage.RecipientType.TO,InternetAddress.parse(correo));
                        message.setContent("Registro exitoso","text/html; charset=utf-8");
                        Transport.send(message);

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                Toast.makeText(this,"Registro exitoso", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();



        }}
    //metodo para eliminar producto
    public void Eliminar (View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDatabase= admin.getWritableDatabase();
        String cedula = ed_cedula.getText().toString();

        if (!cedula.isEmpty()){
            int cantidad= BaseDatabase.delete("usuario", "cedula" + cedula, null);
            BaseDatabase.close();
            ed_cedula.setText("");
            ed_nombre.setText("");
            ed_telefono.setText("");
            ed_direccion.setText("");
            ed_edad.setText("");
            ed_nombredeusuario.setText("");

            if (cantidad ==1){
                Toast.makeText(this, "Articulo elimnado",  Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(this, "Articulo no existe", Toast.LENGTH_SHORT).show();
            }


        }else{
            Toast.makeText(this, "Introducir codigo de articulo" , Toast.LENGTH_SHORT).show();


        }
    }
    //metodo para modificar
    public void Modificar (View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaDatabase = admin.getWritableDatabase();
        String cedula= ed_cedula.getText().toString();
        String nombre= ed_nombre.getText().toString();
        String telefono= ed_telefono.getText().toString();
        String direccion= ed_direccion.getText().toString();
        String edad= ed_edad.getText().toString();
        String nombredeusuario= ed_nombredeusuario.getText().toString();

        if(!cedula.isEmpty() && !nombre.isEmpty() && !telefono.isEmpty() && !direccion.isEmpty() && !edad.isEmpty() && !nombredeusuario.isEmpty()){
            ContentValues registro = new ContentValues();
            registro.put("cedula", cedula);
            registro.put("nombre", nombre);
            registro.put("telefono", telefono);
            registro.put("direccion", direccion);
            registro.put("edad", edad);
            registro.put("nombre de usuario", nombredeusuario);


            int cantidad= BaDatabase.update("articulos", registro, "cedula" +cedula, null);
            BaDatabase.close();

            if (cantidad ==1){
                Toast.makeText(this, "Articulo modificado", Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();

            }


        }else {
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();


        }


    }



}