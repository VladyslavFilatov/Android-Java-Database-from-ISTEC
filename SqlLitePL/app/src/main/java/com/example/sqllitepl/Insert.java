package com.example.sqllitepl;

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

public class Insert extends AppCompatActivity {

    private static final int SACAFOTO = 2;
    EditText editid,editnome, edittelefone;
    Button btinsert;
    ImageView imgfoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        editid= findViewById(R.id.edit_id_inserir);
        editnome=findViewById(R.id.edit_nome_inserir);
        edittelefone=findViewById(R.id.edit_telefone_inserir);
        imgfoto = findViewById(R.id.img_foto_inserir);
        imgfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itfoto = new Intent(Intent.ACTION_GET_CONTENT);
                itfoto.setType("image/*");
                startActivityForResult(itfoto,SACAFOTO);
            }
        });
        btinsert= findViewById(R.id.bt_inserir_inserir);
        btinsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contacto novo=null;
                try {
                    int id = Integer.parseInt(editid.getText().toString());
                    String nome = editnome.getText().toString();
                    String telefone = edittelefone.getText().toString();
                    Drawable dw = imgfoto.getDrawable();
                    if(dw!=null){
                        Bitmap bmp = ((BitmapDrawable) dw).getBitmap();
                        novo = new Contacto(id, nome, telefone, bmp);
                    }else{
                        novo = new Contacto(id, nome, telefone,new byte[]{});

                    }
                    App.contactos.add(novo);
                    App.gravaLista();

                }catch (Exception erro){
                    Toast.makeText(Insert.this, erro.getMessage(), Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==SACAFOTO){
            Uri uri = Uri.parse(data.getData().toString());
            imgfoto.setImageURI(uri);
        }

    }
}