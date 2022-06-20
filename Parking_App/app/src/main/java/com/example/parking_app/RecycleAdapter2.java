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

public class RecycleAdapter2 extends RecyclerView.Adapter<RecycleAdapter2.InnerView> implements View.OnClickListener {
    List<Cliente> myClient;
    Context ctx2;
    MyDB mybd;
    ISacaFotos listener2;
    public void setOnSacaFotosListener(ISacaFotos l2){
        this.listener2 = l2;
    }

    public RecycleAdapter2(Context context, List<Cliente> clientes) {
        ctx2 = context;
        myClient = clientes;
    }

    @NonNull
    @Override
    public RecycleAdapter2.InnerView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v  = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle2,parent,false);
        return new RecycleAdapter2.InnerView(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleAdapter2.InnerView holder, @SuppressLint("RecyclerView") int position2) {
        Cliente cl = myClient.get(position2);
        holder.txtcod.setText(String.valueOf(cl.cod));
        holder.editname.setText(cl.name);
        holder.edittelefone.setText(cl.telefone);
        holder.btdelete2.setTag(cl);
        holder.btdelete2.setOnClickListener(this);
        holder.btedit2.setOnClickListener(this);
        holder.btedit2.setTag(holder);
        holder.btcall.setTag(cl);
        holder.btcall.setOnClickListener(this);
        holder.imgfotos.setImageBitmap(Parquemento.ArrayToBitmap(cl.fotos));
        holder.imgfotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //saca foto
                listener2.sacafotos_handler(position2);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myClient.size();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_delete_rec2:
                mybd = new MyDB(ctx2);
                Cliente c = (Cliente) view.getTag();
                mybd.deleteCliente(c.cod);
                App.clientes = mybd.getClientes();
                ((Clients)ctx2).finish();
                ((Clients)ctx2).overridePendingTransition(0,0);
                ((Clients)ctx2).startActivity( ((Clients)ctx2).getIntent());
                ((Clients)ctx2).overridePendingTransition(0,0);

                break;

            case R.id.bt_update_rec2:
                Cliente cl;
                RecycleAdapter2.InnerView holder = (RecycleAdapter2.InnerView)view.getTag();
                int cod = Integer.parseInt(holder.txtcod.getText().toString());
                String name = holder.editname.getText().toString();
                String telefone = holder.edittelefone.getText().toString();
                Drawable dw = holder.imgfotos.getDrawable();
                mybd = new MyDB(ctx2);
                if (dw != null){
                    Bitmap bmp = ((BitmapDrawable)dw).getBitmap();
                    if (bmp == null){
                        cl = new Cliente(cod,name,telefone,new byte[]{});

                    }else {
                        cl = new Cliente(cod,name,telefone,bmp);
                    }
                } else {
                    cl = new Cliente(cod,name,telefone,new byte[]{});

                }
                mybd.updateCliente(cl);
                App.clientes = mybd.getClientes();
                ((Clients)ctx2).finish();
                ((Clients)ctx2).overridePendingTransition(0,0);
                ((Clients)ctx2).startActivity( ((Clients)ctx2).getIntent());
                ((Clients)ctx2).overridePendingTransition(0,0);

                Toast.makeText(ctx2, "Edit", Toast.LENGTH_SHORT).show();
                break;

            case R.id.bt_Call_rec2:
                Toast.makeText(ctx2, "Call", Toast.LENGTH_LONG).show();
                Intent itCall = new Intent(Intent.ACTION_CALL);
                String tlf = ((Cliente)view.getTag()).telefone;
                itCall.setData(Uri.parse("tel:" + tlf));
                
                if(ContextCompat.checkSelfPermission(ctx2, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(((Clients)ctx2), new String[]{Manifest.permission.CALL_PHONE}, 3);

                }else {

                    ctx2.startActivity(itCall);

                }

                break;
        }
    }

    public class InnerView extends RecyclerView.ViewHolder {
        TextView txtcod;
        EditText editname, edittelefone;
        Button btedit2, btdelete2, btcall;
        ImageView imgfotos;
        public InnerView(@NonNull View v) {
            super(v);
            txtcod = v.findViewById(R.id.txt_id_rec2);
            editname =v.findViewById(R.id.edit_nome_rec2);
            edittelefone=v.findViewById(R.id.edit_telefone_rec2);
            btcall=v.findViewById(R.id.bt_Call_rec2);
            btedit2=v.findViewById(R.id.bt_update_rec2);
            btdelete2=v.findViewById(R.id.bt_delete_rec2);
            imgfotos=v.findViewById(R.id.img_foto_rec2);
        }
    }
}
