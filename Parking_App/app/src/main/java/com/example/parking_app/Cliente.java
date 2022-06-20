package com.example.parking_app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.ByteArrayOutputStream;

public class Cliente implements Parcelable {
    public int cod;
    public String name;
    public String telefone;
    public  byte[] fotos;


    public  static Bitmap ArrayToBitmap(byte[] myfoto){
        return BitmapFactory.decodeByteArray(myfoto,0,myfoto.length);
    }

    public  static byte[] BitmapToArray(Bitmap bmp){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG,100,stream);
        return stream.toByteArray();
    }

    public Cliente(int cod, String name, String telefone, byte[] fotos) {
        this.cod = cod;
        this.name = name;
        this.telefone = telefone;
        this.fotos = fotos;
    }


    public Cliente(int cod, String name, String telefone, Bitmap bmp) {
        this.cod = cod;
        this.name = name;
        this.telefone = telefone;
        this.fotos = BitmapToArray(bmp);
    }

    protected Cliente(Parcel in) {
        cod = in.readInt();
        name = in.readString();
        telefone = in.readString();
        fotos = in.createByteArray();
    }

    public static final Creator<Cliente> CREATOR = new Creator<Cliente>() {
        @Override
        public Cliente createFromParcel(Parcel in) {
            return new Cliente(in);
        }

        @Override
        public Cliente[] newArray(int size) {
            return new Cliente[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(cod);
        parcel.writeString(name);
        parcel.writeString(telefone);
        parcel.writeByteArray(fotos);
    }
}
