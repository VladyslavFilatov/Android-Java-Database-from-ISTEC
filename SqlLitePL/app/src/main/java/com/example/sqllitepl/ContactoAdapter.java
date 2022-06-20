package com.example.sqllitepl;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.util.ArrayList;

public class ContactoAdapter extends ArrayAdapter<Contacto> {
    public Context ctx;
    public int myresource;
    IOnSacaFotoListener listener;
    ArrayList<Contacto> mycontacts;
    public void setOnSacaFotoListener(IOnSacaFotoListener lst){
        this.listener = lst;
    }

    public class Handler {
        public TextView txtid;
        public EditText editnome;
        public EditText edittelefone;
        public ImageView imgfoto;
        public Button btupdate, btdelete, btcall;
    }

    public ContactoAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Contacto> contactos) {
        super(context, resource, contactos);
        ctx = context;
        mycontacts = contactos;
        myresource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View v, @NonNull ViewGroup parent) {
        Handler handler = new Handler();
        if (v == null) {
            v=LayoutInflater.from(ctx).inflate(myresource, parent, false);
            handler.txtid = v.findViewById(R.id.txt_id_item);
            handler.editnome = v.findViewById(R.id.edit_nome_item);
            handler.edittelefone = v.findViewById(R.id.edit_telefone_item);
            handler.imgfoto = v.findViewById(R.id.img_foto_item);
            handler.btcall = v.findViewById(R.id.bt_call_item);
            handler.btupdate = v.findViewById(R.id.bt_update_item);
            handler.btdelete = v.findViewById(R.id.bt_delete_item);
            v.setTag(handler);
        }else{
            handler = (Handler) v.getTag();
        }
        Contacto contacto = App.contactos.get(position);
        handler.txtid.setText(String.valueOf(contacto.id));
        handler.editnome.setText(contacto.nome);
        handler.edittelefone.setText(contacto.telefone);
        handler.btcall.setTag(contacto);
        handler.imgfoto.setImageBitmap(Contacto.ArrayToBitmap(contacto.foto));
        handler.imgfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnSacaFotoHandler(position);
            }
        });
        handler.btcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itcall = new Intent(Intent.ACTION_CALL);
                String telefone = ((Contacto)view.getTag()).telefone;
                itcall.setData(Uri.parse("tel:" + telefone));
                if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(((MainActivity)ctx), new String[]{Manifest.permission.CALL_PHONE},20);

                }else {
                    
                    ctx.startActivity(itcall);

                }

            }
        });
        handler.btdelete.setTag(contacto);
        handler.btdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App.contactos.remove(contacto);
                try {
                    App.gravaLista();
                    App.carregaLista();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ((MainActivity)ctx).adpt.notifyDataSetChanged();
            }
        });
        handler.btupdate.setTag(contacto);
        Handler finalHandler = handler;
        handler.btupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = contacto.id; //Integer.parseInt(finalHandler.txtid.getText().toString());
                String nome = finalHandler.editnome.getText().toString();
                String telefona = finalHandler.edittelefone.getText().toString();
                Drawable dw = finalHandler.imgfoto.getDrawable();
                Bitmap bmp = ((BitmapDrawable)dw).getBitmap();
                for (Contacto c : App.contactos){
                    if (c.id==id){
                        c.nome=nome;
                        c.telefone=telefona;
                        c.foto=Contacto.BitmapToArray(bmp);
                        try {
                            App.gravaLista();
                            App.carregaLista();
                            ((MainActivity)ctx).adpt.notifyDataSetChanged();
                            Toast.makeText(ctx, "Update Conseguido", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(ctx, "Falha no update", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });


        return v;
    }
}
