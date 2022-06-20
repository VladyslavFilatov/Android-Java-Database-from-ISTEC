package com.example.tres;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

public class Filme implements Parcelable, Serializable {
    public int id;
    public String titulo;
    public String categoria;
    public byte[] foto;

    public Filme(int id, String titulo, String categoria, Bitmap bmp) {
        this.id = id;
        this.titulo = titulo;
        this.categoria = categoria;
        this.foto = fromBitmapToArray(bmp);
    }

    public Filme(int id, String titulo, String categoria, byte[] f) {
        this.id = id;
        this.titulo = titulo;
        this.categoria = categoria;
        this.foto = f;
    }

    public static byte[] fromBitmapToArray(Bitmap bmp){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG,100,stream);
        return  stream.toByteArray();
    }

    public  Bitmap fromArrayToBitmap(){
      return   BitmapFactory.decodeByteArray(foto,0,foto.length);
    }

    protected Filme(Parcel in) {
        id = in.readInt();
        titulo = in.readString();
        categoria = in.readString();
        foto = in.createByteArray();
    }

    public static final Creator<Filme> CREATOR = new Creator<Filme>() {
        @Override
        public Filme createFromParcel(Parcel in) {
            return new Filme(in);
        }

        @Override
        public Filme[] newArray(int size) {
            return new Filme[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(titulo);
        parcel.writeString(categoria);
        parcel.writeByteArray(foto);
    }
}
