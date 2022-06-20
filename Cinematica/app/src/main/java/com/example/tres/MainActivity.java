package com.example.tres;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final int CANALINSERT = 2;
    ListView lista;
    public FilmeAdapter adpt;
    public  int posicao;
    FloatingActionButton fab;

    @Override
    protected void onPause() {
        super.onPause();
        //App.gravarLista();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lista = findViewById(R.id.list_filmes_mainactivity);
        adpt = new FilmeAdapter(MainActivity.this, R.layout.itemfilme, App.filmes);
        adpt.setOnSacaFotoListener(new ISacaFotoListener() {
            @Override
            public void OnSacaFotoHandler(int pos) {
               Intent it= new Intent(Intent.ACTION_GET_CONTENT);
               it.setType("image/*");
               posicao=pos;
               startActivityForResult(it,1);
            }
        });
        lista.setAdapter(adpt);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bmp=null;
        if(requestCode==1){
            Uri uri =Uri.parse(data.getData().toString());
            try {
                bmp = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                App.filmes.get(posicao).foto=Filme.fromBitmapToArray(bmp);
                adpt.notifyDataSetChanged();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode==CANALINSERT){
            adpt.notifyDataSetChanged();
            Toast.makeText(MainActivity.this, String.valueOf(resultCode), Toast.LENGTH_SHORT).show();
        }


    }

    public void Inserir(View v){
        Intent itinser= new Intent(MainActivity.this, InsertFilme.class);
        startActivityForResult(itinser,CANALINSERT);
        //Toast.makeText(MainActivity.this, "Inserir", Toast.LENGTH_SHORT).show();
    }

}