package com.example.sqllitepl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

public class Contacto implements Parcelable, Serializable {
    public  int id;
    public  String nome;
    public String telefone;
    public  byte[] foto;

    @NonNull
    @Override
    public String toString() {
        return  String.format("Id:%d  Nome:%s Telefone:%s",this.id,this.nome,this.nome);
    }

    protected Contacto(Parcel in) {
        id = in.readInt();
        nome = in.readString();
        telefone = in.readString();
        foto = in.createByteArray();
    }

    public static final Creator<Contacto> CREATOR = new Creator<Contacto>() {
        @Override
        public Contacto createFromParcel(Parcel in) {
            return new Contacto(in);
        }

        @Override
        public Contacto[] newArray(int size) {
            return new Contacto[size];
        }
    };

    public  static Bitmap ArrayToBitmap(byte[] myfoto){
       return BitmapFactory.decodeByteArray(myfoto,0,myfoto.length);
    }

    public  static byte[] BitmapToArray(Bitmap bmp){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG,100,stream);
        return stream.toByteArray();
    }
    public Contacto(int id, String nome, String telefone, byte[] foto) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.foto = foto;
    }
    public Contacto(int id, String nome, String telefone, Bitmap bmp) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.foto = BitmapToArray(bmp);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(nome);
        parcel.writeString(telefone);
        parcel.writeByteArray(foto);
    }
}
