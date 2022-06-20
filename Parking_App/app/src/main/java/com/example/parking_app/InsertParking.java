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

public class InsertParking extends AppCompatActivity {

    private static final int SACAFOTO = 2;
    ImageView imgfoto;
    Button btinsert;
    EditText editid, editnome, editendreco;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_parking);
        imgfoto = findViewById(R.id.img_foto_insert);
        imgfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itfoto = new Intent(Intent.ACTION_GET_CONTENT);
                itfoto.setType("image/*");
                startActivityForResult(itfoto, SACAFOTO);
            }
        });
        editid = findViewById(R.id.edit_id_insert);
        editnome = findViewById(R.id.edit_nome_insert);
        editendreco = findViewById(R.id.edit_endreco_insert);
        btinsert = findViewById(R.id.bt_insert_insert);
        btinsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Parquemento novo;
                int id = Integer.parseInt(editid.getText().toString());
                String nome = editnome.getText().toString();
                String endreco = editendreco.getText().toString();
                Drawable dw = imgfoto.getDrawable();
                if (dw == null){
                    novo = new Parquemento(id,nome,endreco,new byte[]{});
                }else {
                    Bitmap bmp = ((BitmapDrawable) dw).getBitmap();
                    novo = new Parquemento(id,nome,endreco,bmp);

                }
                MyDB myDB = new MyDB(InsertParking.this);
                myDB.addParquemento(novo);
                App.parquementos = myDB.getParquementos();
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = Uri.parse(data.getData().toString());
        imgfoto.setImageURI(uri);
    }
}