package com.example.parking_app;

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

public class InsertClients extends AppCompatActivity {

    private static final int SACAFOTOS = 2;
    ImageView imgfotos;
    Button btinserts;
    EditText editcod, editname, edittelefone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_clients);
        imgfotos = findViewById(R.id.img_foto_insert_client);
        imgfotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itfoto = new Intent(Intent.ACTION_GET_CONTENT);
                itfoto.setType("image/*");
                startActivityForResult(itfoto, SACAFOTOS);
            }
        });
        editcod = findViewById(R.id.edit_id_insert_client);
        editname = findViewById(R.id.edit_nome_insert_client);
        edittelefone = findViewById(R.id.edit_telefone_insert_client);
        btinserts = findViewById(R.id.bt_insert_insert_client);
        btinserts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cliente novo;
                int cod = Integer.parseInt(editcod.getText().toString());
                String name = editname.getText().toString();
                String telefone = edittelefone.getText().toString();
                Drawable dw = imgfotos.getDrawable();
                if (dw == null){
                    novo = new Cliente(cod,name,telefone,new byte[]{});
                }else {
                    Bitmap bmp = ((BitmapDrawable) dw).getBitmap();
                    novo = new Cliente(cod,name,telefone,bmp);

                }
                MyDB myDB = new MyDB(InsertClients.this);
                myDB.addCliente(novo);
                App.clientes = myDB.getClientes();
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = Uri.parse(data.getData().toString());
        imgfotos.setImageURI(uri);
    }
}