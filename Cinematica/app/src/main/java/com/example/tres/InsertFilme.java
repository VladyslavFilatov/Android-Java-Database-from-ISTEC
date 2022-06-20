package com.example.tres;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class InsertFilme extends AppCompatActivity {

    private static final int GETFOTO = 5;
    Button btinsert;
    EditText editid, edittitulo;
    Spinner spincat;
    ImageView foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_filme);
        btinsert = findViewById(R.id.bt_insert_insertfilme);
        btinsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    setResult(RESULT_OK);
                    int id = Integer.parseInt(editid.getText().toString());
                    String titulo = edittitulo.getText().toString();
                    String categoria = spincat.getSelectedItem().toString();
                    Drawable drw = foto.getDrawable();
                    Bitmap bmp = ((BitmapDrawable)drw).getBitmap();
                    Filme novo = new Filme(id, titulo, categoria, bmp);
                    App.filmes.add(novo);
                    App.gravarLista();
                    finish();
                }catch (Exception erro){
                    Toast.makeText(InsertFilme.this, erro.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });
        editid = findViewById(R.id.edit_id_insertfilme);
        edittitulo = findViewById(R.id.edit_titulo_insertfilme);
        ArrayAdapter<CharSequence>ad = ArrayAdapter.createFromResource(InsertFilme.this, R.array.categorias, R.layout.support_simple_spinner_dropdown_item);
        ad.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spincat = findViewById(R.id.spinner_categoria_insertfilme);
        spincat.setAdapter(ad);
        foto=findViewById(R.id.img_foto_insertfilme);
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