package si.example.kamna.care;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Prabh on 5/18/2017.
 */

public class DBHelper extends SQLiteOpenHelper
{

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);


    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS doc (id INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT, pass TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS patient (id INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT, name TEXT, age INTEGER, number INTEGER, weight INTEGER, height INTEGER, diab INTEGER, bp INTEGER, probs TEXT, doc_id INTEGER);");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS prescription (id INTEGER PRIMARY KEY AUTOINCREMENT, prob TEXT, med TEXT, dos TEXT, t TIMESTAMP DEFAULT CURRENT_TIMESTAMP);");

    }

    public Cursor getAllPrescriptions()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        cursor = db.rawQuery("Select * from prescription;", null);

        return cursor;
    }

    public void insertPrescription(String prob, String med, String dos)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("prob", prob);
        contentValues.put("med", med);
        contentValues.put("dos", dos);
        contentValues.put("t", getDateTime());
        db.insert("prescription", null, contentValues);
        db.close();


    }
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void insertPatient(String name, int age, long number, int weight, int height,String email, int bp, int diab, String probs, int doc_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("age", age);
        contentValues.put("number", number);
        contentValues.put("weight", weight);
        contentValues.put("height", height);
        contentValues.put("email", email);
        contentValues.put("bp", bp);
        contentValues.put("diab", diab);
        contentValues.put("probs", probs);
        contentValues.put("doc_id", doc_id);
        db.insert("patient", null, contentValues);
        db.close();

    }

    public Cursor getPatient(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] cols = { "name", "age", "number", "weight", "height","email","bp","diab","probs" ,"doc_id"};
        String selection = "id=?";
        String[] args = {String.valueOf(id)};
        Cursor cursor = null;
        try {
            cursor = db.query("patient", cols, selection, args, null, null, null);
        }
        catch (Exception e)
        {
            Log.e("error",e.getMessage());
        }
        return cursor;
    }

    public Cursor getAllPatients()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        cursor = db.rawQuery("Select * from patient;", null);

        return cursor;
    }

    public void deletePatient(int id)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("patient", "id=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void insertDoc(String email, String pass)
    {
        Cursor cursor = getDoc(email);

        if(cursor.getCount() > 0) {
           //doc exists
        }
        else
        {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("email", email);
            contentValues.put("pass", pass);
            db.insert("doc", null, contentValues);
            db.close();
        }
    }

    public Cursor getDoc(String email)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] cols = {"id", "email", "pass"};
        String selection = "email = ?";
        String[] args = {email};
        Cursor cursor = null;
        try {
            cursor = db.query("doc", cols, selection, args, null, null, null);
        }
        catch (Exception e)
        {
            Log.e("error",e.getMessage());
        }
        //cursor.close();

        return  cursor;
    }
}
