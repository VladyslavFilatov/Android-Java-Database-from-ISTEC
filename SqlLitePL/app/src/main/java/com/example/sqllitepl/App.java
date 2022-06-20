package com.example.sqllitepl;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class App extends Application {
    private static final String TAG = "XPTO";
    public static MyDB myDB;

    public  static ArrayList<Contacto> contactos = new ArrayList<Contacto>();
    static Context ctx;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG,"On Create");
        //for(Contacto c : contactos)Log.i(TAG,c.toString());
        ctx=getApplicationContext();
        myDB = new MyDB(ctx);
        //myDB.addContacto(new Contacto(1,"Pato Donald", "964694483", new byte[]{}));
        Contacto novo = new Contacto(1,"Pato Donald", "964694483", new byte[]{});
        contactos = (ArrayList<Contacto>) myDB.getContactos();
        for (Contacto c : contactos){
            Log.i(TAG, c.nome);
        }
        long total = myDB.deleteContacto(novo.id);
        long t2 = myDB.updateContacto(novo);
//        try {
//            carregaLista();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
    public static void gravaLista() throws IOException {
        ObjectOutputStream outstream=null;
        try{
            File dir = new File(ctx.getFilesDir(),"mydir");
            if(!dir.exists()){
                dir.mkdir();
            }
            File fich = new File(dir,"contactos.data");
            outstream = new ObjectOutputStream(new FileOutputStream(fich));
            outstream.writeObject(contactos);

        }
        catch (Exception erro){

        }
        finally {
            if(outstream!=null)outstream.close();
        }
    }

    public  static void carregaLista() throws IOException {
        ObjectInputStream inputStream=null;
        try{
            File dir = new File(ctx.getFilesDir(),"mydir");
            if(!dir.exists()){
                dir.mkdir();
            }
            File fich = new File(dir,"contactos.data");
            inputStream = new ObjectInputStream(new FileInputStream(fich));
            contactos = (ArrayList<Contacto>) inputStream.readObject();

        }
        catch(Exception erro){
            Toast.makeText(ctx, erro.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally {
            if(inputStream!=null)inputStream.close();
        }
    }

}
