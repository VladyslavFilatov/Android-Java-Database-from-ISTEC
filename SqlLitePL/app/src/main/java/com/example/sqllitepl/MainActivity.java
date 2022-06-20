package com.example.sqllitepl;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final int CANALINSERT = 1;
    private static final int SACAFOTO = 15;
    ListView lista;
  ContactoAdapter adpt;
  FloatingActionButton fab;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== CANALINSERT){
            finish();
            overridePendingTransition( 0, 0);
            startActivity(getIntent());
            overridePendingTransition( 0, 0);
        }
        if (requestCode==SACAFOTO){
            Uri uri = Uri.parse(data.getData().toString());
            try {
                Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                App.contactos.get(posimagem).foto=Contacto.BitmapToArray(bmp);
                adpt.notifyDataSetChanged();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int posimagem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lista=findViewById(R.id.list_contactos_main);
        adpt = new ContactoAdapter(MainActivity.this,R.layout.item_contacto,App.contactos);
        adpt.setOnSacaFotoListener(new IOnSacaFotoListener() {
            @Override
            public void OnSacaFotoHandler(int posicao) {
                posimagem=posicao;
                Intent itfoto = new Intent(Intent.ACTION_GET_CONTENT);
                itfoto.setType("image/*");
                startActivityForResult(itfoto,SACAFOTO);
            }
        });
        lista.setAdapter(adpt);
        fab=findViewById(R.id.fab_inserir_main);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent it = new Intent(MainActivity.this,Insert.class);
               startActivityForResult(it,CANALINSERT);
            }
        });
    }
}