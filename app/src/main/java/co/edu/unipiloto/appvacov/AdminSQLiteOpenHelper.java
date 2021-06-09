package co.edu.unipiloto.appvacov;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "BaseDeDatos";
    private static final int DB_VERSION= 1;
    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase BaseDeDatos) {
        BaseDeDatos.execSQL
                ("create table usuario(cedula integer primary key, nombre text, edad text, telefono integer, direccion text, correo text, password text, tipo integer )");
        BaseDeDatos.execSQL
                ("create table reg_vac(cedula integer primary key, ocupacion text, morbidities text, eps text, direccion1 text, direccion2 text )");
        BaseDeDatos.execSQL
                ("create table vacunacion(cedula integer primary key, vacunado integer, vacunador integer, fech_dosis1 text, fech_dosis2 text)");
        BaseDeDatos.execSQL
                ("create table sitiovac(cedula integer primary key, vacunas number)");
        BaseDeDatos.execSQL
                ("create table distri(guia integer primary key AUTOINCREMENT,cedula integer , ubicacion text, vacunas number, accepted boolean )");
        BaseDeDatos.execSQL
                ("create table inventario(cedula integer primary key, vacunas number)");
        BaseDeDatos.execSQL
                ("create table informe_perso(id integer primary key AUTOINCREMENT,cedula integer , sintomas text, vacunador integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
