package com.example.lista_telefonica;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class InsertContacto extends AppCompatActivity {
    private static final int GETFOTO = 5;
    Button btinsert;
    EditText editid, editnome, editnumero;
    ImageView foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_contacto);
        btinsert = findViewById(R.id.bt_insert_cont);
        btinsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    setResult(RESULT_OK);
                    int id = Integer.parseInt(editid.getText().toString());
                    String nome = editnome.getText().toString();
                    String numero = editnumero.getText().toString();
                    Drawable drw = foto.getDrawable();
                    Bitmap bmp = ((BitmapDrawable)drw).getBitmap();
                    Contactos novo = new Contactos(id, nome, numero, bmp);
                    App.contactoslist.add(novo);
                    App.gravarLista();
                    finish();
                }catch (Exception erro){
                    Toast.makeText(InsertContacto.this, erro.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });
        editid = findViewById(R.id.id_lista_cont);
        editnome = findViewById(R.id.lista_nome_cont);
        editnumero = findViewById(R.id.lista_numeno_cont);
        foto=findViewById(R.id.img_foto_lista_cont);
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itfoto = new Intent(Intent.ACTION_GET_CONTENT);
                itfoto.setType("image/*");
                startActivityForResult(itfoto,GETFOTO);
            }
        });
    }//oncreate

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==GETFOTO){
            Uri uri = Uri.parse(data.getData().toString());
            foto.setImageURI(uri);
        }
    }
}