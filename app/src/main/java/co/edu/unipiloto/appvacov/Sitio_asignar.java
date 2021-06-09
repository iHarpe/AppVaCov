package co.edu.unipiloto.appvacov;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Sitio_asignar extends AppCompatActivity {
    private EditText idPaciente;
    private EditText fecha1;
    private EditText fecha2;
    private EditText idVacunador;
    javax.mail.Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitio_asignar);
        idPaciente=(EditText) findViewById(R.id.edDocumento);
        fecha1=(EditText) findViewById(R.id.edFecha1);
        fecha2=(EditText) findViewById(R.id.edFecha2);
        idVacunador=(EditText) findViewById(R.id.edVacunador);
    }
    public void Asignar (View view){

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administrador", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String paciente = idPaciente.getText().toString();
        String fech1 = fecha1.getText().toString();
        String fech2 = fecha2.getText().toString();
        String vacunador = idVacunador.getText().toString();
        final String correoperfil = "secocomail@gmail.com";
        final String contraperfil ="JavaEE1379";

        if(!paciente.isEmpty() && !fech1.isEmpty() && !fech2.isEmpty() && !vacunador.isEmpty()){
            int cantidad = 1;
            int inv =0;
            Cursor filx = BaseDeDatos.rawQuery
                    ("select vacunas from inventario where cedula =" +MainActivity.getIdentificador(), null);
            if(filx.moveToFirst()){
                inv = Integer.parseInt(filx.getString(0));
                if(inv-cantidad>0){
            ContentValues registro = new ContentValues();
            registro.put("cedula", paciente);
            registro.put("vacunado", 0);
            registro.put("vacunador", vacunador);
            registro.put("fech_dosis1", fech1);
            registro.put("fech_dosis1", fech2);

            Cursor fila = BaseDeDatos.rawQuery
                    ("select cedula from vacunacion where cedula =" +paciente, null);
            if(fila.moveToFirst()){
                Toast.makeText(this, "Paciente ya asignado", Toast.LENGTH_SHORT).show();
                BaseDeDatos.close();
            }else{
                Cursor x = BaseDeDatos.rawQuery
                        ("select tipo, correo from usuario where cedula =" +vacunador, null);
                if(x.moveToFirst()){
                    if(x.getString(0).compareTo("2")== 0){
                        BaseDeDatos.insert("vacunacion", null, registro);
                        idPaciente.setText("");
                        idVacunador.setText("");
                        fecha1.setText("");
                        fecha2.setText("");
                        ContentValues reg = new ContentValues();
                        reg.put("vacunas",inv-cantidad);
                        int z= BaseDeDatos.update("inventario", reg, "cedula =" +MainActivity.getIdentificador(), null);
                        if (z ==1){
                            Toast.makeText(this, "Inventario actualizado", Toast.LENGTH_SHORT).show();
                        }



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
                                message.setRecipients(MimeMessage.RecipientType.TO,InternetAddress.parse(x.getString(1)));
                                message.setContent("Registro exitoso","text/html; charset=utf-8");
                                Transport.send(message);

                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }



                        BaseDeDatos.close();
                        Toast.makeText(this,"Asignacion exitosa", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this, "Usuario no es personal de vacunacion", Toast.LENGTH_SHORT).show();
                        BaseDeDatos.close();
                    }

                }else{
                    Toast.makeText(this,"Usuario no existe", Toast.LENGTH_SHORT).show();
                }

        }}else{
                    Toast.makeText(this, "Inventario insuficiente", Toast.LENGTH_SHORT).show();
                    BaseDeDatos.close();
                }
            }else{
                Toast.makeText(this, "No hay vacunas en el inventario", Toast.LENGTH_SHORT).show();
                BaseDeDatos.close();
            }
                }else{
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
}