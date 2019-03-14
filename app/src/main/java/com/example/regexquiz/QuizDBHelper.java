package com.example.regexquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuizDBHelper extends SQLiteOpenHelper {
    // db parameters:
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "dbquiz";
    private static final String TABLE_NAME = "quiz";
    private static final String COL_ID = "quiz_id";
    private static final String COL_QUESTION = "regex_text";
    private static final String COL_SOLVED = "solved";
    private static final List<String> REGEX_QUESTIONS = Arrays.asList(
        "[a-z]+",
        "[0-9]{1,5}"
    );

    private SQLiteDatabase dbase;

    public QuizDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        dbase = db;
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( "
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_QUESTION + " TEXT, "
                + COL_SOLVED + " INTEGER DEFAULT 0"
                + ")";
        db.execSQL(sql);
        populateDB();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        resetDB();
    }

    private void populateDB() {
        for (String q: REGEX_QUESTIONS) {
            this.addRegexQuestion(new RegexQuestion(q));
        }
    }

    private void addRegexQuestion(RegexQuestion q) {
        ContentValues values = new ContentValues();
        values.put(COL_QUESTION, q.getREGEXTEXT());
        dbase.insert(TABLE_NAME, null, values);
    }

    public List<RegexQuestion> getAllRegexQuestions() {
        List<RegexQuestion> regexlist = new ArrayList<RegexQuestion>();

        dbase = this.getReadableDatabase();
        Cursor cursor = dbase.rawQuery("select * from " + TABLE_NAME, null);

        while (cursor.moveToNext()) {
            RegexQuestion q = new RegexQuestion();
            q.setID(cursor.getInt(0));
            q.setREGEXTEXT(cursor.getString(1));
            q.setSOLVED(cursor.getInt(2));
            regexlist.add(q);
        }
        return regexlist;
    }

    public void setQuestionSolved(int question_id) {
        ContentValues values = new ContentValues();
        values.put(COL_SOLVED, 1);
        String[] args = new String[]{"" + question_id};
        dbase.update(TABLE_NAME,values, COL_ID+"=?",args);
    }

    public void resetDB() {
        Log.i("DB", "reset DB");
        dbase = this.getWritableDatabase();
        dbase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(dbase);
    }
}