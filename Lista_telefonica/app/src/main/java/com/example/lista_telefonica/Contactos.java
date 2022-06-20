package com.example.lista_telefonica;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

public class Contactos implements Parcelable, Serializable {
    public int id;
    public String nome;
    public String numero;
    public byte[] foto;

    public Contactos(int id, String nome, String numero, Bitmap btm) {
        this.id = id;
        this.nome = nome;
        this.numero = numero;
        this.foto = fromBitmapToArray(btm);
    }

    public Contactos(int id, String nome, String numero, byte[] f) {
        this.id = id;
        this.nome = nome;
        this.numero = numero;
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

    protected Contactos(Parcel in) {
        id = in.readInt();
        nome = in.readString();
        numero = in.readString();
        foto = in.createByteArray();
    }

    public static final Creator<Contactos> CREATOR = new Creator<Contactos>() {
        @Override
        public Contactos createFromParcel(Parcel in) {
            return new Contactos(in);
        }

        @Override
        public Contactos[] newArray(int size) {
            return new Contactos[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(nome);
        parcel.writeString(numero);
        parcel.writeByteArray(foto);
    }
}
