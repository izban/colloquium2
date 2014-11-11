package ru.ifmo.md.colloquium2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by izban on 11.11.14.
 */
public class ElectionHelper extends SQLiteOpenHelper {
    public final static int DATABASE_VERSION = 1;
    public final static String DATABASE_NAME = "elections.db";
    public final static String TABLE_NAME = "elections";
    public final static String COLUMN_ID = "_id";
    public final static String COLUMN_NAME = "name";
    public final static String COLUMN_COUNT = "count";

    public ElectionHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID +
                " INTEGER PRIMARY KEY, " + COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_COUNT + " INTEGER NOT NULL)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old, int _new) {
        if (DATABASE_VERSION < _new) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

    public ArrayList<Candidate> getAllCandidates() {
        ArrayList<Candidate> candidateList = new ArrayList<Candidate>();
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Candidate candidate = new Candidate();
                candidate.id = (Integer.parseInt(cursor.getString(0)));
                candidate.name = (cursor.getString(1));
                candidate.count = (Integer.parseInt(cursor.getString(2)));
                candidateList.add(candidate);
            } while (cursor.moveToNext());
        }
        return candidateList;
    }

    public void addCandidate(Candidate candidate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID, candidate.id);
        cv.put(COLUMN_NAME, candidate.name);
        cv.put(COLUMN_COUNT, candidate.count);
        Log.i("", candidate.toString());
        db.insert(TABLE_NAME, null, cv);
        db.close();
    }

    public Candidate getCandidate(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[] { COLUMN_ID, COLUMN_NAME, COLUMN_COUNT},
                COLUMN_ID + "=?", new String[] { String.valueOf(id) }, null, null, null);
        if(cursor != null)
            cursor.moveToFirst();
        Candidate candidate = new Candidate(Integer.parseInt(cursor.getString(0)), cursor.getString(1), Integer.parseInt(cursor.getString(2)));
        return candidate;
    }

    public int updateCandidate(Candidate candidate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID, candidate.id);
        cv.put(COLUMN_NAME, candidate.name);
        cv.put(COLUMN_COUNT, candidate.count);
        Log.i("", candidate.toString());
        Log.i("", cv.toString());
        return db.update(TABLE_NAME, cv, COLUMN_ID + "=?", new String[] { String.valueOf(candidate.id) });
    }

    public int deleteCandidate(Candidate candidate) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[] { String.valueOf(candidate.id) });
    }

    public int getCandidateCount() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int res = cursor.getCount();
        cursor.close();
        return res;
    }

    public Cursor getAllCursor() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, null, null, null, null, null, null);
    }

    public void clear() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.close();
    }
}
