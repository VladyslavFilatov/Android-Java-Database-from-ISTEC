package com.example.tres;

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
import java.util.List;

public class App extends Application {
    public static   ArrayList<Filme>filmes= new ArrayList<Filme>();
    public  static Context ctx;
    @Override
    public void onCreate() {
        super.onCreate();
        ctx= getApplicationContext();
        CriaLista();
    }

    static  void CriaLista(){
        //filmes.add(new Filme(1,"O Padrinho","Terror",new byte[]{}));
        //filmes.add(new Filme(2,"O Rei Leão","Infantil",new byte[]{}));
        //filmes.add(new Filme(3,"Simplemente Maria","Romance",new byte[]{}));
        filmes=CarregaLista();
    }

    public  static  int retornaIndex(String opcao){
        String[] categorias = ctx.getResources().getStringArray(R.array.categorias);
        int i;
        for(i=0; i< categorias.length; i++){

            if(categorias[i].equals(opcao))return i;
        }
        return -1;
    }

    public static ArrayList<Filme>CarregaLista(){
        try {
            File dir = new File(ctx.getFilesDir(), "midir");
            if (!dir.exists()){
                dir.mkdir();
            }
            File fichin = new File(dir, "filmes.data");
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(fichin));
            ArrayList<Filme>filmes = (ArrayList<Filme>) is.readObject();
            is.close();
            Toast.makeText(ctx, "Lista Carregada com sucesso", Toast.LENGTH_LONG).show();
            return filmes;

        }catch (Exception e){
            ArrayList<Filme>filmes = new ArrayList<Filme>();
            Toast.makeText(ctx, "Lista Vazia", Toast.LENGTH_LONG).show();
            return filmes;

        }
    }

    public static void gravarLista(){
        try {
            File dir = new File(ctx.getFilesDir(), "midir");
            if (!dir.exists()){
                dir.mkdir();
            }
            File fichout = new File(dir, "filmes.data");
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fichout));
            os.writeObject(filmes);
            os.close();
            Toast.makeText(ctx, "Lista Gravada com sucesso", Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(ctx, "Erro de Gravação de lista", Toast.LENGTH_LONG).show();
        }
    }

    public static void updateFilme(int oldid, int id, String titulo, String categoria, Bitmap bmp){
        Boolean flag = false;
        for (int i=0; i< filmes.size(); i++){
            if (filmes.get(i).id== oldid){
                filmes.get(i).id=id;
                filmes.get(i).titulo=titulo;
                filmes.get(i).categoria=categoria;
                filmes.get(i).foto=Filme.fromBitmapToArray(bmp);
                gravarLista();
                Toast.makeText(ctx, "Registo Gravado", Toast.LENGTH_SHORT).show();
                flag=true;
                break;
            }
        }
        if (!flag) Toast.makeText(ctx, "Não Gravou", Toast.LENGTH_SHORT).show();
    }

}
