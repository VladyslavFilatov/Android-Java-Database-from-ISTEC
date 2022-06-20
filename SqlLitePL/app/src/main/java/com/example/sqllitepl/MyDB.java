package com.example.sqllitepl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MyDB extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "contactos";
    public static final String DATABASE_NAME = TABLE_NAME + ".db";
    public static final int VERSAO = 1;
    public static final String ID = "id";
    public static final String NOME = "nome";
    public static final String TELEFONE = "telefone";
    public static final String FOTO = "foto";
    private static final String TAG = "XPTO";
    Context ctx;

    public MyDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSAO);
        ctx=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String strsql = "CREATE TABLE " + TABLE_NAME + " (\n" +
                "    " + ID + "       INTEGER       PRIMARY KEY AUTOINCREMENT, " +
                "    " + NOME + "     VARCHAR (120)," +
                "    " + TELEFONE + " VARCHAR (9)," +
                "    " + FOTO + "     BLOB" +
                ");";
        db.execSQL(strsql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String strsql = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        db.execSQL(strsql);
        onCreate(db);
    }

    public long addContacto(Contacto ct){
        long resp = 0;
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put(ID,ct.id);
            cv.put(NOME,ct.nome);
            cv.put(TELEFONE,ct.telefone);
            cv.put(FOTO,ct.foto);
            resp = db.insert(TABLE_NAME, null,cv);
            db.setTransactionSuccessful();
        } catch (SQLException erro) {
            Toast.makeText(ctx, erro.getMessage(), Toast.LENGTH_SHORT).show();
            Log.i(TAG,erro.getMessage());
        }
        finally {
            db.endTransaction();
            db.close();
        }
        return resp;
    }

    public List<Contacto> getContactos(){
        List<Contacto>lista = new ArrayList<Contacto>();
        SQLiteDatabase db = getReadableDatabase();
        try {
            String sql =" SELECT * FROM " + TABLE_NAME + ";";
            Cursor cur = db.rawQuery(sql, null);
            if (cur.moveToFirst()){
                do{
                    lista.add(new Contacto(cur.getInt(0),cur.getString(1),cur.getString(2),cur.getBlob(3)));
                }while (cur.moveToNext());
            }

        } catch (SQLException erro){
            Toast.makeText(ctx, erro.getMessage(), Toast.LENGTH_SHORT).show();
        }finally {
            db.close();
        }
        return lista;
    }

    public long deleteContacto(int id){
        long rslt = 0;
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();
            rslt = db.delete(TABLE_NAME, ID + "=?",new String[]{String.valueOf(id)});
            db.setTransactionSuccessful();
        } catch (SQLException erro){
            Toast.makeText(ctx, erro.getMessage(), Toast.LENGTH_SHORT).show();
        }finally {
            db.endTransaction();
            db.close();
        }
        return  rslt;
    }

    public long updateContacto(Contacto ct){
        long rslt=0;
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put(NOME, ct.nome);
            cv.put(TELEFONE, ct.telefone);
            cv.put(FOTO, ct.foto);
            db.beginTransaction();
            rslt = db.update(TABLE_NAME, cv, ID + "=?", new  String[]{String.valueOf(ct.id)});
            db.setTransactionSuccessful();
        }catch (SQLException erro){
            Toast.makeText(ctx, erro.getMessage() , Toast.LENGTH_SHORT).show();
        }finally {
            db.endTransaction();
            db.close();
        }

        return rslt;
    }
}
