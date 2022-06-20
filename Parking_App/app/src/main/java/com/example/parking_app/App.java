package com.example.parking_app;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class App extends Application {

    public static final String TAG = "XPTO";
    public static MyDB myDB;
    public static List<Parquemento> parquementos = new ArrayList<>();
    public static List<Cliente> clientes = new ArrayList<>(); // TABLE 2
    static Context ctx;


    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "On Create");
        ctx = getApplicationContext();
        myDB = new MyDB(ctx);
        //myDB.addParquemento(new Parquemento(1,"Central","Avenida de Liberdade",new byte[]{}));
        parquementos = myDB.getParquementos();
        clientes = myDB.getClientes(); // TABLE 2
    }

    public static void gravaLista() throws IOException {
        ObjectOutputStream outstream = null;
        try {
            File dir = new File(ctx.getFilesDir(), "mydir");
            if (!dir.exists()) {
                dir.mkdir();
            }
            File fich = new File(dir, "parquementos.data");
            outstream = new ObjectOutputStream(new FileOutputStream(fich));
            outstream.writeObject(parquementos);

        } catch (Exception erro) {

        } finally {
            if (outstream != null) outstream.close();
        }
    }

    public static void carregaLista() throws IOException {
        ObjectInputStream inputStream = null;
        try {
            File dir = new File(ctx.getFilesDir(), "mydir");
            if (!dir.exists()) {
                dir.mkdir();
            }
            File fich = new File(dir, "parquementos.data");
            inputStream = new ObjectInputStream(new FileInputStream(fich));
            parquementos = (ArrayList<Parquemento>) inputStream.readObject();

        } catch (Exception erro) {
            Toast.makeText(ctx, erro.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            if (inputStream != null) inputStream.close();
        }
    }

    // TABLE 2

    public static void gravarLista() throws IOException {
        ObjectOutputStream outstream = null;
        try {
            File dir = new File(ctx.getFilesDir(), "mydir");
            if (!dir.exists()) {
                dir.mkdir();
            }
            File fich = new File(dir, "clientes.data");
            outstream = new ObjectOutputStream(new FileOutputStream(fich));
            outstream.writeObject(clientes);

        } catch (Exception erro) {

        } finally {
            if (outstream != null) outstream.close();
        }
    }

    public static void carregarLista() throws IOException {
        ObjectInputStream inputStream = null;
        try {
            File dir = new File(ctx.getFilesDir(), "mydir");
            if (!dir.exists()) {
                dir.mkdir();
            }
            File fich = new File(dir, "clientes.data");
            inputStream = new ObjectInputStream(new FileInputStream(fich));
            clientes = (ArrayList<Cliente>) inputStream.readObject();

        } catch (Exception erro) {
            Toast.makeText(ctx, erro.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            if (inputStream != null) inputStream.close();
        }
    }


}
