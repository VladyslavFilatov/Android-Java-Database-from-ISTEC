package com.example.tres;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

public class FilmeAdapter extends ArrayAdapter<Filme> {
    private static final String TAG = "XPTO";

    public class Handler {
        EditText editid;
        EditText edittitulo;
        Spinner spincategoria;
        ImageView imgfoto;
        Button btupdate, btinsert, btdelete;
    }

    Context ctx;
    ArrayList<Filme> filmes;
    int idresource;
    ISacaFotoListener lst;
    public  void setOnSacaFotoListener(ISacaFotoListener l){
        this.lst=l;
    }
    public FilmeAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Filme> objects) {
        super(context, resource, objects);
        ctx = context;
        idresource = resource;
        filmes = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        final Filme filme = filmes.get(position);
        Handler handler= new Handler();
        if(view ==null){
            view = LayoutInflater.from(ctx).inflate(idresource,parent,false);
            handler.editid=view.findViewById(R.id.edit_id_itemfilme);
            handler.edittitulo=view.findViewById(R.id.edit_titulo_itemfilme);
            handler.spincategoria=view.findViewById(R.id.spinner_categoria_itemfilme);
            handler.imgfoto=view.findViewById(R.id.img_foto_itemfilme);
            handler.btinsert= view.findViewById(R.id.bt_insert_itemfilme);
            handler.btdelete= view.findViewById(R.id.bt_delete_itemfime);
            handler.btupdate= view.findViewById(R.id.bt_update_itemfilme);
            view.setTag(handler);
        }else{

             handler=(Handler) view.getTag();
        }
        handler.editid.setText(String.valueOf(filme.id));
        handler.edittitulo.setText(filme.titulo);
        ArrayAdapter<CharSequence>adpt = ArrayAdapter.createFromResource(ctx,R.array.categorias,R.layout.support_simple_spinner_dropdown_item);
        adpt.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        handler.spincategoria.setAdapter(adpt);
        handler.spincategoria.setSelection(App.retornaIndex(filme.categoria));
        handler.imgfoto.setImageBitmap(filme.fromArrayToBitmap());
        handler.imgfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lst.OnSacaFotoHandler(position);
            }
        });
        handler.btinsert.setTag(filme);
        handler.btinsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Filme filme = (Filme) view.getTag();
                Log.i(TAG, filme.titulo);
            }
        });
        handler.btdelete.setTag(filme);
        handler.btdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Filme filme = (Filme) view.getTag();
                Log.i(TAG, filme.titulo);
                App.filmes.remove(filme);
                App.gravarLista();
                ((MainActivity)ctx).adpt.notifyDataSetChanged();
                Toast.makeText(ctx, "Registo Eliminado", Toast.LENGTH_SHORT).show();
            }
        });

        handler.btupdate.setTag(filme);
        Handler finalHandler = handler;
        handler.btupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Filme filme = (Filme) view.getTag();
                Log.i(TAG, filme.titulo);
                int id = Integer.parseInt(finalHandler.editid.getText().toString());
                String titulo = finalHandler.edittitulo.getText().toString();
                String categoria = finalHandler.spincategoria.getSelectedItem().toString();
                Drawable dwr = finalHandler.imgfoto.getDrawable();
                Bitmap foto = ((BitmapDrawable)dwr).getBitmap();
                App.updateFilme(filme.id, id, titulo, categoria, foto);
                App.gravarLista();
                ((MainActivity)ctx).adpt.notifyDataSetChanged();
            }
        });
        return view;
    }
}
