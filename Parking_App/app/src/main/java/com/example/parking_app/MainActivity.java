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

public class MainActivity extends AppCompatActivity {
    private static final int INSERTPARQUEMENTO = 1;
    private static final int SACAFOTO = 2;
    RecyclerView recview;
    RecycleAdapter adpt;
    FloatingActionButton fab;
    public int Posicao;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent it;
        switch (item.getItemId()){
            case R.id.clients:
                it = new Intent(MainActivity.this,Clients.class);
                startActivity(it);
                break;
            case R.id.weather:
                it = new Intent(MainActivity.this,Weather.class);
                startActivity(it);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recview = findViewById(R.id.recycle_main);
        adpt = new RecycleAdapter(MainActivity.this,App.parquementos);
        adpt.setOnSacaFotoListener(new ISacaFoto() {
            @Override
            public void sacafoto_handler(int posicao) {
                Intent itfoto = new Intent(Intent.ACTION_GET_CONTENT);
                itfoto.setType("image/*");
                Posicao = posicao;
                startActivityForResult(itfoto, SACAFOTO);
            }
        });
        recview.setAdapter(adpt);
        recview.setLayoutManager(new LinearLayoutManager(this));
        fab = findViewById(R.id.float_insert_main);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Hi", Toast.LENGTH_SHORT).show();
                Intent it = new Intent(MainActivity.this,InsertParking.class);
                startActivityForResult(it, INSERTPARQUEMENTO);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==INSERTPARQUEMENTO){
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        }
        if (requestCode == SACAFOTO){

            Uri uri = Uri.parse(data.getData().toString());
            try {
                Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                App.parquementos.get(Posicao).foto=Parquemento.BitmapToArray(bmp);
                adpt.notifyDataSetChanged();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}