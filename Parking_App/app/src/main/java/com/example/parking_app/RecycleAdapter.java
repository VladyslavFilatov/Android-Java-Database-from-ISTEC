package com.example.parking_app;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.InnerView> implements View.OnClickListener {
    List<Parquemento> myParking;
    Context ctx;
    MyDB mybd;
    ISacaFoto listener;
    public void setOnSacaFotoListener(ISacaFoto l){
        this.listener = l;
    }

    public RecycleAdapter(Context context, List<Parquemento> parquementos) {
        ctx = context;
        myParking = parquementos;
    }

    @NonNull
    @Override
    public InnerView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v  = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle,parent,false);
        return new InnerView(v);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerView holder, @SuppressLint("RecyclerView") int position) {
        Parquemento prq = myParking.get(position);
        holder.txtid.setText(String.valueOf(prq.id));
        holder.editnome.setText(prq.nome);
        holder.editendreco.setText(prq.endreco);
        holder.btdelete.setTag(prq);
        holder.btdelete.setOnClickListener(this);
        holder.btedit.setOnClickListener(this);
        holder.btedit.setTag(holder);
        holder.btfind.setTag(prq);
        holder.btfind.setOnClickListener(this);
        holder.imgfoto.setImageBitmap(Parquemento.ArrayToBitmap(prq.foto));
        holder.imgfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.sacafoto_handler(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myParking.size();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_delete_rec:
                mybd = new MyDB(ctx);
                Parquemento p = (Parquemento) view.getTag();
                mybd.deleteParquemento(p.id);
                App.parquementos = mybd.getParquementos();
                ((MainActivity)ctx).finish();
                ((MainActivity)ctx).overridePendingTransition(0,0);
                ((MainActivity)ctx).startActivity( ((MainActivity)ctx).getIntent());
                ((MainActivity)ctx).overridePendingTransition(0,0);

                break;

            case R.id.bt_update_rec:
                Parquemento prq;
                RecycleAdapter.InnerView holder = (RecycleAdapter.InnerView)view.getTag();
                int id = Integer.parseInt(holder.txtid.getText().toString());
                String nome = holder.editnome.getText().toString();
                String endreco = holder.editendreco.getText().toString();
                Drawable dw = holder.imgfoto.getDrawable();
                mybd = new MyDB(ctx);
                if (dw != null){
                    Bitmap bmp = ((BitmapDrawable)dw).getBitmap();
                    if (bmp == null){
                        prq = new Parquemento(id,nome,endreco,new byte[]{});

                    }else {
                        prq = new Parquemento(id,nome,endreco,bmp);
                    }
                } else {
                    prq = new Parquemento(id,nome,endreco,new byte[]{});

                }
                mybd.updateParquemento(prq);
                App.parquementos = mybd.getParquementos();
                ((MainActivity)ctx).finish();
                ((MainActivity)ctx).overridePendingTransition(0,0);
                ((MainActivity)ctx).startActivity( ((MainActivity)ctx).getIntent());
                ((MainActivity)ctx).overridePendingTransition(0,0);

                Toast.makeText(ctx, "Edit", Toast.LENGTH_SHORT).show();
                break;

            case R.id.bt_find_rec:
                Intent it = new Intent(view.getContext(), Web.class);
                ((MainActivity)ctx).startActivity(it);
                Toast.makeText(ctx, "Find", Toast.LENGTH_LONG).show();


                break;
        }
    }

    public class InnerView extends RecyclerView.ViewHolder {
        TextView txtid;
        EditText editnome, editendreco;
        Button btedit, btdelete, btfind;
        ImageView imgfoto;
        public InnerView(@NonNull View v) {
            super(v);
            txtid = v.findViewById(R.id.txt_id_rec);
            editnome =v.findViewById(R.id.edit_nome_rec);
            editendreco=v.findViewById(R.id.edit_endreco_rec);
            btfind=v.findViewById(R.id.bt_find_rec);
            btedit=v.findViewById(R.id.bt_update_rec);
            btdelete=v.findViewById(R.id.bt_delete_rec);
            imgfoto=v.findViewById(R.id.img_foto_rec);
        }
    }
}
