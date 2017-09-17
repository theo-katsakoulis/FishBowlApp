package com.example.theo.fishbowlapp.MySLQiteHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class MySQLiteHelperWords extends SQLiteOpenHelper {

    private static final String TABLE_WORDS = "words";  // words table word

    /* words Table Columns words */
    private static final String KEY_ID = "id";
    private static final String KEY_WORD = "word";
    private static final String KEY_WORD_STATE = "word_state";

    int flag = 1;

    private static final String[] COLUMNS = {KEY_ID, KEY_WORD, KEY_WORD_STATE};

    /* Database Version */
    private static final int DATABASE_VERSION = 1;

    /* Database Word */
    private static final String DATABASE_NAME = "FishBowlDB4";

    public MySQLiteHelperWords(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /* SQL statement to create words table */
        String CREATE_WORDS_TABLE = "CREATE TABLE words ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "word TEXT, " +
                "word_state TEXT )";

        /* create words table */
        db.execSQL(CREATE_WORDS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /* Drop older words table if existed */
        db.execSQL("DROP TABLE IF EXISTS words");

        /* create fresh words table */
        this.onCreate(db);
    }

    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }

    public void dropTable(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS words");
        onCreate(db);
    }


    public void addWord(Words word){
        //for logging
        Log.d("addWord", word.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_WORD, word.getWord()); // get word word
        values.put(KEY_WORD_STATE, word.getWordState());

        // 3. insert
        db.insert(TABLE_WORDS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column words/ values = column values

        // 4. close
        db.close();
    }

    public Words getWord(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_WORDS, // a. table
                        COLUMNS, // b. column words
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build word object
        Words word = new Words();
        word.setId(Integer.parseInt(cursor.getString(0)));
        word.setWord(cursor.getString(1));
        word.setWordState(cursor.getString(2));

        cursor.close();
        //log
        Log.d("getWord("+id+")", word.toString());

        // 5. return word
        return word;
    }

    public List<Words> getAllWords() {
        List<Words> words = new LinkedList<Words>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_WORDS;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build word and add it to list
        Words word = null;
        if (cursor.moveToFirst()) {
            do {
                word = new Words();
                word.setId(Integer.parseInt(cursor.getString(0)));
                word.setWord(cursor.getString(1));
                word.setWordState(cursor.getString(2));

                // Add word to words
                words.add(word);
            } while (cursor.moveToNext());
        }

        cursor.close();

        Log.d("getAllWords()", words.toString());

        // return words
        return words;
    }

    public int updateWord(Words word) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("word", word.getWord()); // get word
        values.put("word_state", word.getWordState());

        // 3. updating row
        int i = db.update(TABLE_WORDS, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(word.getId()) }); //selection args

        // 4. close
        db.close();
        return i;
    }

    /* returns the number of words that are in the table */
    public int getWordsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_WORDS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        Log.d("getAllWordCount()", String.valueOf(cnt));
        return cnt;
    }

    /* returns the number of words with the word_state 'mistaken' or 'found' */
    public int getWordsCountState() {
        String countQuery = "SELECT  * FROM " + TABLE_WORDS + " WHERE word_state = 'mistaken' OR word_state = 'found'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        Log.d("getAllWordsState()", String.valueOf(cnt));
        return cnt;
    }

    /* returns the number of words with word_state 'found' */
    public int getCountStateFound() {
        String countQuery = "SELECT  * FROM " + TABLE_WORDS + " WHERE word_state = 'found'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        Log.d("getAllCountStateFound()", String.valueOf(cnt));
        return cnt;
    }

    /* returns a random word from the table */
    public Words getRandomWord() {
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_WORDS + " ORDER BY RANDOM() LIMIT 1",
                        new String[] {"*"}, null, null, null, null, null);

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build word object
        Words word = new Words();
        word.setId(Integer.parseInt(cursor.getString(0)));
        word.setWord(cursor.getString(1));
        word.setWordState(cursor.getString(2));

        cursor.close();
        //log
        //Log.d("getWord("+id+")", word.toString());

        // 5. return word
        return word;
    }

    /* it changes the word_state of 'mistaken' words to 'none' */
    public void changeStateOfWordMistaken() {
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();

        values.put(KEY_WORD_STATE, "none");
        db.update(TABLE_WORDS, values, KEY_WORD_STATE + " = 'mistaken'", null);
        db.close();
    }

    /* it changes the word_state of 'found' words to 'none' */
    public void changeStateOfWordFound() {
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();

        values.put(KEY_WORD_STATE, "none");
        db.update(TABLE_WORDS, values, KEY_WORD_STATE + " = 'found'", null);
        db.close();
    }

    /*
    public void deleteWord(Words word) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_WORDS, //table word
                KEY_ID+" = ?",  // selections
                new String[] { String.valueOf(word.getId()) }); //selections args

        // 3. close
        db.close();

        //log
        Log.d("deleteWord", word.toString());
    }*/

}