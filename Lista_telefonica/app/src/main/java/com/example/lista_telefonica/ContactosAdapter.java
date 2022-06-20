package com.example.lista_telefonica;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

public class ContactosAdapter extends ArrayAdapter<Contactos> {
    private static final String TAG = "XPTO";

    public class Handler{
        EditText editid;
        EditText editnome;
        EditText editnumero;
        ImageView imgfoto;
        Button btcall, btupdate, btdelite;
    }

    Context ctx;
    ArrayList<Contactos>contactoslist;
    int idresource;
    ISacaFotoListener lst;
    public void setOnSacaFotoListener(ISacaFotoListener l){
        this.lst=l;
    }
    public ContactosAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Contactos> objects) {
        super(context, resource, objects);
        ctx = context;
        idresource = resource;
        contactoslist = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        final Contactos contactos = contactoslist.get(position);
        Handler handler = new Handler();
        if (view == null){
            view = LayoutInflater.from(ctx).inflate(idresource,parent,false);
            handler.editid=view.findViewById(R.id.id_lista);
            handler.editnome=view.findViewById(R.id.lista_nome);
            handler.editnumero=view.findViewById(R.id.lista_numeno);
            handler.imgfoto=view.findViewById(R.id.img_foto_lista);
            handler.btcall=view.findViewById(R.id.bt_call);
            handler.btupdate=view.findViewById(R.id.bt_update);
            handler.btdelite=view.findViewById(R.id.bt_delete);
            view.setTag(handler);
        }else{
            handler=(Handler) view.getTag();
        }
        handler.editid.setText(String.valueOf(contactos.id));
        handler.editnome.setText(String.valueOf(contactos.nome));
        handler.editnumero.setText(String.valueOf(contactos.numero));
        handler.imgfoto.setImageBitmap(contactos.fromArrayToBitmap());
        handler.imgfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lst.OnSacaFotoHandler(position);
            }
        });
        handler.btdelite.setTag(contactos);
        handler.btdelite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contactos contactos = (Contactos) view.getTag();
                Log.i(TAG, contactos.nome);
                App.contactoslist.remove(contactos);
                App.gravarLista();
                ((MainActivity)ctx).adtp.notifyDataSetChanged();
                Toast.makeText(ctx, "Registo Eliminado", Toast.LENGTH_SHORT).show();
            }
        });
        handler.btupdate.setTag(contactos);
        Handler finalHandler = handler;
        handler.btupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contactos contactos = (Contactos) view.getTag();
                Log.i(TAG, contactos.nome);
                int id = Integer.parseInt(finalHandler.editid.getText().toString());
                String nome = finalHandler.editnome.getText().toString();
                String numero = finalHandler.editnumero.getText().toString();
                Drawable dwr = finalHandler.imgfoto.getDrawable();
                Bitmap foto = ((BitmapDrawable)dwr).getBitmap();
                App.updateContactos(contactos.id, id, nome, numero, foto);
                App.gravarLista();
                ((MainActivity)ctx).adtp.notifyDataSetChanged();
            }
        });

        handler.btcall.setTag(contactos);
        handler.btcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = finalHandler.editnumero.getText().toString();
                if (phone.isEmpty()){
                    Toast.makeText(ctx, "Please enter number", Toast.LENGTH_SHORT).show();
                }else {
                    String s = "tel:" + phone;
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse(s));
                    ((MainActivity)ctx).startActivity(intent);
                }
            }
        });

        return view;
    }
}
