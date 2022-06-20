package com.example.parking_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

public class Clients extends AppCompatActivity {

    private static final int INSERTCLIENTE = 1;
    private static final int SACAFOTOS = 2;
    RecyclerView recview2;
    RecycleAdapter2 adpt2;
    FloatingActionButton fab2;
    public int Posicao2;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent it;
        switch (item.getItemId()){
            case R.id.parking:
                it = new Intent(Clients.this,MainActivity.class);
                startActivity(it);
                break;
            case R.id.weather:
                it = new Intent(Clients.this,Weather.class);
                startActivity(it);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clients);
        recview2 = findViewById(R.id.recycle_clients);
        adpt2 = new RecycleAdapter2(Clients.this,App.clientes);
        adpt2.setOnSacaFotosListener(new ISacaFotos() {
            @Override
            public void sacafotos_handler(int posicao) {
                Intent itfoto = new Intent(Intent.ACTION_GET_CONTENT);
                itfoto.setType("image/*");
                Posicao2 = posicao;
                startActivityForResult(itfoto, SACAFOTOS);
            }
        });
        recview2.setAdapter(adpt2);
        recview2.setLayoutManager(new LinearLayoutManager(this));
        fab2 = findViewById(R.id.float_insert_clients);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Clients.this, "Hi", Toast.LENGTH_SHORT).show();
                Intent it = new Intent(Clients.this,InsertClients.class);
                startActivityForResult(it, INSERTCLIENTE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==INSERTCLIENTE){
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        }
        if (requestCode == SACAFOTOS){

            Uri uri = Uri.parse(data.getData().toString());
            try {
                Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                App.clientes.get(Posicao2).fotos=Cliente.BitmapToArray(bmp);
                adpt2.notifyDataSetChanged();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}