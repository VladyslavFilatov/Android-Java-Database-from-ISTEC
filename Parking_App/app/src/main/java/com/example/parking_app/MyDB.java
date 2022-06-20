package com.example.parking_app;

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

    public static final String TABLE_NAME = "parquementos";
    public static final String DATABASE_NAME = TABLE_NAME + ".db";
    public static final int VERSAO = 1;
    public static final String ID = "id";
    public static final String NOME = "nome";
    public static final String ENDRECO = "endreco";
    public static final String FOTO = "foto";
    public static final String TABLE2_NAME = "clientes";
    public static final String TELEFONE = "telefone";
    public static final String COD = "cod";
    public static final String NAME = "nome";
    public static final String FOTOS = "fotos";
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
                "    " + ENDRECO + " VARCHAR (120)," +
                "    " + FOTO + "     BLOB" +
                ");";
        db.execSQL(strsql);

        String strmysql = "CREATE TABLE " + TABLE2_NAME + " (\n" +// TABLE 2
                "    " + COD + "       INTEGER       PRIMARY KEY AUTOINCREMENT, " +
                "    " + NAME + "     VARCHAR (120)," +
                "    " + TELEFONE + " VARCHAR (120)," +
                "    " + FOTOS + "     BLOB" +
                ");";
        db.execSQL(strmysql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String strsql = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        String strmysql = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        db.execSQL(strmysql); // TABLE 2
        db.execSQL(strsql); // TABLE 2
        onCreate(db);
    }

    public long addParquemento(Parquemento prq){
        long resp = 0;
        SQLiteDatabase db = getWritableDatabase();

        try {
            ContentValues cv = new ContentValues();
            cv.put(ID,prq.id);
            cv.put(NOME,prq.nome);
            cv.put(ENDRECO,prq.endreco);
            cv.put(FOTO,prq.foto);
            db.beginTransaction();
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

    public List<Parquemento> getParquementos(){
        List<Parquemento>lista = new ArrayList<Parquemento>();
        SQLiteDatabase db = getWritableDatabase();
        try {
            String sql =" SELECT * FROM " + TABLE_NAME + ";";
            Cursor cur = db.rawQuery(sql, null);
            if ( cur.moveToFirst() ){
                do{
                    Parquemento novo = new Parquemento(cur.getInt(0),cur.getString(1),cur.getString(2),cur.getBlob(3));
                    lista.add(novo);
                }while (cur.moveToNext());

            }


        } catch (SQLException erro){
            Toast.makeText(ctx, erro.getMessage(), Toast.LENGTH_SHORT).show();
        }finally {
            db.close();
        }
        return lista;
    }

    public long deleteParquemento(int id){
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

    public long updateParquemento(Parquemento prq){
        long rslt=0;
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put(NOME, prq.nome);
            cv.put(ENDRECO, prq.endreco);
            cv.put(FOTO, prq.foto);
            db.beginTransaction();
            rslt = db.update(TABLE_NAME, cv, ID + "=?", new  String[]{String.valueOf(prq.id)});
            db.setTransactionSuccessful();
        }catch (SQLException erro){
            Toast.makeText(ctx, erro.getMessage() , Toast.LENGTH_SHORT).show();
        }finally {
            db.endTransaction();
            db.close();
        }

        return rslt;
    }

    // TABLE 2
    public long addCliente(Cliente cl){
        long resp = 0;
        SQLiteDatabase db = getWritableDatabase();

        try {
            ContentValues cv = new ContentValues();
            cv.put(COD, cl.cod);
            cv.put(NAME,cl.name);
            cv.put(TELEFONE,cl.telefone);
            cv.put(FOTOS,cl.fotos);
            db.beginTransaction();
            resp = db.insert(TABLE2_NAME, null,cv);
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

    public List<Cliente> getClientes(){
        List<Cliente>lista = new ArrayList<Cliente>();
        SQLiteDatabase db = getWritableDatabase();
        try {
            String sql =" SELECT * FROM " + TABLE2_NAME + ";";
            Cursor cur = db.rawQuery(sql, null);
            if ( cur.moveToFirst() ){
                do{
                    Cliente novo = new Cliente(cur.getInt(0),cur.getString(1),cur.getString(2),cur.getBlob(3));
                    lista.add(novo);
                }while (cur.moveToNext());

            }


        } catch (SQLException erro){
            Toast.makeText(ctx, erro.getMessage(), Toast.LENGTH_SHORT).show();
        }finally {
            db.close();
        }
        return lista;
    }

    public long deleteCliente(int cod){
        long rslt = 0;
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();
            rslt = db.delete(TABLE2_NAME, COD + "=?",new String[]{String.valueOf(cod)});
            db.setTransactionSuccessful();
        } catch (SQLException erro){
            Toast.makeText(ctx, erro.getMessage(), Toast.LENGTH_SHORT).show();
        }finally {
            db.endTransaction();
            db.close();
        }
        return  rslt;
    }

    public long updateCliente(Cliente cl){
        long rslt=0;
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put(NAME, cl.name);
            cv.put(TELEFONE, cl.telefone);
            cv.put(FOTOS, cl.fotos);
            db.beginTransaction();
            rslt = db.update(TABLE2_NAME, cv, COD + "=?", new  String[]{String.valueOf(cl.cod)});
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
