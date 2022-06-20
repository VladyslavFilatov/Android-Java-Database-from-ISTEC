package com.example.lista_telefonica;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class App extends Application{
    public static ArrayList<Contactos>contactoslist = new ArrayList<Contactos>();
    private static Context ctx;
    @Override
    public void onCreate() {
        super.onCreate();
        ctx = getApplicationContext();
        CriaLista();
    }

    static void CriaLista(){
        contactoslist=CarregaLista();
    }

    public static ArrayList<Contactos>CarregaLista(){
        try {
            File dir = new File(ctx.getFilesDir(), "midir");
            if (!dir.exists()){
                dir.mkdir();
            }
            File fichin = new File(dir, "contactos.data");
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(fichin));
            ArrayList<Contactos>contactos = (ArrayList<Contactos>) is.readObject();
            is.close();
            Toast.makeText(ctx, "Lista Carregada com sucesso", Toast.LENGTH_LONG).show();
            return contactos;

        }catch (Exception e){
            ArrayList<Contactos>contactos = new ArrayList<Contactos>();
            Toast.makeText(ctx, "Lista Vazia", Toast.LENGTH_LONG).show();
            return contactos;

        }
    }

    public static void gravarLista(){
        try {
            File dir = new File(ctx.getFilesDir(), "midir");
            if (!dir.exists()){
                dir.mkdir();
            }
            File fichout = new File(dir, "contactos.data");
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fichout));
            os.writeObject(contactoslist);
            os.close();
            Toast.makeText(ctx, "Lista Gravada com sucesso", Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(ctx, "Erro de Gravação de lista", Toast.LENGTH_LONG).show();
        }
    }

    public static void updateContactos(int oldid, int id, String nome, String numero, Bitmap bmp){
        Boolean flag = false;
        for (int i=0; i< contactoslist.size(); i++){
            if (contactoslist.get(i).id== oldid){
                contactoslist.get(i).id=id;
                contactoslist.get(i).nome=nome;
                contactoslist.get(i).numero=numero;
                contactoslist.get(i).foto=Contactos.fromBitmapToArray(bmp);
                gravarLista();
                Toast.makeText(ctx, "Registo Gravado", Toast.LENGTH_SHORT).show();
                flag=true;
                break;
            }
        }
        if (!flag) Toast.makeText(ctx, "Não Gravou", Toast.LENGTH_SHORT).show();
    }

}
