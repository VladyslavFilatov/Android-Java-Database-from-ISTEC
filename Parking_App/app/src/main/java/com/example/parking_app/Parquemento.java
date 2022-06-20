package com.example.parking_app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

public class Parquemento implements Parcelable, Serializable {
    public  int id;
    public  String nome;
    public String endreco;
    public  byte[] foto;

    @NonNull
    @Override
    public String toString() {
        return  String.format("Id:%d  Nome:%s Endreco:%s",this.id,this.nome,this.endreco);
    }

    protected Parquemento(Parcel in) {
        id = in.readInt();
        nome = in.readString();
        endreco = in.readString();
        foto = in.createByteArray();
    }

    public static final Creator<Parquemento> CREATOR = new Creator<Parquemento>() {
        @Override
        public Parquemento createFromParcel(Parcel in) {
            return new Parquemento(in);
        }

        @Override
        public Parquemento[] newArray(int size) {
            return new Parquemento[size];
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
    public Parquemento(int id, String nome, String endreco, byte[] foto) {
        this.id = id;
        this.nome = nome;
        this.endreco = endreco;
        this.foto = foto;
    }
    public Parquemento(int id, String nome, String endreco, Bitmap bmp) {
        this.id = id;
        this.nome = nome;
        this.endreco = endreco;
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
        parcel.writeString(endreco);
        parcel.writeByteArray(foto);
    }
}
